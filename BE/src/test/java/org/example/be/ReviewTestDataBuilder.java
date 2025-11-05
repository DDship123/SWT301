package org.example.be;
import org.example.be.entity.Review;
import org.example.be.dto.response.ReviewResponse;

public class ReviewTestDataBuilder {
    public static Review createDefaultReview() {
        Review review = new Review();
        review.setReviewsId(1);
        review.setComment("Great product!");
        review.setStatus("ACTIVE");
        review.setRating(5);
        return review;
    }
    public static Review createReviewWithId(int id) {
        Review review = createDefaultReview();
        review.setReviewsId(id);
        return review;
    }
    public static ReviewResponse createDefaultReviewResponse() {
        ReviewResponse response = new ReviewResponse();
        response.setReviewId(1);
        response.setComment("Great product!");
        response.setStatus("ACTIVE");
        response.setRating(5);
        return response;
    }
}
