package com.deco.transbot.bot

import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.AbsSender

interface UpdateHandler {
  fun handle(sender: AbsSender, update: Update)
}
