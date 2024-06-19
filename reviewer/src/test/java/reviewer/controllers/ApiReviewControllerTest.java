package reviewer.controllers;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

//import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jakarta.servlet.http.HttpServletRequest;
import reviewer.config.JwtService;
import reviewer.data.ReviewRepository;
import reviewer.data.UserRepository;
import reviewer.model.Review;
import reviewer.model.User;
import reviewer.rest.ApiReviewController;
import reviewer.service.JwtExtractor;
import reviewer.util.ReviewForm;
import reviewer.util.ReviewKey;


public class ApiReviewControllerTest {
	
	
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
	private ApiReviewController apiReviewController;
	
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

		
    @Test
	public void getReviewSuccess()
	{
    	Long mockPaperId = 1L;
    	String mockUsername = "tarun";
    	ReviewKey reviewKey = new ReviewKey(mockPaperId , mockUsername);
		Review review = new Review(reviewKey);
		
    	when(jwtExtractor.getUsernameFromToken()).thenReturn(mockUsername);
    	when(reviewRepo.findById(reviewKey)).thenReturn(Optional.of(review));
    	
    	assertEquals(HttpStatus.OK, apiReviewController.getReview(mockPaperId).getStatusCode());
		
	}
    
    
    @Test
	public void getReviewFailure()
	{
        Long mockPaperId = 1L;
        String mockUsername = "tarun";
    	ReviewKey reviewKey = new ReviewKey(mockPaperId , mockUsername);
		
    	when(jwtExtractor.getUsernameFromToken()).thenReturn("tarun");
    	when(reviewRepo.findById(reviewKey)).thenReturn(Optional.empty());
    	
    	assertEquals(HttpStatus.NOT_FOUND, apiReviewController.getReview(mockPaperId).getStatusCode());
		
	}
    
    
    @Test
	public void declineReviewSuccess()
	{
        Long mockPaperId = 1L;
        String mockUsername = "tarun";
        
    	ReviewKey reviewKey = new ReviewKey(mockPaperId , mockUsername);
    	Review review = new Review(reviewKey);
		
    	when(jwtExtractor.getUsernameFromToken()).thenReturn("tarun");
    	when(reviewRepo.findById(reviewKey)).thenReturn(Optional.of(review));
    	
    	ResponseEntity<Review> response = apiReviewController.declineReview(mockPaperId);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals("decline",response.getBody().getReviewerStatus());
		
	}
    
    @Test
    public void clearReviewSuccess()
    {
    	Long mockPaperId = 1L;
        String mockUsername = "tarun";
         
     	ReviewKey reviewKey = new ReviewKey(mockPaperId , mockUsername);
     	Review review = new Review(reviewKey);
 		
     	when(jwtExtractor.getUsernameFromToken()).thenReturn("tarun");
     	when(reviewRepo.findById(reviewKey)).thenReturn(Optional.of(review));
     	
     	ResponseEntity<Review> response = apiReviewController.clearReview(mockPaperId);
     	
     	assertEquals(HttpStatus.OK, response.getStatusCode());
     	assertEquals("new" , response.getBody().getReviewStatus());
     	assertEquals(true, response.getBody().checkReviewForm(new ReviewForm()));
     	
    }
    
    
    @Test
    public void saveReviewForm()
    {
    	Long mockPaperId = 1L;
        String mockUsername = "tarun";
         
     	ReviewKey reviewKey = new ReviewKey(mockPaperId , mockUsername);
     	Review review = new Review(reviewKey);
 		
     	when(jwtExtractor.getUsernameFromToken()).thenReturn("tarun");
     	when(reviewRepo.findById(reviewKey)).thenReturn(Optional.of(review));
     	
     	ResponseEntity<Review> response = apiReviewController.updateReview(mockPaperId, new ReviewForm());
     	
     	assertEquals(HttpStatus.OK, response.getStatusCode());
     	assertEquals("draft" , response.getBody().getReviewStatus());
     	assertEquals(true, response.getBody().checkReviewForm(new ReviewForm()));
    }
    
    
    
    
    
    
    
    
    
    
    

}

