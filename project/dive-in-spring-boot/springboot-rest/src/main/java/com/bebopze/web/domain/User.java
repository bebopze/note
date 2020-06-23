package com.bebopze.web.domain;

/**
 *  用户模型
 *
 * @author bebopze
 * @since 2018/5/27
 */
public class User {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
