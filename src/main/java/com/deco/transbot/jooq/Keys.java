/*
 * This file is generated by jOOQ.
*/
package com.deco.transbot.jooq;


import com.deco.transbot.jooq.tables.ConfigUser;
import com.deco.transbot.jooq.tables.Laguage;
import com.deco.transbot.jooq.tables.records.ConfigUserRecord;
import com.deco.transbot.jooq.tables.records.LaguageRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>transbot</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ConfigUserRecord> KEY_CONFIG_USER_CONFIG_USER_USERNAME_UINDEX = UniqueKeys0.KEY_CONFIG_USER_CONFIG_USER_USERNAME_UINDEX;
    public static final UniqueKey<LaguageRecord> KEY_LAGUAGE_PRIMARY = UniqueKeys0.KEY_LAGUAGE_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<ConfigUserRecord, LaguageRecord> CONFIG_USER_LAGUAGE_ID_FK = ForeignKeys0.CONFIG_USER_LAGUAGE_ID_FK;
    public static final ForeignKey<ConfigUserRecord, LaguageRecord> CONFIG_USER_SOURCE_LAGUAGE_ID_FK = ForeignKeys0.CONFIG_USER_SOURCE_LAGUAGE_ID_FK;
    public static final ForeignKey<ConfigUserRecord, LaguageRecord> CONFIG_USER_TARGET_LAGUAGE_ID_FK = ForeignKeys0.CONFIG_USER_TARGET_LAGUAGE_ID_FK;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<ConfigUserRecord> KEY_CONFIG_USER_CONFIG_USER_USERNAME_UINDEX = createUniqueKey(ConfigUser.CONFIG_USER, "KEY_config_user_config_user_username_uindex", ConfigUser.CONFIG_USER.USERNAME);
        public static final UniqueKey<LaguageRecord> KEY_LAGUAGE_PRIMARY = createUniqueKey(Laguage.LAGUAGE, "KEY_laguage_PRIMARY", Laguage.LAGUAGE.ID);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<ConfigUserRecord, LaguageRecord> CONFIG_USER_LAGUAGE_ID_FK = createForeignKey(com.deco.transbot.jooq.Keys.KEY_LAGUAGE_PRIMARY, ConfigUser.CONFIG_USER, "config_user_laguage_id_fk", ConfigUser.CONFIG_USER.LANG_SOURCE);
        public static final ForeignKey<ConfigUserRecord, LaguageRecord> CONFIG_USER_SOURCE_LAGUAGE_ID_FK = createForeignKey(com.deco.transbot.jooq.Keys.KEY_LAGUAGE_PRIMARY, ConfigUser.CONFIG_USER, "config_user_source_laguage_id_fk", ConfigUser.CONFIG_USER.LANG_SOURCE);
        public static final ForeignKey<ConfigUserRecord, LaguageRecord> CONFIG_USER_TARGET_LAGUAGE_ID_FK = createForeignKey(com.deco.transbot.jooq.Keys.KEY_LAGUAGE_PRIMARY, ConfigUser.CONFIG_USER, "config_user_target_laguage_id_fk", ConfigUser.CONFIG_USER.LANG_TARGET);
    }
}
