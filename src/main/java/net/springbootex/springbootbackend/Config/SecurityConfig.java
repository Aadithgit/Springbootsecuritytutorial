package net.springbootex.springbootbackend.Config;


import net.springbootex.springbootbackend.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter authFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
   /* @Autowired
    private AuthenticationProvider authenticationProvider; */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("api/v1/employees/authenticate","api/v1/employees")
                        .permitAll())
                .authorizeHttpRequests(requests -> requests
                        //.requestMatchers(HttpMethod.POST, "api/v1/employees").permitAll()
                        .requestMatchers( "api/v1/employees/id").hasRole("ADMIN")

                        // .requestMatchers(HttpMethod.PUT, "/employees/modify/{id}").hasRole("ADMIN")
                        //.requestMatchers(HttpMethod.DELETE, "/employees/delete/{id}").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();


    }


    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/employees/{id}","/api/v1/employees").authenticated()
                .requestMatchers("/api/v1/employees/authenticate").permitAll()

                .and().build();
        //.authorizeHttpRequests().requestMatchers("/api/v1/employees")
        // .permitAll().and().formLogin().and().build();
    }*/

    @Bean
    //authentication
    public UserDetailsService userDetailsService() {
       UserDetails admin = User.withUsername("Basant")
                .password(passwordEncoder().encode("Pwd1"))
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("John")
                .password(passwordEncoder().encode("Pwd2"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);


    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }



   /* @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests((authorize)->authorize.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());


        return http.build();
    }*/
  /* @Bean
   SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http.csrf().disable()
               .authorizeHttpRequests((authorize) -> authorize
                       // Permit all POST requests to /api/v1/employees
                       .requestMatchers(HttpMethod.POST, "/api/v1/employees") // Change here
                       .permitAll()
                       // Other authorization rules...
                       .anyRequest()
                       .authenticated()
               )
               // Configure HTTP Basic
               .httpBasic().realmName("My Realm")// Customize realm name if needed
               .and().build();
   }*/





  /* @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http.csrf().disable()
               .authorizeHttpRequests()
               .requestMatchers("/api/v1/employees/{id}","/api/v1/employees").authenticated()
               .requestMatchers("/api/v1/employees/authenticate").permitAll()

                .and().build();
               //.authorizeHttpRequests().requestMatchers("/api/v1/employees")
              // .permitAll().and().formLogin().and().build();
   }*/








    //@Bean
   /* public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Disable CSRF (if necessary):
                // .csrf(CsrfConfigurer::disable)  // Uncomment if you must disable CSRF

                // Authorize requests:
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("api/v1/employees","api/v1/employees/new")
                        .permitAll()
                        .requestMatchers("api/v1/employees/id")
                        .authenticated()
                        //.anyRequest()
                       // .authenticated()  // Add this line

                )

                // Form login configuration:
                .formLogin(Customizer.withDefaults())
                .build();
    }*/




}

