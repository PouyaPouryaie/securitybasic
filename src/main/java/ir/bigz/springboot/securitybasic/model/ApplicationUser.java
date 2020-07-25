package ir.bigz.springboot.securitybasic.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "application_user")
@Access(AccessType.FIELD)
public class ApplicationUser {

    //serialize and deserialize
    static final long serialVersionUID=4L;

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "isAccountNonExpired", nullable = false)
    private boolean isAccountNonExpired;

    @Column(name = "isAccountNonLocked", nullable = false)
    private boolean isAccountNonLocked;

    @Column(name = "isCredentialsNonExpired", nullable = false)
    private boolean isCredentialsNonExpired;

    @Column(name = "isEnabled", nullable = false)
    private boolean isEnabled;


    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "application_user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<ApplicationUserRole> applicationUserRoles = new HashSet<>();


    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "application_user_permission",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "permission_id") }
    )
    private Set<ApplicationUserPermission> applicationUserPermissions = new HashSet<>();

    public ApplicationUser(){

    }

    public ApplicationUser(long id,
                           String userName,
                           String password,
                           boolean isAccountNonExpired,
                           boolean isAccountNonLocked,
                           boolean isCredentialsNonExpired,
                           boolean isEnabled) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Set<ApplicationUserRole> getApplicationUserRoles() {
        return applicationUserRoles;
    }

    public void setApplicationUserRoles(Set<ApplicationUserRole> applicationUserRoles) {
        this.applicationUserRoles = applicationUserRoles;
    }


    public Set<ApplicationUserPermission> getApplicationUserPermissions() {
        return applicationUserPermissions;
    }

    public void setApplicationUserPermissions(Set<ApplicationUserPermission> applicationUserPermissions) {
        this.applicationUserPermissions = applicationUserPermissions;
    }

    @Override
    public String toString() {
        return "ApplicationUser{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isAccountNonExpired=" + isAccountNonExpired +
                ", isAccountNonLocked=" + isAccountNonLocked +
                ", isCredentialsNonExpired=" + isCredentialsNonExpired +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
