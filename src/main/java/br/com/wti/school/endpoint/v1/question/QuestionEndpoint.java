package br.com.wti.school.endpoint.v1.question;

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
import br.com.wti.school.persistence.model.Question;
import br.com.wti.school.persistence.respository.CourseRepository;
import br.com.wti.school.persistence.respository.QuestionRepository;
import br.com.wti.school.util.EndpointUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Washington Antunes for wTI on 25/03/2023.
 */
@RestController
@RequestMapping("v1/professor/course/question")
@Api(description = "Operations related to courses' question")
public class QuestionEndpoint {

	private final QuestionRepository questionRepository;
	private final CourseRepository courseRepository;
    private final GenericService service;
    private final CascadeDeleteService deleteService;
    private final EndpointUtil endpointUtil;
    
    @Autowired
    public QuestionEndpoint(QuestionRepository questionRepository,
    						CourseRepository courseRepository,
    						GenericService service,
    						CascadeDeleteService deleteService,
                            EndpointUtil endpointUtil) {
        this.questionRepository = questionRepository;
        this.courseRepository = courseRepository;
        this.service = service;
        this.deleteService = deleteService;
        this.endpointUtil = endpointUtil;
    }

    @ApiOperation(value = "Return a question based on it's id", response = Question.class)
    @GetMapping(path = "{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable long id) {
        return endpointUtil.returnObjectOrNotFound(questionRepository.findOne(id));
    }

    @ApiOperation(value = "Return a list of question related to course", response = Question.class)
    @GetMapping(path = "list/{courseId}/")
    public ResponseEntity<?> listQuestions(@PathVariable long courseId, @ApiParam("Question title") @RequestParam(value = "title", defaultValue = "") String title) {
        return new ResponseEntity<>(questionRepository.listQuestionsByCourseAndTitle(courseId, title), OK);
    }

    @ApiOperation(value = "Delete a specific question and all related choices and return 200 Ok with no body")
    @DeleteMapping(path = "{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable long id) {
    	validateQuestionExistenceOnDB(id);
    	deleteService.deleteQuestionAndAllRelatedEntities(id);
        return new ResponseEntity<>(OK);
    }

    @ApiOperation(value = "Update question and return 200 Ok with no body")
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Question question) {
    	validateQuestionExistenceOnDB(question.getId());
        questionRepository.save(question);
        return new ResponseEntity<>(OK);
    }

    @ApiOperation(value = "Create question and return the question created", response = Question.class)
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Question question) {
    	service.throwResourceNotFoundIfDoesNotExist(question.getCourse(), courseRepository, "Course not found");
        question.setProfessor(endpointUtil.extractProfessorFromToken());
        return new ResponseEntity<>(questionRepository.save(question), OK);
    }
    
    private void validateQuestionExistenceOnDB(Long id) {
        service.throwResourceNotFoundIfDoesNotExist(id, questionRepository, "Question not found");
    }
}
