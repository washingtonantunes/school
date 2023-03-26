/**
 * 
 */
package br.com.wti.school.endpoint.v1.deleteservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wti.school.persistence.respository.AssignmentRepository;
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
	private final AssignmentRepository assignmentRepository;

	@Autowired
	public CascadeDeleteService(QuestionRepository questionRepository, ChoiceRepository choiceRepository,
			CourseRepository courseRepository, AssignmentRepository assignmentRepository) {
		this.questionRepository = questionRepository;
		this.choiceRepository = choiceRepository;
		this.courseRepository = courseRepository;
		this.assignmentRepository = assignmentRepository;
	}

	public void deleteCourseAndAllRelatedEntities(long courseId) {
		courseRepository.delete(courseId);
		questionRepository.deleteAllQuestionsRelatedToCourse(courseId);
		choiceRepository.deleteAllChoicesRelatedToCourse(courseId);
		assignmentRepository.deleteAllAssignmentsRelatedToCourse(courseId);
	}

	public void deleteQuestionAndAllRelatedEntities(long questionId) {
		questionRepository.delete(questionId);
		choiceRepository.deleteAllChoicesRelatedToQuestion(questionId);
	}
}
