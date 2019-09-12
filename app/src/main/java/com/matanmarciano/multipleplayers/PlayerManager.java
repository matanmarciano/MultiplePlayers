package com.matanmarciano.multipleplayers;


import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.android.exoplayer2.Player.STATE_READY;

public class PlayerManager implements Player.EventListener {
    private static final String TAG = "PlayerManager";
    private Map<String, SimpleExoPlayer> players;
    private final Context context;
    private final boolean isPlayWhenReady;
    private final boolean isPlayAudio;
    private final boolean isRepeatMode;
    private int countDownPlayersReadyIndicator;
    private boolean isPlayersStartPlay = false;
    private final List<Stream> streams;

    public PlayerManager(List<Stream> streams, Context context, boolean isPlayWhenReady, boolean isPlayAudio, boolean isRepeatMode) {
        this.streams = streams;
        this.context = context;
        this.isPlayWhenReady = isPlayWhenReady;
        this.isPlayAudio = isPlayAudio;
        this.isRepeatMode = isRepeatMode;
        this.players = new HashMap<>();
        this.countDownPlayersReadyIndicator = streams.size();

        preparePlayers();
    }

    private void preparePlayers() {
        for (Stream stream : streams) {
            final SimpleExoPlayer newPlayer = ExoPlayerFactory.newSimpleInstance(context);
            newPlayer.setPlayWhenReady(isPlayWhenReady);
            newPlayer.setVolume(isPlayAudio ? 1 : 0);
            newPlayer.setRepeatMode(isRepeatMode ? Player.REPEAT_MODE_ALL : Player.REPEAT_MODE_OFF);
            newPlayer.addListener(this);

            players.put(stream.getId(), newPlayer);

            new Handler().post(() -> {
                MediaSource mediaSource = buildMediaSource(stream.getVideoUrl());
                newPlayer.prepare(mediaSource, false, false);
                Log.d(TAG, "Preparing player " + stream.getId());
            });
        }
    }

    private MediaSource buildMediaSource(String previewPlaybackUrl) {
        Uri uri = Uri.parse(previewPlaybackUrl);

        DataSource.Factory dataSourceFactory = buildDataSourceFactory(uri);

        Uri uriWithoutExtension = Uri.parse(previewPlaybackUrl.substring(0, previewPlaybackUrl.lastIndexOf(".")));
        String extension = previewPlaybackUrl.substring(previewPlaybackUrl.lastIndexOf(".") + 1);
        @C.ContentType int type = Util.inferContentType(uriWithoutExtension, extension);

        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            default:
                throw new IllegalStateException("Unsupported type: " + type);
        }
    }

    private DataSource.Factory buildDataSourceFactory(Uri uri) {
        switch (uri.getScheme()) {
            case "file":
                return new DefaultDataSourceFactory(context, context.getPackageName());
            default:
                return new DefaultHttpDataSourceFactory(context.getPackageName());
        }
    }

    private void playAll() {
        for (Map.Entry<String, SimpleExoPlayer> entry : players.entrySet()) {
            String streamId = entry.getKey();
            SimpleExoPlayer player = entry.getValue();

            if (player != null) {
                Log.d(TAG, "Playing player " + streamId);
                player.setPlayWhenReady(true);
            }
        }
    }

    public void releaseAllPlayers() {
        for (Map.Entry<String, SimpleExoPlayer> entry : players.entrySet()) {
            String streamId = entry.getKey();
            SimpleExoPlayer player = entry.getValue();

            Log.d(TAG, "Releasing player " + streamId);
            player.release();
        }

        players.clear();
    }

    public SimpleExoPlayer getPlayer(Stream stream) {
        return players.get(stream.getId());
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == STATE_READY) {
            countDownPlayersReadyIndicator--;

            if(countDownPlayersReadyIndicator == 0 && !isPlayersStartPlay) {
                isPlayersStartPlay = true;
                playAll();
            }
        }
    }
}
