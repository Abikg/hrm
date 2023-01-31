package com.makalu.hrm.utils;

import com.makalu.hrm.enumconstant.UserType;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FieldService {

    public List<Object> getDepartmentFields() {
        List<Object> fields = new ArrayList<>();

        fields.add(Map.of("name", "title", "displayName", "Title", "orderable", true));
        fields.add(Map.of("name", "departmentCode", "displayName", "Code", "orderable", true));
        fields.add(Map.of("name", "detail", "displayName", "Details", "orderable", true));
        fields.add(Map.of("name", "action", "displayName", "Action", "orderable", false, "width", "120px"));
        return fields;
    }

    public List<Object> getPositionFields() {
        List<Object> fields = new ArrayList<>();
        fields.add(Map.of("name", "title", "displayName", "Title", "orderable", true));
        fields.add(Map.of("name", "detail", "displayName", "Details", "orderable", true));
        fields.add(Map.of("name", "action", "displayName", "Action", "orderable", false, "width", "120px"));
        return fields;
    }

    public List<Object> getEmployeeFields() {
        List<Object> fields = new ArrayList<>();

        fields.add(Map.of("name", "employeeId", "displayName", "Employee ID", "orderable", true));
        fields.add(Map.of("name", "fullname", "displayName", "Full Name", "orderable", true));
        fields.add(Map.of("name", "departmentName", "displayName", "Department", "orderable", true));
        fields.add(Map.of("name", "positionName", "displayName", "Position", "orderable", true));
        fields.add(Map.of("name", "contactDetailDTO.contactPhone", "displayName", "Phone", "orderable", true));
        fields.add(Map.of("name", "email", "displayName", "Email Address", "orderable", true));
        fields.add(Map.of("name", "employeeStatus", "displayName", "Status", "orderable", true));
        fields.add(Map.of("name", "action", "displayName", "Action", "orderable", false, "width", "120px"));
        return fields;
    }

    public  List<Object> getAttendanceFields(){
        List<Object> fields=new ArrayList<>();
        if(AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())){
            fields.add(Map.of("name","user.username","displayName","User","orderable",false));
        }
        fields.add(Map.of("name","punchInDate","displayName","Punchin Date","orderable",true));
        fields.add(Map.of("name","punchInIp","displayName","Punchin Ip","orderable",false));
        fields.add(Map.of("name","punchOutDate","displayName","Punchout Ip","orderable",true));
        fields.add(Map.of("name","punchOutIp","displayName","Punchout Date","orderable",false));
        fields.add(Map.of("name","totalWorkedHours","displayName","Worked Hours","orderable",true));
         return  fields;
    }



    public  List<Object> getMinuteFields(){
        List<Object> fields=new ArrayList<>();
        fields.add(Map.of("name","title","displayName","Title","orderable",false));
        fields.add(Map.of("name","meetingDate","displayName","Date","orderable",true));
        fields.add(Map.of("name","createdBy.username","displayName","Created By","orderable",false));
        return  fields;
    }

}
