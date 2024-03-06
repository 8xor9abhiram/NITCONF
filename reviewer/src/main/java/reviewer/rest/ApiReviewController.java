package reviewer.rest;


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
import reviewer.util.ReviewForm;
import reviewer.util.ReviewKey;


@RestController
@RequestMapping(path="/api/review" , produces="application/json")
@SessionAttributes("user")
public class ApiReviewController {
	
	
		@Autowired
		private HttpServletRequest request;
		
		@Autowired
		private JwtService jwtService;
	
		@Autowired
		private ReviewRepository reviewRepo;
		
		
		
		private String getUsernameFromToken()
		{
		    String authHeader = request.getHeader("Authorization");
			    
			//	    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {     // no need of this as token will be verified before
			//	      return null;
			//	    }
			    
		    String jwt = authHeader.substring(7);  
		    String username = jwtService.extractUsername(jwt);
		    return username;
		}
	 
	 
	    @GetMapping("/view")
		public ResponseEntity<Review> getReview(@RequestParam("id") Long paperId)
		{
		     
	    	 String username = getUsernameFromToken();
	    	 
		     Optional<Review> optReview = reviewRepo.findById(new ReviewKey(paperId,username));
		     
		     if(optReview.isPresent() == false)
		     {
		    	 return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);   
		     }
		     
		     Review review = optReview.get();
		     System.out.println(review.toString());
			 return new ResponseEntity<>(review,HttpStatus.OK);
		    
		}
	    
	    
	    @GetMapping("/clear")
		public ResponseEntity<Review> clearReview(@RequestParam("id") Long paperId)
		{
		     
	    	 String username = getUsernameFromToken();
	    	 
		     Optional<Review> optReview = reviewRepo.findById(new ReviewKey(paperId,username));
		     
		     if(optReview.isPresent() == false)
		     {
		    	 return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);   
		     }
		     
		     Review review = optReview.get();
		  

		     reviewRepo.save(review);
		     return new ResponseEntity<>(review,HttpStatus.OK);	
		    
		    
	
		}

	    
	    
	    
	    @PostMapping("/save")
	    public ResponseEntity<Review> updateReview(@RequestParam("id") Long paperId,@RequestBody ReviewForm reviewForm )
	    {
	    	
	    	 String username = getUsernameFromToken();
             Optional<Review> optReview = reviewRepo.findById(new ReviewKey(paperId,username));
		     
		     if(optReview.isPresent() == false)
		     {
		    	 return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);   
		     }
		     
		     Review review = optReview.get();
	 	  
	 		 reviewRepo.save(review);
	 		 return new ResponseEntity<>(review,HttpStatus.OK);
	 	  
	    }
	    
	    @PostMapping("/submit")
	    public ResponseEntity<Review> saveReview(@RequestParam("id") Long paperId,@RequestBody ReviewForm reviewForm )
	    {
	    	
	    	 String username = getUsernameFromToken();
             Optional<Review> optReview = reviewRepo.findById(new ReviewKey(paperId,username));
		     
		     if(optReview.isPresent() == false)
		     {
		    	 return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);   
		     }
		     
		     Review review = optReview.get();
	 	   
	 		   //TODO fill the actions
	 		 return new ResponseEntity<>(null,HttpStatus.OK);
	    }
	
}