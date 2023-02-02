package com.makalu.hrm.utils;

import com.makalu.hrm.enumconstant.UserType;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FieldService {

    public List<Object> getDepartmentFields() {
        List<Object> fields = new ArrayList<>();
        fields.add(Map.of("name", "title", "displayName", "Title", "type", "string", "orderable", true));
        fields.add(Map.of("name", "departmentCode", "displayName", "Code", "type", "string", "orderable", true));
        fields.add(Map.of("name", "detail", "displayName", "Details", "type", "string", "orderable", true));
        fields.add(Map.of("name", "action", "displayName", "Action", "type", "string", "orderable", false, "width", "120px"));
        return fields;
    }

    public List<Object> getPositionFields() {
        List<Object> fields = new ArrayList<>();
        fields.add(Map.of("name", "title", "displayName", "Title", "type", "string", "orderable", true));
        fields.add(Map.of("name", "detail", "displayName", "Details", "type", "string", "orderable", true));
        fields.add(Map.of("name", "action", "displayName", "Action", "type", "string", "orderable", false, "width", "120px"));
        return fields;
    }

    public List<Object> getEmployeeFields() {
        List<Object> fields = new ArrayList<>();

        fields.add(Map.of("name", "employeeId", "displayName", "Employee ID", "type", "string", "orderable", true));
        fields.add(Map.of("name", "fullname", "displayName", "Full Name", "type", "string", "orderable", true));
        fields.add(Map.of("name", "departmentName", "displayName", "Department", "type", "string", "orderable", true));
        fields.add(Map.of("name", "positionName", "displayName", "Position", "type", "string", "orderable", true));
        fields.add(Map.of("name", "contactDetailDTO.contactPhone", "displayName", "type", "string", "Phone", "orderable", true));
        fields.add(Map.of("name", "email", "displayName", "Email Address", "type", "string", "orderable", true));
        fields.add(Map.of("name", "employeeStatus", "displayName", "Status", "type", "string", "orderable", true));
        fields.add(Map.of("name", "action", "displayName", "Action", "type", "string", "orderable", false, "width", "120px"));
        return fields;
    }

    public List<Object> getAttendanceFields() {
        List<Object> fields = new ArrayList<>();
        if (AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())) {
            fields.add(Map.of("name", "user.username", "displayName", "User", "type", "string", "orderable", false));
        }
        fields.add(Map.of("name", "punchInDate", "displayName", "Punchin Date", "type", "date", "orderable", true));
        fields.add(Map.of("name", "punchInIp", "displayName", "Punchin IP", "type", "string", "orderable", false));
        fields.add(Map.of("name", "punchOutIp", "displayName", "Punchout IP", "type", "string", "orderable", true));
        fields.add(Map.of("name", "punchOutDate", "displayName", "Punchout Date", "type", "date", "orderable", false));
        fields.add(Map.of("name", "totalWorkedHours", "displayName", "Worked Hours", "type", "string", "orderable", true));
        return fields;
    }

    public List<Object> getMinuteFields() {
        List<Object> fields = new ArrayList<>();
        fields.add(Map.of("name", "title", "displayName", "Title", "type", "string", "orderable", false));
        fields.add(Map.of("name", "meetingDate", "displayName", "Date", "type", "date", "orderable", true));
        fields.add(Map.of("name", "createdBy.username", "displayName", "Created By","type", "string",  "orderable", false));
        return fields;
    }

}
