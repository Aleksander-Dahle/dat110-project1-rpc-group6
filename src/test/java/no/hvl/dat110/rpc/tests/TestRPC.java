package no.hvl.dat110.rpc.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCServer;
import no.hvl.dat110.rpc.RPCClientStopStub;

import java.util.concurrent.atomic.AtomicBoolean;

@TestMethodOrder(OrderAnnotation.class)
public class TestRPC {

	private static int PORT = 8080;
	private static String SERVER = "localhost";

	@Test
	@Order(1)
	public void testStartStop() {

		AtomicBoolean failure = new AtomicBoolean(false);

		RPCClient client = new RPCClient(SERVER, PORT);
		RPCServer server = new RPCServer(PORT);

		Thread serverthread = new Thread() {

			public void run() {

				try {
					server.run();
				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);

				} finally {
					server.stop();
				}
			}
		};

		Thread clientthread = new Thread() {

			public void run() {

				try {
					client.connect();

					RPCClientStopStub stub = new RPCClientStopStub(client);

					stub.stop();

					client.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				}
			}
		};

		System.out.println("System starting ... ");

		try {

			serverthread.start();
			clientthread.start();

			serverthread.join();
			clientthread.join();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {

			System.out.println("System stopping ... ");
			
			if (failure.get()) {
				fail();
			}
		}

	}

	@Test
	@Order(2)
	public void testVoidCall() {

		RPCClient client = new RPCClient(SERVER, PORT);
		RPCServer server = new RPCServer(PORT);

		AtomicBoolean failure = new AtomicBoolean(false);
		
		Thread serverthread = new Thread() {

			public void run() {

				try {
					TestVoidVoidImpl voidvoidimpl = new TestVoidVoidImpl((byte) 1, server);

					server.run();
				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				} finally {
					server.stop();
				}
			}
		};

		Thread clientthread = new Thread() {

			public void run() {

				try {
					client.connect();

					RPCClientStopStub stopstub = new RPCClientStopStub(client);
					TestVoidVoidStub voidvoidstub = new TestVoidVoidStub(client);

					// void test case
					voidvoidstub.m();

					assertTrue(true); // just check that we complete call
					stopstub.stop();

					client.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				}

			}
		};

		System.out.println("System starting ... ");

		try {

			serverthread.start();
			clientthread.start();

			serverthread.join();
			clientthread.join();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			
			System.out.println("System stopping ... ");

			if (failure.get()) {
				fail();
			}
		}
	}

	@Test
	@Order(3)
	public void testStringCall() {

		RPCClient client = new RPCClient(SERVER, PORT);
		RPCServer server = new RPCServer(PORT);

		AtomicBoolean failure = new AtomicBoolean(false);
		
		Thread serverthread = new Thread() {

			public void run() {

				try {

					TestStringStringImpl stringstringimpl = new TestStringStringImpl((byte) 2, server);

					server.run();
				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				} finally {
					server.stop();
				}
			}
		};

		Thread clientthread = new Thread() {

			public void run() {

				try {
					client.connect();

					RPCClientStopStub stopstub = new RPCClientStopStub(client);
					TestStringStringStub stringstringstub = new TestStringStringStub(client);

					// string test case
					String teststr = "string";
					String resstr = stringstringstub.m(teststr);

					assertEquals(teststr + teststr, resstr);

					stopstub.stop();

					client.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				}

			}
		};

		System.out.println("System starting ... ");

		try {

			serverthread.start();
			clientthread.start();

			serverthread.join();
			clientthread.join();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {

			System.out.println("System stopping ... ");
			
			if (failure.get()) {
				fail();
			}
		}
	}

	@Test
	@Order(4)
	public void testIntCall() {

		RPCClient client = new RPCClient(SERVER, PORT);
		RPCServer server = new RPCServer(PORT);

		AtomicBoolean failure = new AtomicBoolean(false);
		
		Thread serverthread = new Thread() {

			public void run() {

				try {
					TestIntIntImpl intintimpl = new TestIntIntImpl((byte) 3, server);

					server.run();
				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				} finally {
					server.stop();
				}
			}
		};

		Thread clientthread = new Thread() {

			public void run() {

				try {
					client.connect();

					RPCClientStopStub stopstub = new RPCClientStopStub(client);
					TestIntIntStub intintstub = new TestIntIntStub(client);

					// int test case
					int x = 42;
					int resx = intintstub.m(x);

					assertEquals(x, resx);

					stopstub.stop();

					client.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				}

			}
		};

		System.out.println("System starting ... ");

		try {

			serverthread.start();
			clientthread.start();

			serverthread.join();
			clientthread.join();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {

			System.out.println("System stopping ... ");
			
			if (failure.get()) {
				fail();
			}
		}
	}

	@Test
	@Order(5)
	public void testBoolCall() {

		RPCClient client = new RPCClient(SERVER, PORT);
		RPCServer server = new RPCServer(PORT);

		AtomicBoolean failure = new AtomicBoolean(false);
		
		Thread serverthread = new Thread() {

			public void run() {

				try {

					TestBooleanBooleanImpl boolboolimpl = new TestBooleanBooleanImpl((byte) 4, server);

					server.run();

				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				} finally {
					server.stop();
				}
			}
		};

		Thread clientthread = new Thread() {

			public void run() {

				RPCClientStopStub stopstub = null;

				try {
					client.connect();

					stopstub = new RPCClientStopStub(client);
					TestBooleanBooleanStub boolboolstub = new TestBooleanBooleanStub(client);

					// boolean test case

					boolean testb = true;
					boolean resb = boolboolstub.m(testb);

					assertEquals(!testb, resb);

					testb = false;
					resb = boolboolstub.m(testb);
					assertEquals(!testb, resb);

					stopstub.stop();

					client.disconnect();

				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				}
			}
		};

		System.out.println("System starting ... ");

		try {

			serverthread.start();
			clientthread.start();

			serverthread.join();
			clientthread.join();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {

			System.out.println("System stopping ... ");
			
			if (failure.get()) {
				fail();
			}
		}
	}

	@Test
	@Order(6)
	public void testAllCalls() {

		RPCClient client = new RPCClient(SERVER, PORT);
		RPCServer server = new RPCServer(PORT);

		AtomicBoolean failure = new AtomicBoolean(false);
		
		Thread serverthread = new Thread() {

			public void run() {

				try {
					TestVoidVoidImpl voidvoidimpl = new TestVoidVoidImpl((byte) 1, server);
					TestStringStringImpl stringstringimpl = new TestStringStringImpl((byte) 2, server);
					TestIntIntImpl intintimpl = new TestIntIntImpl((byte) 3, server);
					TestBooleanBooleanImpl boolboolimpl = new TestBooleanBooleanImpl((byte) 4, server);

					server.run();
				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				} finally {
					server.stop();
				}
			}
		};

		Thread clientthread = new Thread() {

			public void run() {

				try {
					client.connect();

					RPCClientStopStub stopstub = new RPCClientStopStub(client);
					TestVoidVoidStub voidvoidstub = new TestVoidVoidStub(client);
					TestStringStringStub stringstringstub = new TestStringStringStub(client);
					TestIntIntStub intintstub = new TestIntIntStub(client);
					TestBooleanBooleanStub boolboolstub = new TestBooleanBooleanStub(client);

					// void test case
					voidvoidstub.m();

					// string test case
					String teststr = "string";
					String resstr = stringstringstub.m(teststr);

					assertEquals(teststr + teststr, resstr);

					// int test case
					int x = 42;
					int resx = intintstub.m(x);

					assertEquals(x, resx);
					// boolean test case

					boolean testb = true;
					boolean resb = boolboolstub.m(testb);

					assertEquals(!testb, resb);

					testb = false;
					resb = boolboolstub.m(testb);
					assertEquals(!testb, resb);

					stopstub.stop();

					client.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				}

			}
		};

		System.out.println("System starting ... ");

		try {

			serverthread.start();
			clientthread.start();

			serverthread.join();
			clientthread.join();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {

			System.out.println("System stopping ... ");
			
			if (failure.get()) {
				fail();
			}
		}
	}
}
