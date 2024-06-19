package reviewer.nonrest;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import reviewer.data.ReviewRepository;
import reviewer.model.Review;
import reviewer.model.User;

@Controller
@SessionAttributes(("user"))
public class DraftsController {
	
	
	@Autowired
	private ReviewRepository reviewRepo;
	
	 /**
     * Retrieves and displays the drafts associated with the logged-in user.
     * 
     * @param user  the user object retrieved from the session
     * @param model the model to which attributes can be added
     * @return the view name for the "drafts" page which contains the drafts of the user
     */
	@GetMapping("/drafts")
	public String drafts(@ModelAttribute User user,Model model) {
		
		ArrayList<Review> draftList = new ArrayList<Review>();
     	draftList = reviewRepo.findAllByIdUserIdAndReviewStatus(user.getUsername(),"draft");
	    model.addAttribute("draftList", draftList);
		return "drafts";
	}

}