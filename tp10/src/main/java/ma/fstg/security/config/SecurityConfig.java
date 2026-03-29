package ma.fstg.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public UserDetailsService userDetailsService() {

    UserDetails manager = User.withUsername("sara")
            .password("{noop}manager123")
            .roles("MANAGER")
            .build();

    UserDetails employe = User.withUsername("karim")
            .password("{noop}employe123")
            .roles("EMPLOYE")
            .build();

    return new InMemoryUserDetailsManager(manager, employe);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login", "/css/**").permitAll()
                    .requestMatchers("/manager/**").hasRole("MANAGER")
                    .requestMatchers("/employe/**").hasAnyRole("EMPLOYE", "MANAGER")
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticate")
                    .defaultSuccessUrl("/accueil", true)
                    .failureUrl("/login?erreur=true")
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/deconnexion")
                    .logoutSuccessUrl("/login?deconnecte=true")
                    .logoutRequestMatcher(new org.springframework.security.web.util.matcher.AntPathRequestMatcher("/deconnexion", "GET"))
                    .permitAll()
            );
    return http.build();
  }
}