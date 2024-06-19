package reviewer.rest;

import java.util.ArrayList;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import reviewer.config.JwtService;
import reviewer.data.TagsRepository;
import reviewer.data.UserRepository;
import reviewer.model.Tags;
import reviewer.model.User;
import reviewer.service.JwtExtractor;
import reviewer.util.ReviewForm;


@RestController
@RequestMapping(path="/api" , produces="application/json")
public class ApiEditProfileController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TagsRepository tagsRepo;
	
	@Autowired
	private JwtExtractor jwtExtractor;
	
	
	
	@GetMapping("/all-tags")
	public Iterable<Tags> getAllTags()
	{
		ArrayList<Tags> tags = tagsRepo.findAll();
		return tags;
	}
	
	@GetMapping("/get-profile")
	public User getUserDetails()
	{
	    String username = jwtExtractor.getUsernameFromToken();
	   Optional<User> optUser = userRepo.findById(username);
		
		if(optUser.isPresent())
		{
			return userRepo.findById(username).get();	
		}
		
		return null;
		
	}
	
	@PostMapping(path= "/edit-profile" ,consumes = "application/json")
	public ResponseEntity<User> editProfile(@RequestBody User user1)
	{
		
	   String username = jwtExtractor.getUsernameFromToken();
	   Optional<User> optUser = userRepo.findById(username);
	   System.out.println(user1.getUsername());
		System.out.println(username);
	   
	   
		
		if(optUser.isPresent())
		{
			User user = optUser.get();

			if(user1.getUsername().equals(user.getUsername()))
			{
				user.modify(user1);
				userRepo.save(user);
				return new ResponseEntity<>(user,HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
				
				
		}
		
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		
	}
	
	

}
