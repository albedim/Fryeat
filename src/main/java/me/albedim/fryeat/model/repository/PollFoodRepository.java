package me.albedim.fryeat.model.repository;

import me.albedim.fryeat.model.entity.PollFood;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 11/01/23
 * Created at: 16:14
 * Version: 1.0.0
 * Description: This is the class for the polls_food repository
 */

public interface PollFoodRepository extends CrudRepository<PollFood, Long>
{

    @Query(value = "SELECT COUNT(*) FROM polls_food WHERE poll_id = :pollId AND food_id = :foodId", nativeQuery = true)
    Integer exists(@Param("pollId") Long pollId, @Param("foodId") Long foodId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM polls_food WHERE poll_id = :pollId AND food_id = :foodId", nativeQuery = true)
    void delete(@Param("pollId") Long pollId, @Param("foodId") Long foodId);

    @Override
    <S extends PollFood> S save(S entity);
}
