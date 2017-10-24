package com.deco.transbot.bot

import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.AbsSender
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.bots.TelegramWebhookBot

interface UpdateHandler {
  fun handle(sender: TelegramLongPollingBot, update: Update)
  fun handle(sender: TelegramWebhookBot, update: Update)
}
