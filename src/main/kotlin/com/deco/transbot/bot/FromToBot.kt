package com.deco.transbot.bot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot

@Component
class FromToBot : TelegramLongPollingBot() {
  @Autowired
  lateinit var updateHandler: UpdateHandler


  override fun getBotToken(): String {
    return "412051876:AAGBXCv5r96ZqRFa9Q9BGG0DdZ1SN-2qroE"
  }

  override fun getBotUsername(): String {
    return "@fromto_bot"
  }

  override fun onUpdateReceived(update: Update) {
    this.updateHandler.handle(this, update)
  }
}
