package ir.bigz.springboot.securitybasic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }


    /**
     * this method just for test in memory used
     * @return UserDetails
     */
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails pouya = User.builder()
                .username("pouya")
                .password(passwordEncoder.encode("password"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(pouya);
    }
}
