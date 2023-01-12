package com.ilbolan.pitoswebproject.forms.annotations.validators;
import com.ilbolan.pitoswebproject.forms.annotations.EmailConstrains;
import com.ilbolan.pitoswebproject.utils.AppLogger;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import javax.naming.*;
import javax.naming.directory.*;

/**
 * SMTP Validator used as validator class for annotation
 */
public class EmailValidator implements ConstraintValidator<EmailConstrains, String> {

    private static final AppLogger logger = AppLogger.getLogger(EmailValidator.class);
    @Override
    public void initialize(EmailConstrains constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return isAddressValid(email);
    }

    /**
     * This class functions as a container for an mx record &
     * it's priority, it also offers a comparison method for
     * the preference
     */
    private static class MX implements Comparable<MX>{

        private String mxRec;
        private int priority;

        MX(String mxRec){
            this.mxRec = mxRec;
            priority = Integer.parseInt(mxRec.split(" ")[0]);
        }

        public String getMx() {
            return mxRec;
        }

        public int getPriority() {
            return priority;
        }


        @Override
        public int compareTo(MX o) {
            // order is in reverse! (the lower the number the higher the priority)
            return Integer.compare(o.getPriority(), priority);
        }
    }

    /*
    Hears the response (integer) from mail exchanger
     */
    private int hear(BufferedReader in) throws IOException {
        String line ;
        int res = 0;

        while ((line = in.readLine()) != null) {
            String prefix = line.substring( 0, 3 );
            try {
                res = Integer.parseInt(prefix);
            }
            catch (Exception ex) {
                res = -1;
            }
            if (line.charAt( 3 ) != '-') break;
        }
        return res;
    }

    /*
    Typical BufferedWriter method
     */
    private void say(BufferedWriter wr, String text) throws IOException {
        wr.write( text + "\r\n" );
        wr.flush();
    }

    /**
     * Finds all the mx records associated with given hostname
     * @param hostName hostname isolated from email address
     * @return all the mx records found for given hostname
     * @throws NamingException
     */
    private List<MX> getMX(String hostName) throws NamingException {

        // Perform a DNS lookup for MX records in the domain
        Hashtable<String, String> env = new Hashtable<>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        DirContext ictx = new InitialDirContext(env);
        Attributes attrs = ictx.getAttributes(hostName, new String[] {"MX"});
        Attribute attr = attrs.get("MX");

        // if no MX record found, try the machine itself
        if ((attr == null) || (attr.size() == 0)) {
            attrs = ictx.getAttributes( hostName, new String[] {"A"});
            attr = attrs.get("A");
            System.out.println(attr);
            if( attr == null )
                throw new NamingException("No match for name : " + hostName);
        }

        // Found machines to try. Return them as an array list
        // NOTE: We SHOULD take the preference into account to be absolutely correct
        List<MX> res = new ArrayList<>();
        NamingEnumeration<?> en = attr.getAll();

        while (en.hasMore()) {
            String x = (String) en.next();
            res.add(new MX(x));
        }
        return res;
    }

    /**
     * Perform SMTP validation by checking MX records
     * @param address is the address to be tested
     * @return True if mail exchanger accepted
     * NOTE: For store and forward mail servers validation can not be made
     */

    public boolean isAddressValid(String address) {

        // Separator for the domain name
        int pos = address.indexOf('@');

        // If the address does not contain an '@', it's not valid
        if (pos == -1) return false;

        // Isolate the domain/machine name and get a list of mail exchangers
        String domain = address.substring(++pos);
        List<MX> mxList;
        try {
            mxList = getMX(domain);
        }
        catch (NamingException e) {
            return false;
        }

        // Successfully sending an email -> email may exist
        // Unable to send email -> email definitely doesn't exist
        if (mxList.size() == 0) return false;

        // Sort the list to check from low to high priority
        mxList.sort(MX::compareTo);

        /*
        Perform SMTP validation, try each mail exchanger until we get a
        positive acceptance. Store & Forward mail servers may return positive
        acceptance, so we can not be sure but checking with preference in mind increases
        our probability of being correct
         */
        boolean valid = false;

        for (var mx : mxList) {

            String[] fullMx = mx.getMx().split("\s");
            // in case that exchanger record ends with '.' remove it
            if ( fullMx[1].endsWith( "." ) )
                fullMx[1] = fullMx[1].substring( 0, (fullMx[1].length() - 1));

            // isolate the mail exchanger (no priority)
            mx.mxRec = fullMx[1];

            try (Socket skt = new Socket(mx.getMx(), 25)){
                int res;

                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(skt.getInputStream()));
                BufferedWriter writer = new BufferedWriter
                        (new OutputStreamWriter(skt.getOutputStream()));

                res = hear(reader);
                if (res != 220) throw new Exception("Invalid header");
                say(writer, "HELO ilbolan.com");

                res = hear(reader);
                if (res != 250) throw new Exception("Not ESMTP");

                // validate the sender address
                say(writer, "MAIL FROM: <pitospies@gmail.com>");
                res = hear(reader);
                if (res != 250) throw new Exception("Sender rejected");

                say(writer, "RCPT TO: <" + address + ">");
                res = hear(reader);

                // be polite
                say(writer, "RSET");
                hear(reader);
                say(writer, "QUIT");
                hear(reader);

                if (res != 250) throw new Exception("Address is not valid!");

                valid = true;
                reader.close();
                writer.close();
            } catch (Exception e) {
                // Do nothing but try next host
                e.printStackTrace();
                valid = false;
            }
        }
        logger.log(Level.INFO, "Order made with mail: " + address + " valid? " + valid);
        return valid;
    }
}
