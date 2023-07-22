package com.shivaya.auth;

import com.google.common.collect.Lists;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.shivaya.Security.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDaoService implements  ApplicationUserDao{

    final PasswordEncoder passwordEncoder;

    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUserName(String username) {
        return getApplicationUsers()
                .stream()
                .filter(user->user.getUsername().equals(username))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser>applicationUsers = Lists.newArrayList(
                 new ApplicationUser(ADMIN.getGrantedAuthority(),
                         "shiv",
                         passwordEncoder.encode("password"),
                         true,
                         true,
                         true,
                         true),
                new ApplicationUser(STUDENT.getGrantedAuthority(),
                        "rahul",
                        passwordEncoder.encode("password"),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser(ADMINTRAINEE.getGrantedAuthority(),
                        "govind",
                        passwordEncoder.encode("password"),
                        true,
                        true,
                        true,
                        true)
        );
        return applicationUsers;
    }
}
