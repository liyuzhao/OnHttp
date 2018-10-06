
package com.hacknife.onhttp.imageloader.core.assist;

public class FailReason {

	private final FailType type;

	private final Throwable cause;

	public FailReason(FailType type, Throwable cause) {
		this.type = type;
		this.cause = cause;
	}

 	public FailType getType() {
		return type;
	}

	
	public Throwable getCause() {
		return cause;
	}

	
	public static enum FailType {
		
		IO_ERROR,
		
		DECODING_ERROR,

		NETWORK_DENIED,
		
		OUT_OF_MEMORY,
		
		UNKNOWN
	}
}