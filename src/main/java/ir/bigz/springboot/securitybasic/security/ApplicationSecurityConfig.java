package ir.bigz.springboot.securitybasic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static ir.bigz.springboot.securitybasic.security.ApplicationUserPermission.EDITOR_WRITE;
import static ir.bigz.springboot.securitybasic.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .antMatchers("/api/**").hasRole(ADMIN.name())
/*                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(EDITOR_WRITE.getPermission())
                .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(EDITOR_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())*/
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

        //this by explicit Role
/*        UserDetails pouya = User.builder()
                .username("pouya")
                .password(passwordEncoder.encode("password"))
                .roles("ADMIN") // Role_ADMIN
                .build();*/

        //this by implicit Role
/*        UserDetails pouya = User.builder()
                .username("pouya")
                .password(passwordEncoder.encode("password"))
                .roles(ADMIN.name())
                .build();*/

        UserDetails pouya = User.builder()
                .username("pouya")
                .password(passwordEncoder.encode("password"))
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails anna = User.builder()
                .username("anna")
                .password(passwordEncoder.encode("password"))
                .authorities(EDITOR.getGrantedAuthorities())
                .build();

        UserDetails tom = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("password"))
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(pouya, anna,tom);
    }
}
