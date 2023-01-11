package me.albedim.fryeat.controller;

import me.albedim.fryeat.service.VoteService;
import me.albedim.fryeat.utils.Util;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 11/01/23
 * Created at: 18:14
 * Version: 1.0.0
 * Description: This is the class for the vote controller
 */

@RestController
@RequestMapping(Util.URL + "/vote")
public class VoteController
{
    private VoteService voteService;

    public VoteController(VoteService voteService)
    {
        this.voteService = voteService;
    }

    @GetMapping("/getVotes/{pollId}")
    @CrossOrigin
    public List<HashMap> getPollFood(@PathVariable Long pollId)
    {
        return this.voteService.getVotes(pollId);
    }

    @PostMapping("/add")
    @CrossOrigin
    public HashMap addVote(@RequestBody HashMap request)
    {
        return this.voteService.addVote(request);
    }

}
