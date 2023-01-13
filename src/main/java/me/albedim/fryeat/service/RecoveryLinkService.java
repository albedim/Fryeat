package me.albedim.fryeat.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import me.albedim.fryeat.model.entity.Food;
import me.albedim.fryeat.model.entity.RecoveryLink;
import me.albedim.fryeat.model.entity.User;
import me.albedim.fryeat.model.repository.FoodRepository;
import me.albedim.fryeat.model.repository.RecoveryLinkRepository;
import me.albedim.fryeat.utils.Util;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 12/01/23
 * Created at: 17:14
 * Version: 1.0.0
 * Description: This is the class for the recover_links service
 */

@Service
public class RecoveryLinkService
{
    private RecoveryLinkRepository recoveryLinkRepository;

    private UserService userService;

    public RecoveryLinkService(RecoveryLinkRepository recoveryLinkRepository, UserService userService)
    {
        this.userService = userService;
        this.recoveryLinkRepository = recoveryLinkRepository;
    }

    public HashMap exists(HashMap request)
    {
        try{
            RecoveryLink recoveryLink = this.recoveryLinkRepository.get(request.get("link").toString());
            if(recoveryLink != null)
                return Util.createResponse(true, recoveryLink.getUserId().toString());
            else return Util.createResponse(false, Util.USER_NOT_FOUND, 404);
        }catch (NullPointerException exception){
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        }
    }

    private void sendMail(User user, String link) throws MessagingException, UnsupportedEncodingException
    {
        MimeMessage msg = Util.getMessage();
        msg.setFrom(new InternetAddress(Util.NOREPLY_EMAIL, Util.NOREPLY_NAME));
        msg.setReplyTo(InternetAddress.parse(Util.NOREPLY_EMAIL, false));
        msg.setSubject(Util.RECOVERY_LINK_MAIL_SUBJECT, "UTF-8");
        msg.setContent(Util.RECOVERY_LINK_MAIL_TEXT.replace("{name}", user.getName()).replace("{link}", link), "text/html");
        msg.setSentDate(new Date());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail(), false));
        Transport.send(msg);
    }

    public HashMap addLink(HashMap request)
    {
        try{
            User user = this.userService.getByEmail(request.get("email").toString());
            if(user == null)
                return Util.createResponse(false, Util.USER_NOT_FOUND);
            else{
                String link = UUID.randomUUID().toString().replace("-", "");
                RecoveryLink recoveryLink = new RecoveryLink(
                        user.getId(),
                        link
                );
                this.recoveryLinkRepository.save(recoveryLink);
                sendMail(user, link);
                return Util.createResponse(true, Util.RECOVERY_LINK_SUCCESSFULLY_CREATED);
            }
        }catch (NullPointerException | MessagingException | UnsupportedEncodingException exception){
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        }
    }

    public HashMap deleteLink(Long userId)
    {
        this.recoveryLinkRepository.delete(userId);
        return Util.createResponse(true, Util.RECOVERY_LINK_SUCCESSFULLY_DELETED);
    }

}
