package me.albedim.fryeat.service;

import me.albedim.fryeat.model.entity.Poll;
import me.albedim.fryeat.model.repository.PollRepository;
import me.albedim.fryeat.utils.Util;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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

    public PollService(PollRepository pollRepository)
    {
        this.pollRepository = pollRepository;
    }

    public HashMap create(HashMap request)
    {
        try{
            Poll poll = new Poll(Long.parseLong(request.get("ownerId").toString()), request.get("name").toString());
            this.pollRepository.save(poll);
            return Util.createResponse(true, Util.POLL_SUCCESSFULLY_CREATED);
        }catch (NullPointerException exception){
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        }
    }
}
