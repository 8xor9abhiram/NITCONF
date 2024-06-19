package reviewer.rest;


import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import reviewer.service.JwtExtractor;
import reviewer.util.ReviewForm;
import reviewer.util.ReviewKey;


@RestController
@RequestMapping(path="/api/review" , produces="application/json")
public class ApiReviewController {
	
	
		@Autowired
		private HttpServletRequest request;
		
		@Autowired
		private JwtService jwtService;
	
		@Autowired
		private ReviewRepository reviewRepo;
		
		@Autowired
		private JwtExtractor jwtExtractor;
		
	 
	 
	    @GetMapping("/get")
		public ResponseEntity<Review> getReview(@RequestParam("paperId") Long paperId)
		{
	    	 String username = jwtExtractor.getUsernameFromToken();
		     Optional<Review> optReview = reviewRepo.findById(new ReviewKey(paperId,username));
		     
		     if(optReview.isPresent() == false)
		     {
		    	 return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);   
		     }
		     
		     Review review = optReview.get();
			 return new ResponseEntity<>(review,HttpStatus.OK);
		    
		}
	    
	    
	    @GetMapping("/decline")
		public ResponseEntity<Review> declineReview(@RequestParam("paperId") Long paperId)
		{
		     
	    	 String username = jwtExtractor.getUsernameFromToken();
	    	 
		     Optional<Review> optReview = reviewRepo.findById(new ReviewKey(paperId,username));
		     
		     if(optReview.isPresent() == false)
		     {
		    	 return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);   
		     }
		     
		     Review review = optReview.get();
		     review.setReviewerStatus("decline");  
		     reviewRepo.save(review);
		     
			 return new ResponseEntity<>(review,HttpStatus.OK);
		    
		}
	    
	    
	    
	    @GetMapping("/clear")
		public ResponseEntity<Review> clearReview(@RequestParam("paperId") Long paperId)
		{
		     
	    	 String username = jwtExtractor.getUsernameFromToken();
	    	 
		     Optional<Review> optReview = reviewRepo.findById(new ReviewKey(paperId,username));
		     
		     if(optReview.isPresent() == false)
		     {
		    	 return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);   
		     }
		     
		     Review review = optReview.get();
		     
	    	 review.modifyReview(new ReviewForm());
	    	 review.setReviewStatus("new");
		     reviewRepo.save(review);
		     return new ResponseEntity<>(review,HttpStatus.OK);	
		    
		    
	
		}

	    
	    
	    
	    @PostMapping("/save")
	    public ResponseEntity<Review> updateReview(@RequestParam("paperId") Long paperId,@RequestBody ReviewForm reviewForm )
	    {
	    	
	    	 String username = jwtExtractor.getUsernameFromToken();
             Optional<Review> optReview = reviewRepo.findById(new ReviewKey(paperId,username));
		     
		     if(optReview.isPresent() == false)
		     {
		    	 return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);   
		     }
		     
		     Review review = optReview.get();
			 review.modifyReview(reviewForm);
			 review.setReviewStatus("draft");
	 	  
	 		 reviewRepo.save(review);
	 		 return new ResponseEntity<>(review,HttpStatus.OK);
	 	  
	    }
	    
	    @PostMapping("/submit")
	    public ResponseEntity<Review> saveReview(@RequestParam("paperId") Long paperId,@RequestBody ReviewForm reviewForm )
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