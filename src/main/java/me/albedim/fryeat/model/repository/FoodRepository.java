package me.albedim.fryeat.model.repository;

import me.albedim.fryeat.model.entity.Food;
import me.albedim.fryeat.model.entity.Participation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    <S extends Food> S save(S entity);
}
