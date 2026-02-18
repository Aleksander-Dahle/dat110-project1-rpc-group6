package no.hvl.dat110.system.display;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.RPCServer;
import no.hvl.dat110.system.controller.Common;


public class DisplayDevice {
	
	public static void main(String[] args) {
		
		System.out.println("Display server starting ...");
		
		RPCServer server = new RPCServer(Common.DISPLAYPORT);
		new DisplayImpl((byte) Common.WRITE_RPCID, server);
		server.run();
		
		System.out.println("Display server stopping ...");
		
	}
}
