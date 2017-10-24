package org.kelex.loans.bean;

import org.kelex.loans.enumeration.CurrencyCodeEnum;

/**
 * Created by hechao on 2017/9/1.
 */
public interface OpenAccountRequest {
    /**
     * 开户者产品码
     * @return
     */
    public String getProductCode();

    /**
     * 开户者产品模块码
     * @return
     */
    public String getModuleCode();

    /**
     * 开户者在产品中的成员ID
     * @return
     */
    public String getMemberId();

    /**
     * 开户者在产品中的成员类型
     * @return
     */
    public String getMemberType();

    /**
     * 开户者手机号
     * @return
     */
    public String getMobilePhone();

    /**
     * 币种
     * @return
     */
    public CurrencyCodeEnum getCurrencyCodeEnum();

}
