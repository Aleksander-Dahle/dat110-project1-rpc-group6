package no.hvl.dat110.messaging;

import java.util.Arrays;

public class MessageUtils {

    public static final int SEGMENTSIZE = 128;

    public static int MESSAGINGPORT = 8080;
    public static String MESSAGINGHOST = "localhost";

    public static byte[] encapsulate(Message message) {

        byte[] data = message.getData();
        int len = data.length;

        byte[] segment = new byte[SEGMENTSIZE];

        // header: payload length
        segment[0] = (byte) len;

        // payload
        System.arraycopy(data, 0, segment, 1, len);

        // remaining bytes are padding (0 by default)
        return segment;
    }

    public static Message decapsulate(byte[] segment) {

        // read length as unsigned
        int len = segment[0] & 0xFF;

        byte[] data = Arrays.copyOfRange(segment, 1, 1 + len);

        return new Message(data);
    }
}
