package ir.bigz.springboot.securitybasic.service;

import ir.bigz.springboot.securitybasic.dao.ApplicationUserDao;
import ir.bigz.springboot.securitybasic.dao.ApplicationUserRoleDao;
import ir.bigz.springboot.securitybasic.model.ApplicationUser;
import ir.bigz.springboot.securitybasic.model.ApplicationUserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component("ApplicationUserServiceImpl")
@Slf4j
public class ApplicationUserServiceImpl implements ApplicationUserService{

    private final ApplicationUserDao applicationUserDao;
    private final ApplicationUserRoleDao applicationUserRoleDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserServiceImpl(ApplicationUserDao applicationUserDao,
                                      ApplicationUserRoleDao applicationUserRoleDao,
                                      PasswordEncoder passwordEncoder) {
        this.applicationUserDao = applicationUserDao;
        this.applicationUserRoleDao = applicationUserRoleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(ApplicationUser applicationUser) {
        applicationUserFillData(applicationUser);
        applicationUserDao.save(applicationUser);
        setRoleForApplicationUser(applicationUser);
        applicationUserDao.save(applicationUser);
        if(findUser(applicationUser.getUserName())){
            log.info(String.format("%s added successfully", applicationUser.getUserName()));
        }
        else{
            log.error(String.format("%s added failed", applicationUser.getUserName()));
        }
    }

    @Override
    public void updateUser(ApplicationUser applicationUser) {
        try {
            ApplicationUser basicApplicationUser = applicationUserExistById(applicationUser.getId());
            applicationUserDataEntryForUpdate(basicApplicationUser, applicationUser);
            applicationUserDao.save(basicApplicationUser);
        }catch (IllegalStateException e){
            log.error("update process has problem \n" + e.getMessage());
        }
    }

    @Override
    public boolean findUser(String userName){
        Optional<ApplicationUser> applicationUser = applicationUserDao.findApplicationUserByUserName(userName);
        return applicationUser.isPresent();
    }

    @Override
    public Optional<ApplicationUser> getApplicationUserById(long id) {
        return applicationUserDao.findById(id);
    }

    private ApplicationUser applicationUserExistById(long id){
        Optional<ApplicationUser> applicationUserOptional = getApplicationUserById(id);

        if(applicationUserOptional.isPresent()){
            return applicationUserOptional.get();
        }else{
            throw new IllegalStateException(String.format("user by %s id not found", id));
        }
    }

    private void applicationUserDataEntryForUpdate(ApplicationUser basicUserApp, ApplicationUser userApp){
        basicUserApp.setUserName(userApp.getUserName());
    }

    private void applicationUserFillData(ApplicationUser applicationUser){
        applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
        applicationUser.setAccountNonExpired(true);
        applicationUser.setAccountNonLocked(true);
        applicationUser.setCredentialsNonExpired(true);
        applicationUser.setEnabled(true);
    }

    private void setRoleForApplicationUser(ApplicationUser applicationUser){
        Optional<ApplicationUserRole> roleEditor = applicationUserRoleDao
                .findApplicationUserRoleByRoleName("ROLE_Editor");
        Set<ApplicationUserRole> applicationUserRoles = new HashSet<>();

        if(roleEditor.isPresent()){
            applicationUserRoles.add(roleEditor.get());
            applicationUser.setApplicationUserRoles(applicationUserRoles);
        }
    }
}
