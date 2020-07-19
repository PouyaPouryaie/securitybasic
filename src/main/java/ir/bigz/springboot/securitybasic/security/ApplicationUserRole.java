package ir.bigz.springboot.securitybasic.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ir.bigz.springboot.securitybasic.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    EDITOR(Sets.newHashSet(EDITOR_READ,EDITOR_WRITE)),
    ADMIN(Sets.newHashSet(EDITOR_WRITE,EDITOR_READ,COURSE_READ,COURSE_WRITE)),
    ADMINTRAIN(Sets.newHashSet(EDITOR_READ,COURSE_READ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
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
