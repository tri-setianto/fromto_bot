package com.deco.transbot.bot

import com.deco.transbot.jooq.tables.pojos.ConfigUser
import com.deco.transbot.models.LanguageDao
import com.deco.transbot.models.UserConfigDao
import com.deco.transbot.models.UserManager
import com.deco.transbot.translator.Translator
import com.sun.org.apache.xml.internal.serializer.utils.Utils.messages
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.methods.AnswerInlineQuery
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle
import org.telegram.telegrambots.bots.AbsSender
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.exceptions.TelegramApiException

@Component
class TransBotUpdateHandler @Autowired
constructor(val userDao: UserManager,
            val userConfigDao: UserConfigDao,
            val languageDao: LanguageDao
) : UpdateHandler {
  override fun handle(sender: TelegramLongPollingBot, update: Update) {
    //println(update)
    if (update.hasMessage() && update.message.hasText()) {
      println("###############################")
      println(update.message.from?.userName)
      println(update.message.text)
      println("is reply : " + update.message.isReply)
      println("is private : " + update.message.chat.isUserChat)
      println("text : " + update.message)
      val userName = update.message.from.userName
      val message = SendMessage()
        .setChatId(update.message.chatId)

      if (userName != null) {
        this.createUserConfigIfNotAvailable(userName)
        if (isCommand(update.message.text)) {
          this.handleCommand(sender, sender.botUsername ,update.message)
        } else {
          this.handleNonCommand(sender, update.message)
        }
      } else {
        if (update.message.chat.isUserChat) {
          message.text = "create username"
          try {
            sender.sendMessage(message) // Call method to send the message
          } catch (e: TelegramApiException) {
            println("yoi")
            e.printStackTrace()
          }
        }
      }
    } else if (update.hasInlineQuery()) {
      if (update.inlineQuery.from.userName != null) {
        this.createUserConfigIfNotAvailable(update.inlineQuery.from.userName)
        println(update)
        this.handleInlineQuery(sender, update.inlineQuery)
      }
    }
  }

  override fun handle(sender: TelegramWebhookBot, update: Update) {

  }

  private fun isCommand(text: String): Boolean {
    return text[0] == '/'
  }

  private fun handleCommand(sender: AbsSender, botUsername: String,
                            message: Message) {
    val arrText = message.text.split(Regex("\\s+|\\n"))
    val command = arrText[0]
    val userName = message.from.userName

    fun send(text: String) {
      try {
        val sendMessage = SendMessage()
          .setChatId(message.chatId)
          .setReplyToMessageId(message.messageId)
          .setText(text)
        sender.execute(sendMessage)
      } catch (e: TelegramApiException) {
        e.printStackTrace()
      }
    }

    when (command) {
      "/switch", "/switch@$botUsername" -> {
        val configUser = this.userConfigDao.getById(message.from.userName)
        this.userConfigDao.updateConfig(ConfigUser(
          message.from.userName, configUser.langTarget, configUser.langSource
        ))
        send("switch to ${configUser.langTarget} - ${configUser.langSource}")
      }
      "/translate", "/translate@$botUsername" -> {
        if (message.isReply) {
          when {
              arrText.size == 1 -> send(this.translate(userName,
                message.replyToMessage.text))
              arrText.size == 2 -> {
                send(this.translate(message.replyToMessage.text, arrText[1],
                  userConfigDao.getById(userName).langTarget))
              }
              arrText.size >= 3 -> send(this.translate(
                message.replyToMessage.text, arrText[1], arrText[2]))
          }
        } else {
          send("cannot read reply")
        }
      }
      "/set", "/set@$botUsername" -> {
        if (arrText.size == 3) {
          when {
              languageDao.fetchOneById(arrText[1]).id == null -> send(
                "id bahasa ${arrText[1]} tidak di dukung")
              languageDao.fetchOneById(arrText[2]).id == null -> send(
                "id bahasa ${arrText[1]} tidak di dukung")
              else -> {
                userConfigDao.updateConfig(ConfigUser(message.from.userName,
                  arrText[1], arrText[2]))
                val sourceName = languageDao.fetchOneById(arrText[1]).name
                val targetName = languageDao.fetchOneById(arrText[2]).name
                send("set bahasa ke $sourceName -> $targetName")
              }
          }
        } else {
          send("format penulisan salah, gunakan format penulisan dibawah ini:" +
            "\n\n/set id_bahasa_sumber id_bahasa_target\n\n" +
            "contoh: /set en id")
        }
      }
      "/me", "/me@$botUsername" -> {
        val config = userConfigDao.getById(userName)
        val langSource = languageDao.fetchOneById(config.langSource)
        val langTarget = languageDao.fetchOneById(config.langTarget)
        send("""
            useername : $userName
            langSource : ${langSource.id} (${langSource.name})
            langTarget : ${langTarget.id} (${langTarget.name})
        """.trimIndent())
      }
      "/help", "/help@$botUsername" -> {
        send("""
              Available Command
              /switch - tukar bahasa
              /translate - terjemahkan pesan yang di reply
                - replay pesan yang akan di terjemahkan dan gunakan comman dibawah ini
                - /translate
                - atau gunakan inline setting: /translate id en
              /set - atur bahasa yang digunakan
                - contoh: /set id en
              /me - tampilkan info pengguna
              /help - tampilkan pesan ini
          """.trimIndent()
        )
      }
      else -> if (message.chat.isUserChat) { send("command tidak tersedia") }
    }
  }


  private fun handleNonCommand(sender: AbsSender, message: Message) {
    fun send(text: String) {
      try {
        val sendMessage = SendMessage()
          .setChatId(message.chatId)
          .setReplyToMessageId(message.messageId)
          .setText(text)
        sender.execute(sendMessage)
      } catch (e: TelegramApiException) {
        e.printStackTrace()
      }
    }
    if (message.chat.isUserChat) {
      send(this.translate(message.from.userName, message.text))
    }
  }


  private fun handleInlineQuery(sender: AbsSender, query: InlineQuery) {
    val userName = query.from.userName
    val config = userConfigDao.getById(userName)

    val article = InlineQueryResultArticle()
    article.id = "1"
    article.title = "Traslate"
    article.description = "from ${config.langSource} to ${config.langTarget}"

    val text = InputTextMessageContent()
      .disableWebPagePreview()
    text.messageText = translate(userName, query.query)
    article.inputMessageContent = text
    val tes = AnswerInlineQuery()
      .setInlineQueryId(query.id)
      .setResults(article)
    sender.execute(tes)
  }


  private fun sendText(sender: AbsSender, text: String) {

  }


  private fun translate(userName: String, text: String): String {
    val userConfig = userConfigDao.getById(userName)
    val langSource = userConfig.langSource ?: "en"
    val langTarget = userConfig.langTarget ?: "id"
    return this.translate(text, langSource, langTarget)
  }

  private fun translate(text: String, source: String, target: String): String {
    val translator = Translator()
    return translator.callUrlAndParseResult(source, target, text)
  }


  private fun createUserConfigIfNotAvailable(userName: String) {
    val config = this.userConfigDao.getById(userName)
    if (config.username == null) {
      this.userConfigDao.createConfig(ConfigUser(userName, "en", "id"))
      this.userConfigDao.createConfig(ConfigUser())
    }
  }
}
