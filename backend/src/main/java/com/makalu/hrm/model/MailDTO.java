package com.makalu.hrm.model;

import lombok.Data;

@Data
public class MailDTO {

    private String message;

    private String username;

    private String  password;


    public void setMessage(String username, String password){
        this.message = "You have been registered to " +
                "MakaluHRM with username: "+username+" password: "+password;
    }

}
