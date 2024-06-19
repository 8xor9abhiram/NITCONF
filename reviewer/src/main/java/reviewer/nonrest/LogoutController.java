package reviewer.nonrest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LogoutController {
	
	@GetMapping("/logout")
	public String logout()
	{
		return "login";
	}
	
	
}
