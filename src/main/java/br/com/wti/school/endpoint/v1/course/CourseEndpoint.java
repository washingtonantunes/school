package br.com.wti.school.endpoint.v1.course;

import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.wti.school.endpoint.v1.genericservice.GenericService;
import br.com.wti.school.persistence.model.Course;
import br.com.wti.school.persistence.respository.CourseRepository;
import br.com.wti.school.util.EndpointUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Washington Antunes for wTI on 22/03/23.
 */
@RestController
@RequestMapping("v1/professor/course")
@Api(description = "Operations related to professors' course")
public class CourseEndpoint {

	private final CourseRepository courseRepository;
	private final GenericService service;
	private final EndpointUtil endpointUtil;

	@Autowired
	public CourseEndpoint(CourseRepository courseRepository, GenericService service, EndpointUtil endpointUtil) {
		this.courseRepository = courseRepository;
		this.service = service;
		this.endpointUtil = endpointUtil;
	}

	@ApiOperation(value = "Return a course based on it's id", response = Course.class)
	@GetMapping(path = "{id}")
	public ResponseEntity<?> getCourseById(@PathVariable long id) {
		return endpointUtil.returnObjectOrNotFound(courseRepository.findOne(id));
	}

	@ApiOperation(value = "Return a list of courses related to professor", response = Course.class)
	@GetMapping(path = "list")
	public ResponseEntity<?> listCourses(@ApiParam("Course name") @RequestParam(value = "name", defaultValue = "") String name) {
		return new ResponseEntity<>(courseRepository.listCoursesByName(name), OK);
	}

	@ApiOperation(value = "Delete a specific course and return 200 Ok with no body")
	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		service.throwResourceNotFoundIfDoesNotExist(id, courseRepository, "Course not found");
		courseRepository.delete(id);
		return new ResponseEntity<>(OK);
	}
	
	@ApiOperation(value = "Update course and return 200 Ok with no body")
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Course course) {
		service.throwResourceNotFoundIfDoesNotExist(course, courseRepository, "Course not found");
        courseRepository.save(course);
        return new ResponseEntity<>(OK);
    }
	
	@ApiOperation(value = "Create course and return the course created")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Course course) {
        course.setProfessor(endpointUtil.extractProfessorFromToken());
        return new ResponseEntity<>(courseRepository.save(course), OK);
    }
}
