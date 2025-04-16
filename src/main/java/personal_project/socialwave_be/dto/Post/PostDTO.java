package personal_project.socialwave_be.dto.Post;

import java.time.LocalDateTime;

public class PostDTO {
    private int postId;
    private String imageSource;
    private String caption;
    private int likeCount;
    private int userId;

    public PostDTO(int postId, String imageSource, String caption, int likeCount, int userId) {
        this.postId = postId;
        this.imageSource = imageSource;
        this.caption = caption;
        this.likeCount = likeCount;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
