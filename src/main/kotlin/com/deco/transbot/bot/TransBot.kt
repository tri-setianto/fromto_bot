package com.deco.transbot.bot

import com.deco.transbot.models.JooqUserManager
import com.deco.transbot.models.UserConfigDao
import com.deco.transbot.translator.Translator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.exceptions.TelegramApiException
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message

@Component
class TransBot : TelegramLongPollingBot() {

  @Autowired
  lateinit var userDao: JooqUserManager
  @Autowired
  lateinit var userConfigDao: UserConfigDao

  override fun onUpdateReceived(update: Update) {
    if (update.hasMessage() && update.message.hasText()) {
      val translator = Translator()
      val userName = update.message.from.userName ?: ""
      val userConfig = userConfigDao.getById(userName)
      val langSorce = userConfig.langSource ?: "en"
      val langTarget = userConfig.langTarget ?: "id"
      val word = translator.callUrlAndParseResult(
        langSorce, langTarget, update.message.text)
      var aut = "not user"
      val autUser = userDao.findById(userName)
      if (autUser != "") {
        aut = "user"
      }
      val message = SendMessage()
        .setChatId(update.message.chatId)
        .setText("$aut $word")
      try {
        this.sendMessage(message) // Call method to send the message
      } catch (e: TelegramApiException) {
        println("yoi")
        e.printStackTrace()
      }

    }
  }

  override fun getBotToken(): String {
    return "460194313:AAFINrEtTphZB2YQwgbnI0RsWJq1Ko1DkTw"
  }

  override fun getBotUsername(): String {
    return "traslateBot"
  }

  private fun hanleMessage(message: Message) {
  }

}
