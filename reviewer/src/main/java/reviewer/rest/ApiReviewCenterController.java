package reviewer.rest;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import reviewer.config.JwtService;
import reviewer.data.PaperRepository;
import reviewer.data.ReviewRepository;
import reviewer.model.Paper;
import reviewer.model.Review;
import reviewer.service.JwtExtractor;


@RestController
@RequestMapping(path="/api" , produces="application/json")
public class ApiReviewCenterController {
	

	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	private ReviewRepository reviewRepo;
	
	@Autowired
	private PaperRepository paperRepo;
	
	@Autowired 
	private JwtExtractor jwtExtractor;
	
	
	@GetMapping("/get-to-review")
	public Iterable<Review> getToReview()
	{
		
		String username = jwtExtractor.getUsernameFromToken();
		ArrayList<Review> reviewList = new ArrayList<Review>();
		reviewList= reviewRepo.findAllByIdUserIdAndReviewerStatus(username,"accept");
	    return reviewList;
	}
	
	@GetMapping("/get-paper")
	public Paper getPaper(@RequestParam Long paperId)
	{
		Optional<Paper> optPaper =  paperRepo.findPaperById(paperId);
	
		if(optPaper.isPresent())
		{
			return optPaper.get();	
		}
		
		return null;
	}
		
		
	

	


}

