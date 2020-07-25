package ir.bigz.springboot.securitybasic.security;

/**
 * this is not use in practice
 */
public enum ApplicationUserPermissionOld {

    EDITOR_READ("editor:read"),
    EDITOR_WRITE("editor:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    private final String permission;

    ApplicationUserPermissionOld(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
