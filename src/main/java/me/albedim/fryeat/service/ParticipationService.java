package me.albedim.fryeat.service;

import me.albedim.fryeat.model.entity.Participation;
import me.albedim.fryeat.model.entity.User;
import me.albedim.fryeat.model.repository.ParticipationRepository;
import me.albedim.fryeat.model.repository.UserRepository;
import me.albedim.fryeat.utils.Util;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 10/01/23
 * Created at: 14:34
 * Version: 1.0.0
 * Description: This is the class for the participation service
 */

@Service
public class ParticipationService
{
    private ParticipationRepository participationRepository;
    private UserService userService;

    public ParticipationService(ParticipationRepository participationRepository, @Lazy UserService userService)
    {
        this.participationRepository = participationRepository;
        this.userService = userService;
    }

    private boolean exists(Long id)
    {
        return this.participationRepository.exists(id) > 0;
    }

    public HashMap add(HashMap request)
    {
        try{
            User user = userService.getByUsername(request.get("username").toString());
            if(exists(user.getId()))
                return Util.createResponse(false, Util.PARTICIPATION_ALREADY_EXISTS, 403);
            else{
                Participation participation = new Participation(user.getId(), Long.parseLong(request.get("poll_id").toString()));
                this.participationRepository.save(participation);
                return Util.createResponse(true, Util.PARTICIPATION_SUCCESSFULLY_ADDED);
            }
        }catch (NullPointerException exception){
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        }
    }

    public HashMap deleteParticipation(Long userId, Long pollId)
    {
        this.participationRepository.deleteParticipation(userId, pollId);
        return Util.createResponse(false, Util.PARTICIPATION_SUCCESSFULLY_DELETED);
    }
}
