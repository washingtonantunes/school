package br.com.wti.school.persistence.respository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.wti.school.persistence.model.Question;

/**
 * @author Washington Antunes for wTI on 25/03/2023.
 */
public interface QuestionRepository extends CustomPagingAndSortRepository<Question, Long> {
	
    @Query("select q from Question q where q.course.id = ?1 and q.title like %?2% and q.professor = ?#{principal.professor} and q.enabled = true")
    List<Question> listQuestionsByCourseAndTitle(long courseId, String title);
    
    @Query("update Question q set q.enabled = false where q.course.id = ?1 and q.professor = ?#{principal.professor} and q.enabled = true")
    @Modifying
    void deleteAllQuestionsRelatedToCourse(long courseId);
}
