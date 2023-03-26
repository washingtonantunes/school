/**
 * 
 */
package br.com.wti.school.endpoint.v1.deleteservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wti.school.persistence.respository.ChoiceRepository;
import br.com.wti.school.persistence.respository.CourseRepository;
import br.com.wti.school.persistence.respository.QuestionRepository;

/**
 * @author Washington Antunes for wTI on 26/03/2023.
 */
@Service
public class CascadeDeleteService {

	private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final CourseRepository courseRepository;
    
    @Autowired
    public CascadeDeleteService(QuestionRepository questionRepository, ChoiceRepository choiceRepository, CourseRepository courseRepository) {
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
        this.courseRepository = courseRepository;
    }

    public void cascadeDeleteCourseQuestionAndChoice(long courseId){
        courseRepository.delete(courseId);
        questionRepository.deleteAllQuestionsRelatedToCourse(courseId);
        choiceRepository.deleteAllChoicesRelatedToCourse(courseId);
    }
    
    public void cascadeDeleteQuestionAndChoice(long questionId){
        questionRepository.delete(questionId);
        choiceRepository.deleteAllChoicesRelatedToQuestion(questionId);
    }
}
