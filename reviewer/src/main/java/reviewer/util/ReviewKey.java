package reviewer.util;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Embeddable
public class ReviewKey implements Serializable{

	

	@Column(name="user_id")
	private String userId;
	
	@Column(name="paper_id")
	private Long paperId;
	
    public ReviewKey(){
		
	}

	public ReviewKey(Long paperId, String userId) {
		
		this.paperId = paperId;
		this.userId = userId;
		
	}

	public String getUserId() {
		return userId;
	}

	public Long getPaperId() {
		return paperId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(paperId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReviewKey other = (ReviewKey) obj;
		return Objects.equals(paperId, other.paperId) && Objects.equals(userId, other.userId);
	}
	
	
	
	

}