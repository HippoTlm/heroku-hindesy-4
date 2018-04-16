package entities;

import java.time.LocalDate;

/**
 * Class that embodies the content of the donation form someone filled on the page "Envie de nous soutenir ?".
 * This form is characterized by :
 * - an identifier
 * - the amount of the donation
 * - the email, civility, first and last name, address, address complement, postal code, city, country,
 *      phone number, and birth date of the person that filled the form
 * - if the person that filled the form wants a fiscal receipt or not
 */
public class FormDonation {

    private Integer id;
    private double amount;
    private String email;
    private String civility;
    private String firstName;
    private String lastName;
    private String address;
    private String addressCompl;
    private String postalCode;
    private String city;
    private String country;
    private String phone;
    private LocalDate birthDate;
    private boolean fiscalReceipt;

    /**
     * FormDonation constructor
     *
     * @param id the donation identifier
     * @param amount the amount of the donation
     * @param email the email of the person that filled the form
     * @param civility his civility
     * @param firstName his first name
     * @param lastName his last name
     * @param address his address
     * @param addressCompl the address complement
     * @param postalCode the postal code of his address
     * @param city his city
     * @param country his country
     * @param phone his phone number
     * @param birthDate his birth date
     * @param fiscalReceipt if he wants a fiscal receipt or not
     */
    public FormDonation(Integer id, double amount, String email, String civility, String firstName, String lastName,
                        String address, String addressCompl, String postalCode, String city, String country,
                        String phone, LocalDate birthDate, boolean fiscalReceipt) {
        this.id = id;
        this.amount = amount;
        this.email = email;
        this.civility = civility;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.addressCompl = addressCompl;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.birthDate = birthDate;
        this.fiscalReceipt = fiscalReceipt;
    }

    /* Getters */

    public Integer getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getEmail() {
        return email;
    }

    public String getCivility() {
        return civility;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getAddressCompl() {
        return addressCompl;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean wantsFiscalReceipt() {
        return fiscalReceipt;
    }
}
