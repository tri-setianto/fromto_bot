package com.deco.transbot.bot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot

@Component
class FromToBot : TelegramLongPollingBot() {
  @Autowired
  lateinit var updateHandler: UpdateHandler
  @Autowired
  private lateinit var env: Environment


  override fun getBotToken(): String {
    return env.getProperty("fromtobot.bot.token")
  }

  override fun getBotUsername(): String {
    return env.getProperty("fromtobot.bot.token")
  }

  override fun onUpdateReceived(update: Update) {
    this.updateHandler.handle(this, update)
  }
}
