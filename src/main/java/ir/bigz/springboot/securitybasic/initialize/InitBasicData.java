package ir.bigz.springboot.securitybasic.initialize;

import ir.bigz.springboot.securitybasic.dao.ApplicationUserDao;
import ir.bigz.springboot.securitybasic.dao.ApplicationUserPermissionDao;
import ir.bigz.springboot.securitybasic.dao.ApplicationUserRoleDao;
import ir.bigz.springboot.securitybasic.model.ApplicationUser;
import ir.bigz.springboot.securitybasic.model.ApplicationUserPermission;
import ir.bigz.springboot.securitybasic.model.ApplicationUserRole;
import ir.bigz.springboot.securitybasic.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InitBasicData {

    private ApplicationUserService applicationUserService;
    private ApplicationUserDao applicationUserDao;
    private ApplicationUserPermissionDao applicationUserPermissionDao;
    private ApplicationUserRoleDao applicationUserRoleDao;


    @Autowired
    public InitBasicData(ApplicationUserService applicationUserService,
                         ApplicationUserDao applicationUserDao,
                         ApplicationUserPermissionDao applicationUserPermissionDao,
                         ApplicationUserRoleDao applicationUserRoleDao) {
        this.applicationUserService = applicationUserService;
        this.applicationUserDao = applicationUserDao;
        this.applicationUserPermissionDao = applicationUserPermissionDao;
        this.applicationUserRoleDao = applicationUserRoleDao;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event){
        initPermission(applicationUserPermissionDao);
        initRole(applicationUserRoleDao,applicationUserPermissionDao);
        initUser(applicationUserService,applicationUserDao,applicationUserRoleDao);
    }

    void initPermission(ApplicationUserPermissionDao applicationUserPermissionDao){
        List<ApplicationUserPermission> applicationUserPermissions = new ArrayList<>();
        ApplicationUserPermission applicationUserPermissionEditorRead = new ApplicationUserPermission();
        applicationUserPermissionEditorRead.setPermissionName("editor:read");
        applicationUserPermissions.add(applicationUserPermissionEditorRead);
        ApplicationUserPermission applicationUserPermissionEditorWrite = new ApplicationUserPermission();
        applicationUserPermissionEditorWrite.setPermissionName("editor:write");
        applicationUserPermissions.add(applicationUserPermissionEditorWrite);
        ApplicationUserPermission applicationUserPermissionCourseRead = new ApplicationUserPermission();
        applicationUserPermissionCourseRead.setPermissionName("course:read");
        applicationUserPermissions.add(applicationUserPermissionCourseRead);
        ApplicationUserPermission applicationUserPermissionCourseWrite = new ApplicationUserPermission();
        applicationUserPermissionCourseWrite.setPermissionName("course:write");
        applicationUserPermissions.add(applicationUserPermissionCourseWrite);

        applicationUserPermissionDao.saveAll(applicationUserPermissions);
    }

    void initRole(ApplicationUserRoleDao applicationUserRoleDao,
                  ApplicationUserPermissionDao applicationUserPermissionDao){

        ApplicationUserRole applicationUserRoleAdmin = new ApplicationUserRole();
        applicationUserRoleAdmin.setRoleName("ROLE_Admin");
        applicationUserRoleAdmin.setRoleDescription("role with all privilege");
        applicationUserRoleDao.save(applicationUserRoleAdmin);

        ApplicationUserRole applicationUserRoleEditor = new ApplicationUserRole();
        applicationUserRoleEditor.setRoleName("ROLE_Editor");
        applicationUserRoleEditor.setRoleDescription("role with editor permission");
        applicationUserRoleDao.save(applicationUserRoleEditor);

        ApplicationUserRole applicationUserRoleAdminTrainer = new ApplicationUserRole();
        applicationUserRoleAdminTrainer.setRoleName("ROLE_AdminTrainer");
        applicationUserRoleAdminTrainer.setRoleDescription("role with writer permission");
        applicationUserRoleDao.save(applicationUserRoleAdminTrainer);

        List<ApplicationUserPermission> applicationUserPermissions = applicationUserPermissionDao.findAll();
        Set<ApplicationUserPermission> applicationUserPermissionSet = new HashSet<>();

        applicationUserPermissions.stream()
                .forEach(applicationUserPermission -> {
                    applicationUserPermissionSet.add(applicationUserPermission);
                });

        applicationUserRoleAdmin.setApplicationUserPermissionsForRole(applicationUserPermissionSet);
        applicationUserRoleDao.save(applicationUserRoleAdmin);

        applicationUserPermissionSet.clear();

        applicationUserPermissions.stream()
                .filter(applicationUserPermission ->
                        applicationUserPermission.getPermissionName().contains("editor"))
                .forEach(applicationUserPermission -> {
                    applicationUserPermissionSet.add(applicationUserPermission);
                });

        applicationUserRoleEditor.setApplicationUserPermissionsForRole(applicationUserPermissionSet);
        applicationUserRoleDao.save(applicationUserRoleEditor);

        applicationUserPermissionSet.clear();

        applicationUserPermissions.stream()
                .filter(applicationUserPermission ->
                        applicationUserPermission.getPermissionName().contains("read"))
                .forEach(applicationUserPermission -> {
                    applicationUserPermissionSet.add(applicationUserPermission);
                });

        applicationUserRoleAdminTrainer.setApplicationUserPermissionsForRole(applicationUserPermissionSet);
        applicationUserRoleDao.save(applicationUserRoleAdminTrainer);
    }

    void initUser(ApplicationUserService applicationUserService, ApplicationUserDao applicationUserDao, ApplicationUserRoleDao applicationUserRoleDao){

        ApplicationUser applicationUserAdmin = new ApplicationUser();
        applicationUserAdmin.setUserName("pouya");
        applicationUserAdmin.setPassword("password");
        applicationUserService.addUser(applicationUserAdmin);

        Optional<ApplicationUserRole> roleAdmin = applicationUserRoleDao.findApplicationUserRoleByRoleName("ROLE_Admin");
        Set<ApplicationUserRole> applicationUserRoles = new HashSet<>();
        applicationUserRoles.add(roleAdmin.get());

        applicationUserAdmin.setApplicationUserRoles(applicationUserRoles);
        applicationUserDao.save(applicationUserAdmin);

        ApplicationUser applicationUserAdminTrain = new ApplicationUser();
        applicationUserAdminTrain.setUserName("tom");
        applicationUserAdminTrain.setPassword("password");
        applicationUserService.addUser(applicationUserAdminTrain);

        Optional<ApplicationUserRole> roleAdminTrain = applicationUserRoleDao.findApplicationUserRoleByRoleName("ROLE_AdminTrainer");
        Set<ApplicationUserRole> applicationUserAdminTrains = new HashSet<>();
        applicationUserAdminTrains.add(roleAdminTrain.get());

        applicationUserAdminTrain.setApplicationUserRoles(applicationUserAdminTrains);
        applicationUserDao.save(applicationUserAdminTrain);

    }
}
