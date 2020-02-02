package com.experian.serasa.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FilaProducer {

	public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection con = factory.createConnection();
		con.start();
		Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("financeiroPropQueue");
		
		MessageProducer producer = session.createProducer(fila);
		
		for (int i = 0; i < 1000; i++) {
//			Message message = session.createTextMessage("<pedido><id>" + (i+1) + "</id></pedido>");
			Message message = session.createObjectMessage(new String("Minha string " + i));
			producer.send(message);
		}
		
		
		//new Scanner(System.in).nextLine();

		session.close();
		con.close();
		context.close();
	}
	
}
