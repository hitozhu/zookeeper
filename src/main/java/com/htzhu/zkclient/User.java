package com.htzhu.zkclient;

import java.io.Serializable;

/**
 * Created by htzhu on 2017/8/26.
 */
public class User implements Serializable{

    private static final long serialVersionUID = -2664618063142924426L;

    private Integer id;

    private String name;

    public User() {

    }

   public  User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
