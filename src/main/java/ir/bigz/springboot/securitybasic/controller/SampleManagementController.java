package ir.bigz.springboot.securitybasic.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("management/api/v1/sample")
public class SampleManagementController {


    @GetMapping
    public String getHome(){
        return "Hello Manager";
    }

    @PostMapping
    public void postHome(@RequestParam String name){
        System.out.println(String.format("Hello %s", name));
    }

    @DeleteMapping(path = "{sampleId}")
    public void deleteHome(@PathVariable("sampleId") Long sampleId){
        System.out.println(String.format("delete %d", sampleId));
    }
}
