package ir.bigz.springboot.securitybasic.service;

import ir.bigz.springboot.securitybasic.model.ApplicationUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ApplicationUserService {

    void addUser(ApplicationUser applicationUser);

    void updateUser(ApplicationUser applicationUser);

    boolean findUser(String userName);

    Optional<ApplicationUser> getApplicationUserById(long id);

    String createTokenForSignUpUser(String userName);
}
