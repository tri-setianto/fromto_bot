/*
 * This file is generated by jOOQ.
*/
package com.deco.transbot.jooq.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


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
public class Laguage implements Serializable {

    private static final long serialVersionUID = -984735491;

    private String id;
    private String name;

    public Laguage() {}

    public Laguage(Laguage value) {
        this.id = value.id;
        this.name = value.name;
    }

    public Laguage(
        String id,
        String name
    ) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Laguage (");

        sb.append(id);
        sb.append(", ").append(name);

        sb.append(")");
        return sb.toString();
    }
}