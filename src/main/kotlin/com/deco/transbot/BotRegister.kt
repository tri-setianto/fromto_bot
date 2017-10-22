package com.deco.transbot

import com.deco.transbot.bot.TransBot
import com.deco.transbot.bot.TransBotWebHook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.exceptions.TelegramApiException

@Component
class BotRegister : CommandLineRunner {

  @Autowired
  private lateinit var transbot: TransBot
  @Autowired
  private lateinit var transbot2: TransBotWebHook

  override fun run(vararg args: String?) {

    val botApi = TelegramBotsApi()

    try {
      botApi.registerBot(transbot)
    } catch (e: TelegramApiException) {
      e.printStackTrace()
    }
  }

  init {
    ApiContextInitializer.init()
  }

}
