package com.N2O2.Nitrouz_Studioz.security;

import com.N2O2.Nitrouz_Studioz.model.service.ProfileDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ProfileDetailsService profileDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
            .userDetailsService(profileDetailsService)
            .passwordEncoder(bCryptPasswordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers().authenticated()
            .antMatchers( "/", "/about", "/signup", "/signUpForm",
                "/signUpFormError", "/login", "/logOut", "/ForgotPasswordPage", "/Forgot_Password",
                "/SignUp", "/registrationComplete", "/members").permitAll()
            .antMatchers("/loggedInUser/**").hasAnyAuthority("ADMIN", "USER", "MODERATOR")
            .anyRequest().authenticated().and().csrf().disable().formLogin()
            .loginPage("/login").failureUrl("/login?error=true")
            .defaultSuccessUrl("/loggedInUser/success")
            .usernameParameter("email")
            .passwordParameter("password")
            .and().logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logOut"))
            .logoutSuccessUrl("/")
            .and()
            .headers()
            .frameOptions()
            .disable()
            .addHeaderWriter(new StaticHeadersWriter("X-FRAME-OPTIONS",
                    "ALLOW-FROM https://www.youtube.com/watch?v=HV2LVEPrKGs&feature=emb_title"));
    }

    @Override
    public void configure(WebSecurity web){
        web
            .ignoring()
            .antMatchers("/resources/**", "/static/**", "/css/**", "/scss/**", "/js/**", "/images/**");
    }
}
