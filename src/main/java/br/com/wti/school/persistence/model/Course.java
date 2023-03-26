package br.com.wti.school.persistence.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Washington Antunes for wTI on 22/03/23.
 */
@Getter
@Setter
@Entity
public class Course extends AbstractEntity {

	private static final long serialVersionUID = -6734497290490034501L;
	
	@NotEmpty(message = "The field name cannot be empty")
    @ApiModelProperty(notes = "The name of the course")
    private String name;
    @ManyToOne(optional = false)
    private Professor professor;
    
    public static final class Builder {
		private Course course;

        private Builder() {
            course = new Course();
        }

        public static Builder newCourse() {
            return new Builder();
        }

        public Builder id(Long id) {
            course.setId(id);
            return this;
        }

        public Builder name(String name) {
            course.setName(name);
            return this;
        }

        public Builder professor(Professor professor) {
            course.setProfessor(professor);
            return this;
        }

        public Course build() {
            return course;
        }
    }
}
