package personal_project.socialwave_be.dto.User;

import personal_project.socialwave_be.dto.Post.PostDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * UserRegistrationDTO: receive a body request that defines a full entity
 */
public class UserRegistrationDTO {
    private int userId;
    private String name;
    private String userName;
    private String password;
    private String email;
    private String bio;
    private String avatarSource;
    private List<PostDTO> posts;

    public UserRegistrationDTO(int userId, String name, String userName, String password, String email, String avatarSource, String bio) {
        this.userId = userId;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.avatarSource = avatarSource;
        this.bio = bio;
        this.posts = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarSource() {
        return avatarSource;
    }

    public void setAvatarSource(String avatarSource) {
        this.avatarSource = avatarSource;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }
}
