package com.deco.transbot

import com.deco.transbot.bot.FromToBot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.exceptions.TelegramApiException

@SpringBootApplication
class TransbotApplication : CommandLineRunner{
  @Autowired
  private lateinit var fromToBot: FromToBot

  override fun run(vararg args: String?) {

    val botApi = TelegramBotsApi()

    try {
      botApi.registerBot(fromToBot)
    } catch (e: TelegramApiException) {
      e.printStackTrace()
    }

  }

  init {
    ApiContextInitializer.init()
  }
}

fun main(args: Array<String>) {
  SpringApplication.run(TransbotApplication::class.java, *args)
}
