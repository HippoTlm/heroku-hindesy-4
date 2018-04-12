package entities;

public class Contributor {

    private Integer id;
    private String firstName;
    private String lastName;
    private String photo;
    private String label;

    /**
     * Contributor constructor.
     *@param id
     * @param firstName the name of the Contributor
     * @param photo the path of its logo
     */
    public Contributor(Integer id, String firstName, String lastName, String photo, String label) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.label = label;
    }

    /* Getters */

    public String getfirstName() { return firstName; }

    public String getlastName() { return lastName; }

    public String getphoto() { return photo; }

    public String getlabel() { return label; }


    public Integer getId() { return id; }

}
