package br.com.wti.school.persistence.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Washington Antunes for wTI on 26/03/2023.
 */
@Getter
@Setter
@Entity
public class Choice extends AbstractEntity{

	private static final long serialVersionUID = -5415438648913158113L;
	
	@NotEmpty(message = "The field title cannot be empty")
    @ApiModelProperty(notes = "The title of the choice")
    private String title;
    @NotNull(message = "The field correctAnswer must be true or false")
    @ApiModelProperty(notes = "Correct answer for the associated question, you can have only one correct answer per question")
    private boolean correctAnswer;
    @ManyToOne(optional = false)
    private Question question;
    @ManyToOne(optional = false)
    private Professor professor;
}
