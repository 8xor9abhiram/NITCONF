package reviewer.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import reviewer.model.Review;
import reviewer.util.ReviewKey;

@Repository
public interface ReviewRepository extends JpaRepository<Review,ReviewKey> {
	
	ArrayList<Review> findAllByIdUserId(String userId);
	Optional<Review> findById(ReviewKey reviewKey);
	ArrayList<Review> findAllByIdUserIdAndReviewStatus(String userId, String reviewStatus);
	ArrayList<Review> findAllByIdUserIdAndReviewerStatus(String username, String string);

	

}