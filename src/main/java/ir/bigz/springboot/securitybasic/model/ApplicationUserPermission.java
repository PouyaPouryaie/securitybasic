package ir.bigz.springboot.securitybasic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permission")
@Access(AccessType.FIELD)
public class ApplicationUserPermission {

    @Id
    @GeneratedValue
    @Column(name = "permission_id")
    private long permissionId;

    @Column(name = "permission_name")
    private String permissionName;

    @ManyToMany(mappedBy = "applicationUserPermissions")
    @JsonIgnore
    private Set<ApplicationUser> applicationUsers = new HashSet<>();

    @ManyToMany(mappedBy = "applicationUserPermissionsForRole")
    @JsonIgnore
    private Set<ApplicationUserRole> roles = new HashSet<>();

    public ApplicationUserPermission() {
    }

    public ApplicationUserPermission(String permissionName) {
        this.permissionName = permissionName;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Set<ApplicationUser> getApplicationUsers() {
        return applicationUsers;
    }

    public void setApplicationUsers(Set<ApplicationUser> applicationUsers) {
        this.applicationUsers = applicationUsers;
    }

    public Set<ApplicationUserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<ApplicationUserRole> roles) {
        this.roles = roles;
    }
}
