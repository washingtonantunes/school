package br.com.wti.school.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

/**
 * @author Washington Antunes for wTI on 19/03/23.
 */
@MappedSuperclass
@Data
public class AbstractEntity implements Serializable {

	private static final long serialVersionUID = -8078502217222991528L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
	@Column(columnDefinition = "boolean default true", nullable = false)
    private boolean enabled = true;
}
