/*
 * This file is generated by jOOQ.
*/
package com.deco.transbot.jooq.tables;


import com.deco.transbot.jooq.Keys;
import com.deco.transbot.jooq.Transbot;
import com.deco.transbot.jooq.tables.records.ConfigUserRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigUser extends TableImpl<ConfigUserRecord> {

    private static final long serialVersionUID = 501762255;

    /**
     * The reference instance of <code>transbot.config_user</code>
     */
    public static final ConfigUser CONFIG_USER = new ConfigUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConfigUserRecord> getRecordType() {
        return ConfigUserRecord.class;
    }

    /**
     * The column <code>transbot.config_user.username</code>.
     */
    public final TableField<ConfigUserRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR.length(60), this, "");

    /**
     * The column <code>transbot.config_user.lang_source</code>.
     */
    public final TableField<ConfigUserRecord, String> LANG_SOURCE = createField("lang_source", org.jooq.impl.SQLDataType.VARCHAR.length(6).defaultValue(org.jooq.impl.DSL.inline("en", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>transbot.config_user.lang_target</code>.
     */
    public final TableField<ConfigUserRecord, String> LANG_TARGET = createField("lang_target", org.jooq.impl.SQLDataType.VARCHAR.length(6).defaultValue(org.jooq.impl.DSL.inline("id", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * Create a <code>transbot.config_user</code> table reference
     */
    public ConfigUser() {
        this("config_user", null);
    }

    /**
     * Create an aliased <code>transbot.config_user</code> table reference
     */
    public ConfigUser(String alias) {
        this(alias, CONFIG_USER);
    }

    private ConfigUser(String alias, Table<ConfigUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private ConfigUser(String alias, Table<ConfigUserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Transbot.TRANSBOT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ConfigUserRecord>> getKeys() {
        return Arrays.<UniqueKey<ConfigUserRecord>>asList(Keys.KEY_CONFIG_USER_CONFIG_USER_USERNAME_UINDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ConfigUserRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ConfigUserRecord, ?>>asList(Keys.CONFIG_USER_LAGUAGE_ID_FK, Keys.CONFIG_USER_SOURCE_LAGUAGE_ID_FK, Keys.CONFIG_USER_TARGET_LAGUAGE_ID_FK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigUser as(String alias) {
        return new ConfigUser(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ConfigUser rename(String name) {
        return new ConfigUser(name, null);
    }
}
