package com.deco.transbot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TransbotApplication

fun main(args: Array<String>) {
  SpringApplication.run(TransbotApplication::class.java, *args)
}
