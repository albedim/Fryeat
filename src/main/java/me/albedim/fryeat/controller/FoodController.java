package me.albedim.fryeat.controller;

import me.albedim.fryeat.model.entity.Food;
import me.albedim.fryeat.service.FoodService;
import me.albedim.fryeat.utils.Util;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 11/01/23
 * Created at: 00:34
 * Version: 1.0.0
 * Description: This is the class for the food controller
 */

@RestController
@RequestMapping(Util.URL + "/food")
public class FoodController
{
    private FoodService foodService;

    public FoodController(FoodService foodService)
    {
        this.foodService = foodService;
    }

    @GetMapping("/getPollFood/{pollId}")
    @CrossOrigin
    public Iterable<Food> getPollFood(@PathVariable Long pollId)
    {
        return this.foodService.getPollFood(pollId);
    }

    @GetMapping("/get")
    @CrossOrigin
    public Iterable<Food> getFood()
    {
        return this.foodService.getFood();
    }

    @PostMapping("/add")
    @CrossOrigin
    public HashMap addFood(HashMap request)
    {
        return this.foodService.addFood(request);
    }

}
