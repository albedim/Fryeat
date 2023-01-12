package me.albedim.fryeat.model.repository;

import me.albedim.fryeat.model.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
    <S extends User> S save(S entity);

    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    User get(@Param("id") Long id);

    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    User getByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET password = :password WHERE id = :id", nativeQuery = true)
    void changePassword(@Param("password") String password, @Param("id") Long id);

    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    User getByEmail(@Param("email") String email);

    @Query(value = "SELECT users.* " +
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
