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

	public static final class Builder {
		private QuestionAssignment questionAssignment;

		private Builder() {
			questionAssignment = new QuestionAssignment();
		}

		public static Builder newQuestionAssignment() {
			return new Builder();
		}

		public Builder id(Long id) {
			questionAssignment.setId(id);
			return this;
		}

		public Builder enabled(boolean enabled) {
			questionAssignment.setEnabled(enabled);
			return this;
		}

		public Builder professor(Professor professor) {
			questionAssignment.setProfessor(professor);
			return this;
		}

		public Builder question(Question question) {
			questionAssignment.setQuestion(question);
			return this;
		}

		public Builder assignment(Assignment assignment) {
			questionAssignment.setAssignment(assignment);
			return this;
		}

		public Builder grade(double grade) {
			questionAssignment.setGrade(grade);
			return this;
		}

		public QuestionAssignment build() {
			return questionAssignment;
		}
	}
}
