package com.deco.transbot.models

import com.deco.transbot.jooq.tables.Laguage.LAGUAGE
import com.deco.transbot.jooq.tables.pojos.Laguage
import org.jooq.DSLContext
import org.jooq.Record2
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class JooqLanguageDao @Autowired
constructor(val jooq: DSLContext): LanguageDao{
  override fun fetchOneById(id: String): Laguage {
    val result: Result<Record2<String, String>> = this.jooq
      .select(LAGUAGE.ID, LAGUAGE.NAME)
      .from(LAGUAGE)
      .where(LAGUAGE.ID.eq(id))
      .fetch()
    result.forEach {
      return Laguage(it.value1(), it.value2())
    }
    return Laguage()
  }
}
