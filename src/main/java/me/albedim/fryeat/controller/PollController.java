package me.albedim.fryeat.controller;


import me.albedim.fryeat.model.entity.Poll;
import me.albedim.fryeat.service.ParticipationService;
import me.albedim.fryeat.service.PollService;
import me.albedim.fryeat.utils.Util;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(Util.URL + "/poll")
public class PollController
{
    private PollService pollService;
    private ParticipationService participationService;

    public PollController(PollService pollService, ParticipationService participationService)
    {
        this.participationService = participationService;
        this.pollService = pollService;
    }

    @PostMapping("/add")
    @CrossOrigin
    public HashMap add(@RequestBody HashMap request)
    {
        return this.pollService.create(request);
    }

    @GetMapping("/get/{userId}")
    @CrossOrigin
    public List<Poll> getPolls(@PathVariable Long userId)
    {
        return this.pollService.getPolls(userId);
    }

    @GetMapping("/getOwn/{ownerId}")
    @CrossOrigin
    public List<Poll> getOwnPolls(@PathVariable Long ownerId)
    {
        return this.pollService.getOwnPolls(ownerId);
    }
}
