package me.albedim.fryeat.service;

import me.albedim.fryeat.model.entity.Food;
import me.albedim.fryeat.model.entity.Vote;
import me.albedim.fryeat.model.repository.VoteRepository;
import me.albedim.fryeat.utils.Util;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 11/01/23
 * Created at: 18:14
 * Version: 1.0.0
 * Description: This is the class for the vote service
 */

@Service
public class VoteService
{
    private VoteRepository voteRepository;

    private FoodService foodService;

    public VoteService(VoteRepository voteRepository, @Lazy FoodService foodService)
    {
        this.voteRepository = voteRepository;
        this.foodService = foodService;
    }

    public List<HashMap> getVotes(Long pollId)
    {
        List<Food> pollFoods = this.foodService.getPollFood(pollId);
        List<HashMap> result = new ArrayList<>();
        for(Food pollFood : pollFoods) {
            Integer votes = this.voteRepository.getVotes(pollId, pollFood.getId());
            result.add(pollFood.toJson(votes));
        }
        return result;
    }

    public HashMap addVote(HashMap request)
    {
        try{
            Vote vote = new Vote(
                    Long.parseLong(request.get("pollId").toString()),
                    Long.parseLong(request.get("foodId").toString())
            );
            this.voteRepository.save(vote);
            return Util.createResponse(true, Util.VOTE_SUCCESSFULLY_ADDED);
        }catch (NullPointerException exception){
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        }
    }

}
