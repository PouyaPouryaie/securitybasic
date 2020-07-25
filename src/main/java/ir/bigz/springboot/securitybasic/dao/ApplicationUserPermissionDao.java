package ir.bigz.springboot.securitybasic.dao;

import ir.bigz.springboot.securitybasic.model.ApplicationUserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserPermissionDao extends JpaRepository<ApplicationUserPermission, Long> {
}
