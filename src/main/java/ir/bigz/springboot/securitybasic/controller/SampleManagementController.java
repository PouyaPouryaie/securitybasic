package ir.bigz.springboot.securitybasic.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("management/api/v1/sample")
public class SampleManagementController {


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public String getHome(){
        return "Hello Manager";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('editor:write')")
    public void postHome(@RequestParam String name){
        System.out.println(String.format("Hello %s", name));
    }

    @DeleteMapping(path = "{sampleId}")
    @PreAuthorize("hasAuthority('editor:write')")
    public void deleteHome(@PathVariable("sampleId") Long sampleId){
        System.out.println(String.format("delete %d", sampleId));
    }
}
