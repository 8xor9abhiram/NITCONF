package reviewer.nonrest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import reviewer.data.ReviewRepository;
import reviewer.model.Paper;
import reviewer.model.Review;
import reviewer.model.Tags;
import reviewer.model.User;

@Controller
@RequestMapping("/to-review")
@SessionAttributes(("user"))
public class ToReviewController {
	
	
	@Autowired
	ReviewRepository reviewRepo;
	
	@ModelAttribute("paper")
	public Paper paper()
	{
		return new Paper();
	}
	
	@ModelAttribute("tag")
	public Tags tag()
	{
		return new Tags();
	}
	
	
	@ModelAttribute("review")
	public Review review()
	{
		return new Review();
	}
	
	
	/**
     * Handles GET requests to display papers available for review associated with current logged-in user.
     *
     * @param user  the user object retrieved from the session
     * @param model the model to which attributes can be added
     * @return the view name for the "to-review" page
     */
	@GetMapping
	public String toReview(@ModelAttribute User user , Model model)
	{
		
		//TODO search by review_status 
		ArrayList<Review> reviewList = new ArrayList<Review>();
		reviewList= reviewRepo.findAllByIdUserId(user.getUsername());
		
		model.addAttribute("reviewList", reviewList);
		return "to-review";
	}
	
	

}
