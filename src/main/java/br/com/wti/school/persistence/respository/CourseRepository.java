package br.com.wti.school.persistence.respository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.wti.school.persistence.model.Course;

/**
 * @author Washington Antunes for wTI on 22/03/23.
 */
public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {

	@Query("select c from Course c where c.id = ?1 and c.professor = ?#{principal.professor}")
	@Override
    Course findOne(Long aLong);
}
