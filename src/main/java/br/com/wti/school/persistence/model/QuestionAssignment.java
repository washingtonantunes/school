/**
 * 
 */
package br.com.wti.school.persistence.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Washington Antunes for wTI on 26/03/2023.
 */
@Getter
@Setter
@Entity
@ApiModel(description = "This will be the class responsible for generating the exam for the students,"
		+ "The sum of all questions must be equal to 100")
public class QuestionAssignment extends AbstractEntity {

	private static final long serialVersionUID = 1948987730710365149L;

	@ManyToOne(optional = false)
	private Professor professor;
	@ManyToOne
	@ApiModelProperty(notes = "The question with at least two choices and one of them is true")
	private Question question;
	@ManyToOne
	@ApiModelProperty(notes = "The assignment this question belongs to")
	private Assignment assignment;
	@ApiModelProperty(notes = "The grade value for the question")
	private double grade;
}
