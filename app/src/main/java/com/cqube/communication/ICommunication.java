package com.cqube.communication;


public interface ICommunication {
	
	/**
	 * 接收数据
	 * @param buffer
	 * @param size
	 */
	public void onDataReceived(byte[] buffer, int size);
}
