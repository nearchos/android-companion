/*
 * Copyright (c) 2020. Nearchos Paspallis
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 United
 * States License, described at this URL: https://creativecommons.org/licenses/by-nc-nd/3.0/us/.
 * Unless required by applicable law or agreed to in writing, code listed in this site is
 * distributed on "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 */

package io.github.nearchos.archcomp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class GithubFollowerListAdapter extends RecyclerView.Adapter<GithubFollowerListAdapter.GithubFollowerViewHolder> {

    private final LayoutInflater inflater;
    private List<GithubFollower> githubFollowers;

    public GithubFollowerListAdapter(final Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    @NonNull
    public GithubFollowerViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View itemView = inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new GithubFollowerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final GithubFollowerViewHolder holder, int position) {
        if (githubFollowers != null) {
            final GithubFollower current = githubFollowers.get(position);
            holder.githubItemView.setText(current.getLogin());
        } else {
            // Covers the case of data not being ready yet.
            holder.githubItemView.setText("...");
        }
        holder.githubItemView.setOnClickListener(v -> GithubFollowerListAdapter.this.handleClick(position));
    }

    private OnGithubFollowerSelected onGithubFollowerSelected = null;

    public void setOnGithubFollowerSelected(OnGithubFollowerSelected onGithubFollowerSelected) {
        this.onGithubFollowerSelected = onGithubFollowerSelected;
    }

    private void handleClick(final int position) {
        if(onGithubFollowerSelected != null) {
            onGithubFollowerSelected.githubFollowerSelected(position, githubFollowers.get(position));
        }
    }

    void setGithubFollowers(List<GithubFollower> githubFollowers){
        this.githubFollowers = githubFollowers;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return githubFollowers != null ? githubFollowers.size() : 0;
    }

    static class GithubFollowerViewHolder extends RecyclerView.ViewHolder {
        private final TextView githubItemView;

        private GithubFollowerViewHolder(View itemView) {
            super(itemView);
            githubItemView = itemView.findViewById(R.id.textView);
        }
    }
}