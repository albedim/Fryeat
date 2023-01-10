package me.albedim.fryeat.service;

import me.albedim.fryeat.model.entity.Food;
import me.albedim.fryeat.model.entity.Participation;
import me.albedim.fryeat.model.entity.User;
import me.albedim.fryeat.model.repository.FoodRepository;
import me.albedim.fryeat.model.repository.ParticipationRepository;
import me.albedim.fryeat.utils.Util;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 11/01/23
 * Created at: 00:34
 * Version: 1.0.0
 * Description: This is the class for the food service
 */

@Service
public class FoodService
{
    private FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository)
    {
        this.foodRepository = foodRepository;
    }

    public Iterable<Food> getFood()
    {
        return this.foodRepository.findAll();
    }

    public HashMap addFood(HashMap request)
    {
        try{
            Food food = new Food(request.get("name").toString(), request.get("image").toString());
            this.foodRepository.save(food);
            return Util.createResponse(true, Util.FOOD_SUCCESSFULLY_CREATED);
        }catch (NullPointerException exception){
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        }
    }
}
