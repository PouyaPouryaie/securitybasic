package ir.bigz.springboot.securitybasic.auth;

import ir.bigz.springboot.securitybasic.dao.ApplicationUserDao;
import ir.bigz.springboot.securitybasic.model.ApplicationUser;
import ir.bigz.springboot.securitybasic.model.ApplicationUserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/*
JdbcDaoImpl you can use for UserDetailsService if you wont to impl UserDetailsService
 */

@Service
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserDao applicationUserDao;

    @Autowired
    public ApplicationUserService(ApplicationUserDao applicationUserDao) {
        this.applicationUserDao = applicationUserDao;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<ApplicationUser> applicationUserFromDao = applicationUserDao.findApplicationUserByUserName(userName);

        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        if(applicationUserFromDao.isPresent()){
            Set<SimpleGrantedAuthority> collectUserPermissionFromUser = applicationUserFromDao.get()
                    .getApplicationUserPermissions()
                    .stream()
                    .map(userPermission -> new SimpleGrantedAuthority(userPermission.getPermissionName()))
                    .collect(Collectors.toSet());

            Set<ApplicationUserPermission> userPermissionsFromRole = applicationUserFromDao.get()
                    .getApplicationUserRoles()
                    .stream()
                    .map(userRole -> userRole.getApplicationUserPermissionsForRole())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());

            Set<SimpleGrantedAuthority> collectUserPermissionFromRole = userPermissionsFromRole
                    .stream()
                    .map(userPermission -> new SimpleGrantedAuthority(userPermission.getPermissionName()))
                    .collect(Collectors.toSet());

            simpleGrantedAuthorities.addAll(collectUserPermissionFromUser);
            simpleGrantedAuthorities.addAll(collectUserPermissionFromRole);

            Set<SimpleGrantedAuthority> collectRoleForUser = applicationUserFromDao.get().getApplicationUserRoles()
                    .stream()
                    .map(userRole -> userRole.getRoleName())
                    .map(s -> new SimpleGrantedAuthority(s))
                    .collect(Collectors.toSet());

            simpleGrantedAuthorities.addAll(collectRoleForUser);

        }

        UserPrincipal userPrincipal = new UserPrincipal(simpleGrantedAuthorities,
                applicationUserFromDao.get().getUserName(),
                applicationUserFromDao.get().getPassword(),
                true,
                true,
                true,
                true);

        return userPrincipal;
    }
}
