package me.albedim.fryeat.model.entity;

import jakarta.persistence.*;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 12/01/23
 * Created at: 17:14
 * Version: 1.0.0
 * Description: This is the class for the recovery link entity
 */

@Entity
@Table(name = "recovery_links")

public class RecoveryLink
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String link;

    public RecoveryLink() {  }

    public RecoveryLink(Long userId, String link)
    {
        setUserId(userId);
        setLink(link);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
}
