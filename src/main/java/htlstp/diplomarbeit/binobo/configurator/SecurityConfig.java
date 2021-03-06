package htlstp.diplomarbeit.binobo.configurator;

import htlstp.diplomarbeit.binobo.controller.util.FlashMessage;
import htlstp.diplomarbeit.binobo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/pictures/**", "/styles/**", "/scripts/**","/login/register", "/roboData/rest_api/**");
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/home", "/project", "/developer", "/sponsoring").permitAll()
                .antMatchers("/blog/**", "/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**", "/simulator3D").hasRole("ADMIN")
        .and()
            .formLogin().loginPage("/login")
            .permitAll()
            .successHandler(loginSuccessHandler())
            .failureHandler(loginFailureHandler())
        .and()
            .logout()
            .permitAll()
            .logoutSuccessUrl("/")
        .and()
            .csrf();
    }

    public AuthenticationSuccessHandler loginSuccessHandler(){
        return (request, response, authentication) -> response.sendRedirect("/blog");
    }

    public AuthenticationFailureHandler loginFailureHandler(){
        return ((request, response, exception) -> {
            request.getSession().setAttribute("flash", new FlashMessage("Incorrect data parsed!", FlashMessage.Status.FAILURE));
            response.sendRedirect("/login");
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
