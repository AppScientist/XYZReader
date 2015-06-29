package com.krypto.xyzreader;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pojo implements Parcelable{

    @Expose
    private String id;
    @Expose
    private String photo;
    @Expose
    private String author;
    @Expose
    private String title;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @Expose
    private String body;


    /**
     *
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The photo
     */
    public String getPhoto() {
        return photo;
    }


    /**
     *
     * @return
     *     The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     *
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return
     *     The publishedDate
     */
    public String getPublishedDate() {
        return publishedDate;
    }

    /**
     *
     * @return
     *     The body
     */
    public String getBody() {
        return body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(publishedDate);
        dest.writeString(body);
        dest.writeString(photo);

    }

    public static final Parcelable.Creator<Pojo> CREATOR = new Parcelable.Creator<Pojo>() {
        public Pojo createFromParcel(Parcel in) {
            return new Pojo(in);
        }

        public Pojo[] newArray(int size) {
            return new Pojo[size];
        }

    };

    public Pojo(Parcel in) {
        title = in.readString();
        author = in.readString();
        publishedDate = in.readString();
        body = in.readString();
        photo = in.readString();
    }
}

