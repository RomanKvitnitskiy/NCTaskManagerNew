package ua.edu.sumdu.j2se.kvitnytskyi.tasks.controller;

/**
 * Interface for changing and checking passwords.
 *
 * @author kvitnytskyi
 */
public interface SecurityController {

    /**
     * implements a password change
     * @param password - verification password
     */
    void changePassword(String password);

    /**
     * Performs password checking
     */
    boolean checkPassword();
}
