package ir.bigz.springboot.securitybasic.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ir.bigz.springboot.securitybasic.security.ApplicationUserPermissionOld.*;

/**
 * this is not use in practice
 */
public enum ApplicationUserRoleOld {
    EDITOR(Sets.newHashSet(EDITOR_READ,EDITOR_WRITE)),
    ADMIN(Sets.newHashSet(EDITOR_WRITE,EDITOR_READ,COURSE_READ,COURSE_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(EDITOR_READ,COURSE_READ));

    private final Set<ApplicationUserPermissionOld> permissions;

    ApplicationUserRoleOld(Set<ApplicationUserPermissionOld> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermissionOld> getPermissions() {
        return permissions;
    }

    /**
     * add this method for convert permission in role to simpleGrantedAuthority
     * @return Set<SimpleGrantedAuthority>
     */
    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
