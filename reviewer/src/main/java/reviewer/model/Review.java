package reviewer.model;


import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import reviewer.util.ReviewForm;
import reviewer.util.ReviewKey;


@Entity
@Data
public class Review {

	@EmbeddedId
	private ReviewKey id;
	
	@Column
	private Integer technical;
	
	@Column
	private Integer significance;
	
	@Column
	private Integer originality;
	
	@Column
	private Integer appropriate;
	
	@Column
	private Integer organization;
	
	@Column
	private Integer style;
	
	@Column
	private Integer refeersConfidence;
	
	@Column
	private Integer overall;
	
	@Column
	private Calendar deadline;
	
	@Column
	private Calendar submissionTime;
	
	@Column
	private Calendar excuse;
	
	@Column
	private String comment;
	
	//values = accept , decline
	@Column(columnDefinition = "varchar(255) default 'accept'")
	private String reviewerStatus;
	
	
	// values = new , draft , submit
	@Column(columnDefinition = "varchar(255) default 'new'")
	private String reviewStatus;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JoinColumn(name="user_id")
	User user;
	
	@JsonIgnore
	@ManyToOne 
	@MapsId("paperId")
	@JoinColumn(name="paper_id")
	Paper paper;
	
	public Review()
	{
		
	}
	
	public Review(ReviewKey reviewKey)
	{
		this.id = reviewKey;
	}
	

	public void modifyReview(ReviewForm review) {

 		this.originality = review.getOriginality();
 		this.technical = review.getTechnical();
 		this.significance = review.getSignificance();
 		this.originality  = review.getOriginality();
 		this.appropriate = review.getAppropriate();
 		this.organization = review.getOrganization();
 		this.style = review.getStyle();
 		this.refeersConfidence = review.getRefeersConfidence();
 		this.overall  = review.getOverall();
 		this.comment   = review.getComment();

 	}
	
	public Boolean checkReviewForm(ReviewForm review) {

		
 		return this.originality ==  review.getOriginality() && 
 		this.technical == review.getTechnical() &&
 		this.significance == review.getSignificance() &&
 		this.originality  == review.getOriginality() &&
 		this.appropriate == review.getAppropriate() &&
 		this.organization == review.getOrganization() && 
 		this.style == review.getStyle() && 
 		this.refeersConfidence ==  review.getRefeersConfidence() &&
 		this.overall  == review.getOverall() &&
 		this.comment  == review.getComment();
 		


 	}
	
	public void setReviewStatus(String reviewStatus) {
 		this.reviewStatus = reviewStatus;
 	}

	public void setReviewKey(ReviewKey reviewKey) {
		
		this.id = reviewKey;
		
	}

	public ReviewKey getId() {
		
		return this.id;
	}
	

	
	public void setSubmissionTime(Calendar submissionTime)
	{
		this.submissionTime = submissionTime;
	}

	public Integer getTechnical() {
		return technical;
	}

	public void setTechnical(Integer technical) {
		this.technical = technical;
	}

	public Integer getSignificance() {
		return significance;
	}

	public void setSignificance(Integer significance) {
		this.significance = significance;
	}

	public Integer getOriginality() {
		return originality;
	}

	public void setOriginality(Integer originality) {
		this.originality = originality;
	}

	public Integer getAppropriate() {
		return appropriate;
	}

	public void setAppropriate(Integer appropriate) {
		this.appropriate = appropriate;
	}

	public Integer getOrganization() {
		return organization;
	}

	public void setOrganization(Integer organization) {
		this.organization = organization;
	}

	public Integer getStyle() {
		return style;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	public Integer getRefeersConfidence() {
		return refeersConfidence;
	}

	public void setRefeersConfidence(Integer refeersConfidence) {
		this.refeersConfidence = refeersConfidence;
	}

	public Integer getOverall() {
		return overall;
	}

	public void setOverall(Integer overall) {
		this.overall = overall;
	}

	public Calendar getDeadline() {
		return deadline;
	}

	public void setDeadline(Calendar deadline) {
		this.deadline = deadline;
	}

	public Calendar getExcuse() {
		return excuse;
	}

	public void setExcuse(Calendar excuse) {
		this.excuse = excuse;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getReviewerStatus() {
		return reviewerStatus;
	}

	public void setReviewerStatus(String reviewerStatus) {
		this.reviewerStatus = reviewerStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public Calendar getSubmissionTime() {
		return submissionTime;
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setId(ReviewKey id) {
		this.id = id;
	}
	
	
	
	
	
	
	

	
	
	
}