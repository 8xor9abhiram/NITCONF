package reviewer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig{
	


	@Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
	@Autowired
    private  AuthenticationProvider authenticationProvider;

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
             http.csrf(requests -> requests.disable())
                 .authorizeHttpRequests((requests) -> requests
         				.requestMatchers("/api/login","/swagger-ui/index.html").permitAll()
         				.anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .logout(logout ->
//                        logout.logoutUrl("/api/v1/auth/logout")
//                                .addLogoutHandler(logoutHandler)
//                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
//                )
//        ;

        return http.build();
    }
	
	
//  @Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {   
//		http
//		    .csrf(requests -> requests.disable())
//			.authorizeHttpRequests((requests) -> requests
//				.requestMatchers("/", "/home").permitAll()
//				.anyRequest().authenticated()
//			)
//			.formLogin((form) -> form
//				.loginPage("/login")
//				.permitAll()
//				.defaultSuccessUrl("/dashboard")
//			)
//			.logout((logout) -> logout.permitAll());
//
//		return http.build();
//	}
//	

	
	
	
}


