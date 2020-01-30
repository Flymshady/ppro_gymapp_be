package cz.ppro.gymapp.be.authentication;

import cz.ppro.gymapp.be.repository.AccountRepository;
import cz.ppro.gymapp.be.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
//pridany
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(basePackageClasses = AccountRepository.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    private final CustomUserDetailsService userDetailService;

    public SecurityConfiguration(CustomUserDetailsService customUserDetailsService){
        this.userDetailService= customUserDetailsService;
    }
//taky pridano
    @Override
    protected void configure(AuthenticationManagerBuilder auth)  {
        auth.authenticationProvider(authenticationProvider());
    }
/***
    Návod na security conf:
        hasRole()
            pro metody CREATE/UOPDATE/DELETE   dát HttpMethod.OPTIONS  .antMatchers(HttpMethod.OPTIONS,"/courses/create/**"
            pro GET
        autheticated()
            ete nevim myslim ze staci bez OPTIONS
  ***/


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/courses/create/**", "/courses/update/**", "/courses/remove/**", "/courses/allByTrainer").hasRole("Trainer")
                .antMatchers(HttpMethod.OPTIONS, "/accounts/update/**").authenticated()
                //to pod tim odkomentovat po vytvoreni roli a admina
                        .antMatchers(HttpMethod.OPTIONS, "/accounts/create/admin/**", "/roles/all", "/roles/detail/**", "/roles/create", "/roles/update/**", "/roles/remove/**").hasRole("Admin")
                .antMatchers(HttpMethod.OPTIONS, "/accounts/all/trainer", "/accounts/all/clients", "/accounts/detail/**", "/accounts/update/**", "/accountSignedCourse/allByClient/**").authenticated()
                .antMatchers(HttpMethod.OPTIONS, "/courses/allByClient/**", "/entrances/ticket/**", "/entrances/detail/**", "/tickets/all", "/tickets/account/**", "/tickets/validCheck/**", "/tickets/detail/**").authenticated()
                .antMatchers(HttpMethod.OPTIONS, "/courses/sign/**", "/courses/signout/**").hasRole("Client")
                .antMatchers(HttpMethod.OPTIONS, "/entrances/create/**", "/entrances/update/**","/entrances/remove/**", "/tickets/create/**", "/tickets/update/all", "/tickets/remove/**", "/ticketTypes/all", "/ticketTypes/detail/**", "/ticketTypes/create", "/ticketTypes/update/**").hasRole("Employee")
              //  .antMatchers("/demo/hello").hasRole("Trainer")
            //    .antMatchers(HttpMethod.OPTIONS,"/demo/auth").authenticated()
               // .antMatchers(HttpMethod.OPTIONS,"/courses/all").authenticated()
            //    .antMatchers("/courses/all").hasRole("Admin")
                .anyRequest().permitAll()
                .and()
                //.formLogin().and()
                .httpBasic();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userDetailService);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

