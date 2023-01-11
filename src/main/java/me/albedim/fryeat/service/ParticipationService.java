package me.albedim.fryeat.service;

import me.albedim.fryeat.model.entity.Participation;
import me.albedim.fryeat.model.entity.User;
import me.albedim.fryeat.model.repository.ParticipationRepository;
import me.albedim.fryeat.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.HashMap;

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

    @Autowired
    private JavaMailSender javaMailSender;

    public ParticipationService(ParticipationRepository participationRepository, @Lazy UserService userService)
    {
        this.participationRepository = participationRepository;
        this.userService = userService;
    }

    private boolean exists(Long pollId, Long userId)
    {
        return this.participationRepository.exists(pollId, userId) > 0;
    }

    public HashMap add(HashMap request)
    {
        try{
            User user = userService.getByUsername(request.get("username").toString());
            if(exists(Long.parseLong(request.get("poll_id").toString()), user.getId()))
                return Util.createResponse(false, Util.PARTICIPATION_ALREADY_EXISTS, 403);
            else{
                Participation participation = new Participation(user.getId(), Long.parseLong(request.get("poll_id").toString()));
                this.participationRepository.save(participation);
                sendMail(user);
                return Util.createResponse(true, Util.PARTICIPATION_SUCCESSFULLY_ADDED);
            }
        }catch (NullPointerException exception){
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        }
    }

    public void sendMail(User user)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Util.NOREPLY_EMAIL);
        message.setTo(user.getEmail());
        message.setSubject(Util.MAIL_OBJECT);
        message.setText(Util.MAIL_TEXT);
        javaMailSender.send(message);
    }

    public HashMap hasVoted(Long pollId, Long userId)
    {
        boolean hasVoted = this.participationRepository.hasVoted(pollId, userId) > 0;
        return Util.createResponse(
                true,
                String.valueOf(hasVoted)
        );
    }

    public HashMap setVote(Long pollId, Long userId)
    {
        this.participationRepository.setVote(pollId, userId);
        return Util.createResponse(true, Util.VOTE_SUCCESSFULLY_SET);
    }

    public HashMap deleteParticipation(Long userId, Long pollId)
    {
        this.participationRepository.deleteParticipation(userId, pollId);
        return Util.createResponse(false, Util.PARTICIPATION_SUCCESSFULLY_DELETED);
    }
}
