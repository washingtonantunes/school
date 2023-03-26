package br.com.wti.school.endpoint.v1.question;

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
import br.com.wti.school.persistence.model.Question;
import br.com.wti.school.persistence.respository.QuestionRepository;
import br.com.wti.school.util.EndpointUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Washington Antunes for wTI on 25/03/2023.
 */
@RestController
@RequestMapping("v1/professor/question/question")
@Api(description = "Operations related to courses' question")
public class QuestionEndpoint {

	private final QuestionRepository questionRepository;
    private final GenericService service;
    private final EndpointUtil endpointUtil;
    
    @Autowired
    public QuestionEndpoint(QuestionRepository questionRepository,
    						GenericService service,
                            EndpointUtil endpointUtil) {
        this.questionRepository = questionRepository;
        this.service = service;
        this.endpointUtil = endpointUtil;
    }

    @ApiOperation(value = "Return a question based on it's id", response = Question.class)
    @GetMapping(path = "{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable long id) {
        return endpointUtil.returnObjectOrNotFound(questionRepository.findOne(id));
    }

    @ApiOperation(value = "Return a list of question related to course", response = Question.class)
    @GetMapping(path = "list")
    public ResponseEntity<?> listQuestions(@ApiParam("Course id") @RequestParam(value = "courseId") long courseId,
                                           @ApiParam("Question title") @RequestParam(value = "title", defaultValue = "") String name) {
        return new ResponseEntity<>(questionRepository.listQuestionsByCourseAndTitle(courseId, name), OK);
    }

    @ApiOperation(value = "Delete a specific question and return 200 Ok with no body")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
    	service.throwResourceNotFoundIfDoesNotExist(id, questionRepository, "Question not found");
        questionRepository.delete(id);
        return new ResponseEntity<>(OK);
    }

    @ApiOperation(value = "Update question and return 200 Ok with no body")
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Question question) {
    	service.throwResourceNotFoundIfDoesNotExist(question, questionRepository, "Question not found");
        questionRepository.save(question);
        return new ResponseEntity<>(OK);
    }

    @ApiOperation(value = "Create question and return the question created")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Question question) {
        question.setProfessor(endpointUtil.extractProfessorFromToken());
        return new ResponseEntity<>(questionRepository.save(question), OK);
    }
}