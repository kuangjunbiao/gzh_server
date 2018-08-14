package com.gaoan.forever.constant;

import com.gaoan.forever.utils.LanguageUtil;


public enum LanguageConstant {
	
	aurora_recharge_remark("aurora_recharge_remark", "极光充值", "Aurora recharge"),
	ore_recharge_remark("ore_recharge_remark", "ore矿机充值", "Ore recharge"),
	COIN_WITHDRAWAL_REMARK("COIN_WITHDRAWAL_REMARK", "%s出金" ,"%s withdrawal"),
	COIN_WITHDRAWAL_FREEZE_REMARK("COIN_WITHDRAWAL_FREEZE_REMARK", "%s出金冻结出金金额" ,"%s Freeze the amount of money!"),
	COIN_WITHDRAWAL_AMOUNT_FREEZE_REMARK("COIN_WITHDRAWAL_AMOUNT_FREEZE_REMARK", "%s出金-金额冻结" ,"%s withdrawal remark!"),
	COIN_WITHDRAWAL_POUNDAGE_FREEZE("COIN_WITHDRAWAL_POUNDAGE_FREEZE", "%s出金冻结手续费金额" ,"%s withdrawal_poundage_freeze_remark !"),
	COIN_WITHDRAWAL_POUNDAGE_FREEZE_REMARK("COIN_WITHDRAWAL_POUNDAGE_FREEZE_REMARK", "%s出金-手续费金额冻结" ,"%s withdrawal_poundage_freeze_remark !"),
	force_dkc_withdrawal_notify_success_thaw_poundage("force_dkc_withdrawal_notify_success_thaw_poundage", "DKC出金原力-原力回调状态：success - 解冻手续费" ,"force_dkc_withdrawal_notify_success_thaw_poundage !"),
	force_dkc_withdrawal_notify_success_thaw_amount("force_dkc_withdrawal_notify_success_thaw_amount", "DKC出金原力-原力回调状态：success - 解冻金额" ,"force_dkc_withdrawal_notify_success_thaw_amount !"),
	force_dkc_withdrawal_notify_fail_thaw_poundage("force_dkc_withdrawal_notify_fail_thaw_poundage", "DKC出金原力-原力回调状态：error - 解冻手续费" ,"force_dkc_withdrawal_notify_fail_thaw_poundage !"),
	force_dkc_withdrawal_notify_fail_thaw_amount("force_dkc_withdrawal_notify_fail_thaw_amount", "DKC出金原力-原力回调状态：error - 解冻金额" ,"force_dkc_withdrawal_notify_fail_thaw_amount !"),
	force_dkc_withdrawal_notify_addressnotexist_thaw_poundage("force_dkc_withdrawal_notify_addressnotexist_thaw_poundage","DKC出金原力地址不存在退款|手续费","The address does not exist unfreeze fee!"),
	force_dkc_withdrawal_notify_addressnotexist_thaw_amount("force_dkc_withdrawal_notify_addressnotexist_thaw_amount","DKC出金原力地址不存在退款|出金金额","The address does not exist unfreeze amount!"),
	
	force_dkc_withdrawal_notify_rollback_thaw_poundage("force_dkc_withdrawal_notify_rollback_thaw_poundage","DKC出金原力撤单退款|手续费","force_dkc_withdrawal_notify_rollback_thaw_poundage"),
	force_dkc_withdrawal_notify_rollback_thaw_amount("force_dkc_withdrawal_notify_rollback_thaw_amount","DKC出金原力撤单退款|出金金额","force_dkc_withdrawal_notify_rollback_thaw_amount"),
	
	force_withdrawal_notify_rollback_thaw_poundage("force_withdrawal_notify_rollback_thaw_poundage","%s出金撤单退款|手续费","force_withdrawal_notify_rollback_thaw_poundage"),
	force_withdrawal_notify_rollback_thaw_amount("force_withdrawal_notify_rollback_thaw_amount","%s出金撤单退款|出金金额","force_withdrawal_notify_rollback_thaw_amount"),

	COIN_WITHDRAWAL_NOTCHECKPASS_THAW_POUNDAGE_REMARK("COIN_WITHDRAWAL_NOTCHECKPASS_THAW_POUNDAGE_REMARK", "%s出金审核不通过退款|手续费" ,"%s withdrawal Checkout does not pass the refund | fee"),
	COIN_WITHDRAWAL_NOTCHECKPASS_THAW_AMOUNT_REMARK("COIN_WITHDRAWAL_NOTCHECKPASS_THAW_AMOUNT_REMARK", "%s出金审核不通过退款|出金金额" ,"%s withdrawal Check out the deposit without refund | amount"),
	
	COIN_WITHDRAWAL_REFUND_FEE_REMARK("COIN_WITHDRAWAL_REFUND_FEE_REMARK", "出金退款|手续费" ,"withdrawal refund | fee"),
	COIN_WITHDRAWAL_REFUND_AMOUNT_REMARK("COIN_WITHDRAWAL_REFUND_AMOUNT_REMARK", "出金退款|出金金额" ,"withdrawal refund | amount"),
	COIN_WITHDRAWAL_SUCCESS_AMOUNT_REMARK("COIN_WITHDRAWAL_SUCCESS_AMOUNT_REMARK", "出金成功|出金金额" ,"withdrawal success | amount"),
	COIN_WITHDRAWAL_SUCCESS_FEE_REMARK("COIN_WITHDRAWAL_SUCCESS_FEE_REMARK", "出金成功|手续费" ,"withdrawal success | fee"),
	
	//转账相关
	TRANSFER_MONEY_REMARK_SUCCESS("transfer_money_remark_success","转账完成","Transfer finished"),
	TRANSFER_MONEY_REMARK_CHECK("transfer_money_remark_check","待审核","Approving"),
	TRANSFER_MONEY_REMARK_PASS("transfer_money_remark_pass","转账审核通过","Transfer approved"),
	TRANSFER_MONEY_REMARK_NOT_PASS("transfer_money_remark_not_pass","转账审核未通过","Transfer unapproved"),
	TRANSFER_MONEY_REMARK_FAIL("transfer_money_remark_fail","转账失败","Transfer failed"),
	TRANSFER_MONEY_REMARK_ROLLBACK("transfer_money_remark_rollback","转账撤单","Transfer rollback"),

	TRANSFER_MONEY_WALLET_RECORD_OUT("transfer_money_wallet_record_out","%s(%s) 汇出","Transfer out from %s(%s)"),
	TRANSFER_MONEY_WALLET_RECORD_IN("transfer_money_wallet_record_in","%s(%s) 汇入","Transfer in from %s(%s)"),
	TRANSFER_MONEY_WALLET_RECORD_FEE("transfer_money_wallet_record_fee","转账手续费","Transfer service fees"),
	TRANSFER_MONEY_WALLET_RECORD_FAIL_OUT("transfer_money_wallet_record_fail_out","转账退款","Transfer refund"),
	TRANSFER_MONEY_WALLET_RECORD_FAIL_FEE("transfer_money_wallet_record_fail_fee","手续费退款","Service fees refund"),

	APP_TYPE_1("app_type_1","互助转账","Withdraw to Force"),
	APP_TYPE_2("app_type_2","出金到原力","Withdraw to Force"),
	
	LANG_COIN_DKC("LANG_COIN_DKC","蒂克币","DKC"),
	LANG_COIN_BTC("LANG_COIN_BTC","比特币","BTC"),
	LANG_COIN_DKCT("LANG_COIN_DKCT","蒂克币平方","DK²T"),
	LANG_COIN_BCDS("LANG_COIN_BCDS","BCDS","BCDS"),
	LANG_COIN_BTG("LANG_COIN_BTG","比特币黄金","BTG"),
	LANG_COIN_LTC("LANG_COIN_LTC","LTC","LTC"),
	LANG_COIN_YSC("LANG_COIN_YSC","YSC","YSC"),
	LANG_COIN_LCC("LANG_COIN_LCC","LCC","LCC"),
	LANG_COIN_ETH("LANG_COIN_ETH","ETH","ETH"),
	
	LANG_COIN_DKC_WALLET("LANG_COIN_DKC_WALLET","蒂克币钱包","dkc wallet"),
	LANG_COIN_DKC_FORCE_WALLET("LANG_COIN_DKC_FORCE_WALLET","原力钱包","force wallet"),
	LANG_COIN_DKC_ORE_WALLET("LANG_COIN_DKC_ORE_WALLET","矿机蒂克钱包","ore wallet"),
	LANG_COIN_DK2_ORE_WALLET("LANG_COIN_DK2_ORE_WALLET","矿机DK²T钱包","dk²t wallet"),
	LANG_COIN_BTC_WALLET("LANG_COIN_BTC_WALLET","比特币钱包","btc wallet"),
	LANG_COIN_BCDS_WALLET("LANG_COIN_BCDS_WALLET","bcds钱包","bcds wallet"),
	LANG_COIN_BTG_WALLET("LANG_COIN_BTG_WALLET","比特币黄金钱包","btg wallet"),
	LANG_COIN_LTC_WALLET("LANG_COIN_LTC_WALLET","ltc钱包","ltc wallet"),
	LANG_COIN_YSC_WALLET("LANG_COIN_YSC_WALLET","ysc钱包","ysc wallet"),
	LANG_COIN_LCC_WALLET("LANG_COIN_LCC_WALLET","lcc钱包","lcc wallet"),
	LANG_COIN_ETH_WALLET("LANG_COIN_ETH_WALLET","eth钱包","eth wallet"),
	
	LANG_TRADE_TRANSACTION_FREEZE_TRADE("LANG_TRADE_TRANSACTION_FREEZE_TRADE","交易冻结|交易币","transaction freeze|transaction currency"),
	LANG_TRADE_TRANSACTION_FREEZE_PLEDGE("LANG_TRADE_TRANSACTION_FREEZE_PLEDGE","交易冻结|质押币","trading freeze|pledge"),
	LANG_TRADE_TRANSACTION_FREEZE_FEE("LANG_TRADE_TRANSACTION_FREEZE_FEE","交易冻结|手续费","transaction freeze|fee"),
	LANG_TRADE_TRANSACTION_REFUND_PLEDGE("LANG_TRADE_TRANSACTION_REFUND_PLEDGE","交易退款|质押币","transaction refund|pledge"),
	LANG_TRADE_TRANSACTION_REFUND_TRADE("LANG_TRADE_TRANSACTION_REFUND_TRADE","交易退款|交易币","transaction refund|transaction currency"),
	LANG_TRADE_TRANSACTION_REFUND_FEE("LANG_TRADE_TRANSACTION_REFUND_FEE","交易退款|手续费","transaction refund|fee"),
	LANG_TRADE_SELLER_PENDING_ORDERS("LANG_TRADE_SELLER_PENDING_ORDERS","卖家挂单卖出","seller pending order to sell"),
	LANG_TRADE_WAIT_SELLER_CONFIRM("LANG_TRADE_WAIT_SELLER_CONFIRM","等待卖家确认收款","wait for the seller to confirm the payment"),
	LANG_TRADE_WAIT_BUYERS_PAY("LANG_TRADE_WAIT_BUYERS_PAY","等待买家付款","waiting for buyers payment"),
	LANG_TRADE_SELLER_CONFIRM("LANG_TRADE_SELLER_CONFIRM","卖家确认收款","the seller confirmed receipt"),
	LANG_TRADE_SELLER_CANCEL_ORDER("LANG_TRADE_SELLER_CANCEL_ORDER","卖家撤销挂单","the seller canceled the pending order"),
	LANG_TRADE_WAIT_CHECK("LANG_TRADE_ORDER_TRADE_CHECK","等待系统审核","waiting for pending"),
    LANG_TRADE_CHECK_PASS("LANG_TRADE_ORDER_TRADE_CHECK","系统审核通过","system audit through"),
    LANG_TRADE_CHECK_NOT_PASS("LANG_TRADE_ORDER_TRADE_CHECK","系统审核未通过","system audit failed to pass"),
	LANG_TRADE_ORDER_TRADE_COMPLETE("LANG_TRADE_ORDER_TRADE_COMPLETE","订单交易完成","order transaction completed"),
	LANG_TRADE_IMPORT_TRANSACTION_PLEDGE("LANG_TRADE_IMPORT_TRANSACTION_PLEDGE","交易汇入|质押币","import transaction|pledge"),
	LANG_TRADE_IMPORT_TRANSACTION_TRADE("LANG_TRADE_IMPORT_TRANSACTION_TRADE","交易汇入|交易币","transaction freeze|transaction currency"),
	LANG_TRADE_IMPORT_TRANSACTION_FEE("LANG_TRADE_IMPORT_TRANSACTION_FEE","交易汇入|手续费","transaction freeze|fee"),
	LANG_TRADE_EXPORT_TRANSACTION_PLEDGE("LANG_TRADE_EXPORT_TRANSACTION_PLEDGE","交易汇出|质押币","export transaction|pledge"),
	LANG_TRADE_EXPORT_TRANSACTION_TRADE("LANG_TRADE_EXPORT_TRANSACTION_TRADE","交易汇出|交易币","export transaction|transaction currency"),
	LANG_TRADE_EXPORT_TRANSACTION_FEE("LANG_TRADE_EXPORT_TRANSACTION_FEE","交易汇出|手续费","export transaction|fee"),
	LANG_TRADE_WITHDRAWAL_REFUND_TRADE("LANG_TRADE_WITHDRAWAL_REFUND_TRADE","撤单退款|交易币","withdrawal refund|trading currency"),
	LANG_TRADE_WITHDRAWAL_REFUND_PLEDGE("LANG_TRADE_WITHDRAWAL_REFUND_PLEDGE","撤单退款|质押币","withdrawal refund|pledge"),
	LANG_TRADE_WITHDRAWAL_REFUND_FEE("LANG_TRADE_WITHDRAWAL_REFUND_FEE","撤单退款|手续费","withdrawal refund|fee"),
	LANG_TRADE_PLATFORM_INVOLVED_JUDGE_BUYER("LANG_TRADE_PLATFORM_INVOLVED_JUDGE_BUYER","平台介入-判定为买家违约","platform involved-judge buyer default"),
	LANG_TRADE_PLATFORM_INVOLVED_DETERMINE_BUYER_PAY("LANG_TRADE_PLATFORM_INVOLVED_DETERMINE_BUYER_PAY","平台介入-判定为买家付款违约","platform involved-determine buyer default payment"),
	LANG_TRADE_PLATFORM_INVOLVED_NO_CURRENCY_PROCESS("LANG_TRADE_PLATFORM_INVOLVED_NO_CURRENCY_PROCESS","平台介入-判定为无币处理","platform involved-judged as no currency processing"),
	LANG_TRADE_PLATFORM_INVOLVED_DETERMINE_SELLER("LANG_TRADE_PLATFORM_INVOLVED_DETERMINE_SELLER","平台介入-判定为卖家违约","platform involved-determine the seller default"),
	LANG_TRADE_PLATFORM_INVOLVED_SELLER_NOT_RECEIVE("LANG_TRADE_PLATFORM_INVOLVED_SELLER_NOT_RECEIVE","平台介入-判定为卖家未收到款违约","platform involved-determine the seller did not receive payment default"),
    LANG_TRADE_PLATFORM_INVOLVED_UNNORMAL("LANG_TRADE_PLATFORM_INVOLVED_UNNORMAL","系统审核-判定为违规虚假交易","System determined the order is false order"),
    LANG_TRADE_DEDUCT_BUYERS_PLEDGES("LANG_TRADE_DEDUCT_BUYERS_PLEDGES","-扣除买家质押币作为平台奖励金","-deduct buyers pledges as a platform reward"),
	LANG_TRADE_DEDUCT_SELLER_PLEDGE("LANG_TRADE_DEDUCT_SELLER_PLEDGE","-扣除卖家质押币作为平台奖励金","-deduct the seller pledge as a platform reward"),
	LANG_TRADE_NON_INDUSTRIAL("LANG_TRADE_NON_INDUSTRIAL","非工商银行卡交易,扣除买家积分%s分","non-industrial and commercial bank card transactions, excluding buyer points %s point"),
	LANG_TRADE_FIGHT_OVERTIME("LANG_TRADE_FIGHT_OVERTIME","打款超时,订单号:%s","fight overtime, order number:%s"),
	LANG_TRADE_CONFIRM_PAYMENT_OVERTIME("LANG_TRADE_CONFIRM_PAYMENT_OVERTIME","确认收款超时,订单号:%s","confirm payment overtime, order number:%s"),
	LANG_TRADE_TRANSACTION_SUCCESSFUL("LANG_TRADE_TRANSACTION_SUCCESSFUL","交易成功执行积分奖励操作订单号:%s","transaction successful execution points reward actions order number:%s"),
	LANG_TRADE_BLACK_TRADE("LANG_TRADE_BLACK_TRADE","黑心交易扣除买家积分%s分","black deal deducts buyer points %s point"),
	LANG_TRADE_DEDUCT_SELLER_POINTS("LANG_TRADE_DEDUCT_SELLER_POINTS","扣除卖家积分%s分","deduct seller points %s point"),
	LANG_TRADE_DEDUCT_BUYER_POINTS("LANG_TRADE_DEDUCT_BUYER_POINTS","扣除买家积分%s分","deduct buyer points %s point"),
	LANG_TRADE_BONUS("LANG_TRADE_BONUS","奖金","bonus"),
	LANG_TRADE_MASS_APPROVED("LANG_TRADE_MASS_APPROVED","批量审核通过","mass approved"),
	LANG_TRADE_ADDRESS_NOT_EXIST("LANG_TRADE_ADDRESS_NOT_EXIST","地址不存在","the address does not exist"),
	LANG_TRADE_POINTS_SUCCESS("LANG_TRADE_POINTS_SUCCESS","积分转入成功","points into success"),
	LANG_TRADE_SYSTEM_INTO_GOLD("LANG_TRADE_SYSTEM_INTO_GOLD","系统入金","system into the gold"),
	LANG_TRADE_WALLET_INTO_GOLD("LANG_TRADE_WALLET_INTO_GOLD","钱包入金","wallet into the gold"),
	LANG_TRADE_DEDUCT_SELLER_TRADING_CURRENCY("LANG_TRADE_DEDUCT_SELLER_TRADING_CURRENCY","-扣除卖家的交易币作为平台的奖励金","-deduct the seller's transaction currency as a platform bonus"),
	
	LANG_TRADE_SYSTEM("LANG_TRADE_SYSTEM", "系统", "System"),
	
	/********************************安全中心提示语言包*********begin*********************************************/
	LANG_SAFE_CENTER_APICODE_SUCCESS("SAFE_CENTER_APICODE_SUCCESS","成功","success"),
	LANG_SAFE_CENTER_APICODE_FAIL("SAFE_CENTER_APICODE_FAIL","失败","fail"),
	LANG_SAFE_CENTER_APICODE_ERROR("SAFE_CENTER_APICODE_ERROR","错误","error"),
	LANG_SAFE_CENTER_APICODE_VERIFYSIGNFAIL("SAFE_CENTER_APICODE_VERIFYSIGNFAIL","验签失败","Verification failed"),
	
	LANG_SAFE_CENTER_PARAM_ERRER("SAFE_CENTER_PARAM_ERRER","参数错误","Parameter error"),
	LANG_SAFE_CENTER_SGINFAIL("SAFE_CENTER_SGINFAIL","加签失败","Endorsement failed"),
	LANG_SAFE_CENTER_SECRETKEYNOTEXIST("SAFE_CENTER_SECRETKEYNOTEXIST","没有密钥，请重新登录makeys","Without a key, please log in again"),
	LANG_SAFE_CENTER_ACCOUNTDISABLED("SAFE_CENTER_ACCOUNTDISABLED","用户被禁止使用该系统","The user is prohibited from using the system"),
	LANG_SAFE_CENTER_REQUSTEXCEEDSLIMIT("SAFE_CENTER_REQUSTEXCEEDSLIMIT","超出请求频率限制","Exceeded request frequency limit"),
	LANG_SAFE_CENTER_POPENSYSPROTECTION("SAFE_CENTER_POPENSYSPROTECTION","请先开启系统保护","Please turn on system protection"),
	
	LANG_SAFE_CENTER_PLATFORMNOTAUTHORIZED("SAFE_CENTER_PLATFORMNOTAUTHORIZED","该平台尚未被授权","The platform has not been authorized"),
	LANG_SAFE_CENTER_SYSTEMISBUSY("SAFE_CENTER_SYSTEMISBUSY","系统很忙碌，请稍后再试","The system is very busy, please try again later"),
	LANG_SAFE_CENTER_SYSTEMLOGINBUSY("SAFE_CENTER_SYSTEMLOGINBUSY","系统登录繁忙","System login busy"),
	LANG_SAFE_CENTER_PAYMENTISBUSY("SAFE_CENTER_PAYMENTISBUSY","系统支付繁忙","System payment is busy"),
	LANG_SAFE_CENTER_USERTEMPORARILYLOCKED("SAFE_CENTER_USERTEMPORARILYLOCKED","用户被临时锁定","User is temporarily locked"),
	LANG_SAFE_CENTER_USERLOCKEDBYADMIN("SAFE_CENTER_USERLOCKEDBYADMIN","用户被管理员锁定","The user is locked by the administrator"),
	LANG_SAFE_CENTER_PWDERRORLOCKED("SAFE_CENTER_PWDERRORLOCKED","密码错误次数太多，永久锁定","Too many password errors, permanent lock"),
	LANG_SAFE_CENTER_INVALIDUSERSTATUS("SAFE_CENTER_INVALIDUSERSTATUS","用户状态无效","Invalid user status"),
	
	LANG_SAFE_CENTER_PAYMENTRIGHTSLOCKED("SAFE_CENTER_PAYMENTRIGHTSLOCKED","支付权限被锁定","Payment rights are locked"),
	LANG_SAFE_CENTER_DYNAMICCODEERROR("SAFE_CENTER_DYNAMICCODEERROR","动态密码错误","Dynamic password error"),
	LANG_SAFE_CENTER_DYNAMICCODTRYAGIN("SAFE_CENTER_DYNAMICCODTRYAGIN","动态密码长度不正确，请重新输入或者重新登录makeys","Dynamic password length is incorrect, please re-enter or re-login makeys"),
	LANG_SAFE_CENTER_TOTPCAPTCHACANUSEONE("SAFE_CENTER_TOTPCAPTCHACANUSEONE","TOTP验证码只能被使用一次","TOTP captcha can only be used once"),
	
	SYSTEM_CHARGE("SYSTEM_CHARGE", "系统扣费", "System charge"),
	SYSTEM_RECHARGE("SYSTEM_RECHARGE", "系统充值", "System recharge")
	
	/********************************安全中心提示语言包*********end*********************************************/
	;
	
	LanguageConstant(String code, String msg_cn, String msg_en){
		this.code = code;
		this.msgZh = msg_cn;
		this.msgEn = msg_en;
	}
	
	private String code;
	
	private String msgZh;
	
	private String msgEn;
	
	public static LanguageConstant getLanguageByCode(String code){
		LanguageConstant[] ls = LanguageConstant.values();
		for(LanguageConstant l : ls){
			if(l.getCode().equals(code)){
				return l;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsgZh() {
		return msgZh;
	}

	public void setMsgZh(String msgZh) {
		this.msgZh = msgZh;
	}

	public String getMsgEn() {
		return msgEn;
	}

	public void setMsgEn(String msgEn) {
		this.msgEn = msgEn;
	}
	
    public String getMsg() {

	// String msg = this.getMsgEn();
	// Object obj =
	// SecurityUtils.getSubject().getSession().getAttribute("lang");
	//
	// if(null != obj && obj.toString().equalsIgnoreCase("zh")){
	// msg = this.getMsgZh();
	// }else{
	// // 为空默认为英文
	// msg = this.getMsgEn();
	// }
	return LanguageUtil.getMsg(this);
    }
	
	public String getMsg(Object... params) {
		return LanguageUtil.getMsg(this,params);
	}
}
