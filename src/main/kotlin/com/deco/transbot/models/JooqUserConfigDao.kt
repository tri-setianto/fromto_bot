package com.deco.transbot.models

import com.deco.transbot.jooq.tables.pojos.ConfigUser
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import com.deco.transbot.jooq.tables.ConfigUser.CONFIG_USER
import org.jooq.Record3
import org.jooq.Result

@Component
@Transactional
class JooqUserConfigDao @Autowired
constructor(val jooq: DSLContext): UserConfigDao {
  override fun getById(userName: String): ConfigUser {
    val result: Result<Record3<String, String, String>> = this.jooq
      .select(CONFIG_USER.USERNAME,
        CONFIG_USER.LANG_SOURCE,
        CONFIG_USER.LANG_TARGET)
      .from(CONFIG_USER)
      .where(CONFIG_USER.USERNAME.eq(userName))
      .fetch()
    result.forEach {
      return ConfigUser(it.value1(), it.value2(), it.value3())
    }
    return ConfigUser()
  }

  override fun createConfig(config: ConfigUser) {
    this.jooq.insertInto(CONFIG_USER,
        CONFIG_USER.USERNAME, CONFIG_USER.LANG_SOURCE, CONFIG_USER.LANG_TARGET)
      .values(config.username, config.langSource, config.langTarget)
      .execute()
  }

  override fun updateConfig(userName: String, langSource: String,
                            langTarget: String) {
    this.jooq.update(CONFIG_USER)
      .set(CONFIG_USER.LANG_SOURCE, langSource)
      .set(CONFIG_USER.LANG_TARGET, langTarget)
      .where(CONFIG_USER.USERNAME.eq(userName))
      .execute()
  }

}
