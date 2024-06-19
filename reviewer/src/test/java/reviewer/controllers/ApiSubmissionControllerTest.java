package reviewer.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;
import jakarta.servlet.http.HttpServletRequest;
import reviewer.config.JwtService;
import reviewer.data.PaperRepository;
import reviewer.data.ReviewRepository;
import reviewer.model.Paper;
import reviewer.model.Review;
import reviewer.rest.ApiReviewCenterController;
import reviewer.rest.ApiSubmissionsController;
import reviewer.service.JwtExtractor;
import reviewer.util.ReviewForm;
import reviewer.util.ReviewKey;

public class ApiSubmissionControllerTest {
	
	@Mock
	private JwtService jwtService;

	@Mock
	private HttpServletRequest request;
	
	@Mock
	private PaperRepository paperRepo;
	
	@Mock
	private ReviewRepository reviewRepo;
	
	@Mock 
	private JwtExtractor jwtExtractor;
	
	@InjectMocks
	private ApiSubmissionsController apiSubmissionsController;
	
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    private void setDetails(Review review , Long paperId , String username)
    {
    	review.setReviewKey(new ReviewKey(paperId,username));
    	review.setReviewStatus("submit");
    }
    
    
    @Test
    public void getSubmissionsTest()
    {
    	String mockUsername = "taurn";
    	ArrayList<Review> reviewList = new ArrayList<Review>();
    	
    	for(int i=0;i<3;i++)
    	{
    		Review review = new Review();
    		setDetails(review,(long)i,"tarun");
    		reviewList.add(review);
    		
    	}
    	
    	
    	when(jwtExtractor.getUsernameFromToken()).thenReturn(mockUsername);
    	when(reviewRepo.findAllByIdUserIdAndReviewStatus(mockUsername,"submit")).thenReturn(reviewList);
    	
    	
    	assertEquals(reviewList, apiSubmissionsController.getSubmissions());
    		
    }
    
    
    @Test
    public void EditSubmissionSuccess()
    {
    	Long mockPaperId = 1L;
        String mockUsername = "tarun";
         
     	ReviewKey reviewKey = new ReviewKey(mockPaperId , mockUsername);
     	Review review = new Review(reviewKey);
 		
     	when(jwtExtractor.getUsernameFromToken()).thenReturn("tarun");
     	when(reviewRepo.findById(reviewKey)).thenReturn(Optional.of(review));
     	
     	ResponseEntity<Review> response = apiSubmissionsController.editSubmission(mockPaperId , new ReviewForm());
     	
     	assertEquals(HttpStatus.OK, response.getStatusCode());
     	assertEquals("submit" , response.getBody().getReviewStatus());
     	assertEquals(true, response.getBody().checkReviewForm(new ReviewForm()));
     	
    }
    
    @Test
    public void EditSubmissionFailure()
    {
    	Long mockPaperId = 1L;
        String mockUsername = "tarun";
         
     	ReviewKey reviewKey = new ReviewKey(mockPaperId , mockUsername);
 		
     	when(jwtExtractor.getUsernameFromToken()).thenReturn("tarun");
     	when(reviewRepo.findById(reviewKey)).thenReturn(Optional.empty());
     	
     	ResponseEntity<Review> response = apiSubmissionsController.editSubmission(mockPaperId , new ReviewForm());
     	
     	assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
     	
     	
    }
    
    
    @Test
    public void DeleteSubmissionSuccess()
    {
    	Long mockPaperId = 1L;
        String mockUsername = "tarun";
         
     	ReviewKey reviewKey = new ReviewKey(mockPaperId , mockUsername);
     	Review review = new Review(reviewKey);
 		
     	when(jwtExtractor.getUsernameFromToken()).thenReturn("tarun");
     	when(reviewRepo.findById(reviewKey)).thenReturn(Optional.of(review));
     	
     	ResponseEntity<Review> response = apiSubmissionsController.deleteSubmission(mockPaperId);
     	
     	assertEquals(HttpStatus.OK, response.getStatusCode());
     	assertEquals("new" , response.getBody().getReviewStatus());
     	assertEquals(true, response.getBody().checkReviewForm(new ReviewForm()));
     	
    }
    
    
    @Test
    public void DeleteSubmissionFailure()
    {
    	Long mockPaperId = 1L;
        String mockUsername = "tarun";
         
     	ReviewKey reviewKey = new ReviewKey(mockPaperId , mockUsername);

 		
     	when(jwtExtractor.getUsernameFromToken()).thenReturn("tarun");
     	when(reviewRepo.findById(reviewKey)).thenReturn(Optional.empty());
     	
     	ResponseEntity<Review> response = apiSubmissionsController.deleteSubmission(mockPaperId);
     	
     	assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    
     	
    }
    
    
    
    
   
    
    


}

