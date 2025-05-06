package personal_project.socialwave_be.dto.Post;

import personal_project.socialwave_be.dto.User.UserPostDTO;

public class PostUserDTO {
    private int postId;
    private String imageSource;
    private String caption;
    private int likeCount;
    private UserPostDTO userPostDTO;

    public PostUserDTO() {
    }

    public PostUserDTO(int postId, String imageSource, String caption, int likeCount, UserPostDTO userPostDTO) {
        this.postId = postId;
        this.imageSource = imageSource;
        this.caption = caption;
        this.likeCount = likeCount;
        this.userPostDTO = userPostDTO;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public UserPostDTO getUserPostDTO() {
        return userPostDTO;
    }

    public void setUserPostDTO(UserPostDTO userPostDTO) {
        this.userPostDTO = userPostDTO;
    }
}
