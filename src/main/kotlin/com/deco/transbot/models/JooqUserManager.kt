package com.deco.transbot.models

import com.deco.transbot.jooq.tables.User
import org.jooq.DSLContext
import org.jooq.Record2
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class JooqUserManager @Autowired
constructor(val jooq: DSLContext) : UserManager {

  override fun findById(id: String): String {
    val result: Result<Record2<String, String>>? = this.jooq
      .select(User.USER.USERNAME, User.USER.NAME)
      .from(User.USER)
      .where(User.USER.USERNAME.eq(id))
      .fetch()
    result?.forEach {
      return it.value1()
    }
    return ""
  }
}
