package personal_project.socialwave_be.dto.User;

public class UserPostDTO {
    private int userId;
    private String userName;
    private String avatarSource;

    public UserPostDTO() {
    }

    public UserPostDTO(int userId, String userName, String avatarSource) {
        this.userId = userId;
        this.userName = userName;
        this.avatarSource = avatarSource;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
