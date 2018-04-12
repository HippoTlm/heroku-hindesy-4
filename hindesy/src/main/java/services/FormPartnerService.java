package services;

import daos.FormPartnerDao;
import entities.FormPartner;

public class FormPartnerService {

    private FormPartnerDao partnerDao = new FormPartnerDao();

    /**
     * Intern class that contains the static instance of FormPartnerService
     */
    private static class FormPartnerServiceHolder {
        private static FormPartnerService instance = new FormPartnerService();
    }

    /**
     * Returns the static instance of FormPartnerDao of FormPartnerServiceHolder
     * @return FormPartnerServiceHolder.instance
     */
    public static FormPartnerService getInstance() { return FormPartnerService.FormPartnerServiceHolder.instance; }

    public void addPartner(FormPartner newFormPartner) {
        if(newFormPartner.getFirstName() == null || newFormPartner.getFirstName().length()>25) {
            throw new IllegalArgumentException("Veuillez insérer un prénom valide");
        }

        partnerDao.addPartner(newFormPartner);
    }


}
