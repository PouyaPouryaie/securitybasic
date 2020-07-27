package ir.bigz.springboot.securitybasic.service;

import ir.bigz.springboot.securitybasic.model.ApplicationUserPermission;
import ir.bigz.springboot.securitybasic.model.ApplicationUserRole;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ApplicationUserRoleService {

    Set<ApplicationUserPermission> getPermissionFromRoles(String roleName);

    ApplicationUserRole getApplicationUserRole(String roleName);
}
