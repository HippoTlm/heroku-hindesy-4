package entities;

/**
 * Class that embodies a client, that is characterized by:
 * - his first name
 * - his last name
 * - his email address
 */
public class Client {

    private String firstName;
    private String lastName;
    private String email;

    /**
     * Constructor of a Client.
     * @param firstName his first name
     * @param lastName his last name
     * @param email his email address
     */
    public Client(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /* Getters */

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getEmail() { return email; }
}
