package com.matanmarciano.multipleplayers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FeedFragment extends Fragment {
    protected ViewGroup mRootView;
    private FeedAdapter feedAdapter;
    private RecyclerView recyclerView;
    private List<Stream> streams;
    private PlayerManager playerManager;

    public FeedFragment(List<Stream> streams, PlayerManager playerManager) {
        this.streams = streams;
        this.playerManager = playerManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = (ViewGroup) inflater.inflate(R.layout.activity_feed_fragment, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        feedAdapter = new FeedAdapter(playerManager, new DiffUtil.ItemCallback<Stream>() {
            @Override
            public boolean areItemsTheSame(@NonNull Stream oldItem, @NonNull Stream newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Stream oldItem, @NonNull Stream newItem) {
                return oldItem.equals(newItem);
            }
        });

        feedAdapter.submitList(streams);

        recyclerView = mRootView.findViewById(R.id.feed_recycler);
        recyclerView.setAdapter(feedAdapter);
    }
}
