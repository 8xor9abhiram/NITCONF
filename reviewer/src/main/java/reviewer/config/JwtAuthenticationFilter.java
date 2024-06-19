package reviewer.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	
  @Autowired
  private JwtService jwtService;
  
  @Autowired
  private  UserDetailsService userDetailsService ;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
		  @NonNull HttpServletResponse response,
		  @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
	  

    final String authHeader = request.getHeader("Authorization");
    final String contentType = request.getHeader("Content-Type");
    /*if(contentType!= null && contentType.equals("application/x-www-form-urlencoded"))
    {
    	
    	System.out.println("OMG");
    	String username = request.getParameter("username");
    	System.out.println("username");
    }*/
    
    System.out.println("contentType " + contentType);
    String jwt;
    final String userEmail;
    
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
    
      if(request.getParameter("token") != null)
      {
    	  System.out.println("token");
    	  jwt = request.getParameter("token");
    	  System.out.println(jwt);
    	  
      }else
      {
    	  System.out.println("invalid");
          filterChain.doFilter(request, response);
          return;
      }
      
    }
    else
    {
    	jwt = authHeader.substring(7);  
    }
    
    
    userEmail = jwtService.extractUsername(jwt);
    
    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
      
//      var isTokenValid = tokenRepository.findByToken(jwt)
//          .map(t -> !t.isExpired() && !t.isRevoked())
//          .orElse(false);
      
      if (jwtService.isTokenValid(jwt, userDetails) /*&& isTokenValid*/) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}
