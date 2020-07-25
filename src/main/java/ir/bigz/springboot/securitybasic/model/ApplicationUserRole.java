package ir.bigz.springboot.securitybasic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Access(AccessType.FIELD)
public class ApplicationUserRole {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private long roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_description")
    private String roleDescription;

    @ManyToMany(mappedBy = "applicationUserRoles")
    @JsonIgnore
    private Set<ApplicationUser> applicationUsers = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "role_permission",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "permission_id") }
    )
    private Set<ApplicationUserPermission> applicationUserPermissionsForRole = new HashSet<>();

    public ApplicationUserRole() {
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Set<ApplicationUser> getApplicationUsers() {
        return applicationUsers;
    }

    public void setApplicationUsers(Set<ApplicationUser> applicationUsers) {
        this.applicationUsers = applicationUsers;
    }

    public Set<ApplicationUserPermission> getApplicationUserPermissionsForRole() {
        return applicationUserPermissionsForRole;
    }

    public void setApplicationUserPermissionsForRole(Set<ApplicationUserPermission> applicationUserPermissionsForRole) {
        this.applicationUserPermissionsForRole = applicationUserPermissionsForRole;
    }
}
