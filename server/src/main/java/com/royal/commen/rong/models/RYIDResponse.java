package com.royal.commen.rong.models;

/**
 * 融云获取通讯ID请求对象
 * 
 * @author Royal
 * 
 */
public class RYIDResponse {
	/**
	 * userId 用户唯一标识，如TS_ID
	 */
	private String userId;
	/**
	 * userName 用户名
	 */
	private String userName;
	/**
	 * 头像URL
	 */
	private String img;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * * @param userId 用户唯一标识，如TS_ID
	 * 
	 * @param userName
	 *            用户名
	 * @param img
	 *            用户头像URL
	 */
	public RYIDResponse(String userId, String userName, String img) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.img = img;
	}

	public RYIDResponse() {
		super();
	}

	@Override
	public String toString() {
		return "RYIDResponse [userId=" + userId + ", userName=" + userName + ", img=" + img + "]";
	}

}
