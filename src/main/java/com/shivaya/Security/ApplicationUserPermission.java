package com.shivaya.Security;

import lombok.AllArgsConstructor;


public enum ApplicationUserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");
    String permission;
    ApplicationUserPermission(String permission){
        this.permission=permission;
    }

    public String getPermission() {
        return permission;
    }
}
