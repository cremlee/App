package android.luna.rs232;


public interface ICommunication {
	
	/**
	 * 接收数据
	 * @param buffer
	 * @param size
	 */
	 void onDataReceived(byte[] buffer, int size);
}
