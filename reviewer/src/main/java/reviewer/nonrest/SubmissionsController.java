package reviewer.nonrest;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import reviewer.data.ReviewRepository;
import reviewer.model.Review;
import reviewer.model.User;
import reviewer.util.ReviewForm;
import reviewer.util.ReviewKey;


@Controller
@RequestMapping("/submissions")
@SessionAttributes(("user"))
public class SubmissionsController {
	
	
	@Autowired
	private ReviewRepository reviewRepo;
	
	 /**
     * Handles GET requests to display the submissions page.
     *
     * @return the view name for the "submissions" page
     */
	@GetMapping
	public String submission(@ModelAttribute User user,Model model)
	{
		ArrayList<Review> SubmissionList = new ArrayList<Review>();
     	SubmissionList = reviewRepo.findAllByIdUserIdAndReviewStatus(user.getUsername(),"submit");
	    model.addAttribute("SubmissionList", SubmissionList);
		return "submissions";
	}
	
	  
	   /**
	    * Handles GET requests to view or clear a submission.<br>
	    * If <code>action</code> is equal to view<br>
	    *   It displays the submission associated with the given paperId and current logged-in user<br>
	    * If <code>action</code> is equal to delete<br>
	    *   It deletes the submission associated with the given paperId and current logged-in user<br>
	    * @param action   the action to perform (view or delete)
	    * @param paperId  the ID of the paper associated with the review
	    * @param model    the model to which attributes can be added
	    * @param user     the user object retrieved from the session.
	    * 
	    *  
	    * @return the view name based on the action performed
	    */
	@GetMapping("/{paperId}")
	public String getSubmission(@RequestParam(name = "action")String action ,@RequestParam("id") Long paperId,Model model,@ModelAttribute User user)
	{
		
		
		if(action.equals("view")) {
			
			Review review = reviewRepo.findById(new ReviewKey(paperId,user.getUsername())).get();
		    model.addAttribute("review",review);
		    //model.addAttribute("reviewForm",new ReviewForm(review));
			
			
			return "redirect:/review";
		}
		
		if(action.equals("delete")) {
			
			 // change reviewstatus to new from submit ...
			 // and clear that review
			 Review review = reviewRepo.findById(new ReviewKey(paperId,user.getUsername())).get();
	    	 review.modifyReview(new ReviewForm());
	    	 review.setReviewStatus("new");
	    	 reviewRepo.save(review);
			
			
			return "submissions";
		}
		
		
		System.out.println("not working");
		return "redirect:/error";
		
		
		
	    
	}
	
	
	/**
	    * Handles POST requests to edit submission.
	    * 
	    * If <code>action</code> is equal to edit<br>
	    *  It integrates the data from the reviewForm into the submitted review associated with the specified paperId and the currently logged-in user and updates the timestamp for submission<br>
	    * 
	    *   
	    * @param action      the action to perform (update or save)
	    * @param paperId     the ID of the paper associated with the review
	    * @param reviewForm  the review form data
	    * @param user        the user object retrieved from the session
	    * @return the redirect URL based on the action performed
	    */
	   @PostMapping
	   public String updateSubmission(@RequestParam(name = "action")String action ,@RequestParam("id") Long paperId,@ModelAttribute("reviewForm") ReviewForm reviewForm ,@ModelAttribute("user") User user)
	   {
		   
		   if(action.equals("edit")) {
				
			   Review review = reviewRepo.findById(new ReviewKey(paperId,user.getUsername())).get();
			   review.modifyReview(reviewForm);
			   review.setReviewStatus("submit");// for assurity
			 //  System.out.println(review.toString());
			   reviewRepo.save(review);
			  
				
			   return "redirect:/submissions";
		   }
		   
		  System.out.println("not working");
		  return "redirect:/error";
	   }


	public ReviewRepository getReviewRepo() {
		return reviewRepo;
	}


	public void setReviewRepo(ReviewRepository reviewRepo) {
		this.reviewRepo = reviewRepo;
	}

	   
   

}