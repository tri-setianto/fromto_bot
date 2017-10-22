package com.deco.transbot.models

import com.deco.transbot.jooq.tables.pojos.ConfigUser

interface UserConfigDao {
  fun getById(userName: String): ConfigUser
  fun createConfig(config: ConfigUser)
  fun updateConfig(config: ConfigUser)
}
