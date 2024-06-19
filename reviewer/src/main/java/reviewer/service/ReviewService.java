

package reviewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import reviewer.data.ReviewRepository;
import reviewer.model.Review;
import reviewer.util.ReviewForm;
import reviewer.util.ReviewKey;

public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    /**
     * Finds a review by the given user and paper ID.
     *
     * @param username the username of the reviewer
     * @param paperId   the ID of the paper associated with the review
     * @return an Optional containing the review object if found, or empty Optional otherwise
     */
    public Optional<Review> findReviewByUserAndPaperId(String username, Long paperId) {
        ReviewKey reviewKey = new ReviewKey( paperId,username);
        return reviewRepo.findById(reviewKey);
    }

    /**
     * Deletes the given review.
     *
     * @param review the review to be deleted
     */
    public void deleteReview(Review review) {
        reviewRepo.delete(review);
    }

    /**
     * Updates the given review with data from the review form.
     *
     * @param review    the review to be updated
     * @param reviewForm the review form data containing updates
     */
    public void updateReview(Review review, ReviewForm reviewForm) {
        // Implement logic to update review properties based on reviewForm data
        // (e.g., review.setRecommendation(reviewForm.getRecommendation());)
        reviewRepo.save(review);
    }
}
