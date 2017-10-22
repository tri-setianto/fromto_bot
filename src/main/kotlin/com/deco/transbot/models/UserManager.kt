package com.deco.transbot.models

import com.deco.transbot.jooq.tables.User

interface UserManager {
  fun findById(id: String): String
}
