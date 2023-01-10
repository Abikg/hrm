package com.book.validation.error;

import lombok.Data;

@Data
public class DepartmentError {

    private boolean valid;

    private String title;

    private String detail;

    private String departmentCode;

    private String generalMessage;
}
