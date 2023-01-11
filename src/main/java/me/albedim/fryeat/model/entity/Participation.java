package me.albedim.fryeat.model.entity;

import jakarta.persistence.*;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 10/01/23
 * Created at: 14:34
 * Version: 1.0.0
 * Description: This is the class for the poll entity
 */

@Entity
@Table(name = "participations")
public class Participation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long pollId;

    @Column(nullable = false)
    private boolean hasVoted;

    Participation() { }

    public Participation(Long userId, Long pollId)
    {
        setUserId(userId);
        setPollId(pollId);
        setHasVoted(false);
    }


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPollId() { return pollId; }

    public void setPollId(Long pollId) { this.pollId = pollId; }

    public boolean hasVoted() { return hasVoted; }

    public void setHasVoted(boolean hasVoted) { this.hasVoted = hasVoted; }
}
