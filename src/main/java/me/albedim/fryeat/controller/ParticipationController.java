package me.albedim.fryeat.controller;

import me.albedim.fryeat.model.entity.User;
import me.albedim.fryeat.service.ParticipationService;
import me.albedim.fryeat.service.UserService;
import me.albedim.fryeat.utils.Util;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 09/01/23
 * Created at: 17:38
 * Version: 1.0.0
 * Description: This is the class for the participation controller
 */

@RestController
@RequestMapping(Util.URL + "/participation")
public class ParticipationController
{
    private ParticipationService participationService;

    public ParticipationController(ParticipationService participationService)
    {
        this.participationService = participationService;
    }

    @PostMapping("/add")
    @CrossOrigin
    public HashMap add(@RequestBody HashMap request)
    {
        return this.participationService.add(request);
    }

    @DeleteMapping("/delete")
    @CrossOrigin
    public HashMap delete(@RequestParam Long userId, @RequestParam Long pollId)
    {
        return this.participationService.deleteParticipation(userId, pollId);
    }

}
