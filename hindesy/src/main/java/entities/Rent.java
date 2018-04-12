package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that embodies a rent. A rent is characterized by:
 * - its identifier
 * - the date of its beginning
 * - the date of its ending
 * - the email address of the client
 * - a boolean stating if the rent is over or not
 */
public class Rent {

    private Integer id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String email;
    private boolean confirmed;

    /**
     * Rent constructor.
     *
     * @param id the identifier of the rent
     * @param dateDebut its beginning date
     * @param dateFin its ending date
     * @param email the email address of the client
     */
    public Rent(Integer id, LocalDate dateDebut, LocalDate dateFin, String email) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.email = email;
    }

    /* Getters */

    public Integer getId() { return id; }

    public LocalDate getDateDebut() {  return dateDebut; }

    public LocalDate getDateFin() { return dateFin; }

    public String getEmail() { return email; }

    public boolean isConfirmed() { return confirmed; }
}
