package me.albedim.fryeat.model.entity;

import jakarta.persistence.*;
import java.util.HashMap;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 09/01/23
 * Created at: 17:38
 * Version: 1.0.0
 * Description: This is the class for the user entity
 */

@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String place;


    private User() { }

    public User(String name, String username, String email, String password, String place)
    {
        setName(name);
        setUsername(username);
        setEmail(email);
        setPlace(place);
        setPassword(password);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public HashMap toJson()
    {
        HashMap response = new HashMap();
        response.put("id", id);
        response.put("name", name);
        response.put("username", username);
        response.put("password", password);
        return response;
    }

}

