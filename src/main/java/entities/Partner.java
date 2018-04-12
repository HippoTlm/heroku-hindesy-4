package entities;

/**
 * Class that embodies a commercial partner, who will be displayed in the "Partenaires" webpage.
 * A partner is characterized by its name, and its logo.
 */
public class Partner {
private Integer  id;
    private String name;
    private String logoPath;

    /**
     * Partner constructor.
     *
     * @param name the name of the partner
     * @param logoPath the path of its logo
     */
    public Partner(Integer id, String name, String logoPath) {
        this.id= id;
        this.name = name;
        this.logoPath = logoPath;
    }

    /* Getters */

    public Integer getId() { return id; }

    public String getName() { return name; }

    public String getLogoPath() { return logoPath; }
}
