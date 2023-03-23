package br.com.wti.school.endpoint.v1.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wti.school.exception.ResourceNotFoundException;
import br.com.wti.school.persistence.model.Course;
import br.com.wti.school.persistence.respository.CourseRepository;

/**
 * @author Washington Antunes for wTI on 22/03/23.
 */
@Service
public class CourseService {

	private final CourseRepository courseRepository;

	@Autowired
	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public void throwResourceNotFoundIfCourseDoesNotExist(Course course) {
		if (course == null || course.getId() == null || courseRepository.findOne(course.getId()) == null)
			throw new ResourceNotFoundException("Course not found");
	}
	
	public void throwResourceNotFoundIfCourseDoesNotExist(long courseId) {
		if (courseId == 0 || courseRepository.findOne(courseId) == null)
			throw new ResourceNotFoundException("Course not found");
	}
}
