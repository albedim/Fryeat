package me.albedim.fryeat.model.entity;

import jakarta.persistence.*;
import java.util.HashMap;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 11/01/23
 * Created at: 00:34
 * Version: 1.0.0
 * Description: This is the class for the food entity
 */

@Entity
@Table(name = "food")
public class Food
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    Food() { }

    public Food(String name, String image)
    {
        setName(name);
        setName(image);
    }


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public HashMap toJson(Integer votes)
    {
        HashMap response = new HashMap();
        response.put("id", id);
        response.put("name", name);
        response.put("votes", votes);
        return response;
    }

}
