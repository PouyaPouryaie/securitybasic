package ir.bigz.springboot.securitybasic.dao;

import ir.bigz.springboot.securitybasic.model.ApplicationUser;
import ir.bigz.springboot.securitybasic.model.ApplicationUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRoleDao extends JpaRepository<ApplicationUserRole, Long> {

    Optional<ApplicationUserRole> findApplicationUserRoleByRoleName(String roleName);
}
