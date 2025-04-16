package personal_project.socialwave_be.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "name")
    private String name;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar_source")
    private String avatarSource;

    @Column(name = "bio")
    private String bio;

    @Column(name = "last_active_at")
    private LocalDateTime lastActiveAt;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    //one-to-many: lazy load by default
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    public List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Story> stories = new ArrayList<>();

    //new relationship for follow feature
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> following = new HashSet<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> followers = new HashSet<>();

    public User() {
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

    public LocalDateTime getLastActiveAt() {
        return lastActiveAt;
    }

    public void setLastActiveAt(LocalDateTime lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
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

    public Set<Follow> getFollowing() {
        return following;
    }

    public void setFollowing(Set<Follow> following) {
        this.following = following;
    }

    public Set<Follow> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Follow> followers) {
        this.followers = followers;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    // add convenience methods for bi-directional relationship
    public void add(Post tempPost) {

        if (posts == null) {
            posts = new ArrayList<>();
        }

        posts.add(tempPost);

        tempPost.setUser(this);
    }

    // Convenience methods for follow relationships
    public void follow(User userToFollow) {
        Follow follow = new Follow(this, userToFollow);
        following.add(follow);
        userToFollow.getFollowers().add(follow);
    }

    public void unfollow(User userToUnfollow) {
        for (Follow follow : new ArrayList<>(following)) {
            if (follow.getFollowing().equals(userToUnfollow)) {
                following.remove(follow);
                userToUnfollow.getFollowers().remove(follow);
                break;
            }
        }
    }

    // Helper method to get user's followers
    public List<User> getFollowerUsers() {
        List<User> followerUsers = new ArrayList<>();
        for (Follow follow : followers) {
            followerUsers.add(follow.getFollower());
        }
        return followerUsers;
    }

    // Helper method to get users that this user follows
    public List<User> getFollowingUsers() {
        List<User> followingUsers = new ArrayList<>();
        for (Follow follow : following) {
            followingUsers.add(follow.getFollowing());
        }
        return followingUsers;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatarSource='" + avatarSource + '\'' +
                ", bio='" + bio + '\'' +
                ", lastActiveAt=" + lastActiveAt +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", posts=" + posts +
                ", comments=" + comments +
                ", stories=" + stories +
                ", following=" + following +
                ", followers=" + followers +
                '}';
    }
}
