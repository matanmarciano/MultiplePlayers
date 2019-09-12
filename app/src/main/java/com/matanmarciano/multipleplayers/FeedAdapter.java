package com.matanmarciano.multipleplayers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ui.PlayerView;

public class FeedAdapter extends ListAdapter<Stream, FeedAdapter.ViewHolder> {
    PlayerManager playerManager;

    protected FeedAdapter(PlayerManager playerManager, @NonNull DiffUtil.ItemCallback<Stream> diffCallback) {
        super(diffCallback);
        this.playerManager = playerManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_object, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private PlayerView playerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            playerView = itemView.findViewById(R.id.item_player);
            image = itemView.findViewById(R.id.item_image);
        }

        void bind(Stream streamItem) {
            if(streamItem.getVideoUrl() == null) {
                if(image.getDrawable() == null) {
                    image.setImageDrawable(streamItem.getImage());
                }

                image.setVisibility(View.VISIBLE);
                playerView.setVisibility(View.GONE);
            }
            else {
                if(playerView.getPlayer() == null) {
                    playerView.setPlayer(playerManager.getPlayer(streamItem));
                }

                image.setVisibility(View.GONE);
                playerView.setVisibility(View.VISIBLE);
            }
        }
    }
}
