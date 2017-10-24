package com.deco.transbot.bot

import com.deco.transbot.jooq.tables.pojos.ConfigUser
import com.deco.transbot.models.LanguageDao
import com.deco.transbot.models.UserConfigDao
import com.deco.transbot.models.UserManager
import com.deco.transbot.translator.Translator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.AbsSender
import org.telegram.telegrambots.exceptions.TelegramApiException

@Component
class TransBotUpdateHandler @Autowired
constructor(val userDao: UserManager,
            val userConfigDao: UserConfigDao,
            val languageDao: LanguageDao
) : UpdateHandler {

  override fun handle(sender: AbsSender, update: Update) {
    //println(update)
    if (update.hasMessage() && update.message.hasText()) {
      println("###############################")
      println(update.message.from?.userName)
      println(update.message.text)
      println(update.message.isReply)
      println(update.message.chat.isUserChat)
      val userName = update.message.from.userName
      val message = SendMessage()
        .setChatId(update.message.chatId)

      if (userName != null) {
        this.createUserConfigIfNotAvailable(userName)
        if (isCommand(update.message.text)) {
          this.handleCommand(sender, update.message)
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
    }
  }

  private fun isCommand(text: String): Boolean {
    return text[0] == '/'
  }

  private fun handleCommand(sender: AbsSender, message: Message) {
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
      "/switch", "/switch@traslateBot" -> {
        val configUser = this.userConfigDao.getById(message.from.userName)
        this.userConfigDao.updateConfig(ConfigUser(
          message.from.userName, configUser.langTarget, configUser.langSource
        ))
        send("switch to ${configUser.langTarget} - ${configUser.langSource}")
      }
      "/translate", "/translate@traslateBot" -> {
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
      "/set", "/set@traslateBot" -> {
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
      "/me", "/me@traslateBot" -> {
        val config = userConfigDao.getById(userName)
        val langSource = languageDao.fetchOneById(config.langSource)
        val langTarget = languageDao.fetchOneById(config.langTarget)
        send("""
            useername : $userName
            langSource : ${langSource.id} (${langSource.name})
            langTarget : ${langTarget.id} (${langTarget.name})
        """.trimIndent())
      }
      "/help", "/help@traslateBot" -> {
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
    }
  }
}
