package ir.bigz.springboot.securitybasic.dao;

import com.google.common.collect.Lists;
import ir.bigz.springboot.securitybasic.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static ir.bigz.springboot.securitybasic.security.ApplicationUserRoleOld.*;

//@Component("fake")

/**
 * dont User this class in practice
 * this is for test
 */
public class FakeApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDao(PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserPrincipal> selectApplicationUserByUserName(String username) {
        return getApplicationUsers().stream()
                .filter(userPrincipal -> userPrincipal.getUsername().equals(username))
                .findFirst();
    }


    private List<UserPrincipal> getApplicationUsers() {
        List<UserPrincipal> userPrincipals = Lists.newArrayList(
                new UserPrincipal(
                        ADMIN.getGrantedAuthorities(),
                        "pouya",
                        passwordEncoder.encode("password"),
                        true,
                        true,
                        true,
                        true
                ),
                new UserPrincipal(
                        EDITOR.getGrantedAuthorities(),
                        "anna",
                        passwordEncoder.encode("password"),
                        true,
                        true,
                        true,
                        true
                ),
                new UserPrincipal(
                        ADMINTRAINEE.getGrantedAuthorities(),
                        "tom",
                        passwordEncoder.encode("password"),
                        true,
                        true,
                        true,
                        true
                )
        );

        return userPrincipals;
    }
}
