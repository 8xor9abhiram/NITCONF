package reviewer.data;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import reviewer.model.Paper;



@Repository
public interface PaperRepository extends JpaRepository<Paper,Long>{
	
	Optional<Paper> findPaperById(Long Id);
}
 