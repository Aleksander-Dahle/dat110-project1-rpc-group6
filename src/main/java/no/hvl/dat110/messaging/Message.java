package no.hvl.dat110.messaging;


public class Message {

	// the up to 127 bytes of data (payload) that a message can hold
	private byte[] data;

	// construction a Message with the data provided
	public Message(byte[] data) {
		
		if (data == null) {
			throw new IllegalArgumentException("data cannot be null");
		}

		if (data.length > 127) {
			throw new IllegalArgumentException("data too long");
		}

		this.data = data;
	}

	public byte[] getData() {
		return this.data; 
	}

}
