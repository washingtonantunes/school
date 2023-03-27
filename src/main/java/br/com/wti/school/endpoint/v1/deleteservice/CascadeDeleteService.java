/**
 * 
 */
package br.com.wti.school.endpoint.v1.deleteservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wti.school.persistence.respository.AssignmentRepository;
import br.com.wti.school.persistence.respository.ChoiceRepository;
import br.com.wti.school.persistence.respository.CourseRepository;
import br.com.wti.school.persistence.respository.QuestionAssignmentRepository;
import br.com.wti.school.persistence.respository.QuestionRepository;

/**
 * @author Washington Antunes for wTI on 26/03/2023.
 */
@Service
public class CascadeDeleteService {

	private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final CourseRepository courseRepository;
    private final AssignmentRepository assignmentRepository;
    private final QuestionAssignmentRepository questionAssignmentRepository;

    @Autowired
    public CascadeDeleteService(QuestionRepository questionRepository, ChoiceRepository choiceRepository, CourseRepository courseRepository, AssignmentRepository assignmentRepository, QuestionAssignmentRepository questionAssignmentRepository) {
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
        this.courseRepository = courseRepository;
        this.assignmentRepository = assignmentRepository;
        this.questionAssignmentRepository = questionAssignmentRepository;
    }

    public void deleteCourseAndAllRelatedEntities(long courseId){
        courseRepository.delete(courseId);
        questionRepository.deleteAllQuestionsRelatedToCourse(courseId);
        choiceRepository.deleteAllChoicesRelatedToCourse(courseId);
        assignmentRepository.deleteAllAssignmentsRelatedToCourse(courseId);
        questionAssignmentRepository.deleteAllQuestionAssignmentsRelatedToCourse(courseId);
    }

    public void deleteAssignmentAndAllRelatedEntities(long assignmentId) {
        assignmentRepository.delete(assignmentId);
        questionAssignmentRepository.deleteAllQuestionAssignmentsRelatedToAssignment(assignmentId);
    }

    public void deleteQuestionAndAllRelatedEntities(long questionId){
        questionRepository.delete(questionId);
        choiceRepository.deleteAllChoicesRelatedToQuestion(questionId);
        questionAssignmentRepository.deleteAllQuestionAssignmentsRelatedToQuestion(questionId);
    }

    public void deleteQuestionAssignmentAndAllRelatedEntities(long questionAssignmentId) {
        questionAssignmentRepository.delete(questionAssignmentId);
    }
}
