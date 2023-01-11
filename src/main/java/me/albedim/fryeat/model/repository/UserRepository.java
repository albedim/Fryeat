package me.albedim.fryeat.model.repository;

import me.albedim.fryeat.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
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

    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    User getByUsername(@Param("username") String username);

    @Query(value = "SELECT users.id, users.name, users.username, users.email, users.password, users.place " +
            "FROM users " +
            "JOIN participations " +
            "ON participations.user_id = users.id " +
            "AND participations.poll_id = :pollId", nativeQuery = true)
    List<User> getParticipants(@Param("pollId") Long pollId);

    @Query(value = "SELECT * " +
            "FROM users " +
            "WHERE username = :email_username " +
            "OR email = :email_username " +
            "AND password = :password", nativeQuery = true)
    User signIn(@Param("email_username") String emailUsername, @Param("password") String password);

    @Query(value = "SELECT COUNT(*) FROM users WHERE username = :username", nativeQuery = true)
    Integer existsByUsername(@Param("username") String username);

    @Query(value = "SELECT COUNT(*) FROM users WHERE email = :email", nativeQuery = true)
    Integer existsByEmail(@Param("email") String email);

}
