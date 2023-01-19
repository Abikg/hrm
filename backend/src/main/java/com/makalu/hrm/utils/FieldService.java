package com.makalu.hrm.utils;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FieldService {

    public List<Object> getDepartmentFields(){
        List<Object> fields =new ArrayList<>();

        fields.add(Map.of("name","title","displayName","Title","orderable",true));
        fields.add(Map.of("name" ,"departmentCode","displayName", "Code","orderable", true));
        fields.add(Map.of("name" ,"detail","displayName", "Details","orderable", true));
        fields.add(Map.of("name" ,"action","displayName", "Action","orderable", false,"width","120px"));
        return fields;
    }

    public List<Object> getPositionFields(){
        List<Object> fields =new ArrayList<>();
        fields.add(Map.of("name","title","displayName","Title","orderable",true));
        fields.add(Map.of("name" ,"detail","displayName", "Details","orderable", true));
        fields.add(Map.of("name" ,"action","displayName", "Action","orderable", false,"width","120px"));
        return fields;
    }

    public List<Object> getEmployeeFields(){
        List<Object> fields =new ArrayList<>();
        fields.add(Map.of("name", "fullname","displayName","Name","orderable",true));
        fields.add(Map.of("name" ,"address","displayName", "Address","orderable", true));
        fields.add(Map.of("name" ,"phone","displayName", "Phone","orderable", true));
        fields.add(Map.of("name" ,"email","displayName", "email","orderable", true));
        fields.add(Map.of("name" ,"action","displayName", "Action","orderable", false,"width","120px"));
        return fields;
    }

}
