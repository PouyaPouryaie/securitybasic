package ir.bigz.springboot.securitybasic.security;

public enum ApplicationUserPermission {

    EDITOR_READ("editor:read"),
    EDITOR_WRITE("editor:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
