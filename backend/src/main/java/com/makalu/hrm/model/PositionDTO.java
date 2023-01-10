package com.makalu.hrm.model;

import lombok.Data;
import java.util.UUID;

@Data
public class PositionDTO {

    private UUID id;

    private String title;

    private String detail;

}
