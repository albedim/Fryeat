package me.albedim.fryeat.model.repository;

import me.albedim.fryeat.model.entity.Participation;
import me.albedim.fryeat.model.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 10/01/23
 * Created at: 14:34
 * Version: 1.0.0
 * Description: This is the class for the participation repository
 */

public interface ParticipationRepository extends CrudRepository<Participation, Long>
{

    @Query(value = "SELECT COUNT(*) FROM participations WHERE user_id = :userId AND poll_id = :pollId AND has_voted = 1", nativeQuery = true)
    Integer hasVoted(@Param("pollId") Long pollId, @Param("userId") Long userId);
    @Query(value = "SELECT COUNT(*) FROM participations WHERE user_id = :userId AND poll_id = :pollId", nativeQuery = true)
    Integer exists(@Param("pollId") Long pollId, @Param("userId") Long userId);
    @Transactional
    @Modifying
    @Query(value = "UPDATE participations SET has_voted = true WHERE poll_id = :pollId AND user_id = :userId", nativeQuery = true)
    void setVote(@Param("pollId") Long pollId, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM participations WHERE poll_id = :pollId", nativeQuery = true)
    void deleteParticipations(@Param("pollId") Long pollId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM participations WHERE user_id = :userId AND poll_id = :pollId", nativeQuery = true)
    void deleteParticipation(@Param("userId") Long userId, @Param("pollId") Long pollId);
}
