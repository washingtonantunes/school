/**
 * 
 */
package br.com.wti.school.persistence.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Washington Antunes for wTI on 26/03/23.
 */
@Getter
@Setter
@Entity
public class Assignment extends AbstractEntity {

	private static final long serialVersionUID = -7469186905681406216L;

	@NotEmpty(message = "The field title cannot be empty")
	@ApiModelProperty(notes = "The title of the assignment")
	private String title;
	private LocalDateTime createdAt = LocalDateTime.now();
	@ManyToOne(optional = false)
	private Course course;
	@ManyToOne(optional = false)
	private Professor professor;

	public static final class Builder {
		private Assignment assignment;

		private Builder() {
			assignment = new Assignment();
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public Builder id(Long id) {
			assignment.setId(id);
			return this;
		}

		public Builder enabled(boolean enabled) {
			assignment.setEnabled(enabled);
			return this;
		}

		public Builder title(String title) {
			assignment.setTitle(title);
			return this;
		}

		public Builder createdAt(LocalDateTime createdAt) {
			assignment.setCreatedAt(createdAt);
			return this;
		}

		public Builder course(Course course) {
			assignment.setCourse(course);
			return this;
		}

		public Builder professor(Professor professor) {
			assignment.setProfessor(professor);
			return this;
		}

		public Assignment build() {
			return assignment;
		}
	}
}
