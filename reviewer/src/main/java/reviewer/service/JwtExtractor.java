package reviewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import reviewer.config.JwtService;

@Service
public class JwtExtractor {
	
    @Autowired
	private HttpServletRequest request;
	
    @Autowired
	private JwtService jwtService;
	
	
	public String getUsernameFromToken()
	{
	    String authHeader = request.getHeader("Authorization");
		    
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) 
        {     
          return null;
        }
        
	    String jwt = authHeader.substring(7);  
	    String username = jwtService.extractUsername(jwt);
	    return username;
	}

}

