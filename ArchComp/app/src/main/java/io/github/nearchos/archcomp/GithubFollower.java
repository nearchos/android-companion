/*
 * Copyright (c) 2020. Nearchos Paspallis
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 United
 * States License, described at this URL: https://creativecommons.org/licenses/by-nc-nd/3.0/us/.
 * Unless required by applicable law or agreed to in writing, code listed in this site is
 * distributed on "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 */

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