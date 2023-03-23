package br.com.wti.school.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Washington Antunes for wTI on 19/03/23.
 */
@Getter
@Setter
@Entity
public class Professor extends AbstractEntity {

	private static final long serialVersionUID = 4824987199632653694L;

	@NotEmpty(message = "The field name cannot be empty")
	private String name;

	@Email(message = "This email is not valid")
	@NotEmpty(message = "The field email cannot be empty")
	@Column(unique = true)
	private String email;

	public static final class Builder {
		private Professor professor;

		private Builder() {
			professor = new Professor();
		}

		public static Builder newProfessor() {
			return new Builder();
		}

		public Builder name(String name) {
			professor.setName(name);
			return this;
		}

		public Builder id(Long id) {
			professor.setId(id);
			return this;
		}

		public Builder email(String email) {
			professor.setEmail(email);
			return this;
		}

		public Professor build() {
			return professor;
		}
	}
}
