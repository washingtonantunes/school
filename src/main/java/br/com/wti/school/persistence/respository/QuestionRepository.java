package br.com.wti.school.persistence.respository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.wti.school.persistence.model.Professor;
import br.com.wti.school.persistence.model.Question;

/**
 * @author Washington Antunes for wTI on 25/03/2023.
 */
public interface QuestionRepository extends CustomPagingAndSortRepository<Question, Long> {
	
    @Query("select q from Question q where q.course.id = ?1 and q.title like %?2% and q.professor = ?#{principal.professor} and q.enabled = true")
    List<Question> listQuestionsByCourseAndTitle(long courseId, String title);
    
    @Query("select q from Question q where q.course.id = ?1 and q.id not in (select qa.question.id from QuestionAssignment qa where qa.assignment.id = ?2 and qa.professor = ?3 and qa.enabled = true) and q.enabled = true and q.professor = ?3")
    @Transactional
    List<Question> listQuestionsByCourseWithoutOnAssignment(long courseId, long assignmentId, Professor professor);
    
    @Query("update Question q set q.enabled = false where q.course.id = ?1 and q.professor = ?#{principal.professor} and q.enabled = true")
    @Modifying
    void deleteAllQuestionsRelatedToCourse(long courseId);
    
    @Query("select q from Question q where q.course.id = ?1 and q.id not in " +
            "(select qa.question.id from QuestionAssignment qa where qa.assignment.id = ?2 and qa.professor = ?#{principal.professor} and qa.enabled = true) " +
            "and q.professor = ?#{principal.professor} and q.enabled = true")
    @Transactional
    List<Question> listQuestionsByCourseNotAssociatedWithAnAssignment(long courseId, long assigmentId);
}
