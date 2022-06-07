package ua.edu.sumdu.j2se.kvitnytskyi.tasks.controller.impl;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.controller.SecurityController;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.view.util.Output;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Scanner;

/**
 * Class that implements the SecurityController interface.
 *
 * @author kvitnytskyi
 */
public class SecurityControllerImpl implements SecurityController {

    private static final Logger log = Logger.getLogger(SecurityControllerImpl.class);
    private static final File FILE = new File("password.txt");
    private Scanner scanner;

    /**
     * Writing a new password to a file
     * @param password - verification password
     */
    @Override
    public void changePassword(String password) {
       String encryptedPassword = encrypt(password);

       try (FileWriter writer = new FileWriter(FILE)) {
           writer.write(encryptedPassword);
       } catch(IOException e){
           log.info(e);
       }
    }

    /**
     * Checking if the password is available, entered correctly and if it needs to be changed
     * @return true - Access permitted, false - Access denied
     */
    @Override
    public boolean checkPassword() {
        scanner = new Scanner(System.in);

        if (isFileEmpty(FILE)) {
            setNewPassword();
            return true;
        } else {
            String encryptedPassword = null;
            String ps = null;
            String password = null;
            int i = 3;

            try(FileReader reader = new FileReader(FILE)) {
                Scanner scan = new Scanner(reader);

                while (scan.hasNextLine()) {
                    encryptedPassword = scan.nextLine();
                }
            } catch (IOException e) {
                log.info(e);
            }

            while (i-- != 0) {
                Output.print("Enter password: ");
                ps = scanner.nextLine();
                password = encrypt(ps);

                if (!password.equals(encryptedPassword)) {
                    Output.println("Bad password, try again!");
                    Output.println("You have " + i + " attempts left");
                } else {
                    Output.println("Correct password!");
                    Output.println("Want to set a new password? (Yes:No)");
                    String answer = scanner.nextLine();
                    if (answer.equals("Yes") || answer.equals("yes")) {
                        setNewPassword();
                    }
                    return true;
                }
            }

        }
        return false;
    }

    private void setNewPassword() {
        scanner = new Scanner(System.in);
        Output.print("Enter new password: ");
        String password = scanner.nextLine();
        changePassword(password);
    }

    public boolean isFileEmpty(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.readLine() == null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String encrypt(String password) {
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
