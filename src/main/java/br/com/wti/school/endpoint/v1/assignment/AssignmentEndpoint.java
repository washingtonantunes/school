package br.com.wti.school.endpoint.v1.assignment;

import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.wti.school.endpoint.v1.deleteservice.CascadeDeleteService;
import br.com.wti.school.endpoint.v1.genericservice.GenericService;
import br.com.wti.school.persistence.model.Assignment;
import br.com.wti.school.persistence.respository.AssignmentRepository;
import br.com.wti.school.persistence.respository.CourseRepository;
import br.com.wti.school.util.EndpointUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Washington Antunes for wTI on 26/03/23.
 */
@RestController
@RequestMapping("v1/professor/course/assignment")
@Api(description = "Operations related to courses' assignment")
public class AssignmentEndpoint {

	private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final GenericService service;
    private final EndpointUtil endpointUtil;
    private final CascadeDeleteService deleteService;

    @Autowired
    public AssignmentEndpoint(AssignmentRepository assignmentRepository,
                              CourseRepository courseRepository, GenericService service,
                              EndpointUtil endpointUtil,
                              CascadeDeleteService deleteService) {
        this.assignmentRepository = assignmentRepository;
        this.courseRepository = courseRepository;
        this.service = service;
        this.endpointUtil = endpointUtil;
        this.deleteService = deleteService;
    }

    @ApiOperation(value = "Return an assignment based on it's id", response = Assignment.class)
    @GetMapping(path = "{id}")
    public ResponseEntity<?> getAssignmentById(@PathVariable long id) {
        return endpointUtil.returnObjectOrNotFound(assignmentRepository.findOne(id));
    }

    @ApiOperation(value = "Return a list of assignments related to course", response = Assignment[].class)
    @GetMapping(path = "list/{courseId}/")
    public ResponseEntity<?> listAssignments(@PathVariable long courseId,
                                             @ApiParam("Assignment title") @RequestParam(value = "title", defaultValue = "") String title) {
        return new ResponseEntity<>(assignmentRepository.listAssignemntsByCourseAndTitle(courseId, title), OK);
    }

    @ApiOperation(value = "Delete a specific assignment return 200 Ok with no body")
    @DeleteMapping(path = "{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable long id) {
        validateAssignmentExistenceOnDB(id);
        assignmentRepository.delete(id);
        deleteService.deleteAssignmentAndAllRelatedEntities(id);
        return new ResponseEntity<>(OK);
    }

    @ApiOperation(value = "Update assignment and return 200 Ok with no body")
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Assignment assignment) {
        validateAssignmentExistenceOnDB(assignment.getId());
        assignmentRepository.save(assignment);
        return new ResponseEntity<>(OK);
    }

    private void validateAssignmentExistenceOnDB(Long id) {
        service.throwResourceNotFoundIfDoesNotExist(id, assignmentRepository, "Assignment not found");
    }

    @ApiOperation(value = "Create assignment and return the assignment created")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Assignment assignment) {
        service.throwResourceNotFoundIfDoesNotExist(assignment.getCourse(), courseRepository, "Course not found");
        assignment.setProfessor(endpointUtil.extractProfessorFromToken());
        return new ResponseEntity<>(assignmentRepository.save(assignment), OK);
    }
}
