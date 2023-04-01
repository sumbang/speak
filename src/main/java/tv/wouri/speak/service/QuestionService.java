package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Question;
import tv.wouri.speak.repositories.AbonnementRepository;
import tv.wouri.speak.repositories.QuestionRepository;

@Service
public class QuestionService extends AbstractService<Question, Long> {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    protected JpaRepository<Question, Long> getRepository() {
        return questionRepository;
    }
}
