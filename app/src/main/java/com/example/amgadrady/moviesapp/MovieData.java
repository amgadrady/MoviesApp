package com.example.amgadrady.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Amgad Rady on 23/10/2016.
 */

public class MovieData implements Parcelable {

  private  String posterPath ;
    private String overview;
    private  String releaseDate ;
    private String id;
    private  String title ;
    private  String voteAverage;


    public  MovieData()
    {

        super();
    }
    public void setposterPath (String posterPath)
    {
this.posterPath=posterPath;
    }

    public void setoverview (String overview)
    {
        this.overview=overview;
    }

    public void setid (String id)
    {
        this.id=id;
    }
    public void settitle (String title)
    {
        this.title=title;
    }
    public void setreleaseDate(String releaseDate)
    {
        this.releaseDate=releaseDate;
    }
    public void setvoteAverage (String voteAverage) {this.voteAverage=voteAverage;}

    // get functions

    public String getposterPath ()
    {
        return posterPath;
    }

    public String getoverview ()
    {
        return overview;
    }

    public String getid ()
    {
        return id;
    }
    public String gettitle ()
    {
        return title;
    }
    public String getreleaseDate()
    {
         return releaseDate;
    }
    public String getvoteAverage () {return voteAverage;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(voteAverage);


    }
    private MovieData(Parcel in) {
         posterPath=in.readString() ;
        overview=in.readString();
         releaseDate =in.readString();
         id=in.readString();
         title =in.readString();
          voteAverage=in.readString();

    }

  public static final Parcelable.Creator<MovieData> CREATOR =new Parcelable.Creator<MovieData>()
  {


      public MovieData createFromParcel(Parcel in)
      {
          return new MovieData(in);

      }
      public MovieData[] newArray(int size)
      {
          return new MovieData[size];

      }
  };

}
