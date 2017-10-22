package com.deco.transbot

import com.deco.transbot.jooq.transbot.tables.pojos.ConfigUser
import com.deco.transbot.models.UserConfigDao
import org.jooq.exception.DataAccessException
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class TransbotApplicationTests() {

  @Autowired
  private lateinit var userConfigDao : UserConfigDao

  @Test
	fun contextLoads() {
    try {
      if (userConfigDao.getById("Tri").username != null) {
        userConfigDao.updateConfig("Tri", "id", "en")
      } else {
        userConfigDao.createConfig(ConfigUser("Tri", "en", "id"))
      }
    } catch (e: DataAccessException) {
      e.printStackTrace()
    }
	}

}
