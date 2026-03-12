package no.hvl.dat110.rpc;

import java.util.HashMap;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;

public class RPCServer {

	private MessagingServer msgserver;
	private MessageConnection connection;
	
	// hashmap to register RPC methods which are required to extend RPCRemoteImpl
	// the key in the hashmap is the RPC identifier of the method
	private HashMap<Byte,RPCRemoteImpl> services;
	
	public RPCServer(int port) {
		
		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Byte,RPCRemoteImpl>();
		
	}
	
	public void run() {

		// the stop RPC method is built into the server
		RPCRemoteImpl rpcstop = new RPCServerStopImpl(RPCCommon.RPIDSTOP,this);
		register(RPCCommon.RPIDSTOP, rpcstop);

		System.out.println("RPC SERVER RUN - Services: " + services.size());

		connection = msgserver.accept();

		System.out.println("RPC SERVER ACCEPTED");

		boolean stop = false;

		while (!stop) {
			   byte rpcid = 0;
			   Message requestmsg, replymsg;
			   
			   requestmsg = connection.receive();
			   
			   byte[] rpcmsg = requestmsg.getData();
			   
			   rpcid = rpcmsg[0];
			   
			   byte[] param = RPCUtils.decapsulate(rpcmsg);
			   
			   RPCRemoteImpl service = services.get(rpcid);
			   
			   byte[] returnval;
			   
			   if (service == null) {
			       System.out.println("Unknown RPC id: " + rpcid);
			       returnval = new byte[0];
			   } else {
			       returnval = service.invoke(param);
			   }
			   
			   byte[] replyrpcmsg = RPCUtils.encapsulate(rpcid, returnval);
			   
			   replymsg = new Message(replyrpcmsg);
			   
			   connection.send(replymsg);
			   if (rpcid == RPCCommon.RPIDSTOP) {
				   stop = true;
			   }
			}

	}
	
	// used by server side method implementations to register themselves in the RPC server
	public void register(byte rpcid, RPCRemoteImpl impl) {
		services.put(rpcid, impl);
	}
	
	public void stop() {

		if (connection != null) {
			connection.close();
		} else {
			System.out.println("RPCServer.stop - connection was null");
		}
		
		if (msgserver != null) {
			msgserver.stop();
		} else {
			System.out.println("RPCServer.stop - msgserver was null");
		}
		
	}
}
