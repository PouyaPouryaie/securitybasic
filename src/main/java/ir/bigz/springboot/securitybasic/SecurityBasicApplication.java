package ir.bigz.springboot.securitybasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SecurityBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityBasicApplication.class, args);
	}

}
