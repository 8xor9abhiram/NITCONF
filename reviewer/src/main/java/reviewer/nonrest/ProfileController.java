package reviewer.nonrest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import reviewer.model.Tags;
import reviewer.model.User;

@Controller
@RequestMapping("/profile")
@SessionAttributes("user")
public class ProfileController {
	
	
	@ModelAttribute("tags")
	public Tags addTags()
	{
		return new Tags();
	}
	
	
	/**
     * Displays the user profile page with user information.
     * The user object is added to session after login.
     * @param user the user object retrieved from the session 
     * @return the view name for the "profile" page
     */
	@GetMapping
	public String viewprofile(@SessionAttribute User user)
	{
		
		System.out.println(user.toString());
		return "profile";
	}


}
