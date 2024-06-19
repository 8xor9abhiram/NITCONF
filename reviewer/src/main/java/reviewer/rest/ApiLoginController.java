package reviewer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reviewer.auth.AuthenticationRequest;
import reviewer.auth.AuthenticationResponse;
import reviewer.auth.AuthenticationService;

@RestController
@RequestMapping(path="/api" , produces="application/json")
public class ApiLoginController {
	
	  @Autowired
	  private AuthenticationService authService;
	  
	  @PostMapping("/login")
	  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) 
	  {
	    return ResponseEntity.ok(authService.login(request));
	  }

}

