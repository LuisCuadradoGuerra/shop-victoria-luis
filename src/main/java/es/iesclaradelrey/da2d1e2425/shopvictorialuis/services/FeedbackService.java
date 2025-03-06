package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Feedback;
import java.util.List;
import java.util.Optional;

public interface FeedbackService {
    long count();
    Feedback save(Feedback feedback);
    List<Feedback> findAll();
    Optional<Feedback> findById(Long id);
    List<Feedback> findByProductId(Long id);
}
