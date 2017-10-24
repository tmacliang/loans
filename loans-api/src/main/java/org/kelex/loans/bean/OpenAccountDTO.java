package org.kelex.loans.bean;

import org.kelex.loans.enumeration.CurrencyCodeEnum;

import java.io.Serializable;

/**
 * Created by hechao on 2017/8/31.
 */
public class OpenAccountDTO implements OpenAccountRequest, Serializable{
    private static final long serialVersionUID = 6481774584483414083L;
    private String productCode;
    private String moduleCode;
    private String memberId;
    private String memberType;
    private String mobilePhone;
    private CurrencyCodeEnum currencyCode;

    @Override
    public CurrencyCodeEnum getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCodeEnum(CurrencyCodeEnum currencyCode) {
        this.currencyCode = currencyCode;
    }

    private CurrencyCodeEnum currencyCodeEnum;

    @Override
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    @Override
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
