package me.albedim.fryeat.service;

import me.albedim.fryeat.model.entity.User;
import me.albedim.fryeat.model.repository.UserRepository;
import me.albedim.fryeat.utils.Util;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
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

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    private boolean existsByUsername(String username)
    {
        return this.userRepository.existsByUsername(username) > 0;
    }

    public HashMap signUp(HashMap request)
    {
        if(existsByUsername(request.get("username").toString()))
            return Util.createResponse(false, Util.USER_ALREADY_EXISTS, 403);
        else {
            User user = new User(
                    request.get("name").toString(),
                    request.get("username").toString(),
                    Util.hash(request.get("password").toString()),
                    request.get("place").toString()
            );
            this.userRepository.save(user);
            return Util.createResponse(true, Util.USER_SUCCEFULLY_CREATED);
        }
    }

    public HashMap signIn(HashMap request)
    {
        try{
            User user = this.userRepository.signIn(
                    request.get("username").toString(),
                    Util.hash(request.get("password").toString())
            );
            if(user == null)
                return Util.createResponse(false, Util.USER_NOT_FOUND, 404);
            else return Util.createLoginResponse(user.getId());
        }catch (NullPointerException exception){
            return Util.createResponse(false, Util.INVALID_REQUEST, 500);
        }
    }

    public Optional<User> get(Long id)
    {
        return this.userRepository.findById(id);
    }

}
