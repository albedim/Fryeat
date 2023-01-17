package me.albedim.fryeat.service;

import me.albedim.fryeat.model.entity.Poll;
import me.albedim.fryeat.model.repository.PollRepository;
import me.albedim.fryeat.utils.Util;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 09/01/23
 * Created at: 19:38
 * Version: 1.0.0
 * Description: This is the class for the poll service
 */

@Service
public class PollService
{
    private PollRepository pollRepository;
    private UserService userService;
    private PollFoodService pollFoodService;
    private ParticipationService participationService;
    private VoteService voteService;

    public PollService(PollRepository pollRepository, VoteService voteService, @Lazy UserService userService, PollFoodService pollFoodService, ParticipationService participationService)
    {
        this.pollRepository = pollRepository;
        this.voteService = voteService;
        this.pollFoodService = pollFoodService;
        this.userService = userService;
        this.participationService = participationService;
    }

    public HashMap create(HashMap request)
    {
        try{
            Poll poll = new Poll(Long.parseLong(request.get("ownerId").toString()), request.get("name").toString());
            this.pollRepository.save(poll);

            // Making the participation request
            Long createdPollId = Long.parseLong(this.pollRepository.getLastPoll(
                    Long.parseLong(request.get("ownerId").toString())).getId().toString());
            String username = this.userService.get(Long.parseLong(request.get("ownerId").toString())).getUsername();
            HashMap participation = new HashMap();
            participation.put("username", username);
            participation.put("pollId", createdPollId);
            this.participationService.add(participation);

            return Util.createResponse(
                    true,
                    this.pollRepository.getLastPoll(
                            Long.parseLong(request.get("ownerId").toString())).getId().toString());
        }catch (NullPointerException exception){
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        }
    }

    public HashMap closePoll(Long pollId)
    {
        this.pollRepository.close(pollId);
        return Util.createResponse(true, Util.POLL_SUCCESSFULLY_CLOSED);
    }

    public HashMap deletePoll(Long pollId)
    {
        this.pollRepository.deleteById(pollId);
        this.pollFoodService.deleteAll(pollId);
        this.participationService.deleteParticipations(pollId);
        this.voteService.deleteVotes(pollId);
        return Util.createResponse(true, Util.POLL_SUCCESSFULLY_DELETED);
    }

    public HashMap isClosed(Long pollId)
    {
        boolean closed = this.pollRepository.isClosed(pollId) > 0;
        return Util.createResponse(true, closed);
    }

    public List<HashMap> getOwnPolls(Long ownerId)
    {
        List<Poll> ownPolls = this.pollRepository.getOwnPolls(ownerId);
        List<HashMap> result = new ArrayList<>();
        for (Poll ownPoll : ownPolls){
            String ownerNickname = this.userService.get(ownPoll.getOwnerId()).getUsername();
            result.add(ownPoll.toJson(ownerNickname));
        }
        return result;
    }

    public List<HashMap> getPolls(Long userId)
    {
        List<Poll> polls = this.pollRepository.getPolls(userId);
        List<HashMap> result = new ArrayList<>();
        for (Poll poll : polls){
            if(!poll.getOwnerId().equals(userId)){
                String ownerNickname = this.userService.get(poll.getOwnerId()).getUsername();
                result.add(poll.toJson(ownerNickname));
            }
        }
        return result;
    }

    public Poll get(Long id)
    {
        return this.pollRepository.get(id);
    }

}
