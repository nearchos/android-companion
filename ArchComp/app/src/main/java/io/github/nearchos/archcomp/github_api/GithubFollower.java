package io.github.nearchos.archcomp.github_api;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * @author Nearchos
 * Created: 14-Jun-20
 */
@Entity(tableName = "github_followers")
public class GithubFollower {

    @PrimaryKey private long id;
    private String login;
    @SerializedName("avatar_url") private String avatarUrl;
    @SerializedName("repos_url") private String reposUrl;
    @SerializedName("repos_url") private boolean siteAdmin;

    public GithubFollower(long id, String login, String avatarUrl, String reposUrl, boolean siteAdmin) {
        this.id = id;
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.reposUrl = reposUrl;
        this.siteAdmin = siteAdmin;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public boolean isSiteAdmin() {
        return siteAdmin;
    }

    @Override
    @NonNull
    public String toString() {
        return "FollowersResponse{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", reposUrl='" + reposUrl + '\'' +
                ", siteAdmin=" + siteAdmin +
                '}';
    }
}