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
public class Choice extends AbstractEntity {

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

	public static final class Builder {
		private Choice choice;

		private Builder() {
			choice = new Choice();
		}

		public static Builder newChoice() {
			return new Builder();
		}

		public Builder id(Long id) {
			choice.setId(id);
			return this;
		}

		public Builder enabled(boolean enabled) {
			choice.setEnabled(enabled);
			return this;
		}

		public Builder title(String title) {
			choice.setTitle(title);
			return this;
		}

		public Builder correctAnswer(boolean correctAnswer) {
			choice.setCorrectAnswer(correctAnswer);
			return this;
		}

		public Builder question(Question question) {
			choice.setQuestion(question);
			return this;
		}

		public Builder professor(Professor professor) {
			choice.setProfessor(professor);
			return this;
		}

		public Choice build() {
			return choice;
		}
	}
}
