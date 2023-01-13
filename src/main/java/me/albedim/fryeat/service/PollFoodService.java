package me.albedim.fryeat.service;

import me.albedim.fryeat.model.entity.PollFood;
import me.albedim.fryeat.model.repository.PollFoodRepository;
import me.albedim.fryeat.utils.Util;
import org.springframework.stereotype.Service;
import java.util.HashMap;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 11/01/23
 * Created at: 16:14
 * Version: 1.0.0
 * Description: This is the class for the polls_food service
 */


@Service
public class PollFoodService
{
    private PollFoodRepository pollFoodRepository;

    public PollFoodService(PollFoodRepository pollFoodRepository)
    {
        this.pollFoodRepository = pollFoodRepository;
    }

    private boolean exists(Long foodId, Long pollId)
    {
        return this.pollFoodRepository.exists(pollId, foodId) > 0;
    }

    public HashMap add(HashMap request)
    {
        try{
            if(exists(Long.parseLong(request.get("foodId").toString()), Long.parseLong(request.get("pollId").toString())))
                return Util.createResponse(false, Util.POLLFOOD_ALREADY_EXISTS, 403);
            else{
                PollFood pollFood = new PollFood(
                        Long.parseLong(request.get("foodId").toString()),
                        Long.parseLong(request.get("pollId").toString())
                );
                this.pollFoodRepository.save(pollFood);
                return Util.createResponse(true, Util.POLLFOOD_SUCCESSFULLY_CREATED);
            }
        }catch (NullPointerException exception){
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        }
    }

    public HashMap delete(Long pollId, Long foodId)
    {
        this.pollFoodRepository.delete(pollId, foodId);
        return Util.createResponse(true, Util.POLLFOOD_SUCCESSFULLY_DELETED);
    }
}
