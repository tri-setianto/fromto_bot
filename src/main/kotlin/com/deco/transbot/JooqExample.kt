package com.deco.transbot

import com.deco.transbot.jooq.tables.User
import com.deco.transbot.models.JooqUserManager

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.boot.CommandLineRunner
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class JooqExamples(private val dsl: DSLContext, private val jdbc: JdbcTemplate)
    : CommandLineRunner {

  @Autowired
  private lateinit var user: JooqUserManager

  @Throws(Exception::class)
  override fun run(vararg args: String) {
    jooqFetch()
    jooqSql()
  }

  public fun jooqFetch() {
    val results = this.dsl.select().from(User.USER).fetch()
    results.forEach {
      val username = it.getValue(User.USER.USERNAME)
      val name = it.getValue(User.USER.NAME)
      println("Jooq Fetch $username $name")
    }
  }

  private fun jooqSql() {
    println(user.findById("string"))
  }
}
