package com.gaoan.forever.exception;

/**
 *
 * @Title: DotnarException.java
 *
 * @Package com.xmniao.exception
 *
 * @Description: 分账系统异常
 *
 * @author huangxiaobin
 *
 * @date 2016年7月15日 上午11:32:31
 *
 * @version V1.0
 */
public class DotnarException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int id;// mapper 报的异常 编号为2，其他系统运行异常目前编号为1,调用接口异常编号为3

	private String className;

	private String methodName;

	private int rowNum;

	private String message;

	/**
	 * 异常详情
	 */
	private String remark;

	public DotnarException() {
		super();
	}

	public DotnarException(String message) {
		super();
		this.message = message;
	}

	public DotnarException(int id, String message) {
		super();
		this.id = id;
		this.message = message;
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		for (int i = 0; i < stack.length; i++) {
			StackTraceElement ste = stack[i];
			this.className = ste.getClassName();
			this.methodName = ste.getMethodName();
			this.rowNum = ste.getLineNumber();
		}
	}

	/**
	 * 构造
	 * 
	 * @param id
	 *            异常编号 其他系统运行异常目前编号为1, DAO 报的异常 编号为2，调用接口异常编号为3
	 * @param message
	 *            提示信息
	 * @param remark
	 *            异常详情
	 */
	public DotnarException(int id, String message, String remark) {
		super();
		this.id = id;
		this.message = message;
		this.remark = remark;
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		for (int i = 0; i < stack.length; i++) {
			StackTraceElement ste = stack[i];
			this.className = ste.getClassName();
			this.methodName = ste.getMethodName();
			this.rowNum = ste.getLineNumber();
		}
	}

	public DotnarException(DotnarException e) {
		this.id = e.getId();
		this.message = e.getMessage();
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		for (int i = 0; i < stack.length; i++) {
			StackTraceElement ste = stack[i];
			this.className = ste.getClassName();
			this.methodName = ste.getMethodName();
			this.rowNum = ste.getLineNumber();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getMessage() {
		return message;
	}

	public DotnarException setMessage(String message) {
		this.message = message;
		return this;
	}

	@Override
	public String toString() {
		return "DotnarException:" + "\nDotnarException id:" + id + "\nDotnarException className:" + className
				+ "\nDotnarException methodName:" + methodName + "\nDotnarException rowNum:" + rowNum
				+ "\nDotnarException message:" + message + "\nDotnarException remark:" + remark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
