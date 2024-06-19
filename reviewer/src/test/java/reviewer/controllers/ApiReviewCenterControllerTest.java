package reviewer.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;
import jakarta.servlet.http.HttpServletRequest;
import reviewer.config.JwtService;
import reviewer.data.PaperRepository;
import reviewer.data.ReviewRepository;
import reviewer.model.Paper;
import reviewer.model.Review;
import reviewer.rest.ApiReviewCenterController;
import reviewer.service.JwtExtractor;
import reviewer.util.ReviewKey;

public class ApiReviewCenterControllerTest {
	
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
	private ApiReviewCenterController apiReviewCenterController;
	
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    private void setDetails(Review review , Long paperId , String username)
    {
    	review.setReviewKey(new ReviewKey(paperId,username));
    	review.setReviewerStatus("accept");
    }
    
    @Test
    public void getToReviewTest()
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
    	when(reviewRepo.findAllByIdUserIdAndReviewerStatus(mockUsername,"accept")).thenReturn(reviewList);
    	
    	
    	assertEquals(reviewList, apiReviewCenterController.getToReview());
    		
    }
    
    @Test
    public void getPaperTest()
    {
    	Long mockPaperId = 1L;
    	Paper paper = new Paper(mockPaperId);
    	
    	
    	when(paperRepo.findPaperById(mockPaperId)).thenReturn(Optional.of(paper));
    	
    	assertEquals(paper,apiReviewCenterController.getPaper(mockPaperId));
    	
    }
    
    @Test
    public void getPaperTestFailure()
    {
    	Long mockPaperId = 1L;
    	
    	when(paperRepo.findPaperById(mockPaperId)).thenReturn(Optional.empty());
    	
    	assertEquals(null,apiReviewCenterController.getPaper(mockPaperId));
    	
    }
    
    


}

