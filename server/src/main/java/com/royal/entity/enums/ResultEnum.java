package com.royal.entity.enums;

public enum ResultEnum {

	SUCCEED("0","成功"),
	PHONE_FORMAT_ERROR("1","手机号码格式错误"),
	VERIFICATION_CODE_ERROR("2","验证码错误"),
	VERIFICATION_CODE_INVALID("3","验证码失效"),
	ACCOUNT_OR_PASSWORD_ERROR("4","账号或密码错误"),
	SMS_SEND_ERROR("5","短信发送失败"),
	SYMBOL_NOT_BUY("6","该产品目前不支持售卖"),
	BUY_INFO_ERROR("7","买入信息不完整"),
	AMOUNT_INSUFFICIENT_BALANCE("8","账户余额不足"),
	TRANSACTION_STASUA_ERROR("9","交易状态编码错误"),
	TRANSACTION_INFO_ERROR("10","交易信息错误"),
	TRANSACTION_TIME_ERROR("11","交易时间错误"),
	ACCOUNT_EXIST("12","账号已存在"),
	REPETITIVE_OPERATION_ERROR("13","请勿重复操作"),
	PROFIT_COUNT_ERROR("14","波动点位不合法：止盈应大于交易金额的百分之十，小于百分之二百，或为0；止损应大于交易金额的百分之十，小于百分之八十，或为0"),
	UPLOAD_ERROR("15","上传失败"),
	FILE_ERROR("16","文件不可为空"),
	RESERVE_COUNT_MAX("17","挂单数量已达上限"),
	PAY_SIGN_ERROR("18","支付签名错误"),
	PAY_ERROR("19","下单失败"),
	DONT_SUBMIT("20","未进行实名认证"),
	NO_AUDIT("21","认证审核中"),
	REJECTED("22","认证审核不通过"),
	NOT_BANK_CARD("23","未绑定银行卡"),
	MIN_MONEY_ERROR("24","提现最低10美元起"),
	NOT_REGISTER("25","请先完成注册"),
	UPDATE_ERROR("26","修改失败"),
	UNKNOWN_INFO("27","未知的信息"),
	ACCOUNT_NONENTIY("28","账户信息未找到"),
	NOT_FOUND_CASH_COUPON("29","未找到相应优惠券"),
	CASH_COUPON_MONEY_ERROR("30","优惠券金额错误"),
	PRICE_GET_ERROR("31","服务端金额获取错误，请联系客服"),
	PARAMETER_ERROR("-1","参数不完整"),
	SERVER_ERROR("-2","服务器错误"),
	ANEW_LOGIN("-9","请重新登录"),
	REFRESH_JWT("-111","请刷新JWT"),
	ILLEGALITY("-999","非法访问");

	private ResultEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	private String key;

	private String value;

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}


}
