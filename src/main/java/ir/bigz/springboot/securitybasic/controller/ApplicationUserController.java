package ir.bigz.springboot.securitybasic.controller;

import ir.bigz.springboot.securitybasic.model.ApplicationUser;
import ir.bigz.springboot.securitybasic.service.ApplicationUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ApplicationUser/api/v1/sample")
@Slf4j
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;

    @Autowired
    public ApplicationUserController(@Qualifier("ApplicationUserServiceImpl") ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUpUser(@RequestBody ApplicationUser applicationUser){
        applicationUserService.addUser(applicationUser);
        log.info("add User done");
        return ResponseEntity.ok("Ok");
    }
}
