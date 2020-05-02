package com.vpsy._2f.utility;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * @author punith
 * @date 2020-04-25
 * @description
 */
public class SendOTP {
    private static Session mailSession = null;

    private synchronized static Session buildSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "25");

//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "465");
//        props.put("mail.smtp.auth", true);
//        props.put("mail.smtp.ssl.enable", true);

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("pvdreams20@gmail.com", "PVdreams@1989");
            }
        };
        return Session.getInstance(props, auth);
    }

    public static Session getSession() {
        if(mailSession == null) {
            mailSession = buildSession();
        }
        return mailSession;
    }

    public static String generateOTP(String username) {
        String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*?";


        String finalString = alphabets + numbers + symbols;
        int lengthOfFinalString = finalString.length();

        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            otp.append(finalString.charAt(random.nextInt(lengthOfFinalString)));
        }

        sendEmail(getSession(), username, "New Password: ", otp.toString());
        return otp.toString();
    }

    private static void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("pvdreams20@gmail.com"));

            msg.setReplyTo(InternetAddress.parse("pvdreams20@gmail.com", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        generateOTP("punithj.mys@gmail.com");
    }
}
