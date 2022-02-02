package htlstp.diplomarbeit.binobo.configurator;

import htlstp.diplomarbeit.binobo.controller.util.FlashMessage;
import htlstp.diplomarbeit.binobo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * This class configures Spring's Security Plugin, telling it, how the Filter Chain hast to work, and which patters shall
 * be ignored and which not.
 * This class is annotated with @Configuration and @EnableWebSecurity, telling the JVM that it has to be evaluated
 * on Runtime, as well as that this will configure the Spring environment and that Spring has to enable the Security Plugin.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Autowiring throws exception when changed to constructor-based-approach
    @Autowired
    private UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/pictures/**", "/styles/**", "/scripts/**","/login/register",
                "/blog_rest_api/**", "/roboData/rest_api/**");
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/home", "/project", "/developer", "/sponsoring").permitAll()
                .antMatchers("/blog/**", "/user/**", "/emulator3D").hasAnyRole("USER", "ADMIN", "OPERATOR")
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "OPERATOR")
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
            request.getSession().setAttribute("flash_err", new FlashMessage("Incorrect data parsed!", FlashMessage.Status.FAILURE));
            response.sendRedirect("/login");
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
