package com.deco.transbot

import com.deco.transbot.bot.TransBot
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.exceptions.TelegramApiException

@SpringBootApplication
class TransbotApplication

fun main(args: Array<String>) {
  SpringApplication.run(TransbotApplication::class.java, *args)
}
