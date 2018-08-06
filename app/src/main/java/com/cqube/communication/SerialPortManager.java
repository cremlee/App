package com.cqube.communication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

public class SerialPortManager {
	private static final String TAG = "SerialPortManager";
	protected static final boolean DEBUG = true;
	protected SerialPort mSerialPort;
	protected OutputStream mOutputStream;
	private InputStream mInputStream;
	private ReadThread mReadThread;
	private ICommunication dataReceived;
	private static byte[] sendbyte ;
	private SerialPortManager(ICommunication iCommunication) {

		try {
			mSerialPort = getSerialPort();
			mOutputStream = mSerialPort.getOutputStream();
			mInputStream = mSerialPort.getInputStream();

			/* Create a receiving thread */
			mReadThread = new ReadThread();
			mReadThread.start();

			this.dataReceived = iCommunication;
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static SerialPortManager single = null;

	/**
	 * 获取外设管理设备
	 * 
	 * @param iCommunication
	 *            实现ICommunication接口的类
	 * @return
	 */
	public static SerialPortManager getInstance(ICommunication iCommunication) {
		if (single == null) {
			single = new SerialPortManager(iCommunication);
		}
		return single;
	}
	/**
	 * 发送指令给下位机
	 * 
	 * @param cmd
	 */
	public synchronized boolean sendCmd(String cmd) {
		if (mOutputStream == null) {
			return false;
		}
		sendbyte = string2Bytes(cmd);
		try {
			mOutputStream.write(sendbyte);
			mOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;

	}

	public static byte[] string2Bytes(String hexString) {
		String[] hexStrings = hexString.split(" ");
		byte[] bytes = new byte[hexStrings.length];
		for (int i = 0; i < hexStrings.length; i++) {
			Integer integer = Integer.valueOf(hexStrings[i], 16);
			bytes[i] = integer.byteValue();
		}
		return bytes;
	}

	/**
	 * 打开串口
	 * 
	 * @return
	 * @throws SecurityException
	 * @throws IOException
	 * @throws InvalidParameterException
	 */
	protected SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
		if (mSerialPort == null) {
			mSerialPort = new SerialPort(new File("/dev/ttyS3"), 19200, 0);
		}
		return mSerialPort;
	}

	/**
	 * 接收数据的线程
	 * 
	 * @author GilTang
	 * 
	 */
	private class ReadThread extends Thread {

		private boolean isInterrupted = false;

		@Override
		public void run() {
			super.run();
			while (!isInterrupted) {
				int size=0;
				try {
					
					if (mInputStream == null) {
						return;
					}
					while(size==0)
					{
						size = mInputStream.available();
					}
					byte[] buffer = new byte[size];
					mInputStream.read(buffer);
					if (size > 0) {
						dataReceived(buffer, size);
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}

		/**
		 * 关闭线程
		 */
		public void interrupt() {
			isInterrupted = true;
			super.interrupt();
		}
	}

	/**
	 * 处理数据
	 * 
	 * @param buffer
	 * @param size
	 */
	private void dataReceived(byte[] buffer, int size) {
		dataReceived.onDataReceived(buffer, size);
	}

}
