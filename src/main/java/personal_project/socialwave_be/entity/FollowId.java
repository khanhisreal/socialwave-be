package personal_project.socialwave_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FollowId implements Serializable {

    @Column(name = "follower_id")
    private int followerId;

    @Column(name = "following_id")
    private int followingId;

    public FollowId() {
    }

    public FollowId(int followerId, int followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getFollowingId() {
        return followingId;
    }

    public void setFollowingId(int followingId) {
        this.followingId = followingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowId followId = (FollowId) o;
        return followerId == followId.followerId &&
                followingId == followId.followingId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, followingId);
    }
}
