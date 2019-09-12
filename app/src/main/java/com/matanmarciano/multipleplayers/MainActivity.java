package com.matanmarciano.multipleplayers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainFragmentAdapter mainFragmentAdapter;
    private ViewPager viewPager;
    private static final String VIDEO_URL = "https://devstreaming-cdn.apple.com/videos/streaming/examples/bipbop_16x9/bipbop_16x9_variant.m3u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);

        List<Stream> playersList = initPlayersList();
        List<Stream> imagesList1 = initImagesList1();
        List<Stream> imagesList2 = initImagesList2();
        List<Stream> imagesList3 = initImagesList3();

        PlayerManager playerManager = new PlayerManager(playersList, this, false, false, true);

        FeedFragment players = new FeedFragment(playersList, playerManager);
        FeedFragment images1 = new FeedFragment(imagesList1, playerManager);
        FeedFragment images2 = new FeedFragment(imagesList2, playerManager);
        FeedFragment images3 = new FeedFragment(imagesList3, playerManager);
        mainFragmentAdapter.addFragment(players);
        mainFragmentAdapter.addFragment(images1);
        mainFragmentAdapter.addFragment(images2);
        mainFragmentAdapter.addFragment(images3);

        viewPager.setAdapter(mainFragmentAdapter);
    }

    private List<Stream> initPlayersList() {
        List<Stream> playersStreams = new ArrayList<>();

        playersStreams.add(new Stream("Player1", getDrawable(R.drawable.image), VIDEO_URL));
        playersStreams.add(new Stream("Player2", getDrawable(R.drawable.image), VIDEO_URL));
        playersStreams.add(new Stream("Player3", getDrawable(R.drawable.image), VIDEO_URL));
        playersStreams.add(new Stream("Player4", getDrawable(R.drawable.image), VIDEO_URL));
        playersStreams.add(new Stream("Player5", getDrawable(R.drawable.image), VIDEO_URL));
        playersStreams.add(new Stream("Player6", getDrawable(R.drawable.image), VIDEO_URL));
        playersStreams.add(new Stream("Player7", getDrawable(R.drawable.image), VIDEO_URL));
        playersStreams.add(new Stream("Player8", getDrawable(R.drawable.image), VIDEO_URL));
        playersStreams.add(new Stream("Player9", getDrawable(R.drawable.image), VIDEO_URL));
        playersStreams.add(new Stream("Player10", getDrawable(R.drawable.image), VIDEO_URL));

        return playersStreams;
    }

    private List<Stream> initImagesList1() {
        List<Stream> imagesList1 = new ArrayList<>();

        imagesList1.add(new Stream("Image1", getDrawable(R.drawable.image), null));
        imagesList1.add(new Stream("Image2", getDrawable(R.drawable.image), null));
        imagesList1.add(new Stream("Image3", getDrawable(R.drawable.image), null));

        return imagesList1;
    }

    private List<Stream> initImagesList2() {
        List<Stream> imagesList2 = new ArrayList<>();

        imagesList2.add(new Stream("Image4", getDrawable(R.drawable.image), null));
        imagesList2.add(new Stream("Image5", getDrawable(R.drawable.image), null));

        return imagesList2;
    }

    private List<Stream> initImagesList3() {
        List<Stream> imagesList3 = new ArrayList<>();

        imagesList3.add(new Stream("Image6", getDrawable(R.drawable.image), null));
        imagesList3.add(new Stream("Image7", getDrawable(R.drawable.image), null));
        imagesList3.add(new Stream("Image8", getDrawable(R.drawable.image), null));
        imagesList3.add(new Stream("Image9", getDrawable(R.drawable.image), null));

        return imagesList3;
    }
}
