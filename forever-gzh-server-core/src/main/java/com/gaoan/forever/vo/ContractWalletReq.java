package com.gaoan.forever.vo;

/**
 * @author ym
 * @version Version 1.0
 */
public class ContractWalletReq {

    private Long timestamp;

    private String user;

    private Long blocknumber;

    private String contractaddress;

    private String sign;


    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getBlocknumber() {
        return blocknumber;
    }

    public void setBlocknumber(Long blocknumber) {
        this.blocknumber = blocknumber;
    }

    public String getContractaddress() {
        return contractaddress;
    }

    public void setContractaddress(String contractaddress) {
        this.contractaddress = contractaddress;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
