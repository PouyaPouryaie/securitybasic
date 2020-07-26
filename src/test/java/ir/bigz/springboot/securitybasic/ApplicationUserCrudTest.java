package ir.bigz.springboot.securitybasic;

import ir.bigz.springboot.securitybasic.dao.ApplicationUserDao;
import ir.bigz.springboot.securitybasic.model.ApplicationUser;
import ir.bigz.springboot.securitybasic.model.ApplicationUserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableTransactionManagement
public class ApplicationUserCrudTest {

    @Autowired
    private ApplicationUserDao applicationUserDao;

    private ApplicationUser applicationUser;


    @Transactional(propagation = Propagation.REQUIRED)
    @Test
    void isShouldGetApplicationUser(){

        Optional<ApplicationUser> optionalApplicationUser = applicationUserDao.findApplicationUserByUserName("pouya");

        applicationUser = optionalApplicationUser.get();

        var applicationUserRoles = applicationUser.getApplicationUserRoles();

        assertThat(applicationUserRoles.size()).isGreaterThan(0);
    }
}
