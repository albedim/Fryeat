package me.albedim.fryeat.model.repository;

import me.albedim.fryeat.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 09/01/23
 * Created at: 17:38
 * Version: 1.0.0
 * Description: This is the class for the user repository
 */

public interface UserRepository extends CrudRepository<User, Long>
{
    @Override
    Optional<User> findById(Long aLong);

    @Override
    <S extends User> S save(S entity);

    @Query(value = "SELECT * FROM users WHERE username = :username AND password = :password", nativeQuery = true)
    User signIn(@Param("username") String username, @Param("password") String password);

    @Query(value = "SELECT COUNT(*) FROM users WHERE username = :username", nativeQuery = true)
    Integer existsByUsername(@Param("username") String username);

}
