package br.com.wti.school.endpoint.v1.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wti.school.exception.ResourceNotFoundException;
import br.com.wti.school.persistence.model.Question;
import br.com.wti.school.persistence.respository.QuestionRepository;

/**
 * @author Washington Antunes for wTI on 25/03/2023.
 */
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void throwResourceNotFoundIfQuestionDoesNotExist(Question question){
        if(question == null || question.getId() == null || questionRepository.findOne(question.getId()) == null)
            throw new ResourceNotFoundException("Question not found");
    }
    public void throwResourceNotFoundIfQuestionDoesNotExist(long questionId){
        if(questionId == 0 || questionRepository.findOne(questionId) == null)
            throw new ResourceNotFoundException("Question not found");
    }
}
