package me.albedim.fryeat.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import me.albedim.fryeat.model.entity.User;
import me.albedim.fryeat.model.repository.UserRepository;
import me.albedim.fryeat.utils.Util;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 09/01/23
 * Created at: 17:38
 * Version: 1.0.0
 * Description: This is the class for the user service
 */

@Service
public class UserService
{
    private UserRepository userRepository;

    private PollService pollService;

    private RecoveryLinkService recoveryLinkService;


    public UserService(UserRepository userRepository, PollService pollService,
                       @Lazy RecoveryLinkService recoveryLinkService)
    {
        this.pollService = pollService;
        this.recoveryLinkService = recoveryLinkService;
        this.userRepository = userRepository;
    }

    private boolean exists(String username, String email)
    {
        return this.userRepository.existsByUsername(username) > 0
                || this.userRepository.existsByEmail(email) > 0;
    }

    public HashMap signUp(HashMap request)
    {
        try{
            if(exists(request.get("username").toString(), request.get("email").toString()))
                return Util.createResponse(false, Util.USER_ALREADY_EXISTS, 403);
            else {
                User user = new User(
                        request.get("name").toString(),
                        request.get("username").toString(),
                        request.get("email").toString(),
                        Util.hash(request.get("password").toString()),
                        request.get("place").toString()
                );
                this.userRepository.save(user);
                sendMail(user);
                return Util.createResponse(true, Util.USER_SUCCESSFULLY_CREATED);
            }
        }catch (NullPointerException exception){
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        }
    }

    public List<User> getParticipants(Long pollId)
    {
        return this.userRepository.getParticipants(pollId);
    }

    public HashMap signIn(HashMap request)
    {
        try{
            User user = this.userRepository.signIn(
                    request.get("email_username").toString(),
                    Util.hash(request.get("password").toString())
            );
            if(user == null)
                return Util.createResponse(false, Util.USER_NOT_FOUND, 404);
            else return Util.createLoginResponse(user.getId());
        }catch (NullPointerException exception){
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        }
    }

    public User getByUsername(String username)
    {
        try{
            return this.userRepository.getByUsername(username);
        }catch (NullPointerException exception){
            return null;
        }
    }

    private void sendMail(User user) throws MessagingException, UnsupportedEncodingException
    {
        MimeMessage msg = Util.getMessage();
        msg.setFrom(new InternetAddress(Util.NOREPLY_EMAIL, Util.NOREPLY_NAME));
        msg.setReplyTo(InternetAddress.parse(Util.NOREPLY_EMAIL, false));
        msg.setSubject(Util.SIGNUP_MAIL_SUBJECT, "UTF-8");
        msg.setContent(Util.SIGNUP_MAIL_TEXT.replace("{name}", user.getName()), "text/html");
        msg.setSentDate(new Date());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail(), false));
        Transport.send(msg);
    }

    public User get(Long id)
    {
        return this.userRepository.get(id);
    }

    public User getByEmail(String email)
    {
        return this.userRepository.getByEmail(email);
    }

    public HashMap changePassword(HashMap request)
    {
        try{
            String hashedPassword = Util.hash(request.get("password").toString());
            this.userRepository.changePassword(hashedPassword, Long.parseLong(request.get("id").toString()));
            this.recoveryLinkService.deleteLink(Long.parseLong(request.get("id").toString()));
            return Util.createResponse(true, Util.USER_PASSWORD_SUCCESSFULLY_CHANGED);
        }catch (NullPointerException exception){
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        }
    }

}
