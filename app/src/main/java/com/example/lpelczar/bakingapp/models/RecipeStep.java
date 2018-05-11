package com.example.lpelczar.bakingapp.models;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class RecipeStep implements RecipeDetail, Parcelable {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;
    private Bitmap videoFrame;

    public void setVideoFrame(Bitmap videoFrame) {
        this.videoFrame = videoFrame;
    }

    public Bitmap getVideoFrame() {
        return videoFrame;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Short Description: " + shortDescription + " Description: " +
                description + " Video URL " + videoURL + " Thumbnail URL: " + thumbnailURL;
    }

    //Parcelable
    public static final Parcelable.Creator<RecipeStep> CREATOR = new Parcelable.Creator<RecipeStep>() {
        public RecipeStep createFromParcel(Parcel in) {
            return new RecipeStep(in);
        }

        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };

    private RecipeStep(Parcel in){
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.description =  in.readString();
        this.videoURL =  in.readString();
        this.thumbnailURL =  in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
    }
}
