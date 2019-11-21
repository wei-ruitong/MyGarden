package com.hcnetsdk.jna;

import com.sun.jna.Native;

public enum HCNetSDKJNAInstance 
{	
	CLASS;
	private static com.hcnetsdk.jna.HCNetSDKByJNA netSdk = null;


	/**
	 * get the instance of HCNetSDK
	 * @return the instance of HCNetSDK
	 */
	public static com.hcnetsdk.jna.HCNetSDKByJNA getInstance()
	{
		if (null == netSdk)
		{
			synchronized (com.hcnetsdk.jna.HCNetSDKByJNA.class)
			{
				netSdk = (com.hcnetsdk.jna.HCNetSDKByJNA) Native.loadLibrary("hcnetsdk",
						com.hcnetsdk.jna.HCNetSDKByJNA.class);
			}			
		}
		return netSdk;
	}
}