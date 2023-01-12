package me.albedim.fryeat.model.repository;

import me.albedim.fryeat.model.entity.Food;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 11/01/23
 * Created at: 00:34
 * Version: 1.0.0
 * Description: This is the class for the food repository
 */

public interface FoodRepository extends CrudRepository<Food, Long>
{
    @Override
    Iterable<Food> findAll();

    @Query(value = "SELECT food.* " +
            "FROM food " +
            "JOIN polls_food " +
            "ON food.id = polls_food.food_id " +
            "AND polls_food.poll_id = :pollId", nativeQuery = true)
    List<Food> getPollFood(@Param("pollId") Long pollId);

    @Override
    <S extends Food> S save(S entity);
}
