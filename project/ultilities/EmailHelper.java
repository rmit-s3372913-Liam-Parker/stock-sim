package ultilities;

import java.io.IOException;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;

/**
 * EmailHelper contains functions related to the sending of emails.
 */
public class EmailHelper 
{
	/**
	 * Sends an email containing registration confirmation data.
	 * 
	 * Reference: 
	 * 
	 * 			Sendgrid integration tutorial 
	 * 			https://app.sendgrid.com/guide/integrate/langs/java
	 * 
	 * @param email The email for the pin to be sent to.
	 * @param pin The pin to send.
	 */
	public static void sendAuthenticationEmail(final String email, final String pin)
	{
		new Thread(() -> {})
		{
			public void run()
			{
				String subject = "ASX Simulator Account Verification";
				Email from = new Email("asxsimulator@gmail.com");
				Email to = new Email(email);
				Content content = new Content("text/plain", "Please enter the following pin into your currently active application to verify. \n\n "
						+ pin + "\n\n"
						+ " If you fail to enter this pin before you close your application a new pin will need to be generated."
						+ " Best enter your pin now before you forget!");

				Mail mail = new Mail(from, subject, to, content);

				SendGrid sg = new SendGrid(System.getenv("SENDGRID_API"));
				Request request = new Request();
				try
				{
					request.setMethod(Method.POST);
					request.setEndpoint("mail/send");
					request.setBody(mail.build());
					sg.api(request);
				}
				catch (IOException ex) { ex.printStackTrace(); }
			}
		}.start();
	}
}