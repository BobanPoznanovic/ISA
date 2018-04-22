package isa.project.utils;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import isa.project.domain.Reservation;
import isa.project.domain.User;

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

	         // Send message
	         Transport.send(message, username, password);
	         System.out.println("Sent message successfully....");
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
		
	}
	
	public static void confirmReservation(String email, Reservation res, User user){
		
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
	         message.setSubject("Confirm reservation");

	         // Now set the actual message
	         String link = "You have been invited by your friend "+user.getName()+" "+user.getSurname()+" to watch "+ res.getTerm().getProjection().getName()+
	         "<p>Reservation details:</p><p>Time: "+res.getTerm().getTermDate()+" "+res.getTerm().getTermTime()+"</p><p>Row: "+res.getRow()+
	         "</p><p>Column: "+res.getColumn()+"</p><p>Hall: "+res.getTerm().getHall().getName()+"</p><p>Cinema/Theater: "+
	         res.getTerm().getHall().getCinemaTheater().getName()+ "</p><p>Price: " + res.getPrice() + "</p><br><p>To confirm your reservation click <a href=\"http://localhost:9000/#/confirmReservation?id=" + res.getId() 
	         + "\">here.</a></p><br/><p>To cancel your reservation click <a href=\"http://localhost:9000/#/cancel?id=" + res.getId() 
	         + "\">here.</a></p>";
	         message.setText(link, "UTF-8", "html");
//	         message.setText("<a href=\"http://localhost:9000/#/successfullRegistration\">ACTIVAR CUENTA</a>");

	         // Send message
	         Transport.send(message, username, password);
	         System.out.println("Sent message successfully....");
	         
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
		
	}
	
public static void reservationDetails(String email, Reservation res, ArrayList<User> friends){
		
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
	         message.setSubject("Reservation details");

	         // Now set the actual message
	         String link = "This is the information about your latest reservation:<p>Projection: "+ res.getTerm().getProjection().getName()+
	         "</p><p>Time: "+res.getTerm().getTermDate()+" "+res.getTerm().getTermTime()+"</p><p>Row: "+res.getRow()+
	         "</p><p>Column: "+res.getColumn()+"</p><p>Hall: "+res.getTerm().getHall().getName()+"</p><p>Cinema/Theater: "+
	         res.getTerm().getHall().getCinemaTheater().getName()+"</p><p>Price: " + res.getPrice() + "</p>";
	         
	         if(friends.size() > 0) 
	         {
	        	 link+="<p>Friends you invited: ";
	        	 
	        	 for (int i = 0; i < friends.size(); i++) 
	        	 {
					link += friends.get(i).getName() + " " + friends.get(i).getSurname() + ", ";
	        	 }
	        	 
	        	 link = link.substring(0, link.length()-2);
	        	 link += "</p>";
	         }
	         message.setText(link, "UTF-8", "html");

	         // Send message
	         Transport.send(message, username, password);
	         System.out.println("Sent message successfully....");
	         
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
		
	}
	
public static void sendAcceptEmail(String email){
		
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
	         String link = "Your offer is accepted";
	         message.setText(link, "UTF-8", "html");
//	         message.setText("<a href=\"http://localhost:9000/#/successfullRegistration\">ACTIVAR CUENTA</a>");

	         // Send message
	         Transport.send(message, username, password);
	         System.out.println("Sent message successfully....");
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
		
	}

public static void sendDeclineEmail(String email){
	
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
         String link = "Your offer is decline";
         message.setText(link, "UTF-8", "html");
//         message.setText("<a href=\"http://localhost:9000/#/successfullRegistration\">ACTIVAR CUENTA</a>");

         // Send message
         Transport.send(message, username, password);
         System.out.println("Sent message successfully....");
      } catch (MessagingException mex) {
         mex.printStackTrace();
      }
	
}
	
}
