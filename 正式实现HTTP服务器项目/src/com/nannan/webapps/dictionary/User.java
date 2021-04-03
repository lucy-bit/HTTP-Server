package com.nannan.webapps.dictionary;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 86187
 * Date: 2021 -03 -31
 * Time: 17:57
 */
import java.io.Serializable;

//实际上写的时候不用按照序列化的方式去写，但是因为要写到 session里头，所以要实现这个接口
public class User implements Serializable {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
