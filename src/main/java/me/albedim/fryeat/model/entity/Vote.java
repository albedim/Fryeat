package me.albedim.fryeat.model.entity;

import jakarta.persistence.*;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 11/01/23
 * Created at: 17:34
 * Version: 1.0.0
 * Description: This is the class for the vote entity
 */

@Entity
@Table(name = "votes")
public class Vote
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long pollId;

    @Column(nullable = false)
    private Long foodId;

    private Vote() { }

    public Vote(Long pollId, Long foodId)
    {
        setFoodId(foodId);
        setPollId(pollId);
    }

    public void setId(Long id) { this.id = id; }
    public Long getPollId() { return pollId; }
    public void setPollId(Long pollId) { this.pollId = pollId; }
    public Long getFoodId() { return foodId; }
    public void setFoodId(Long foodId) { this.foodId = foodId; }


}

