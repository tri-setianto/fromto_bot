/*
 * This file is generated by jOOQ.
*/
package com.deco.transbot.jooq;


import com.deco.transbot.jooq.tables.ConfigUser;
import com.deco.transbot.jooq.tables.User;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in transbot
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>transbot.config_user</code>.
     */
    public static final ConfigUser CONFIG_USER = com.deco.transbot.jooq.tables.ConfigUser.CONFIG_USER;

    /**
     * The table <code>transbot.user</code>.
     */
    public static final User USER = com.deco.transbot.jooq.tables.User.USER;
}
