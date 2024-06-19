package reviewer.model;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;



@Entity
@Table(name="tags")
public class Tags {

	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@Column(name="name")
	private String name;
	
	

	@ManyToMany(mappedBy = "tags")
	private List<Paper> paper = new ArrayList<>();
	
	@ManyToMany(mappedBy = "tags")
	private List<User> user = new ArrayList<>();
	
	

	public Tags()
	{
		
	}
	
	public Tags(Long id, String name) {
		super();
		Id = id;
		this.name = name;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
