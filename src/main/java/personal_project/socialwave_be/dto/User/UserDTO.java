package personal_project.socialwave_be.dto.User;

import personal_project.socialwave_be.dto.Post.PostDTO;

import java.util.List;

/**
 * UserDTO: data will be parsed to UserDTO when return to the client
 * sensitive information will be excluded
 */
public class UserDTO {
    private int userId;
    private String name;
    private String userName;
    private String avatarSource;
    private String bio;
    private int followerCount;
    private int followingCount;

    public UserDTO() {
    }

    public UserDTO(int userId, String name, String userName, String avatarSource, String bio, int followerCount, int followingCount, List<PostDTO> posts) {
        this.userId = userId;
        this.name = name;
        this.userName = userName;
        this.avatarSource = avatarSource;
        this.bio = bio;
        this.followerCount = followerCount;
        this.followingCount = followingCount;

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }



}
