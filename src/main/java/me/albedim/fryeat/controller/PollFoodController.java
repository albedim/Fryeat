package me.albedim.fryeat.controller;

import me.albedim.fryeat.service.PollFoodService;
import me.albedim.fryeat.utils.Util;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 11/01/23
 * Created at: 16:14
 * Version: 1.0.0
 * Description: This is the class for the poll_food controller
 */

@RestController
@RequestMapping(Util.URL + "/pollfood")
public class PollFoodController
{
    private PollFoodService pollFoodService;

    public PollFoodController(PollFoodService pollFoodService)
    {
        this.pollFoodService = pollFoodService;
    }

    @PostMapping("/add")
    @CrossOrigin
    public HashMap addFood(@RequestBody HashMap request)
    {
        return this.pollFoodService.add(request);
    }

    @DeleteMapping("/delete")
    @CrossOrigin
    public HashMap addFood(@RequestParam Long pollId, @RequestParam Long foodId)
    {
        return this.pollFoodService.delete(pollId, foodId);
    }

}
