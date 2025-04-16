package personal_project.socialwave_be.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "story")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_id")
    private int storyId;

    @Column(name = "image_source")
    private String imageSource;

    @Column(name = "is_highlight")
    private boolean isHighLight;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private User user;

    public Story() {
    }

    public Story(String imageSource, boolean isHighLight) {
        this.imageSource = imageSource;
        this.isHighLight = isHighLight;
        this.createTime = LocalDateTime.now();
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public boolean isHighLight() {
        return isHighLight;
    }

    public void setHighLight(boolean highLight) {
        isHighLight = highLight;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Story{" +
                "storyId=" + storyId +
                ", imageSource='" + imageSource + '\'' +
                ", isHighLight=" + isHighLight +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", user=" + user +
                '}';
    }
}
