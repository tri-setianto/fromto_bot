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
  lateinit var updateHandler: UpdateHandler

  override fun onUpdateReceived(update: Update) {
    updateHandler.handle(this, update)
  }

  override fun getBotToken(): String {
    return "460194313:AAFINrEtTphZB2YQwgbnI0RsWJq1Ko1DkTw"
  }

  override fun getBotUsername(): String {
    return "traslateBot"
  }
}
