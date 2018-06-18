package eu.epfc.mypocketmovie.model;

import java.util.Date;

/**
 * Created by Olivier on 11/06/2018.
 */

public class MovieDetail extends Movie{
    private String overview;
    private Date issueDate;
    private String trailerLink;

    public MovieDetail(int id, String title, String overview, double rating, String imagepath, Date issueDate, String trailerLink) {
        super(id,title,rating,imagepath);
        this.overview = overview;
        this.issueDate = issueDate;
        this.trailerLink = trailerLink;
    }

    public String getOverview(){
        return this.overview;
    }
    public void setOverview(String overview){
        this.overview=overview;
    }

    public Date getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getTrailerLink() {
        return trailerLink;
    }
    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

}
