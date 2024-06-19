package reviewer.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reviewer.config.JwtService;
import reviewer.data.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	 @Autowired
	 private  UserRepository repository;
	 
	 @Autowired
	 private  JwtService jwtService;
	 
	 @Autowired
	 private  AuthenticationManager authenticationManager;
	    
	 public AuthenticationResponse login(AuthenticationRequest request) {
	        
		 authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        request.getEmail(),
	                        request.getPassword()));
	        var user = repository.findById(request.getEmail()).orElseThrow();
	   
	        var jwtToken = jwtService.generateToken(user);
	        System.out.println(jwtToken);
	        return new AuthenticationResponse(jwtToken);
	               
	    }

}
