package no.hvl.dat110.messaging.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

// import org.junit.Test;
import org.junit.jupiter.api.Test;

import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessageUtils;

class TestMessage {

	private byte[] createData (int size) {
	
		byte[] data = new byte[size];
		
		for (int i = 0; i<data.length;i++) {
			data[i] = (byte)i;
		}
		
		return data;
	}
	
	@Test
	void testEncapsulate() {
		
		int size = 56;
		byte[] data = createData(size);
		
		Message message = new Message(data);
		
		byte[] encoded = MessageUtils.encapsulate(message);
		
		assertEquals(size,encoded[0]);
		
		assertEquals(MessageUtils.SEGMENTSIZE,encoded.length);
		
		for (int i = 0; i<data.length;i++) {
			assertEquals(data[i],encoded[i+1]);
		}
	}
		
	@Test
	void testDecapsulate() {
		
		byte[] encoded = new byte[MessageUtils.SEGMENTSIZE];
		
		encoded[0] = 5;
		encoded[1] = 1;
		encoded[2] = 2;
		encoded[3] = 3;
		encoded[4] = 4;
		encoded[5] = 5;
		
		Message message = MessageUtils.decapsulate(encoded);
		
		byte[] data = message.getData();
		
		assertEquals(5,data.length);
		
		for (int i = 0;i<5;i++) {
			assertEquals(encoded[i+1],data[i]);
		}
	}

	@Test
	void EncapsulateDecapsulate () {
	
		for (int size = 0;size <= MessageUtils.SEGMENTSIZE-1;size++) {
			
			byte[] data = createData(size);
			
			Message message1 = new Message(data);
			
			byte[] encoded = MessageUtils.encapsulate(message1);
			
			Message message2 = MessageUtils.decapsulate(encoded);
			
			byte[] decoded = message2.getData();
			
			assertTrue(Arrays.equals(data, decoded));
		}
		
	}	
}