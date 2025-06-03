package com.example.tp4;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Book implements Parcelable{
    private String title, author, year, blurb, cover;
    private boolean liked;


    public Book(String title, String author, String year, String blurb, boolean liked, String cover) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.liked = liked;
        this.cover = cover;
    }

    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        year = in.readString();
        blurb = in.readString();
        liked = in.readByte() != 0;
        cover = in.readString();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public boolean isLike()  {
        return liked;
    }

    public void setLike(boolean liked) {
        this.liked = liked;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(year);
        dest.writeString(blurb);
        dest.writeByte((byte) (liked ? 1 : 0));
        dest.writeString(cover);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return title.equals(book.title) && author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author);
    }

}
