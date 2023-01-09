package me.albedim.fryeat.controller;


import me.albedim.fryeat.service.PollService;
import me.albedim.fryeat.utils.Util;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(Util.URL + "/poll")
public class PollController
{
    private PollService pollService;

    public PollController(PollService pollService)
    {
        this.pollService = pollService;
    }

    @PostMapping("/add")
    @CrossOrigin
    public HashMap add(@RequestBody HashMap request)
    {
        return this.pollService.create(request);
    }
}
