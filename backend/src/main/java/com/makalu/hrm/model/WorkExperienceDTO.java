package com.makalu.hrm.model;

import lombok.Data;

@Data
public class WorkExperienceDTO {

    private String previousCompany;

    private String jobTitle;

    private String joinDate;

    private String leftDate;

    private String jobDesc;
}
