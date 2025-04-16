package personal_project.socialwave_be.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postId;

    //the child shouldn't be responsible for cascading operation to its parent
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "image_source")
    private String imageSource;

    @Column(name = "caption")
    private String caption;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.ALL})
    private List<Comment> comments;

    public Post() {
    }

    public Post(String imageSource, String caption) {
        this.imageSource = imageSource;
        this.caption = caption;
        this.createTime = LocalDateTime.now();
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", imageSource='" + imageSource + '\'' +
                ", caption='" + caption + '\'' +
                ", likeCount=" + likeCount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
