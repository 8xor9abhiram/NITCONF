package reviewer.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;
import reviewer.config.JwtService;
import reviewer.data.ReviewRepository;
import reviewer.data.UserRepository;
import reviewer.model.Review;
import reviewer.rest.ApiDraftController;
import reviewer.rest.ApiReviewController;
import reviewer.service.JwtExtractor;
import reviewer.util.ReviewKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ApiDraftControllerTest {

    
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
	private ApiDraftController apiDraftController;
	
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private void setDetails(Review review , Long paperId , String username)
    {
    	review.setReviewKey(new ReviewKey(paperId,username));
    	review.setReviewerStatus("draft");
    }
    
    
    @Test
	public void getDrafts()
	{
    	
    	String mockUsername = "taurn";
		ArrayList<Review> mockdraftList = new ArrayList<Review>();
		
		for(int i=0;i<5;i++)
    	{
    		Review review = new Review();
    		setDetails(review,(long)i,"tarun");
    		mockdraftList.add(review);
    		
    	}
		
		
    	when(jwtExtractor.getUsernameFromToken()).thenReturn(mockUsername);
    	when(reviewRepo.findAllByIdUserIdAndReviewStatus(mockUsername,"draft")).thenReturn(mockdraftList);
    	
    	assertEquals(mockdraftList, apiDraftController.drafts());
    	
		
	}
    
    
    
    
    

//    @Test
//    public void testDraftsSuccess() throws Exception {
//        // Arrange
//        String username = "test_user";
//        String jwtToken = "eyJhbGciOiJIUzI1NiJ9"; // Mock JWT with username
//        ArrayList<Review> expectedDrafts = new ArrayList<>(); // Use ArrayList directly
//        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
//        Mockito.when(jwtService.extractUsername(jwtToken)).thenReturn(username);
//        Mockito.when(reviewRepo.findAllByIdUserIdAndReviewStatus(username, "draft"))
//                .thenReturn(expectedDrafts);
//
//        // Act
//        Iterable<Review> actualDrafts = controller.drafts();
//
//        // Assert
//        assertEquals(expectedDrafts, actualDrafts);
//        verify(jwtService).extractUsername(jwtToken);
//        verify(reviewRepo).findAllByIdUserIdAndReviewStatus(username, "draft");
//    }
//
//    @Test
//    public void testDraftsEmpty() throws Exception {
//        // Arrange
//        String username = "test_user";
//        String jwtToken = "eyJhbGciOiJIUzI1NiJ9"; // Mock JWT with username
//        List<Review> expectedDrafts = Collections.emptyList();
//        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
//        Mockito.when(jwtService.extractUsername(jwtToken)).thenReturn(username);
//        Mockito.when(reviewRepo.findAllByIdUserIdAndReviewStatus(username, "draft"))
//                .thenReturn((ArrayList<Review>) expectedDrafts);
//
//        // Act
//        Iterable<Review> actualDrafts = controller.drafts();
//
//        // Assert
//        assertEquals(expectedDrafts, actualDrafts);
//        verify(jwtService).extractUsername(jwtToken);
//        verify(reviewRepo).findAllByIdUserIdAndReviewStatus(username, "draft");
//    }
//
//    @Test
//    public void testDraftsJwtServiceError() throws Exception {
//        // Arrange
//        String jwtToken = "invalid_token"; // Mock invalid JWT
//        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
//        Mockito.when(jwtService.extractUsername(jwtToken)).thenThrow(new RuntimeException("Mock JWT error"));
//
//    
//        assertThrows(RuntimeException.class, () -> controller.drafts());
//    }
}
