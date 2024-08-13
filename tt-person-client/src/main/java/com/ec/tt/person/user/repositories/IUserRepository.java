package com.ec.tt.person.user.repositories;

import com.ec.tt.common.repositories.IQueryDslBaseRepository;
import com.ec.tt.person.entities.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User resources
 *
 * @author Joel Castro
 * @version 1.0
 */
public interface IUserRepository extends IQueryDslBaseRepository<UserEntity> {

    /**
     * Para obtener el id del usuario por usernam
     * @param username
     * @return
     */
    Optional<Long> getUserIdByUsername(String username);
}