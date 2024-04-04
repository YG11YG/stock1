package com.example.stock.OAuth.OAuthDto;

import com.example.stock.entity.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private Long id; ////이거 추가
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        if (user != null) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.picture = user.getPicture();

            System.out.println("SessionUser created: id=" + id + ", name=" + name + ", email=" + email);
        }
        }


    public Long getId() {
        return id;
    }
}
