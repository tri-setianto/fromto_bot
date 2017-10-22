package com.deco.transbot.bot

import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot

class DoNothingBot : TelegramLongPollingBot() {
  override fun getBotToken(): String {
    return "460194313:AAFINrEtTphZB2YQwgbnI0RsWJq1Ko1DkTw"
  }

  override fun getBotUsername(): String {
    return "traslateBot"
  }

  override fun onUpdateReceived(update: Update?) {
    val message = SendMessage().setText("do nothing")
    sendMessage(message)
  }
}
