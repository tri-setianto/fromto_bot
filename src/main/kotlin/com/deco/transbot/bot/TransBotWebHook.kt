package com.deco.transbot.bot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.methods.BotApiMethod
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramWebhookBot

@Component
class TransBotWebHook : TelegramWebhookBot() {

  @Autowired
  lateinit var updateHandler: UpdateHandler

  override fun getBotPath(): String {
    return "traslateBot"
  }

  override fun getBotToken(): String {
    return "460194313:AAFINrEtTphZB2YQwgbnI0RsWJq1Ko1DkTw"
  }

  override fun onWebhookUpdateReceived(update: Update): BotApiMethod<*>? {
    updateHandler.handle(this, update)
    return null
  }

  override fun getBotUsername(): String {
    return "traslateBot"
  }
}
