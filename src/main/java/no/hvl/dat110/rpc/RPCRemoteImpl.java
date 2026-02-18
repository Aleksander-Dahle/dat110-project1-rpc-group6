package no.hvl.dat110.rpc;

// RPC server-side method implementations must extend this class

public abstract class RPCRemoteImpl {
	
	public RPCRemoteImpl(byte rpcid, RPCServer rpcserver) {
		rpcserver.register(rpcid, this);
	}

	// method that will be invoked by the server
	// params
	public abstract byte[] invoke(byte[] params);
	
}
