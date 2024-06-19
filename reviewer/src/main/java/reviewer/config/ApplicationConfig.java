package reviewer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import reviewer.data.UserRepository;


@Service
@Configuration
@EnableWebSecurity
public class ApplicationConfig {
	
	
	@Autowired 
	private UserRepository userRepo;
	
	
	@Bean 
	PasswordEncoder passwordEncoder()     
	{   
		return new BCryptPasswordEncoder();
	}
	
	 @Bean
	 UserDetailsService userDetailsService() {
	    return username -> userRepo.findById(username)
	        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	  }

	
	@Bean
	AuthenticationProvider authenticationprovider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		return provider;
	}
	
	 @Bean
	  AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	  }

	
	

}
