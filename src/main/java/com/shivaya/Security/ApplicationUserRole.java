package com.shivaya.Security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.shivaya.Security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(STUDENT_READ,STUDENT_WRITE,COURSE_READ,COURSE_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(STUDENT_READ,COURSE_READ));

    Set<ApplicationUserPermission> applicationUserPermissionSet;
    ApplicationUserRole(Set<ApplicationUserPermission> applicationUserPermissionSet){
        this.applicationUserPermissionSet=applicationUserPermissionSet;
    }

    public Set<ApplicationUserPermission> getApplicationUserPermissionSet() {
        return applicationUserPermissionSet;
    }

    public Set<GrantedAuthority> getGrantedAuthority(){
        Set<GrantedAuthority>permissions = getApplicationUserPermissionSet().stream()
                .map(applicationUserPermission -> new SimpleGrantedAuthority(applicationUserPermission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return permissions;

    }
}
