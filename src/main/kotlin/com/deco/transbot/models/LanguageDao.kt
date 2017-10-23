package com.deco.transbot.models

import com.deco.transbot.jooq.tables.pojos.Laguage

interface LanguageDao {
  fun fetchOneById(id: String): Laguage
}
