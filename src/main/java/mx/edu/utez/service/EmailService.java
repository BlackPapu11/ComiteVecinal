package mx.edu.utez.service;

public interface EmailService {
	boolean sendEmail(String emailTo, String emailSubject, String emailContent);

}
