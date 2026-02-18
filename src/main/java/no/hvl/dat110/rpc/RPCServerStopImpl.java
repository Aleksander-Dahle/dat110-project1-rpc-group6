package no.hvl.dat110.rpc;

public class RPCServerStopImpl extends RPCRemoteImpl {

	public RPCServerStopImpl(byte rpcid, RPCServer rpcserver) {
		super(rpcid,rpcserver);
	}
	
	// RPC server-side implementation of the built-in stop RPC method
	// params - marshalled parameter for the method
	// return value - marshalled return value
	public byte[] invoke(byte[] param) {
		
		RPCUtils.unmarshallVoid(param);
		
		byte[] returnval = RPCUtils.marshallVoid();
		
		stop(); 
		
		return returnval;
	}
	
	public void stop() {
		
		System.out.println("RPC server executing stop");
		
	}
}
