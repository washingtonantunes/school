package br.com.wti.school.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Washington Antunes for DevDojo on 19/03/23.
 */
@Getter
@Setter
@Entity
public class ApplicationUser extends AbstractEntity {
	
    @NotEmpty(message = "The field username cannot be empty")
    @Column(unique = true)
    private String username;
    
    @NotEmpty(message = "The field password cannot be empty")
    private String password;
    
    @OneToOne
    private Professor professor;
}
