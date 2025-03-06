package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Feedback;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic.FeedbackRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public long count() {
        return feedbackRepository.count();
    }

    @Override
    public Feedback save(Feedback feedback) {
        feedbackRepository.save(feedback);
        return feedback;
    }

    @Override
    public List<Feedback> findAll() {return feedbackRepository.findAll();}

    @Override
    public Optional<Feedback> findById(Long id) {
        return feedbackRepository.findById(id);
    }

    @Override
    public List<Feedback> findByProductId(Long id) {
        return feedbackRepository.findByProductProductIdOrderByDateDesc(id);
    }
}