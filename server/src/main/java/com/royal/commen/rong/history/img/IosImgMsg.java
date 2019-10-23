package com.royal.commen.rong.history.img;


import com.royal.commen.rong.history.IosUser;

public class IosImgMsg {
	private boolean isFull;
	
	private String imageUri;
	
	private String content;
	
	private IosUser user;

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public IosUser getUser() {
		return user;
	}

	public void setUser(IosUser user) {
		this.user = user;
	}
	
	
}
