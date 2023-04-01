package tv.wouri.speak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tv.wouri.speak.models.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
