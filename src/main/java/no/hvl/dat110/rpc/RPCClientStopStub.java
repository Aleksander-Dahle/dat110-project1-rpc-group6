package no.hvl.dat110.rpc;

public class RPCClientStopStub extends RPCLocalStub {

	public RPCClientStopStub(RPCClient rpcclient) {
		super(rpcclient);
	}
	
	// client-side implementation of the built-in server stop RPC method
	public void stop () {
		
		byte[] request = RPCUtils.marshallVoid();
		
		byte[] response = rpcclient.call(RPCCommon.RPIDSTOP,request);
		
		RPCUtils.unmarshallVoid(response);
	
	}
}
