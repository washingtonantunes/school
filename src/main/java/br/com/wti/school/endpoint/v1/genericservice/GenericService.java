package br.com.wti.school.endpoint.v1.genericservice;

import org.springframework.stereotype.Service;

import br.com.wti.school.exception.ResourceNotFoundException;
import br.com.wti.school.persistence.model.AbstractEntity;
import br.com.wti.school.persistence.respository.CustomPagingAndSortRepository;

/**
 * @author Washington Antunes for wTI on 25/03/2023.
 */
@Service
public class GenericService {

	public <T extends AbstractEntity, ID extends Long> void throwResourceNotFoundIfDoesNotExist(T t, CustomPagingAndSortRepository<T, ID> repository, String msg) {
        if (t == null || t.getId() == null || repository.findOne(t.getId()) == null)
            throw new ResourceNotFoundException(msg);
    }

    public <T extends AbstractEntity, ID extends Long> void throwResourceNotFoundIfDoesNotExist(long id, CustomPagingAndSortRepository<T, ID> repository, String msg) {
        if (id == 0 || repository.findOne(id) == null)
            throw new ResourceNotFoundException(msg);
    }
}
