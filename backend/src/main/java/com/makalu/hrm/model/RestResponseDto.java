package com.makalu.hrm.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Data
public class RestResponseDto {

    private int status;

    private String message;

    private Object detail;

    private HashMap<String, String> response;

    public static RestResponseDto INSTANCE(){
        return new RestResponseDto();
    }

    public RestResponseDto success(){
        this.status = HttpStatus.OK.value();
        return this;
    }

    public RestResponseDto notFound(){
        this.status = HttpStatus.NOT_FOUND.value();
        return this;
    }

    public RestResponseDto validationError(){
        this.status = HttpStatus.BAD_REQUEST.value();
        return this;
    }

    public RestResponseDto internalServerError(){
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return this;
    }


    public RestResponseDto message(String message){
        this.message = message;
        return this;
    }

    public RestResponseDto detail(Object detail){
        this.detail = detail;
        return this;
    }
}
