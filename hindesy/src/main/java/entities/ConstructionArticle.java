package entities;

import servlets.ConstructionEvolutionServlet;

import java.time.LocalDate;

/**
 * The ConstructionArticle class modelises an article that will be displayed in the webpage "L'évolution du chantier".
 * Such an article is characterized by :
 * - an identifier
 * - a title
 * - its content
 * - the path of its picture
 *
 * @see ConstructionEvolutionServlet
 */
public class ConstructionArticle {

    private Integer id;
    private LocalDate date;
    private String titleFR;
    private String titleEN;
    private String contentFR;
    private String contentEN;
    private String photo;

    /**
     * Construction article constructor
     *
     * @param id the article ID
     * @param date the article publication date
     * @param titleFR its French title
     * @param titleEN its English title
     * @param contentFR its French content
     * @param contentEN its English content
     * @param photo the path of its picture
     */
    public ConstructionArticle(Integer id, LocalDate date, String titleFR, String titleEN,
                               String contentFR, String contentEN, String photo) {
        this.id = id;
        this.date = date;
        this.titleFR = titleFR;
        this.titleEN = titleEN;
        this.contentFR = contentFR;
        this.contentEN = contentEN;
        this.photo = photo;
    }

    /* Getters */

    public Integer getId() {
        return id;
    }
    public LocalDate getDate() { return date; }


    public String getTitleFR() {
        return titleFR;
    }

    public String getTitleEN() {
        return titleEN;
    }

    public String getContentFR() {
        return contentFR;
    }

    public String getContentEN() {
        return contentEN;
    }

    public String getPhoto() {
        return photo;
    }
}
