package me.albedim.fryeat.controller;

import me.albedim.fryeat.model.entity.Food;
import me.albedim.fryeat.service.FoodService;
import me.albedim.fryeat.service.RecoveryLinkService;
import me.albedim.fryeat.utils.Util;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 12/01/23
 * Created at: 17:14
 * Version: 1.0.0
 * Description: This is the class for the recovery_links controller
 */

@RestController
@RequestMapping(Util.URL + "/recoveryLink")
public class RecoveryLinkController
{
    private RecoveryLinkService recoveryLinkService;

    public RecoveryLinkController(RecoveryLinkService recoveryLinkService)
    {
        this.recoveryLinkService = recoveryLinkService;
    }

    @GetMapping("/get")
    @CrossOrigin
    public HashMap getLink(@RequestBody HashMap request)
    {
        return this.recoveryLinkService.exists(request);
    }

    @PostMapping("/add")
    @CrossOrigin
    public HashMap addLink(@RequestBody HashMap request)
    {
        return this.recoveryLinkService.addLink(request);
    }

}
