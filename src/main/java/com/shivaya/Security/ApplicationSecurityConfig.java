package com.shivaya.Security;

import com.shivaya.auth.ApplicationUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.shivaya.Security.ApplicationUserPermission.COURSE_READ;
import static com.shivaya.Security.ApplicationUserPermission.COURSE_WRITE;
import static com.shivaya.Security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private  final PasswordEncoder passwordEncoder;
    private  final ApplicationUserDetailsService applicationUserDetailsService;
    ApplicationSecurityConfig( PasswordEncoder passwordEncoder,
                               ApplicationUserDetailsService applicationUserDetailsService){
        this.passwordEncoder=passwordEncoder;
        this.applicationUserDetailsService = applicationUserDetailsService;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/**").permitAll()
                .antMatchers("/api/v1/student/**").hasAnyRole(STUDENT.name())
                .antMatchers(HttpMethod.DELETE,"/management/api/v1/student/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/management/api/v1/student/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST,"/management/api/v1/student/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.GET,"/management/api/v1/student/**").hasAuthority(COURSE_READ.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/courses", true)
                        .passwordParameter("password")
                        .usernameParameter("username")
                .and()
                .rememberMe()
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                        .key("somethingverysecured")
                        .rememberMeParameter("remember-me")
                .and()
                .logout()
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // https://docs.spring.io/spring-security/site/docs/4.2.12.RELEASE/apidocs/org/springframework/security/config/annotation/web/configurers/LogoutConfigurer.html
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .logoutSuccessUrl("/login");
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails shiva = User.builder()
//                .username("shiv")
//                .password(passwordEncoder.encode("password"))
//                .roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthority())
//                .build();
//
//        UserDetails   rahul  =User.builder()
//                .username("rahul")
//                .password(passwordEncoder.encode("password"))
//                .authorities(STUDENT.getGrantedAuthority())
//                .build();
//
//        UserDetails  govind =User.builder()
//                .username("govind")
//                .password(passwordEncoder.encode("password"))
//                .authorities(ADMINTRAINEE.getGrantedAuthority())
//                .build();
//
//        return new InMemoryUserDetailsManager(shiva,govind,rahul);
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());

    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserDetailsService);
        return provider;
    }
}
