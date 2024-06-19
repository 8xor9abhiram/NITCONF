package reviewer.nonrest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class NonRestLoginController {
	
	  @GetMapping("/login")
	  public String login()
	  {
		 return "login";
	  }
	  
	  @GetMapping("/dashboard")
	  public String dashboard()
	  {
		  return "dashboard";
	  }
	  
	  
	  @GetMapping("/profile")
	  public String getProfile()
	  {
		  return "profile";
	  }
	  
	  @GetMapping("/edit-profile")
	  public String editProfile()
	  {
		  return "edit-profile";
	  }
	  
	  @GetMapping("/review-center")
	  public String toReview()
	  {
		  return "review-center";
	  }
	  
	  @GetMapping("/drafts")
	  public String drafts()
	  {
		  return "drafts";
	  }
	  
	  @GetMapping("/submissions")
	  public String submissions()
	  {
		  return "submissions";
	  }
	  
	  @GetMapping("/review/view")
	  public String viewReview()
	  {
		  return "review";
	  }
	  
	  
}
