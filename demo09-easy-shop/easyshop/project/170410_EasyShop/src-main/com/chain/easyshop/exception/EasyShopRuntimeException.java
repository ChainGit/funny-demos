package com.chain.easyshop.exception;

public class EasyShopRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 6813289120498778829L;

	public EasyShopRuntimeException(Exception e) {
		super(e);
	}

	public EasyShopRuntimeException(String e) {
		super(e);
	}

	public EasyShopRuntimeException() {
		super();
	}

}
