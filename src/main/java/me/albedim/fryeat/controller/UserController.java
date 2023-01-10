package me.albedim.fryeat.controller;

import me.albedim.fryeat.model.entity.User;
import me.albedim.fryeat.service.UserService;
import me.albedim.fryeat.utils.Util;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 09/01/23
 * Created at: 17:38
 * Version: 1.0.0
 * Description: This is the class for the user controller
 */

@RestController
@RequestMapping(Util.URL + "/user")
public class UserController
{
    private UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @CrossOrigin
    public HashMap signUp(@RequestBody HashMap request)
    {
        return this.userService.signUp(request);
    }

    @GetMapping("/getIterationUsersWithParticipation/{pollId}")
    @CrossOrigin
    public List<HashMap> getIterationUsers_Participation(@PathVariable Long pollId)
    {
        return this.userService.getIterationUsers_Participation(pollId);
    }

    @GetMapping("/getParticipants/{pollId}")
    @CrossOrigin
    public List<User> getParticipants(@PathVariable Long pollId)
    {
        return this.userService.getParticipants(pollId);
    }

    @PostMapping("/signin")
    @CrossOrigin
    public HashMap signIn(@RequestBody HashMap request)
    {
        return this.userService.signIn(request);
    }

    @GetMapping("get/{id}")
    @CrossOrigin
    public Optional<User> get(@PathVariable Long id)
    {
        return this.userService.get(id);
    }

}
