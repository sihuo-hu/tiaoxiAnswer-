package com.royal.commen.rong.history.txt;


public class AndTxtMessageJson {
	private String appId;
	/**
	 * 发送者ID
	 */
	private String fromUserId;
	/**
	 * 接收者ID
	 */
	private String targetId;
	/**
	 * 会话类型，二人会话是 1 、讨论组会话是 2 、群组会话是 3 、聊天室会话是 4 、客服会话是 5 、 系统通知是 6 、应用公众服务是 7
	 * 、公众服务是 8。targetType 在 SDK 中为 ConversationType。
	 */
	private Integer targetType;
	private String GroupId;
	private String classname;
	private AndTxtMsg content;
	private String dateTime;
	private String msgUID;
	private String source;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	public String getGroupId() {
		return GroupId;
	}

	public void setGroupId(String groupId) {
		GroupId = groupId;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public AndTxtMsg getContent() {
		return content;
	}

	public void setContent(AndTxtMsg content) {
		this.content = content;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getMsgUID() {
		return msgUID;
	}

	public void setMsgUID(String msgUID) {
		this.msgUID = msgUID;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
