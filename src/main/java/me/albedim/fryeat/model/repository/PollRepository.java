package me.albedim.fryeat.model.repository;

import me.albedim.fryeat.model.entity.Poll;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
    <S extends Poll> S save(S entity);

    @Query(value = "SELECT * FROM polls WHERE id = :id", nativeQuery = true)
    Poll get(@Param("id") Long id);

    @Query(value = "SELECT polls.* " +
            "FROM polls " +
            "JOIN participations " +
            "ON participations.poll_id = polls.id " +
            "AND participations.user_id = user_id", nativeQuery = true)
    List<Poll> getPolls(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM polls WHERE owner_id = :ownerId", nativeQuery = true)
    List<Poll> getOwnPolls(@Param("ownerId") Long ownerId);

}
