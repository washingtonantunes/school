package br.com.wti.school.persistence.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Washington Antunes for wTI on 25/03/2023.
 */
@Getter
@Setter
@Entity
public class Question extends AbstractEntity {

	private static final long serialVersionUID = -7074394572995487347L;
	
	@NotEmpty(message = "The field title cannot be empty")
    @ApiModelProperty(notes = "The title of the question")
    private String title;
    @ManyToOne
    private Course course;
    @ManyToOne
    private Professor professor;
}
