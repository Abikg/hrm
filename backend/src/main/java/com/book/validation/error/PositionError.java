package com.book.validation.error;

import lombok.Data;

@Data
public class PositionError {

    private boolean valid;

    private String title;

    private String detail;

    private String generalMessage;

}
