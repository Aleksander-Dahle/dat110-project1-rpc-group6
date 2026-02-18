package no.hvl.dat110.rpc;

// RPC client-side (local) stubs must extend this class

public abstract class RPCLocalStub {

	protected RPCClient rpcclient;
	
	public RPCLocalStub(RPCClient rpcclient) {
		this.rpcclient = rpcclient;
	}
	
}
