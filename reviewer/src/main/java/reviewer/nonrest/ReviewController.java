package reviewer.nonrest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import reviewer.data.PaperRepository;
import reviewer.data.ReviewRepository;
import reviewer.model.Paper;
import reviewer.model.Review;
import reviewer.model.User;
import reviewer.util.ReviewForm;
import reviewer.util.ReviewKey;


@Controller
@RequestMapping("/review")
@SessionAttributes("user")
public class ReviewController {
	
	
   @Autowired
   private ReviewRepository reviewRepo;
   

   /**
    * Handles GET requests to view or clear a review.<br>
    * If <code>action</code> is equal to view<br>
    *   It displays the review associated with the given paperId and current logged-in user<br>
    * If <code>action</code> is equal to clear<br>
    *   It clears the review associated with the give paperId and current logged-in user<br>
    * @param action   the action to perform (view or clear)
    * @param paperId  the ID of the paper associated with the review
    * @param model    the model to which attributes can be added
    * @param user     the user object retrieved from the session.
    * 
    *  
    * @return the view name based on the action performed
    */
   @GetMapping
	public String getReview(@RequestParam(name = "action")String action ,@RequestParam("id") Long paperId,Model model,@ModelAttribute User user)
	{
	    if(action.equals("view"))
	    {
		    Review review = reviewRepo.findById(new ReviewKey(paperId,user.getUsername())).get();
		    model.addAttribute("review",review);
		   // model.addAttribute("reviewForm",new ReviewForm(review));
//		    System.out.println(review.toString());
		    
			return "review";
	    }
	    
	    if(action.equals("clear"))
	    {
	    	//System.out.println("hi clear");
	    	 Review review = reviewRepo.findById(new ReviewKey(paperId,user.getUsername())).get();
//	    	 review.modifyReview(new ReviewForm());
//	    	 review.setReviewStatus("new");
	    	 reviewRepo.save(review);
	    	 return "redirect:/drafts";
	    }
	    
	    System.out.println("not working");
		   return "redirect:/error";
	    
	    
	}
   
   
   /**
    * Handles POST requests to update or save a review.
    * 
    * If <code>action</code> is equal to update<br>
    *  It integrates the data from the reviewForm into the review associated with the specified paperId and the currently logged-in user.<br>
    * If <code>action</code> is equal to save<br>
    *  It integrates the data from the reviewForm into the review associated with the specified paperId and the currenlty logged-in user, marking it as submitted. 
    *   
    * @param action      the action to perform (update or save)
    * @param paperId     the ID of the paper associated with the review
    * @param reviewForm  the review form data
    * @param user        the user object retrieved from the session
    * @return the redirect URL based on the action performed
    */
   @PostMapping
   public String saveReview(@RequestParam(name = "action")String action ,@RequestParam("id") Long paperId,@ModelAttribute("reviewForm") ReviewForm reviewForm ,@ModelAttribute("user") User user)
   {
	   
	   if(action.equals("update"))
	   {
		  // System.out.println(paperId);
		 //  System.out.println(reviewForm.toString());
		   Review review = reviewRepo.findById(new ReviewKey(paperId,user.getUsername())).get();
//		   review.modifyReview(reviewForm);
//		   review.setReviewStatus("draft");
		 //  System.out.println(review.toString());
		   reviewRepo.save(review);
		   return "redirect:/drafts";
	   }
	   
	   if(action.equals("save"))
	   {
		   
		   //TODO fill the actions
		   return "redirect:/submissions";
	   }
	   
	   
	   System.out.println("not working");
	   return "redirect:/error";
	  
   }

	
}
