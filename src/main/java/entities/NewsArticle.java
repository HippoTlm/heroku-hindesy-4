package entities;

import java.time.LocalDate;
import java.util.Date;

/**
 * Class that embodies an article about the news about the project. It is characterized by :
 * - its identifier
 * - its date of release
 * - its photographs (up to three)
 * - its title (in French and English)
 * - its content (in French and English)
 */
public class NewsArticle {

    private Integer id;
    private LocalDate date;
    private String photo1;
    private String photo2;
    private String photo3;
    private String titleFR;
    private String titleEN;
    private String contentFR;
    private String contentEN;

    public NewsArticle(Integer id, LocalDate date, String photo1, String photo2, String photo3,
                       String titleFR, String titleEN, String contentFR, String contentEN) {
        this.id = id;
        this.date = date;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
        this.titleFR = titleFR;
        this.titleEN = titleEN;
        this.contentFR = contentFR;
        this.contentEN = contentEN;
    }

    /* Getters */

    public Integer getId() { return id; }

    public LocalDate getDate() { return date; }

    public String getPhoto1() { return photo1; }

    public String getPhoto2() { return photo2; }

    public String getPhoto3() { return photo3; }

    public String getTitleFR() { return titleFR; }

    public String getContentFR() { return contentFR; }

    public String getTitleEN() { return titleEN; }

    public String getContentEN() { return contentEN; }
}
