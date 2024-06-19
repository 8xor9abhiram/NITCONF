package reviewer.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import io.jsonwebtoken.lang.Assert;
import jakarta.servlet.http.HttpServletRequest;
import reviewer.config.JwtService;
import reviewer.data.ReviewRepository;
import reviewer.data.TagsRepository;
import reviewer.data.UserRepository;
import reviewer.model.Review;
import reviewer.model.Tags;
import reviewer.model.User;
import reviewer.rest.ApiEditProfileController;
import reviewer.rest.ApiReviewController;
import reviewer.service.JwtExtractor;

//@SpringBootTest
public class ApiEditProfileControllerTest {

    @Mock
    private TagsRepository tagsRepository;
    
    @Mock
	private JwtService jwtService;

	@Mock
	private HttpServletRequest request;
	
	@Mock
	private UserRepository userRepo;
	
	@Mock
	private ReviewRepository reviewRepo;
	
	@Mock 
	private JwtExtractor jwtExtractor;
	
	@InjectMocks
	private ApiEditProfileController apiEditProfileController;
	
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllTagsTest() throws Exception {
        // Mock data
        List<Tags> mockTags = new ArrayList<>();
		mockTags.add(new Tags(1L, "tag1"));
        mockTags.add(new Tags(2L, "tag2"));

        Mockito.when(tagsRepository.findAll()).thenReturn((ArrayList<Tags>) mockTags);

        Iterable<Tags> returnedTags = apiEditProfileController.getAllTags();

        assertEquals(mockTags, returnedTags);
    }
    
    @Test
    public void getUserDetailsTest_Success() throws Exception {
    	
		String mockUsername = "tarun";
		User user= new User();
		user.setUsername(mockUsername);
	
		when(jwtExtractor.getUsernameFromToken()).thenReturn(mockUsername);
		when(userRepo.findById(mockUsername)).thenReturn(Optional.of(user));
	        
		assertEquals(user, apiEditProfileController.getUserDetails());		

    }
    
    @Test
    public void getUserDetailsTest_Fail() throws Exception {
    	String mockUsername = "tarun";
    	User user= new User();
    	user.setUsername(mockUsername);

    	when(jwtExtractor.getUsernameFromToken()).thenReturn(mockUsername);
    	when(userRepo.findById(mockUsername)).thenReturn(Optional.empty());
            
    	assertEquals(null, apiEditProfileController.getUserDetails());
    	
    }
    
    @Test
    public void editprofileSuccess() throws Exception {
    	String mockUsername = "tarun";
    	
		User user= new User();
		user.setUsername(mockUsername);
		
		User paramUser = new User();
		
		paramUser.setUsername(mockUsername);
		paramUser.setBio("i am interested in java");
	
		when(jwtExtractor.getUsernameFromToken()).thenReturn(mockUsername);
		when(userRepo.findById(mockUsername)).thenReturn(Optional.of(user));

		
		
        ResponseEntity<User> response = apiEditProfileController.editProfile(paramUser);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(paramUser, response.getBody());
    	
		
		

    }

    
    @Test
    public void editprofileFailure() throws Exception {
    	String mockUsername = "tarun";
    	
		User user= new User();
		user.setUsername(mockUsername);
		
		User paramUser = new User();
		
		paramUser.setUsername(mockUsername);
		paramUser.setBio("i am interested in java");
	
		when(jwtExtractor.getUsernameFromToken()).thenReturn(mockUsername);
		when(userRepo.findById(mockUsername)).thenReturn(Optional.empty());
		
		
        ResponseEntity<User> response = apiEditProfileController.editProfile(paramUser);
    	
    	assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    	
		
		
    }
    
    @Test
    public void editprofileInvalidUsername() throws Exception {
        String mockUsername = "tarun";
    	
		User user= new User();
		user.setUsername(mockUsername);
		
		User paramUser = new User();
		
		paramUser.setUsername("koushik");
		paramUser.setBio("i am interested in java");
	
		when(jwtExtractor.getUsernameFromToken()).thenReturn(mockUsername);
		when(userRepo.findById(mockUsername)).thenReturn(Optional.of(user));
		
		
        ResponseEntity<User> response = apiEditProfileController.editProfile(paramUser);
    	
    	assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  
    	
		
		
    }
    
    
    
}
