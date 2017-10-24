package com.deco.transbot.bot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot

@Component
class Tl1Bot : TelegramLongPollingBot() {

  @Autowired
  lateinit var updateHandler: UpdateHandler


  override fun getBotToken(): String {
    return "474977309:AAFySOwuvDHVtpll5LxJbsfc1tZHDtbdv6w"
  }

  override fun getBotUsername(): String {
    return "@tl1bot"
  }

  override fun onUpdateReceived(update: Update) {
    this.updateHandler.handle(this, update)
  }
}
