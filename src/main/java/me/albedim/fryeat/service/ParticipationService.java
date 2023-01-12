package me.albedim.fryeat.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import me.albedim.fryeat.model.entity.Participation;
import me.albedim.fryeat.model.entity.User;
import me.albedim.fryeat.model.repository.ParticipationRepository;
import me.albedim.fryeat.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

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
            if(exists(Long.parseLong(request.get("pollId").toString()), user.getId()))
                return Util.createResponse(false, Util.PARTICIPATION_ALREADY_EXISTS, 403);
            else{
                Participation participation = new Participation(user.getId(), Long.parseLong(request.get("pollId").toString()));
                this.participationRepository.save(participation);
                sendMail(user, request.get("pollId").toString());
                return Util.createResponse(true, Util.PARTICIPATION_SUCCESSFULLY_ADDED);
            }
        }catch (NullPointerException exception){
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMail(User user, String pollId) throws MessagingException, UnsupportedEncodingException
    {
        MimeMessage msg = Util.getMessage();
        msg.setFrom(new InternetAddress(Util.NOREPLY_EMAIL, Util.NOREPLY_NAME));
        msg.setReplyTo(InternetAddress.parse(Util.NOREPLY_EMAIL, false));
        msg.setSubject(Util.MAIL_SUBJECT, "UTF-8");
        msg.setContent(Util.MAIL_TEXT.replace("{name}", user.getName()).replace("{pollId}", pollId), "text/html");
        msg.setSentDate(new Date());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail(), false));
        Transport.send(msg);
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
