package br.com.wti.school.persistence.respository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import br.com.wti.school.persistence.model.Course;

/**
 * @author Washington Antunes for wTI on 22/03/23.
 */
public interface CourseRepository extends CustomPagingAndSortRepository<Course, Long> {

	@Query("select c from Course c where c.name like %?1% and c.professor = ?#{principal.professor} and c.enabled = true")
	List<Course> listCourses(String name);
}
