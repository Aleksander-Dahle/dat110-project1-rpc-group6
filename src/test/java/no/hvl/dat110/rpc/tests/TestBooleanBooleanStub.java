package no.hvl.dat110.rpc.tests;

import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCLocalStub;
import no.hvl.dat110.rpc.RPCUtils;

public class TestBooleanBooleanStub extends RPCLocalStub {
	
	public TestBooleanBooleanStub(RPCClient rpcclient) {
		super(rpcclient);
	}
	
	public boolean m(boolean b) {
		
		byte[] request = RPCUtils.marshallBoolean(b);
		
		byte[] reply = rpcclient.call((byte)4,request);
		
		boolean bres = RPCUtils.unmarshallBoolean(reply);
		
		return bres;
	}
	
}
