package br.com.wti.school.persistence.respository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.wti.school.persistence.model.QuestionAssignment;

/**
 * @author Washington Antunes for wTI on 26/03/2023.
 */
public interface QuestionAssignmentRepository extends CustomPagingAndSortRepository<QuestionAssignment, Long> {
	
	@Query("update QuestionAssignment qa set qa.enabled = false where qa.assignment.id in (select a.id from Assignment a where a.course.id = ?1) and qa.professor = ?#{principal.professor} and qa.enabled = true")
	@Modifying
	void deleteAllQuestionAssignmentsRelatedToCourse(long courseId);

	@Query("update QuestionAssignment qa set qa.enabled = false where qa.assignment.id = ?1 and qa.professor = ?#{principal.professor} and qa.enabled = true")
	@Modifying
	void deleteAllQuestionAssignmentsRelatedToAssignment(long assignmentId);

	@Query("update QuestionAssignment qa set qa.enabled = false where qa.question.id = ?1 and qa.professor = ?#{principal.professor} and qa.enabled = true")
	@Modifying
	void deleteAllQuestionAssignmentsRelatedToQuestion(long questionId);

	@Query("select qa from QuestionAssignment qa where qa.question.id = ?1 and qa.assignment.id = ?2 and qa.professor = ?#{principal.professor} and qa.enabled = true")
	List<QuestionAssignment> listQuestionAssignmentByQuestionAndAssignment(long questionId, long assignmentId);
	
	@Query("select qa from QuestionAssignment qa where  qa.assignment.id = ?1 and qa.professor = ?#{principal.professor} and qa.enabled = true")
    List<QuestionAssignment> listQuestionAssignmentByAssignmentId(long assignmentId);
}
