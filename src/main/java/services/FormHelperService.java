package services;

import daos.FormHelpersDao;
import entities.FormHelper;

public class FormHelperService {


    private FormHelpersDao helperDao = new FormHelpersDao();

    /**
     * Intern class that contains the static instance of FormHelperService
     */
    private static class FormHelperServiceHolder {
        private static FormHelperService instance = new FormHelperService();
    }

    /**
     * Returns the static instance of FormHelperDao of FormHelperServiceHolder
     * @return FormHelperServiceHolder.instance
     */
    public static FormHelperService getInstance() { return FormHelperService.FormHelperServiceHolder.instance; }

    public void addHelper(FormHelper newFormHelper) {


        helperDao.addHelper(newFormHelper);
    }

}
