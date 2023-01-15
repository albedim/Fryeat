package me.albedim.fryeat.model.entity;

import jakarta.persistence.*;
import java.util.HashMap;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 09/01/23
 * Created at: 19:14
 * Version: 1.0.0
 * Description: This is the class for the poll entity
 */

@Entity
@Table(name = "polls")
public class Poll
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long ownerId;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean finished;

    private Poll() { }

    public Poll(Long ownerId, String name)
    {
        setFinished(false);
        setName(name);
        setOwnerId(ownerId);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isFinished() { return finished; }
    public void setFinished(boolean finished) { this.finished = finished; }

    public HashMap toJson(String ownerUsername)
    {
        HashMap response = new HashMap();
        response.put("id", id);
        response.put("name", name);
        response.put("ownerUsername", ownerUsername);
        response.put("finished", finished);
        return response;
    }

}

