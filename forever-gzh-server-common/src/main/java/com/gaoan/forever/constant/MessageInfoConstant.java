package com.gaoan.forever.constant;

/**
 * 后台消息
 * @author three
 */
public enum MessageInfoConstant {

       STOP_SERVER(-10,"服务暂停，系统升级维护中","Service suspension, system upgrade maintenance"),
       NONE(-100,"服务器繁忙，请稍后重试", "Sever error,please try later"),
       COMMON_ERROR_CODE(-10000, "操作失败", "Failed"),
       COMMON_ERROR_ILLEGAL_ARGUMENT_EXCEPTION(-10002, "请求参数非法", "request parameter error"),
       FORM_IS_HANDLING(-10006,"正在处理中,请不要重复提交","In progress, please do not submit again"),
       UPLOAD_FILE_SIZE_OVER_LIMITED(-10007,"上传文件大小超过限制","File size over limited"),
       REQUEST_TIMESTAMP_IS_INVALID(-10008,"请求时间戳无效","Request timestamp is invalid"),
       
       LOGIN_USERNAME_PASSWORD_ISNULL(10001, "登录账号/密码不能为空", "Username/password can't be empty"),
       ACCOUNT_IS_LOCK(10002, "账户已经被锁定登录,请与管理员联系", "The account is locked, please contact administrator"),
       ACCOUNT_OR_PASSWORD_IS_ERROR(10003, "帐号或密码错误,请重新输入", "Please enter correct username or password"),
       USER_HAD_NOT_LOGIN(10004, "用户未登录", "User not login"),
       USER_LOGIN_PASSWORD_CANNOT_BE_EMPTY(10008, "请输入正确的登录密码", "Please enter correct login password"),
       THE_USER_PASSWORD_MISTAKE(10012, "账户密码错误", "Please enter correct password"),
       THE_USER_PASSWORD_LENGTH(10013, "密码长度为8-32位", "Password needs containing 8-32 characters"),
       
       PARAM_CANT_BE_NULL(20001, "参数不能为空", "Parameters is required"),
       DATAS_INFO_IS_NOT_EXIST(20004, "数据错误", "Data error"),
       PLEASE_CHECK_PARAM(20013, "请仔细检查数据完整", "Check the parameter whether complete or not"),

       DEL_INFO_FAIL(30000, "数据删除失败", "Data delete failed"),
       ROLE_INFO_IS_USE(30005, "角色已被使用,不允许删除", "The role is using, which unallowed to deleted"),
       ROLE_NAME_IS_EXIST(30006, "角色名已经存在", "Role name is exists"),
       RESOURCES_NAME_IS_EXIST(30010, "资源名称已经存在", "Resource name is exists"),
       ROLE_INFO_IS_NOT_EXIST(30017, "角色信息错误", "The role does not exist"),
       USERNAME_IS_EXIST(30023, "用户名已存在", "The username is exists"),
       USER_INFO_IS_NOT_EXIST(30024, "用户不存在", "The username isn't exists"),
       SAVLE_VAR_CODE_NOT_PASS(30052, "动态安全码验证不通过", "Dynamic password verify failed"),
       UPDATE_INFO_DONT_EXIST(30060, "数据不存在", "No data"),
       EXPORT_DATA_IS_EMPTY(30068, "导出数据为空", "Export data is empty"),
       // TODO
       COLOR_IS_EXISTS(30069, "颜色已存在", "Color already exists"),
       SIZE_IS_EXISTS(30070, "尺寸已存在", "Size already exists"),
       
       NOT_ENOUGH_INVENTORY(40001, "没有足够的库存", "Not enough inventory"),
       PRODUCT_ALREADY_EXISTS(40002, "该商品已存在", "The product already exists"),
       PASSWORD_NOT_SAME(40003, "密码不一致", "Password is not the same"),
       USER_REGISTER_FIAL(40004, "用户注册失败,请联系管理员", "User registration failed, please contact administrator"),
       
       IMAGE_UPLAOD_FAIL(60003, "图片上传失败", "Image upload failed"),
       IMAGE_TYPE_UNKNOWN(60004, "上传文件非图片类型", "Unknown image type"),
       URL_UNAUTHORIZED(60007, "请求未通过授权", "Request unauthorised"),
       WEBNOTIFY_TYPE_PARAM_ERROR(60008, "参数错误", "Parameter error"),
       PARAM_FORMAT_EXIST(60038, "参数格式错误", "Parameter format error"),
       ;
    
	/*STOP_SERVER(-10,"服务暂停，系统升级维护中","Service suspension, system upgrade maintenance"),

	NONE(-100,"服务器繁忙，请稍后重试", "Sever error,please try later"),
	COMMON_ERROR_CODE(-10000, "操作失败", "Failed"),
	COMMON_ERROR_ILLEGAL_STATE_EXCEPTION(-10001, "请求状态无效", "invalid status request"),
	COMMON_ERROR_ILLEGAL_ARGUMENT_EXCEPTION(-10002, "请求参数非法", "request parameter error"),
	COMMON_ERROR_ARGUMENT_CONVERSIONNOT_SUPPORTED_EXCEPTION(-10003, "非法参数异常", "parameter error"),
	COMMON_ERROR_ARGUMENT_TYPE_MISMATCH_EXCEPTION(-10004, "无效状态异常", "invalid status error"),
	COMMON_ERROR_MISSING_SERVLET_REQUEST_PART(-10005, "请求缺少参数异常", "lack parameter in request error"),
	FORM_IS_HANDLING(-10006,"正在处理中,请不要重复提交","In progress, please do not submit again"),
	UPLOAD_FILE_SIZE_OVER_LIMITED(-10007,"上传文件大小超过限制","File size over limited"),
	REQUEST_TIMESTAMP_IS_INVALID(-10008,"请求时间戳无效","Request timestamp is invalid"),
	
	LOGIN_USERNAME_PASSWORD_ISNULL(10001, "登录账号/密码不能为空", "Username/password can't be empty"),
	
	ACCOUNT_OR_PASSWORD_IS_ERROR(10003, "帐号或密码错误,请重新输入", "Please enter correct username or password"),
	USER_HAD_NOT_LOGIN(10004, "用户未登录", "User not login"),
	USER_LOGIN_FAIL(10005, "用户登录失败", "Login failed"),
	USER_SIGN_IS_FAIL(10006, "签名失败", "Encryption of the parameters failed"),
	USER_PAY_PASSWORD_FAIL(10007, "支付密码错误", "Please enter correct payment password"),
	USER_LOGIN_PASSWORD_CANNOT_BE_EMPTY(10008, "请输入正确的登录密码", "Please enter correct login password"),
	THE_NEW_PASSWORD_CANNOT_BE_EMPTY(10009, "请输入正确的新密码", "Please enter correct password"),
	TWO_PASSWORD_INPUT_IS_NOT_THE_SAME(10010, "两次密码输入不一致", "Confirm password is different from the first enter"),
	RESET_FAILED(10011, "密码重置失败", "Password reset failed"),
	THE_USER_PASSWORD_MISTAKE(10012, "账户密码错误", "Please enter correct password"),
	THE_USER_PASSWORD_LENGTH(10013, "密码长度为8-32位", "Password needs containing 8-32 characters"),
	THE_USER_PASSWORD_UPDATE_FAIL(10014, "密码修改失败", "Change password failed"),
	NOT_THROUGH_THE_SECURITY_VERIFICATION(10015, "安全验证未通过", "Security verify failed"),
	HAD_NOT_PAY_PASSWORD(10016, "支付密码错误", "Please enter correct payment password"),
	USER_HAD_SET_PAY_PASSWORD(10017, "用户已设置交易密码", "Payment password is exist"),
	LOGIN_NAME_CANT_BE_NULL(10018, "请输入账户名", "Please enter the username"),
	SIGN_CANT_BE_NULL(10019, "签名错误", "Please make sure your signture is correct"),
	TRADE_USER_IS_MISS(10020, "交易用户已被冻结或删除", "Destination account is forzen or deleted"),
	PAY_PASSWORD_NOT_NULL(10021, "请输入交易密码", "Please enter payment password"),
	USER_NOLOGIN(10022, "用户未登录", "User is not logged in"),
	USER_API_ACCESS_WARN(10023,"您的帐号访问网站过于频繁，请注意帐号安全","Your account to visit the site too often, please pay attention to account security"),
	USER_GETLOGIN_QRCODE_FAIL(10024,"获取登录二维码失败","Failed to get login QR code"),
	USER_HASBEENSWEPT(10025,"用户已扫码,等待授权","The user has scanned the code, waiting for authorization"),
	USER_REFUSETOAUTHORIZE(10026,"用户拒绝授权","User denied authorization"),
	
	PLATFORMEXCHAGE_TRADE_ACOUNT_NOT_LESS_ZERO(10027,"兑换的金额不能小于0","The amount of redemption can not be less than 0"),
	
	PARAM_MUST_BE_MENBER(20000, "参数必须为数字", "Parameters must be numbers"),
	PARAM_CANT_BE_NULL(20001, "参数不能为空", "Parameters is required"),
	DATAS_INSTER_FAIL(20002, "数据添加失败", "Data added failed"),
	PARAM_TYPE_CANT_BE_NULL(20003, "类型不能为空", "Type is required"),
	DATAS_INFO_IS_NOT_EXIST(20004, "数据错误", "Data error"),
	DATAS_INFO_UPDATE_FAIL(20005, "修改失败", "Modify failed"),
	DATAS_IS_NOT_MOBILE(20006, "请输入正确的手机号码", "Please enter correct phone number"),
	PASSWORD_DIFFER(20007, "输入密码不一致", "Confirm password is different from the first enter"),
	USER_IS_DELETE(20008, "用户已被禁用或删除", "Account is locked or deleted"),
	CURRENY_MUST_BE_MENBER(20009, "输入金额必须为数字", "The amount must be numbers"),
	INPUT_ID_MUST_BE_MENBER(20010, "输入出金类型编码必须为数字", "withdraw type No. must be numbers"),
	THIS_WITHDRAWAL_ADDRESS_IS_NOT_EXIST(20011, "出金地址错误", "Please enter correct withdraw address"),
	THIS_WITHDRAWAL_ADDRESS_DO_NOT_USE(20012, "出金地址不可用", "withdraw address is invalid"),
	PLEASE_CHECK_PARAM(20013, "请仔细检查数据完整", "Check the parameter whether complete or not"),
	DATA_SUBMINT_FAIL(20014, "信息提交失败", "Submitted failed"),
	THIS_WITHDRAWAL_STATUS_CANNOT_ROLLBACK(20015, "当前交易状态不允许撤单", "Cancel unallowed in this status"),
	RECHARGE_DONT_OPEN(20016, "平台充值功能当前不开放", "Recharge function not open now"),
	WITHDRWAL_DONT_OPEN(20017, "平台出金功能当前不开放", "Withdraw function not open now"),
	PARAM_RATIO_BE_MENBER(20018, "输入比率必须为数字", "Trade amount ：service fees rate"),
	OUT_CHARGER_AMOUNT_NOT_LESS_ZERO(20019, "出金金额不能小于等于零", "Withdraw amount can't less than or equal to 0"),
	
	DEL_INFO_FAIL(30000, "数据删除失败", "Data delete failed"),
	USER_INFO_IS_NOT_EXIST(30001, "用户信息错误", "Please make sure your account information is correct"),
	STATUS_CODE_CANT_BE_NULL(30002, "图片验证码不能为空", "Please make sure your image verify code is correct"),
	STATUS_CODE_IS_ERROR(30003, "图片验证码输入错误", "Please make sure your image verify code is correct"),
	ROLE_NAME_CANT_BE_NULL(30004, "角色名称不能为空", "Please enetr a correct nickname"),
	ROLE_INFO_IS_USE(30005, "角色已被使用,不允许删除", "The role is using, which unallowed to deleted"),
	ROLE_NAME_IS_EXIST(30006, "角色名已经存在", "Role name is exists"),
	RESOURCES_NAME_CANT_BE_NULL(30007, "资源名称不能为空", "Please enetr correct resource name"),
	RESOURCES_URL_CANT_BE_NULL(30008, "资源授权不能为空", "Please enetr correct resource authorization"),
	INFO_ID_CANT_BE_NULL(30009, "数据编号不能为空", "Please enetr correct data number"),
	RESOURCES_NAME_IS_EXIST(30010, "资源名称已经存在", "Resource name is exists"),
	RESOURCES_URL_IS_EXIST(30011, "资源授权已经存在", "Resource authorization is exists"),
	RESOURCES_INFO_IS_USE(30012, "资源已被占用", "The resource is using"),
	THIS_RESOURCES_INFO_CANT_CREATE_ACTION(30013, "当前父节点不能被创建", "The parent node unallowed created"),
	THIS_RESOURCES_INFO_CANT_CREATE_MENU(30014, "当前父节点不能创建菜单", "The parent node unallowed to create menu"),
	THIS_RESOURCES_INFO_CANT_HAD_NEXT(30015, "当前父节点不能创建下级", "The parent node unallowed to create child node"),
	USER_CANT_AUTH_SELF(30016, "用户不能修改自身角色", "Users unallowed to change their roles"),
	ROLE_INFO_IS_NOT_EXIST(30017, "角色信息错误", "The role does not exist"),
	ROLE_CANT_NOT_UPDATE_SELF(30018, "角色不能修改本角色资源", "The role unallowed to change it's own resource"),
	ROLE_CANT_NOT_UPDATE_ADMIN(30019, "角色不能修改管理员资源", "The role unallowed to change administrator resources"),
	ROLE_CANT_NOT_DELETE_SELF(30020, "角色不能删除本角色资源", "The role unallowed to delete it's own resource"),
	ROLE_CANT_NOT_DELETE_ADMIN(30021, "角色不能删除管理员资源", "The role unallowed to delete administrator resources"),
	USERNAME_LENGTH_IS_FAIL(30022, "用户名长度为8-32位", "Username needs containing 8-32 characters"),
	USERNAME_IS_EXIST(30023, "用户名已存在", "The username is exists"),
	USER_INFO_IS_EXIST(30024, "用户不存在", "The username isn't exists"),
	USER_INFO_HAD_NOT_ROLE(30025, "用户未授权角色", "The user doesn't authorize this role"),
	MOBILE_AREAS_IS_NOT_EXIST(30026, "手机号码错误", "Please enter correct phone number"),
	CREATE_TRADE_USER_FAIL(30027, "用户创建失败", "Account created failed"),
	CREATE_TRADE_USER_WALLET_FAIL(30028, "用户钱包创建失败", "User created wallet failed"),
	TRADE_USER_WALLET_IS_NOT_EXIST(30029, "交易地址错误", "Please enter correct transaction address"),
	TRADE_FLOW_NUM_IS_EXIST(30030, "交易流水号已经存在", "The transaction SN is already exists"),
	TRADE_FLOW_NUM_NOT_EXIST(30031, "交易流水号错误", "Please enter correct transaction SN"),
	TRANSFER_AMOUNT_IS_NOT_ENOUGH(30032, "账户余额不足", "The amount is not enough to transfer"),
	THE_WALLET_AMOUNT_DEDUCTED_FROM_FAILURE(30033, "交易扣款失败", "The amount of wallet deducted failed"),
	THE_WALLET_ADDRESS_DOES_NOT_EXIST(30034, "钱包地址错误", "Please enter correct wallet address"),
	ABNORMAL_TRANSACTION_RECORDS_TO_ADD(30035, "交易记录添加异常", "Transaction records add unformal"),
	THE_TRANSACTION_RECORD_DOES_NOT_EXIST(30036, "交易记录错误", "Transaction records doesn't exist"),
	SAVE_THE_ABNORMAL_TRADING_INFORMATION(30037, "交易信息保存异常", "Transaction information submitted unformal"),
	WALLET_COMPUTING_ABNORMAL_PLEASE_CONTACT_YOUR_ADMINISTRATOR(30038, "账户钱包数据异常,请联系管理员", "Wallet is count unformal, please contact your administrator"),
	MESSAGE_TEMPLE_IS_NOT_EXIST(30039, "消息模板错误", "Message templates error"),
	NOT_SETPRIOROTIES(30040, "未设置优先项", "Unset precedence"),
	MESSAGE_SEND_FAIL(30041, "消息发送失败", "Message send failed"),
	MESSAGE_REQ_TYPE_CANNOT_NULL(30042, "消息请求类型不能为空", "Please enter correct message request type"),
	MESSAGE_CHECK_FAIL(30043, "验证码无效", "Verify code invalid"),
	REAL_NAME_AUDIT_FAILURE(30044, "实名审核提交失败", "Real-name audit failed"),
	HAD_PASS_AUTH_REQ(30045, "用户已经通过审核", "The user has passed the real-name audit"),
	HAD_REQ_AUTH_SUB_INFO(30046, "用户已经提交了实名审核,等待审核", "The user has submitted a real-name audit, and it's pending"),
	THIS_EMAIL_HAD_BE_BOUND(30047, "邮箱已经被占用", "This mail is exist already"),
	THIS_EMAIL_BOUND_FAIL(30048, "邮箱绑定失败", "Email binding failed"),
	TIME_FORMATTING_IS_WRONG(30049, "时间格式错误", "Time format error"),
	THIS_VERCODE_BOUND_FAIL(30050, "动态安全码绑定失败", "Dynamic password bound failed"),
	THIS_VERCODE_UNBOUND_FAIL(30051, "动态安全码解除绑定失败", "Ynamic password unbound failed"),
	SAVLE_VAR_CODE_NOT_PASS(30052, "动态安全码验证不通过", "Dynamic password verify failed"),
	THE_WALLET_TRPE_DOES_NOT_EXIST(30053, "钱包类型错误", "The wallet type doesn't exists"),
	THE_WALLET_DOES_NOT_EXIST(30054, "钱包错误", "The wallet doesn't exists"),
	FORCE_OUT_ADDRESS_CHECK_FAIL(30055, "原力平台出金地址检测失败", "Gravity withdraw address detection is failed"),
	TRADE_DONOTSUBMIT_PROOF_FAIL(30056, "提交打款凭证失败", "Payment certificates failed"),
	TRADE_OUT_MODEL_UNKNOWN(30057, "出金模式未知", "Unknown withdraw type"),
	INPUT_PASSWORD_CANT_BE_NULL(30058, "密码不能为空", "Password can't be empty"),
	ROLE_INFO_CREATE_FAIL(30059, "角色信息创建失败", "Profile add failed"),
	UPDATE_INFO_DONT_EXIST(30060, "数据不存在", "No data"),
	USER_OUT_WALLETADDR_IS_EXIST(30061, "该出金地址已存在", "The withdraw address is exist"),
	DIGITAL_CURRENCY_IS_EXIST(30062, "数字币名称或者数据币代码已经存在", "The digital currency name or currency code already exists"),
	COIN_CAN_NOT_TRADE(30063, "该币种暂不支持交易", "The currency does not support trading"),
	REMARK_CONTENT_EXCEED_LIMIT(30064, "备注内容超过限制", "Remarks content exceeds the limit"),
	NO_ASSESS_VISIT(30065, "您的账户暂无访问权限,请联系管理员进行权限分配", "No access to your account, please contact the administrator for permission allocation"),
	OUT_ADDRESS_CHECK_FAIL(30066, "地址不合法", "Withdraw address detection is failed"),
	PARENT_OUT_ADDRESS_DONT_EXIST(30067, "合约主地址不存在", "The withdraw address of superiors doesn't exist"),
	EXPORT_DATA_IS_EMPTY(30068, "导出数据为空", "Export data is empty"),
	QUERY_DATA_IS_EMPTY(30069, "查询数据为空", "Query data is empty"),
	USERNAME_OR_KEY_IS_EXISTS(30070, "用户名或者秘钥已存在", "User name or key already exists"),
	USERNAME_CORRESPOND_KEY_IS_NOT_EXIST(30071, "用户名对应的秘钥不存在", "The user name corresponding to the key does not exist"),

	MOBILE_SEND_SMS_FAIL(40000, "短信发送失败", "Message send failed"),
	MOBILE_SEND_SMS_TYPE_IS_NOT_ESITS(40001, "短信请求类型错误", "The message request type doesn't exist"),
	MOBILE_SMS_CODE_CANT_BE_NULL(40002, "请输入正确的手机验证码", "Please enter the correct phone verification code"),
	MOBILE_SMS_CODE_IS_FAIL(40003, "请输入正确的验证码", "Wrong message verify code"),
	EMAIL_SMS_CODE_SEND_FAIL(40004, "邮件发送失败", "Email sends failed"),
	EMAIL_CANNOT_NULL(40005, "请输入正确的邮箱地址", "Please enter correct email"),
	EMAIL_CODE_CANT_BE_NULL(40006, "请输入正确的邮箱验证码", "Please enter correct verify code"),
	EMAIL_CODE_IS_ERROR(40007, "邮箱验证码验证错误", "Please enter correct verify code"),
	GOOGLE_CODE_CHECK_FAIL(40008, "动态安全验证码验证错误", "Dynamic code verify failed"),
	ID_NUM_CHECK_NOT_PASS(40009, "证件号验证不通过", "Please enter correct ID number"),
	ID_NUM_HAD_NOT_CHECK_PASS(40010, "证件号验证通过", "Your ID is approval"),
	HAD_NOT_TRADE_ADDRESS(40011, "转账地址错误", "Please enter correct transfer address"),
	HAD_NOT_BOUND_GOOGLE_CODE(40012, "未开启动态安全验证", "Unset dynamic security verify"),
	CREDENTIALS_VALIDPERIOD_NOTENOUGHTHREEMONTHS(40013, "证件有效期不足3个月", "Certificate is less than 3 months valid"),
	ICBC_BANK_NUMBER_ILLEGAL(40014, "工商银行卡号不合法", "ICBC card number is invalid"),
	ID_NUM_EXISTED(40015, "身份证件号码被占用", "The ID number is occupied"),

	DKC_RECHARGE_IS_FAIL(50000, "DKC钱包入金失败", "DKC transfer in failed"),
	LEVEL_INFO_INTEGRAL_OVERLAP(50001, "等级信息积分数值重叠", "Same credit rating integral"),
	OER_COIN_TYPE_NOT_EXIST(50002, "矿机入金币种类型不存在","Minerals recharge coins type is not exist"),
	OER_COIN_TYPE_NOT_NULL(50003, "矿机入金币种类型不能为空","Minerals recharge coins type can't be empty"),

	PARAM_MUST_BE_LETTER(60000, "参数必须为字母", "Parameters must be letters"),
	PARAM_MUST_BE_ZEROORONE(60001, "参数必须是0或1", "Parameters must be 0 or 1"),
	TEMPLATE_INFO_IS_NOT_EXIST(60002, "模板数据错误", "Data template doesn't exist"),
	IMAGE_UPLAOD_FAIL(60003, "图片上传失败", "Image upload failed"),
	IMAGE_TYPE_UNKNOWN(60004, "上传文件非图片类型", "Unknown image type"),
	PARAM_MUST_BE_ONEORMINUSONE(60005, "参数必须是1或者-1", "Parameters must be 1 or -1"),
	//国内银行信息管理controller
	BANKCARD_EXIST(60006, "银行卡号已被占用", "The card is exist"),
	URL_UNAUTHORIZED(60007, "请求未通过授权", "Request unauthorised"),
	WEBNOTIFY_TYPE_PARAM_ERROR(60008, "参数错误", "Parameter error"),
	CERTIFICATESIMG_MAX_SIZE(60009, "上传证件图片不能大于6M", "The identification image shouldn't be larger than 6M"),
	USER_EFFECTIVE_BANK_CARD_EXIST(60010, "用户已绑定银行卡", "The user has a valid card already"),
	USER_EFFECTIVE_BANK_CARD_EXIST_EXCPTION(60011, "您已绑定银行卡,请先解绑后再进行操作", "The user has a valid card already, please delete it first then operate"),
	USER_EFFECTIVE_WALLET_EXIST(60012, "用户已绑定该类型钱包出金地址", "You bound this type of withraw address"),
	USER_EFFECTIVE_WALLET_EXIST_EXCPTION(60013, "您已绑定该类型钱包出金地址,请先解绑后再进行操作", "You bound this type of withraw address,please delete it first then operate"),
	PARAM_MUST_BE_RONEORTOWE(60014, "参数必须是1或2", "Parameters must be 1 or 2"),
	WALLET_TRANSFER_RECORD_STATUS_NOTEXIST(60015, "钱包资金记录状态错误", "The amount record of wallet doesn't exist"),
	WALLET_TRANSFER_RECORD_TYPE_NOTEXIST(60016, "钱包资金记录类型错误", "The type record of wallet doesn't exist"),
	TRADE_USERINFO_AUTH_NOTPASSED(60017, "用户信息未通过认证", "The account is unapproved"),
	TRADE_USER_VERIFYPAYPWD_NOTPASSED(60018, "交易密码校验未通过", "Payment password unapproved"),
	TRADE_USER_VERIFYDYNAMICCODE_NOTPASSED(60019, "动态密码校验未通过", "Dynamic password verify failed"),
	TRADE_USER_BALANCE_NOTENOUGH(60020, "账户余额不足", "No enough amount"),
	TRADE_USER_ORDER_NOTEXIST(60021, "交易订单错误", "Order doesn't exist"),
	TRADE_USER_BUY_FAIL(60022, "买入失败", "Order failed"),
	TRADE_USER_SELL_FAIL(60023, "卖出失败", "Order failed"),
	TRADE_USER_ONLINE_ORDER_ROLLBACKFAIL_NOTPENDING(60024, "当前交易状态不允许撤单", "The order unallowed to cancel in this status"),
	TRADE_USER_ORDER_ROLLBACKFAIL(60025, "订单撤单失败", "The order cancel failed"),
	TRADE_USER_BUY_FAIL_NOTPENDING(60026, "交易买入失败,挂单已撤单或已被其他用户购买", "The order buying failed,which is cancelled or bought by others"),
	TRADE_OPENTIME_IS_OUT(60027, "交易已收盘", "Today's deal is closed"),
	TRADE_TODY_SELL_ORDER_COUNT_EXCEED(60028, "挂单数超过了限制", "Sold orders are over limited"),
	TRADE_TODY_SELL_QUANTITY_COUNT_EXCEED(60029, "单笔卖出数量超过了限制", "The number of the day sales over the limit"),
	TRADE_START_LEVEL_NOTEXIST(60030, "星级错误", "Grade error"),
	TRADE_CANNOTBUY_YOUSELFORDER(60031, "不允许购买自己的挂单", "Do not allow to buy your own sold order"),
	TRADE_CANNOT_OPERATE_NOTBELONGRDER(60033, "订单操作失败", "Operate the order failed"),
	PARAM_NOTMATCH_AUTHINFO(60034,"参数与实名认证信息不符","The parameter doesn't match the real-name authentication information"),
	BANKCARD_NOT_EXIST_VALIDBANKCARD(60035, "输入银行卡号错误", "No valid card"),
	BANKCARD_NOT_NOTMATCH(60036, "输入银行卡号校验失败", "Card number verify failed"),
	WALLET_NOT_EXIST(60037, "钱包错误", "Wallet error"),
	PARAM_FORMAT_EXIST(60038, "参数格式错误", "Parameter format error"),
	TRADE_BUYER_PAY_TIMEOUT(60039, "买家打款超时", "Payment is timeout"),
	TRADE_SELLER_CONFIRM_RECEIPT_TIMEOUT(60040, "卖家确认收款超时", "The seller gathering is over time limited"),
	TRADE_DONOTSUBMIT_PROOF_IN_THISSTATUS(60041, "当前状态不能提交打款凭证", "The current status can not be submitted to the voucher"),
	TRADE_DONOTCONFIRMRECEIPT_IN_THISSTATUS(60042, "当前状态不能确认收款", "The gathering can't submit in current status"),
	TRADE_USER_VERIFYPAYPWD_NOTEXIST(60043, "为保障账户资金安全,请尽快设置交易密码", "Please set payment password in time"),

	BOUNS_POOL_NOTEXIST(60044, "Her-Mest奖金池不存在", "The award doesn't exist"),
	BOUNS_POOL_EXCPTION(60045, "Her-Mest奖金池异常", "The award is unformal"),
	BOUNS_POOL_STATUS_CANNOT_GRANT(60046, "当前Her-Mest奖金池已被分配", "The current award of Her-Mest is divided"),
	BOUNS_POOL_WINNERS_NOTEXIST(60047, "Her-Mest奖池分配会员错误", "The winner doesn't exist"),
	BOUNS_POOL_MONEY_NOTMATCH(60048, "Her-Mest奖池分配超额或未分配完全", "The awarded amount is wrong. Please redistribute"),
	TRADE_USERINFO_AUTHINFO_NOTEXIST(60049, "用户实名认证信息错误", "Please enter correct real-name approved information"),
	TRADE_USERINFO_AUTHINFO_WAITING(60050, "用户实名信息未认证", "Unapproved real-name information"),
	TRADE_COIN_CANNOT_NEGATIVE_NUMBER(60051, "交易数量不能为0或负数", "Transaction quantity unallowed being 0 or negtive"),
	TRADE_PRICE_CANNOT_NEGATIVE_NUMBER(60052, "交易单价不能为0或负数", "Transaction price unallowed being 0 or negtive"),
	TRADE_SINGLEORDER_SELL_QUANTITY_COUNT_EXCEED(60053, "单笔卖出数量超过了限制", "The number of the single sales over the limit"),
	TRADE_ORDER_SELL_PRICE_TOOLOW(60054, "挂单价格超过了限制", "The sold price over the limit"),
	TRADE_ORDER_STATUS_NOTALLOWEDTOINTERVENE(60055, "当前状态不允许介入", "The current status is not allowed to intervene"),
	TRADE_IS_CLOSED(60056, "交易已休市！", "Trading closed"),
	
	// 特殊规则受限时提示的 错误信息
	TRADE_NETWORK_ANOMALY(60057, "网络异常请稍后再试", "Network anomalies, please try again later"),
	TRADE_ORDER_WASPURCHASED(60058, "订单已被购买", "Order has been purchased"),
	TRADE_ORDER_FAILED_PURCHASE(60059, " 购买失败请联系客服", "Purchase failure please contact customer service"),
	TRADE_ORDER_WASROLLBACK(60060, "卖家已撤单", "The seller has withdrawn"),
	TRADE_ORDER_LIST_ISFULL(60061, "卖出列表已满请稍后尝试", "The sell list is full Please try later"),
	TRADE_ORDER_FAILLED_TOSELL(60062, "卖出失败请联系客服", "If the failure to sell please contact customer service"),
	TRADE_ORDER_TOSELL_ANOMALY(60063, "卖出异常请检查卖出参数", "Selling an exception Please check the selling parameters"),
	
	TRADE_UNBOUND_BANK_CARD(60064, "未绑定银行卡", "Unbound bank card"),
	
	WALLTE_ADDRESS_HASBINDED(60065, "钱包地址已被占用！请重试", "Wallet address has been occupied! Please try again"),
	WALLTE_NO_ADDRESS_AVAILABLE(60066, "没有可用地址！", "No address available"),
	TRADE_SAME_CURRENCY_ERROR(60067, "相同币种不允许交易！", "The same currency is not allowed to trade"),
	
	CREDIT_SCORE_VALUE_ERROR(60068, "信用最大值必须大于信用最小值！", "The credit maximum must be greater than the credit minimum"),

	EDIT_WALLET_OUT_ADDR_ERROR(60069, "修改出金地址错误！", "EDIT_WALLET_OUT_ADDR_ERROR"),
	
	TRADE_IS_NOT_OPEN(60070, "当前兑换交易未开放", "The current exchange transaction is not open"),
	FILE_TYPE_UPLOAD_WRONG(60071, "上传文件类型错误", "Wrong upload file type"),
	PLATFORM_AOUNT_IS_NOT_ENOUGH(60072, "系统兑换量已达上限", "System exchange has reached the limit"),
	TRADE_EXCHANGE_OUT_LIMIT(60073, "您当前兑换量已达上限", "Your current exchange has reached the limit"),
	TRADE_EXCHANGE_RESTRICT(60074, "系统兑换交易限制，请稍后重试", "System exchange trading restrictions, please try again later"),
	TRADE_EXCHANGE_IS_TIME_OUT(60075, "订单已过期", "Orders have expired"),
	TRADE_EXCHANGE_SYSCOIN_AMCOUNT_NOT_LESS_ZERO(60076, "兑换量不能小于等于0", "Exchange amount can not be less than or equal to 0"),
	TRADE_EXCHANGE_LESS_UNDER_LIMIT(60077, "您当前兑换量小于兑换下限", "Your current exchange amount is less than the exchange limit"),
	
	

	//转账相关
	//TRADE_USERINFO_AUTH_NOTPASSED
	//TRADE_USER_VERIFYPAYPWD_NOTPASSED
	//TRADE_USERINFO_AUTH_NOTPASSED
	//TRADE_USER_BALANCE_NOTENOUGH,
	OUT_WALLET_NOT_EXIST(70001, "钱包出金地址错误", "The withdraw wallet doesn't exist"),
	INTO_WALLET_NOT_EXIST(70002, "钱包入金错误或币种不匹配", "The destination wallet doesn't exist or got wrong coins type"),
	PLATFORM_WALLET_NOT_EXIST(70003, "平台钱包错误或币种不匹配", "The platform wallet doesn't exist or got wrong coins type"),
	TRADE_USER_TRANSFER_MONEY_FAIL(70004, "转账失败", "Transfer failed"),
	TRADE_USER_TRANSFER_MONEY_ORDER_CANT_BE_NULL(70005, "转账流水号不能为空", "Please enter correct transfer SN"),
	TRADE_USER_TRANSFER_MONEY_ORDER_NOTEXIST(70006, "转账单生成错误", "Transfer bill created failed"),
	TRADE_USER_TRANSFER_MONEY_ORDER_THISSTATUS(70007, "转账单不是待处理状态", "The transfer bill doesn’t pending state"),
	TRADE_USER_TRANSFER_MONEY_AMOUNT_CANT_BE_NULL(70008, "转账金额不能为空", "Please enter correct transfer amount"),
	TRADE_PLATFORM_CANT_BE_NULL(70009, "系统平台参数不能为空", "Please enter correct parameter of system platform"),
	TRADE_PLATFORM_VERSION_NOT_EXIST(70010, "已经是最新版本", "There is the latest version of the platform"),
	LOGIN_TOKEN_CANT_BE_NULL(70011, "登录token不能为空", "Please enter correct login of Token"),
	REFRESH_TOKEN_CANT_BE_NULL(70012, "刷新token不能为空", "Please enter correct refresh of Token"),
	LOGIN_TOKEN_IS_EXPIRED_OR_INVALID(70013, "登录token过期或无效", "The login Token is invalid"),
	REFRESH_TOKEN_IS_EXPIRED_OR_INVALID(70014, "刷新token过期或无效", "The refresh token invalid"),
	UUID_CANT_BE_NULL(70015, "设备唯一标识不能为空", "The device's unique identifier can‘t be empty"),
	ADD_TOKEN_IS_FAIL(70016, "获取token失败", "Got Token failed"),
	UPDATE_TOKEN_IS_FAIL(70017, "更新token失败", "Update Token failed"),
	DELETE_TOKEN_IS_FAIL(70018, "删除token失败", "Delete Token failed"),
	AMOUNT_IS_GREATER_THAN_ONE(70019, "转账金额大于1", "The amount of tranfer must more than 1"),
	TRANSFER_MONEY_OFF(70020, "转账功能关闭", "Transfer function closed"),
	TRADE_CANNOT_TRANSFER_MONEY_YOUSELF(70021, "禁止转账给自己", "You can't tranfer to yourself"),
	USER_SIGN_TIMEOUT(70022, "签名过期", "Expiration of signature"),

	//系统充值、扣费相关
	RECORD_TYPE_MUST_BE_ELEVEN_OR_TWELVE(80001, "recordType参数必须是11或12", "RecordTyp parameter must be 11or12"),
	SYS_RECHARGE_RECORD_FAIL(80002, "系统充值失败", "Recharge failed"),
	QUANTITY_IS_GREATER_THAN_ZERO(80003, "充值数量大于0", "Recharge amount grater than 1"),
	SYS_RECHARGE_RECORD_GREATER_ONE_MIN(80004, "充值间隔需大于1分钟", "The recharge interval should be greater than 1 minute"),
	SYS_DEDUCTION_RECORD_FAIL(80005, "系统扣减失败", "System deductions failed"),
	SYS_RECHARGE_RECORD_FAIL_BALANCE_INSUFFICIENT(80006, "平台钱包余额不足", "Insufficient platform wallet balance"),

	//excel 导入excel
	ADDRESS_NOT_AVAILABLE(90001,"钱包地址检查后, 可用地址数量为0","There is no available wallet address"),

	//score积分兑换
	TP_INTEGRAL_WALLET_ADD_ERROR(100001,"第三方积分钱包添加失败","TP_INTEGRAL_WALLET_ADD_ERROR"),
	TP_INTEGRAL_WALLET_NOT_EXIST(100002,"积分钱包不存在","TP_INTEGRAL_WALLET_NOT_EXIST"),
	TRADE_USER_SCORE_FAIL(100003,"积分兑换失败","TRADE_USER_SCORE_FAIL"),
	TP_INTEGRAL_WALLET_FAIL(100004,"积分钱包出/入账失败","TRADE_USER_SCORE_ADD_FAIL"),
	TP_INTEGRAL_ORDER_NOT_EXIST(100005,"积分出/入帐单不存在","TP_INTEGRAL_ORDER_NOT_EXIST"),
	TP_INTEGRAL_ORDER_FAIL(100006,"积分出/入帐单失败","TP_INTEGRAL_ORDER__FAIL"),
	TP_INTEGRAL_WALLET_WITHDRAWALADDR_EXIST(100007, "积分钱包转出地址已绑定", "This mail is exist already"),
	TP_INTEGRAL_EXCHANGE_CLOSED(100008,"兑换交易已关闭","Exchange closed"),
	
	
	
	
	SAFE_CENTER_NOTBAND_HZ(110000,"未绑定互助账号","Unbound mutual aid account"),
	SAFE_CENTER_ACOUNTSBINED(110001,"互助账号或makeys账号已被绑定","Mutual help account or makeys account has been bound"),
	SAFE_CENTER_BINDFAIL(110003,"绑定失败","Binding failed"),
	SAFE_CENTER_NOTBAND_MAKEYS(110004,"未绑定makeys账号","No binding makeys account"),
	SAFE_CENTER_USERHASLOGGED(110005,"用户已登录","User is logged in"),
	SAFE_CENTER_ISBANDMAKEYS(110006,"您已绑定makeys当前操作无效","You have bound makeys The current operation is invalid"),
	SAFE_CENTER_USEMAKEYSLOGIN(110007,"请使用makeys登录","Please use makeys login"),
	SAFE_CENTER_NOTBAND_APP(110008,"请到Her-mest交易平台网页端注册或绑定帐号","Please register in Her-mest or binding Makeys account"),
	SAFE_CENTER_NOWBINDCHANGEOVER(110009,"您当前绑定操作错误累积超过限定次数，请稍后尝试","Your current binding operation error accumulated more than a limited number of times, please try later"),
	SAFE_CENTER_MAKEYSHZNOTSAMEUSER(110010,"请使用Her-mest账号同名的makeys账号进行绑定如：123456789@qeveworld.com","Please use Her-mest account with the same name makeys account binding as: 123456789@qeveworld.com"),

	CONTRACT_WALLET_HASH_IS_EXIST(120000, "合约钱包hash重复", "CONTRACT_WALLET_BATCH_ERROR");*/
	
	private MessageInfoConstant() {}
	
	MessageInfoConstant(Integer code, String msg_cn, String msg_en){
		this.code = code;
		this.msgZh = msg_cn;
		this.msgEn = msg_en;
	}
	
	private Integer code;
	
	private String msgZh;
	
	private String msgEn;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
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

}
