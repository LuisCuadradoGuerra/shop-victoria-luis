package es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByProductProductIdOrderByDateDesc(Long id);
}
