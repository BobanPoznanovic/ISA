package isa.project.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

	public static void sendEmail(String email){
		
		// Recipient's email ID needs to be mentioned.
	      String to = email;

	      // Sender's email ID needs to be mentioned
	      String from = "isa.mail.project@gmail.com";
	      final String username = "isa.mail.project@gmail.com";//change accordingly
		  final String password = "testiranje1";//change accordingly 

	      // Assuming you are sending email from localhost
	      String host = "smtp.gmail.com";

	      // Get system properties
	      Properties properties = new Properties();
	      properties.put("mail.smtp.auth", "true");
	      properties.put("mail.smtp.starttls.enable", "true");
	      properties.put("mail.smtp.host", host);
	      properties.put("mail.smtp.port", "587");
	      Session.getInstance(properties, null);
	      // Setup mail server
	     

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("This is the Subject Line!");

	         // Now set the actual message
	         String link = "To activate your account click <a href=\"http://localhost:9000/#/confirmRegistration?email=" + to + "\">here.</a>";
	         message.setText(link, "UTF-8", "html");
//	         message.setText("<a href=\"http://localhost:9000/#/successfullRegistration\">ACTIVAR CUENTA</a>");

	         // Send message
	         Transport.send(message, username, password);
	         System.out.println("Sent message successfully....");
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
		
	}
	
}
