package me.albedim.fryeat.model.entity;

import jakarta.persistence.*;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 11/01/23
 * Created at: 16:14
 * Version: 1.0.0
 * Description: This is the class for the polls_food entity
 */

@Entity
@Table(name = "polls_food")
public class PollFood
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pollId;

    @Column(nullable = false)
    private Long foodId;

    PollFood() { }

    public PollFood(Long foodId, Long pollId)
    {
        setFoodId(foodId);
        setPollId(pollId);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPollId() { return pollId; }
    public void setPollId(Long pollId) { this.pollId = pollId; }
    public Long getFoodId() { return foodId; }
    public void setFoodId(Long foodId) { this.foodId = foodId; }

}
