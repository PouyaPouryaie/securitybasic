package ir.bigz.springboot.securitybasic.service;

import io.jsonwebtoken.Jwts;
import ir.bigz.springboot.securitybasic.auth.UserDetailServiceImpl;
import ir.bigz.springboot.securitybasic.auth.UserPrincipal;
import ir.bigz.springboot.securitybasic.dao.ApplicationUserDao;
import ir.bigz.springboot.securitybasic.dao.ApplicationUserRoleDao;
import ir.bigz.springboot.securitybasic.jwt.JwtConfig;
import ir.bigz.springboot.securitybasic.model.ApplicationUser;
import ir.bigz.springboot.securitybasic.model.ApplicationUserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

@Component("ApplicationUserServiceImpl")
@Slf4j
public class ApplicationUserServiceImpl implements ApplicationUserService{

    private final ApplicationUserDao applicationUserDao;
    private final ApplicationUserRoleDao applicationUserRoleDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final UserDetailServiceImpl userDetailService;

    @Autowired
    public ApplicationUserServiceImpl(ApplicationUserDao applicationUserDao,
                                      ApplicationUserRoleDao applicationUserRoleDao,
                                      PasswordEncoder passwordEncoder,
                                      JwtConfig jwtConfig,
                                      SecretKey secretKey, UserDetailServiceImpl userDetailService) {
        this.applicationUserDao = applicationUserDao;
        this.applicationUserRoleDao = applicationUserRoleDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
        this.userDetailService = userDetailService;
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

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public String createTokenForSignUpUser(String userName) {

        UserPrincipal userPrincipal = (UserPrincipal) userDetailService.loadUserByUsername(userName);
        if(userPrincipal.getUsername()!=null){
            String token = Jwts.builder()
                    .setSubject(userPrincipal.getUsername())
                    .claim("authorities", userPrincipal.getAuthorities())
                    .setIssuedAt(new Date())
                    .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                    .signWith(secretKey)
                    .compact();

            String jwtToken = jwtConfig.getTokenPrefix() + token;
            return jwtToken;
        }else{
            throw new IllegalStateException(String.format("user with %s not found", userName));
        }
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
