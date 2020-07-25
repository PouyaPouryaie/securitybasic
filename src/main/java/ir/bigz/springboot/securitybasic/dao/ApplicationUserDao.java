package ir.bigz.springboot.securitybasic.dao;

import ir.bigz.springboot.securitybasic.auth.UserPrincipal;
import ir.bigz.springboot.securitybasic.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserDao extends JpaRepository<ApplicationUser, Long> {

//    Optional<UserPrincipal> selectApplicationUserByUserName(String username);

    Optional<ApplicationUser> findApplicationUserByUserName(String userName);
}
