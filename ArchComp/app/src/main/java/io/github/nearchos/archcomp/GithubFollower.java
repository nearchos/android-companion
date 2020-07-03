package io.github.nearchos.archcomp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "github_followers")
public class GithubFollower {

    @PrimaryKey private long id;
    private String login;
    @SerializedName("avatar_url") private String avatarUrl;
    @SerializedName("repos_url") private String reposUrl;
    @SerializedName("site_admin") private boolean siteAdmin;

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
}