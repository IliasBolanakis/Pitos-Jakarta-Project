package com.ilbolan.pitoswebproject.utils;

import com.ilbolan.pitoswebproject.forms.ContactForm;
import com.ilbolan.pitoswebproject.forms.OrderForm;
import com.ilbolan.pitoswebproject.models.AreaDAO;
import com.ilbolan.pitoswebproject.models.PieDAO;
import com.ilbolan.pitoswebproject.models.beans.Pie;
import com.ilbolan.pitoswebproject.models.beans.User;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Class responsible for sending text/html email to client and administrator
 */
public class Email implements Serializable {

    // declare logger
    private static final AppLogger logger = AppLogger.getLogger(Email.class);

    // mail account details held in .properties file
    private static Properties properties;

    private static Properties properties(){
        if(properties != null)
            return properties;

        try (InputStream is = Email.class.getClassLoader().getResourceAsStream("mail.properties")) {
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Sends simple text email
     *
     * @param recipientToEmail email of recipient
     * @param subject email's subject
     * @param body email's text body
     */
    public static void sendTextEmail(String recipientToEmail, String subject, String body) {
        // create session
        Properties finalProperties = properties();
        Session session = Session.getInstance(finalProperties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                finalProperties.getProperty("username"),
                                finalProperties.getProperty("password")
                        );
                    }
                }
        );

        // send e-mail
        Message mimeMessage = new MimeMessage(session);
        try {
            // prepare message
            mimeMessage.setSubject(subject);
            mimeMessage.setText(body);

            // declare recipients
            Address from = new InternetAddress(properties().getProperty("mail.admin"));
            Address to = new InternetAddress(recipientToEmail);

            mimeMessage.setFrom(from);
            mimeMessage.setRecipient(Message.RecipientType.TO, to);

            // send the message
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "Unable to send text email to " + recipientToEmail);
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends Html email
     *
     * @param recipientToEmail email of recipient
     * @param subject email's subject
     * @param body email's html body
     */
    private static void sendHtmlEmail(String recipientToEmail, String subject, String body) {
        // create session
        Properties finalProperties = properties();
        Session session = Session.getInstance(finalProperties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                finalProperties.getProperty("username"),
                                finalProperties.getProperty("password")
                        );
                    }
                }
        );

        // send e-mail
        Message mimeMessage = new MimeMessage(session);
        try {
            // prepare message
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(body, "text/html; charset=UTF-8");

            // declare recipients
            Address from = new InternetAddress(properties.getProperty("mail.admin"));
            Address to = new InternetAddress(recipientToEmail);

            mimeMessage.setFrom(from);
            mimeMessage.setRecipient(Message.RecipientType.TO, to);

            // send the message
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "Unable to send HTML email to " + recipientToEmail);
            throw new RuntimeException(e);
        }
    }

    /**
     * Receives recipient info & sends text email to user post-contact request
     *
     * @param contactForm The {@link ContactForm} that encapsulates all the recipient's data
     */
    public static void sendEmailToClientContactForm(ContactForm contactForm) {
        String text =
                "Παραλάβαμε το μήνυμα σας με τα εξής στοιχεία: \n" +
                        "\tΟνοματεπώνυμο: " + contactForm.getFullName() + "\n" +
                        "\tE-mail: " + contactForm.getEmail() + "\n" +
                        "\tΤηλέφωνο: " + contactForm.getTel() + "\n" +
                        "\tΜήνυμα: " + contactForm.getMessage() + "\n\n" +
                        "και θα επικοινωνήσουμε μαζί σας σύντομα!";

        sendTextEmail(contactForm.getEmail(), "Ενημέρωση Επικοινωνίας", text);
        logger.log(Level.INFO, "Successfully sent contact form email to " + contactForm.getEmail());
    }

    /**
     * Receives recipient info & sends text email to admin post-contact request
     *
     * @param contactForm The {@link ContactForm} that encapsulates all the recipient's data
     */
    public static void sendEmailToAdminContactForm(ContactForm contactForm) {
        // read properties
        Properties properties = properties();

        String text =
                "Νέο μήνυμα από τη φόρμα επικοινωνίας της σελίδας μας: \n" +
                        "\tΟνοματεπώνυμο: " + contactForm.getFullName() + "\n" +
                        "\tE-mail: " + contactForm.getEmail() + "\n" +
                        "\tΤηλέφωνο: " + contactForm.getTel() + "\n" +
                        "\tΜήνυμα: " + contactForm.getMessage() + "\n";

        sendTextEmail(properties.getProperty("mail.admin"), "Εισερχόμενο Μήνυμα από τη φόρμα επικοινωνίας", text);
        logger.log(Level.INFO, "Sent email to Admin (Contact form)");
    }

    /**
     * Forms & sends Html email to client post-ordering
     *
     * @param orderForm The {@link OrderForm} that encapsulates all the recipient details
     */
    public static void sendEmailToClientOrder(OrderForm orderForm) {
        List<Pie> pies = PieDAO.getPies();

        int[] quantities = new int[4];
        double[] values = new double[4];

        for (int i=0; i<values.length; i++) {
            quantities[i] = orderForm.getOrderItems().get(i).getQuantity();
            values[i] = quantities[i] * pies.get(i).getPrice();
        }
        double sum = Arrays.stream(values).sum();

        LocalDateTime timestamp = orderForm.getTimestamp();

        LocalDateTime timestampUntil = timestamp.plus(30, ChronoUnit.MINUTES);


        String text =
                "<header style=\"padding: 30px;text-align: center;font-size: 20px;font-weight: bold;\">Παραγγελία καθ΄οδόν</header>\n" +
                        "<table class=\"table-pies\" style=\"border-collapse: collapse;vertical-align: center;caption-side: bottom;margin: 0 auto;box-shadow: 0 0 4px 1px #483C46;width: 90%;\">\n" +
                        "  <thead>\n" +
                        "  <tr style=\"background-image: linear-gradient(to bottom, #483C46, #675664);font-size: 1em;margin-bottom: 10px;color: #BEEE62;\">\n" +
                        "    <th style=\"padding: 10px 20px;border: 0;\">Πίτα</th>\n" +
                        "    <th style=\"padding: 10px 20px;border: 0;\">Ποσότητα</th>\n" +
                        "    <th style=\"padding: 10px 20px;border: 0;\">Τιμή</th>\n" +
                        "  </tr>\n" +
                        "  </thead>\n" +
                        "  <tbody>\n" +
                        "  <tr>\n" +
                        "    <td style=\"text-align: center;border: 0;padding: 30px;font-weight: normal;\">Σπανακόπιτες</td>\n" +
                        "    <td style=\"text-align: center;border: 0;padding: 30px;font-weight: normal;\">" + quantities[0] + "</td>\n" +
                        "    <td style=\"text-align: center;border: 0;padding: 30px;font-weight: normal;\">" + String.format("%.2f", values[0]) + "€</td>\n" +
                        "  </tr>\n" +
                        "  <tr style=\"background-color: #ded8dd\">\n" +
                        "    <td style=\"text-align: center;border: 0;padding: 30px;font-weight: normal;\">Μανιταρόπιτες</td>\n" +
                        "    <td style=\"text-align: center;border: 0;padding: 30px;font-weight: normal;\">" + quantities[1] + "</td>\n" +
                        "    <td style=\"text-align: center;border: 0;padding: 30px;font-weight: normal;\">" + String.format("%.2f", values[1]) + "€</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td style=\"text-align: center;border: 0;padding: 30px;font-weight: normal;\">Πρασόπιτες</td>\n" +
                        "    <td style=\"text-align: center;border: 0;padding: 30px;font-weight: normal;\">" + quantities[2] + "</td>\n" +
                        "    <td style=\"text-align: center;border: 0;padding: 30px;font-weight: normal;\">" + String.format("%.2f", values[2]) + "€</td>\n" +
                        "  </tr>\n" +
                        "  <tr style=\"background-color: #ded8dd\">\n" +
                        "    <td style=\"text-align: center;border: 0;padding: 30px;font-weight: normal;\">Μπουρέκια</td>\n" +
                        "    <td style=\"text-align: center;border: 0;padding: 30px;font-weight: normal;\">" + quantities[3] + "</td>\n" +
                        "    <td style=\"text-align: center;border: 0;padding: 30px;font-weight: normal;\">" + String.format("%.2f", values[3]) + "€</td>\n" +
                        "  </tr>\n" +
                        "  </tbody>\n" +
                        "  <tfoot>\n" +
                        "  <tr>\n" +
                        "    <td colspan=\"3\" style=\"padding: 10px;font-weight: bold;font-size: 20px;\">Σύνολο: " + String.format("%.2f", sum) + "€\n" +
                        "    </td>\n" +
                        "  </tr>\n" +
                        "  </tfoot>\n" +
                        "</table>\n" +
                        "<p style=\"padding: 30px;text-align: center;font-size: 20px;font-weight: bold;\">Εκτιμώμενη Ώρα Παράδοσης: " +
                        timestampUntil.format(DateTimeFormatter.ofPattern("d/M/u (kk:mm:ss)")) +
                        "</p>";

        sendHtmlEmail(orderForm.getEmail(), "Η Παραγγελία Σας!", text);
    }

    /**
     * Sends text email to admin post-ordering
     *
     * @param orderForm The {@link OrderForm} that encapsulates all the recipient details
     */
    public static void sendEmailToAdminOrderForm(OrderForm orderForm) {
        // read properties
        Properties properties = properties();

        String text =
                "Νέα παραγγελία: \n" +
                        "\tΟνοματεπώνυμο: " + orderForm.getFullName() + "\n" +
                        "\tΔιεύθυνση: " + orderForm.getAddress() + "\n" +
                        "\tΠεριοχή: " + AreaDAO.getAreaById(orderForm.getAreaId()).getDescription() + "\n" +
                        "\tE-mail: " + orderForm.getEmail() + "\n" +
                        "\tΤηλέφωνο: " + orderForm.getTel() + "\n" +
                        "\tΜήνυμα: " + orderForm.getComments() + "\n" +
                        "\tΠαραγγελία: \n" +
                        "\t\tΣπανακόπιτες: " + orderForm.getOrderItems().get(0).getQuantity() + "\n" +
                        "\t\tΜανιταρόπιτες: " + orderForm.getOrderItems().get(1).getQuantity() + "\n" +
                        "\t\tΠρασόπιτες: " + orderForm.getOrderItems().get(1).getQuantity() + "\n" +
                        "\t\tΜπουρέκια: " + orderForm.getOrderItems().get(1).getQuantity() + "\n" +
                        "\tΠροσφορά: " + orderForm.getOffer() + "\n" +
                        "\tΤρόπος Πληρωμής: " + orderForm.getPayment() + "\n";

        sendTextEmail(properties.getProperty("mail.admin"), "Νέα Παραγγελία", text);
    }

    /**
     * Sends an HTML email to redirect the user to a page for register completion
     *
      * @param user The {@link User} that encapsulates the user code & email
     */
    public static void sendEmailCompleteRegister(User user) {
        String text =
                "<div>Ακολουθήστε τον ακόλουθο σύνδεσμο για να ολοκληρώσετε την εγγραφή σας: " +
                        "<a href=\"http://localhost:8080/register?code=" + user.getCode() +"\">" +
                        "http://localhost:8080/register?code=" + user.getCode() + "</a>" +
                        "</div>";

        sendHtmlEmail(user.getEmail(), "Επαλήθευση Εγγραφής", text);
    }

    /**
     * Sends an HTML email to redirect the user to page for resetting the password
     * @param email the address to send the email
     * @param code is the unique code that is used for password reset
     */
    public static void sendEmailResetPassword(String email, String code){
        String text =
                "<div>Ακολουθήστε τον ακόλουθο σύνδεσμο για να επαναφέρετε τον κωδικό σας: " +
                        "<a href=\"http://localhost:8080/change-password?code=" + code +"\">" +
                        "http://localhost:8080/change-password?code=" + code + "</a>" +
                        "</div>";

        sendHtmlEmail(email, "Επαναφορά Κωδικού", text);
    }
}