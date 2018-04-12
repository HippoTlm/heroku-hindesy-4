package entities;

/**
 * Class that embodies someone that filled the form in the "Partenaires" webpage, willing to become a commercial partner.
 * Somebody like this is characterized by :
 * - his identifier
 * - his first name
 * - his last name
 * - his organization
 * - his email
 * - his phone number
 * - his message, if he let one
 */
public class FormPartner {

    private Integer id;
    private String firstName;
    private String lastName;
    private String organization;
    private String email;
    private String phone;
    private String message;

    public FormPartner(Integer id, String firstName, String lastName, String organization,
                       String email, String phone, String message) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOrganization() {
        return organization;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getMessage() {
        return message;
    }
}
