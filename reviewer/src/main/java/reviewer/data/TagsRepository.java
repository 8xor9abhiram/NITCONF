package reviewer.data;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewer.model.Tags;


@Repository
public interface  TagsRepository extends JpaRepository<Tags,Long>{

	 Tags findByName(String name);
	 ArrayList<Tags> findAll(); 
}
