package personal_project.socialwave_be.dto.Auth;

import org.springframework.web.multipart.MultipartFile;

//Intermediate layer that received the form data
public class UserRegistrationForm {
    private String name;
    private String username;
    private String password;
    private String email;
    private String bio;
    private MultipartFile avatar;

    public UserRegistrationForm() {
    }

    public UserRegistrationForm(String name, String username, String password, String email, String bio, MultipartFile avatar) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }
}
