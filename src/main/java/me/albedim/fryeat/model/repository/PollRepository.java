package me.albedim.fryeat.model.repository;

import me.albedim.fryeat.model.entity.Poll;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 09/01/23
 * Created at: 19:38
 * Version: 1.0.0
 * Description: This is the class for the poll repository
 */

public interface PollRepository extends CrudRepository<Poll, Long>
{

    @Override
    void deleteById(Long aLong);

    @Override
    <S extends Poll> S save(S entity);

    @Query(value = "SELECT * FROM polls WHERE id = :id", nativeQuery = true)
    Poll get(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM polls WHERE id = :pollId AND finished = 1",nativeQuery = true)
    Integer isClosed(@Param("pollId") Long pollId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE polls SET finished = 1 WHERE id = :pollId", nativeQuery = true)
    void close(@Param("pollId") Long pollId);

    @Query(value = "SELECT polls.* " +
            "FROM polls " +
            "JOIN participations " +
            "ON participations.poll_id = polls.id " +
            "AND participations.user_id = :userId " +
            "ORDER BY id " +
            "DESC", nativeQuery = true)
    List<Poll> getPolls(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM polls WHERE owner_id = :ownerId ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Poll getLastPoll(@Param("ownerId") Long ownerId);

    @Query(value = "SELECT * FROM polls WHERE owner_id = :ownerId ORDER BY id DESC", nativeQuery = true)
    List<Poll> getOwnPolls(@Param("ownerId") Long ownerId);

}
