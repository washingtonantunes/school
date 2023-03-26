package br.com.wti.school.endpoint.v1.choice;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.wti.school.endpoint.v1.genericservice.GenericService;
import br.com.wti.school.persistence.model.Choice;
import br.com.wti.school.persistence.respository.ChoiceRepository;
import br.com.wti.school.persistence.respository.QuestionRepository;
import br.com.wti.school.util.EndpointUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Washington Antunes for wTI on 25/03/2023.
 */
@RestController
@RequestMapping("v1/professor/course/question/choice")
@Api(description = "Operations related to questions' choice")
public class ChoiceEndpoint {

	private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final GenericService service;
    private final EndpointUtil endpointUtil;
    
    @Autowired
    public ChoiceEndpoint(QuestionRepository questionRepository,
                          ChoiceRepository choiceRepository,
                          GenericService service,
                          EndpointUtil endpointUtil) {
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
        this.service = service;
        this.endpointUtil = endpointUtil;
    }
    
    @ApiOperation(value = "Return a choice based on it's id", response = Choice.class)
    @GetMapping(path = "{id}")
    public ResponseEntity<?> getChoiceById(@PathVariable long id) {
        return endpointUtil.returnObjectOrNotFound(choiceRepository.findOne(id));
    }
    
    @ApiOperation(value = "Return a list of choices related to the questionId", response = Choice[].class)
    @GetMapping(path = "list/{questionId}/")
    public ResponseEntity<?> listChoicesByQuestionId(@PathVariable long questionId) {
        return new ResponseEntity<>(choiceRepository.listChoicesByQuestionId(questionId), OK);
    }

    @ApiOperation(value = "Create choice and return the choice created",
            notes = "If this choice's correctAnswer is true all other choices' correctAnswer related to this question will be updated to false")
    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@Valid @RequestBody Choice choice) {
        validateChoicesQuestion(choice);
        choice.setProfessor(endpointUtil.extractProfessorFromToken());
        Choice savedChoice = choiceRepository.save(choice);
        updateChangingOtherChoicesCorrectAnswerToFalse(choice);
        return new ResponseEntity<>(savedChoice, OK);
    }

    @ApiOperation(value = "Update choice and return 200 Ok with no body",
            notes = "If this choice's correctAnswer is true all other choices' correctAnswer related to this question will be updated to false")
    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@Valid @RequestBody Choice choice) {
        validateChoicesQuestion(choice);
        updateChangingOtherChoicesCorrectAnswerToFalse(choice);
        choiceRepository.save(choice);
        return new ResponseEntity<>(OK);
    }

    @ApiOperation(value = "Delete a specific choice and return 200 Ok with no body")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        service.throwResourceNotFoundIfDoesNotExist(id, choiceRepository, "Choice was not found");
        choiceRepository.delete(id);
        return new ResponseEntity<>(OK);
    }


    private void validateChoicesQuestion(@Valid @RequestBody Choice choice) {
        service.throwResourceNotFoundIfDoesNotExist(choice.getQuestion(), questionRepository, "The question related to this choice was not found");
    }

    private void updateChangingOtherChoicesCorrectAnswerToFalse(Choice choice) {
        if (choice.isCorrectAnswer())
            choiceRepository.updateAllOtherChoicesCorrectAnswerToFalse(choice, choice.getQuestion());
    }
}
