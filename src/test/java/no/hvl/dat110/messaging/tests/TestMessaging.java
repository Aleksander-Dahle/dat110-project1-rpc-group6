package no.hvl.dat110.messaging.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessageUtils;
import no.hvl.dat110.messaging.MessagingClient;
import no.hvl.dat110.messaging.MessagingServer;
import java.util.concurrent.atomic.AtomicBoolean;

public class TestMessaging {

	@Test
	public void test() {

		byte[] clientsent = { 1, 2, 3, 4, 5 };

		AtomicBoolean failure = new AtomicBoolean(false);

		Thread server = new Thread() {

			public void run() {

				MessagingServer server = null;

				try {

					System.out.println("Messaging server - start");

					server = new MessagingServer(MessageUtils.MESSAGINGPORT);

					MessageConnection connection = server.accept();

					Message request = connection.receive();

					byte[] serverreceived = request.getData();

					Message reply = new Message(serverreceived);

					connection.send(reply);

					connection.close();

					assertTrue(Arrays.equals(clientsent, serverreceived));

				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				} finally {
					server.stop();

					System.out.println("Messaging server - stop");
				}

			}
		};

		Thread client = new Thread() {

			public void run() {

				try {

					System.out.println("Messaging client - start");

					MessagingClient client = new MessagingClient(MessageUtils.MESSAGINGHOST,
							MessageUtils.MESSAGINGPORT);

					MessageConnection connection = client.connect();

					Message message1 = new Message(clientsent);

					connection.send(message1);

					Message message2 = connection.receive();

					byte[] clientreceived = message2.getData();

					connection.close();

					System.out.println("Messaging client - stop");

					assertTrue(Arrays.equals(clientsent, clientreceived));
				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				}
			}

		};

		try {
			server.start();
			client.start();

			server.join();
			client.join();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			if (failure.get()) {
				fail();
			}
		}

	}
}
