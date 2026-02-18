package no.hvl.dat110.rpc.tests;

import no.hvl.dat110.rpc.RPCRemoteImpl;
import no.hvl.dat110.rpc.RPCUtils;
import no.hvl.dat110.rpc.RPCServer;

public class TestStringStringImpl extends RPCRemoteImpl {

	public TestStringStringImpl(byte rpcid, RPCServer rpcserver) {
		super(rpcid, rpcserver);
	}
	
	public byte[] invoke(byte[] request) {
	
		String str = RPCUtils.unmarshallString(request);
		
		String resstr = m(str);
		
		byte[] reply = RPCUtils.marshallString(resstr);
		
		return reply;
	}
	
	public String m(String str) {
		System.out.println("String m("+str+") executed");
		return str+str;
	}
}
