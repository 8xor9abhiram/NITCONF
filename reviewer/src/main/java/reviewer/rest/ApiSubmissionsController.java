package reviewer.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import reviewer.config.JwtService;
import reviewer.data.ReviewRepository;
import reviewer.model.Review;
import reviewer.model.User;
import reviewer.service.JwtExtractor;
import reviewer.util.ReviewForm;
import reviewer.util.ReviewKey;

@RestController
@RequestMapping(path="/api" , produces="application/json")
public class ApiSubmissionsController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	private ReviewRepository reviewRepo;
	
	@Autowired
	private JwtExtractor jwtExtractor;
	

	
	@GetMapping("/get-submissions")
	public Iterable<Review> getSubmissions() 
	{

	    String username = jwtExtractor.getUsernameFromToken();
		ArrayList<Review> submissionsList = new ArrayList<Review>();
		
		//TODO change to accepted only
     	submissionsList = reviewRepo.findAllByIdUserIdAndReviewStatus(username,"submit");    
		return submissionsList;
	}
	
	@GetMapping("/delete-submission")
	public ResponseEntity<Review> deleteSubmission(@RequestParam("paperId") Long paperId)
	{
		

		String username = jwtExtractor.getUsernameFromToken();
        Optional<Review> optReview = reviewRepo.findById(new ReviewKey(paperId,username));
	     
	     if(optReview.isPresent() == false)
	     {
	    	 return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);   
	     }
	     
	        
	     Review review = optReview.get();
	     review.modifyReview(new ReviewForm());
	     review.setSubmissionTime(null);
	     review.setReviewStatus("new");
	     reviewRepo.save(review);
		 return new ResponseEntity<>(review,HttpStatus.OK);
		 
	}
	
	
	
	
	@PostMapping("/edit-submission")
	public ResponseEntity<Review> editSubmission(@RequestParam("paperId") Long paperId,@RequestBody ReviewForm reviewForm )
	{
		String username = jwtExtractor.getUsernameFromToken();
        Optional<Review> optReview = reviewRepo.findById(new ReviewKey(paperId,username));
	     
	     if(optReview.isPresent() == false)
	     {
	    	 return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);   
	     }
	     
	     //TODO fill the actions
	     // add submission date for submissions
	     //check for excuse date for submission
	        
	     Review review = optReview.get();
	     review.modifyReview(reviewForm);
	     review.setSubmissionTime(Calendar.getInstance());
	     review.setReviewStatus("submit");
	     reviewRepo.save(review);
		
		 return new ResponseEntity<>(review,HttpStatus.OK);
	}
	
	
	

}