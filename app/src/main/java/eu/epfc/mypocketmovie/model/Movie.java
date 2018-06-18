package eu.epfc.mypocketmovie.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Olivier on 12/05/2018.
 */

public class Movie implements Serializable {
    private int id;
    private String title;
    private double rating;
    private String imagepath;
    public void setRating(double rating){
        this.rating=rating;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setImagePath(String imagepath){
        this.imagepath=imagepath;
    }
    public String getTitle(){
        return this.title;
    }
    public double getRating(){
        return this.rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath(){
        return this.imagepath;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }




    public Movie(int id, String title){
        this.id=id;
        this.title=title;
    }
    public Movie(int id, String title, double rating, String imagepath){
        this.id=id;
        this.title=title;
        this.rating=rating;
        this.imagepath=imagepath;
    }


}
