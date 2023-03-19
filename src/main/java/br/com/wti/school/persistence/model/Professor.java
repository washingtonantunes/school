package br.com.wti.school.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Washington Antunes for DevDojo on 19/03/23.
 */
@Getter
@Setter
@Entity
public class Professor extends AbstractEntity {
	
    @NotEmpty(message = "The field name cannot be empty")
    private String name;
    
    @Email(message = "This email is not valid")
    @NotEmpty(message = "The field email cannot be empty")
    @Column(unique = true)
    private String email;
}
