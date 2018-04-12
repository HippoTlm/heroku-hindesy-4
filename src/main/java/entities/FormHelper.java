package entities;

import servlets.HelpersServlet;

/**
 * The FormHelper class embodies someone who answers the form at "Ils nous ont aidés !".
 * He is characterized by :
 * - his identifier
 * - his first name
 * - his last name
 * - if he has protection shoes or not
 * - if he has a helmet or not
 * - if he has protection gloves or not
 * - if he has some specific equipments
 * - his shoes size
 * - his email
 * - his phone number
 * - the message he would like to leave to explain how he could help
 *
 * @see HelpersServlet
 */
public class FormHelper {

    private Integer id;
    private String firstName;
    private String lastName;
    private Boolean shoes;
    private Boolean helmet;
    private Boolean gloves;
    private String equipment;
    private String size;
    private String email;
    private String phone;
    private String message;

    /**
     * FormHelper constructor
     *
     * @param id the helper identifier
     * @param firstName his first name
     * @param lastName his last name
     * @param shoes if he has protection shoes or not
     * @param helmet if he has a helmet or not
     * @param gloves if he has protection gloves or not
     * @param equipment if he has some specific equipments
     * @param size his shoes size
     * @param email his email
     * @param phone his phone number
     * @param message the message he would like to leave to explain how he could help
     */
    public FormHelper(Integer id, String firstName, String lastName, Boolean shoes, Boolean helmet, Boolean gloves,
                  String equipment, String size, String email, String phone, String message) {
        this.id = id;
        this. firstName = firstName;
        this.lastName = lastName;
        this.shoes = shoes;
        this.helmet = helmet;
        this.gloves = gloves;
        this.equipment = equipment;
        this.size = size;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    /* Getters */

    public Integer getId() { return id; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public boolean hasShoes() { return shoes; }

    public boolean hasHelmet() { return helmet; }

    public boolean hasGloves() { return gloves; }

    public String getEquipment() { return equipment; }

    public String getSize() { return size; }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public String getMessage() { return message; }
}
