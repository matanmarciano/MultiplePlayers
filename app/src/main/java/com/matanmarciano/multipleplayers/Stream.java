package com.matanmarciano.multipleplayers;

import android.graphics.drawable.Drawable;

import java.util.Objects;

class Stream {
    private String id;
    private Drawable image;
    private String videoUrl;

    public Stream(String id, Drawable image, String videoUrl) {
        this.id = id;
        this.image = image;
        this.videoUrl = videoUrl;
    }

    public String getId() {
        return id;
    }

    public Drawable getImage() {
        return image;
    }

    public String getVideoUrl() {
        return videoUrl;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stream stream = (Stream) o;
        return id.equals(stream.id) &&
                image.equals(stream.image) &&
                videoUrl.equals(stream.videoUrl);
    }
}
