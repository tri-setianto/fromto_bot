package com.deco.transbot.bot

import com.deco.transbot.jooq.tables.pojos.ConfigUser
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
constructor(val userDao: UserManager, val userConfigDao: UserConfigDao)
  : UpdateHandler {

  override fun handle(sender: AbsSender, update: Update) {
    println(update)
    if (update.hasMessage() && update.message.hasText()) {
      val userName = update.message.from.userName
      val message = SendMessage()
        .setChatId(update.message.chatId)

      if (isCommand(update.message.text)) {
        message.text = this.handleCommand(update.message)
      } else {
        if (userName != null) {
          message.text = this.translate(userName, update.message.text)
        } else {
          message.text = "create username"
        }
      }

//      var aut = "not user"
//      val autUser = userDao.findById(userName)
//      if (autUser != "") {
//        aut = "user"
//      }

      try {
        sender.sendMessage(message) // Call method to send the message
      } catch (e: TelegramApiException) {
        println("yoi")
        e.printStackTrace()
      }

    }
  }

  private fun isCommand(text: String): Boolean {
    return text[0] == '/'
  }

  private fun handleCommand(message: Message): String {
    val arrText = message.text.split(Regex("\\s+"))
    val command = arrText[0]
    return when (command) {
      "/switch", "/switch@traslateBot" -> {
        val configUser = this.userConfigDao.getById(message.from.userName)
        this.userConfigDao.updateConfig(ConfigUser(
          message.from.userName, configUser.langTarget, configUser.langSource
        ))
        "switch to ${configUser.langTarget} - ${configUser.langSource}"
      }
      else -> "command tidak tersedia"
    }
  }

  private fun handleNonCommand() {

  }

  private fun sendMessage(sender: AbsSender, text: String) {

  }

  private fun translate(userName: String, text: String): String {
    val translator = Translator()
    val langSource: String
    val langTarget: String
    val userConfig = userConfigDao.getById(userName)
    if (userConfig.username != null) {
      langSource = userConfig.langSource ?: "en"
      langTarget = userConfig.langTarget ?: "id"
    } else {
      userConfigDao.createConfig(ConfigUser(userName, "en", "id"))
      val newUserConfig = userConfigDao.getById(userName)
      langSource = newUserConfig.langSource ?: "en"
      langTarget = newUserConfig.langTarget ?: "id"
    }
    return translator.callUrlAndParseResult(
      langSource, langTarget, text)
  }
}
