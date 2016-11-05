package com.yifu.platform.single.net;

import com.yifu.platform.single.net.response.BaseResult;

public interface INetListener {
	
	enum DownLoadStatus {
		EDlsInit,
		EDlsDownLoading,
		EDlsDownLoadComplete,
		EDlsDownLoadErr,	
	};
	
	/**  
	 * 
	 * @param requestType Specify the request type, such like 8 stands for register,
	 * 10 stands for get user info. 
	 * 
	 * @param responseData The response data with local object, need convert to specify 
	 * 	Class instance according to requestType
	 * @param resuestId The request id.
	 * @throws YFParameterInvalidException 
	 */
	public abstract void onNetResponse(int requestTag, BaseResult responseData, int requestId);
	
	public abstract void onDownLoadStatus(DownLoadStatus status, int requestId);
	
	public abstract void onDownLoadProgressCurSize(long curSize, long totalSize, int requestId);
	
	/** 
	 * @param requestTag
	 * @param requestId  
	 * @param errorCode Refer	NetError
	 * @param msg
	 */
	public abstract void onNetResponseErr(int requestTag, int requestId, int errorCode, String msg);
	
}
