package services;

import daos.ClientDao;
import entities.Client;

import java.util.List;

/**
 * Interaction class between the DAO of the rents, and the associated servlet
 *
 * @see ClientDao
 * @see servlets.AdminWelcomeServlet
 */
public class ClientService {

    private ClientDao clientDao = new ClientDao();

    /**
     * Intern class that contains the static instance of ClientService
     */
    private static class ClientServiceHolder {
        private static ClientService instance = new ClientService();
    }

    /**
     * Returns the static instance of ClientDao of ClientServiceHolder
     *
     * @return ClientServiceHolder.instance
     */
    public static ClientService getInstance() { return ClientService.ClientServiceHolder.instance; }

    /**
     * Returns a specific client function of his email address
     *
     * @param email the client email address
     * @return the instance of Client requested
     */
    public Client getClient(String email) { return clientDao.getClient(email); }

    /**
     * Add a new client in the DB
     *
     * @param firstName the client's first name
     * @param lastName the client's last name
     * @param email the email address of the client
     */
    public void addClient(String firstName, String lastName, String email) {
        clientDao.addClient(firstName, lastName, email);
    }

    /**
     * Returns the list of all clients in the DB
     *
     * @return clients the said list
     */
    public List<Client> listClients() { return clientDao.listClients(); }
}
