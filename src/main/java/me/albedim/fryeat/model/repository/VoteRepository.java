package me.albedim.fryeat.model.repository;

import me.albedim.fryeat.model.entity.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 11/01/23
 * Created at: 18:14
 * Version: 1.0.0
 * Description: This is the class for the vote repository
 */

public interface VoteRepository extends CrudRepository<Vote, Long>
{

    @Override
    <S extends Vote> S save(S entity);

    @Query(value = "SELECT COUNT(*) FROM votes WHERE poll_id = :pollId AND food_id = :foodId", nativeQuery = true)
    Integer getVotes(@Param("pollId") Long pollId, @Param("foodId") Long foodId);

}
