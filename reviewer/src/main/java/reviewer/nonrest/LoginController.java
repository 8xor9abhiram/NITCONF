package reviewer.nonrest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@RequestMapping("/")
public class LoginController {
	
//	@Autowired 
//	private SecurityUserDetailsService userDetailsManager; 
//	@Autowired
//	private PasswordEncoder passwordEncoder; 
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoginController.class);

	@GetMapping("/login")
	public String login()
	{

		log.info("get-method reached");
		return "login";
	}

	 
	@PostMapping("/login")            //not entering to post method for login with spring security
	public String loginForm()
	{ 
		log.info("post-method reached");
		//check whether user exists
		return "redirect:/dasboard";
		
	}
}
