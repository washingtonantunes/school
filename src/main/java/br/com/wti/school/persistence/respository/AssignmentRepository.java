/**
 * 
 */
package br.com.wti.school.persistence.respository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.wti.school.persistence.model.Assignment;

/**
 * @author Washington Antunes for wTI on 26/03/23.
 */
public interface AssignmentRepository extends CustomPagingAndSortRepository<Assignment, Long> {

	@Query("select a from Assignment a where a.course.id = ?1 and a.title like %?2% and a.professor = ?#{principal.professor} and a.enabled = true")
    List<Assignment> listAssignemntsByCourseAndTitle(long courseId, String title);

    @Query("update Assignment a set a.enabled = false where a.course.id = ?1 and a.professor = ?#{principal.professor} and a.enabled = true")
    @Modifying
    void deleteAllAssignmentsRelatedToCourse(long courseId);
}
