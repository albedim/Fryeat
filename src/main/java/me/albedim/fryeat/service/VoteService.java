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

    public List<HashMap> getFinalVotes(Long pollId)
    {
        try{
            List<HashMap> votes = getVotes(pollId);
            List<HashMap> result = new ArrayList<>();
            // get max
            HashMap winner = votes.get(0);
            for(HashMap vote : votes)
                if(Long.parseLong(vote.get("votes").toString()) > Long.parseLong(winner.get("votes").toString()))
                    winner = vote;
            // Every vote will be confronted to the max vote and added
            // in the result array and targeted as "winner=true" if it's the same, else "winner=false"
            for(HashMap vote : votes)
                if(vote.get("id").toString().equals(winner.get("id").toString())){
                    vote.put("winner", true);
                    result.add(vote);
                }else{
                    vote.put("winner", false);
                    result.add(vote);
                }
            return result;
        }catch (NullPointerException | ArrayIndexOutOfBoundsException exception){
            return null;
        }
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

    public void deleteVotes(Long pollId)
    {
        this.voteRepository.deleteVotes(pollId);
    }

}
