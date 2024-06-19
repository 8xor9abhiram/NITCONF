package reviewer.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.Test;
import reviewer.auth.AuthenticationRequest;
import reviewer.auth.AuthenticationService;


import reviewer.rest.ApiLoginController;

public class ApiLoginControllerTest {
	
	@Mock
	private AuthenticationService authService;

	
	@InjectMocks
	private ApiLoginController apiLoginController;
	
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void authenticationSuccess()
    {
    	AuthenticationRequest request = new AuthenticationRequest();
    	request.setEmail("tarun");
    	assertEquals(HttpStatus.OK, apiLoginController.authenticate(request).getStatusCode());
		
    }
}
