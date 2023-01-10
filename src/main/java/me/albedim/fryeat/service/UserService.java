package me.albedim.fryeat.service;

import me.albedim.fryeat.model.entity.User;
import me.albedim.fryeat.model.repository.UserRepository;
import me.albedim.fryeat.utils.Util;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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


    public UserService(UserRepository userRepository, PollService pollService)
    {
        this.pollService = pollService;
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
                return Util.createResponse(true, Util.USER_SUCCESSFULLY_CREATED);
            }
        }catch (NullPointerException exception){
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        }
    }

    public List<HashMap> getIterationUsers_Participation(Long pollId)
    {
        try{
            List<User> participants = this.userRepository.getParticipants(pollId);
            List<User> allUsers = (List<User>) this.userRepository.findAll();
            List<HashMap> users = new ArrayList<>();

            for(User user : allUsers)
                if(!user.getId().equals(this.pollService.get(pollId).getOwnerId()))
                    if(participants.contains(user))
                        users.add(user.toJson(true));
                    else
                        users.add(user.toJson(false));

            return users;
        }catch (NullPointerException exception){
            return null;
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

    public Optional<User> get(Long id)
    {
        return this.userRepository.findById(id);
    }

}
