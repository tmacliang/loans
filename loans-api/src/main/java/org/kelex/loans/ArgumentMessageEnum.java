package org.kelex.loans;

/**
 * Created by hechao on 2017/9/1.
 */
public enum ArgumentMessageEnum {
    UNKNOWN_MESSAGE(50000001),
    ERROR_MIN_VALUE(40000001),
    ERROR_MAX_VALUE(40000002),

    ERROR_PRODUCT_NOT_FOUND(50000100),

    ERROR_CURRENCY_CODE_ILLEGAL(50000101, 1, 4),


    ERROR_PRODUCT_CODE_ISNULL(50001011),
    ERROR_PRODUCT_CODE_OUT_OF_RANGE(50001012, 3, 8),

    ERROR_MODULE_CODE_ISNULL(50001021),
    ERROR_MODULE_CODE__OUT_OF_RANGE(50001022, 3, 8),

    ERROR_CUSTOMER_DUPLICATE(50002001),

    ERROR_MEMBER_ID_ISNULL(50002010),
    ERROR_MEMBER_ID_OUT_OF_RANGE(50002011, 8, 64),

    ERROR_MEMBER_TYPE_ISNULL(50002021),
    ERROR_MEMBER_TYPE_OUT_OF_RANGE(50002022, 3, 5),

    ERROR_MOBILE_PHONE_ISNULL(50002031),
    ERROR_MOBILE_PHONE_OUT_OF_RANGE(50002032, 8, 18),

    ERROR_ACT_PROC_CTRL_ISNULL(50003010),

    ERROR_ACCOUNT_ISNULL(50004000),

    ERROR_MERCHANTNAME_ISNULL(50004001),
    ERROR_COMMODITYCODE_ISNULL(50004002),
    ERROR_RETAILORDERNO_ISNULL(50004003),
    ERROR_RETAILAMOUNT_ISNULL(50004004),
    ERROR_TOTALTERMS_ISNULL(50004005),
    ERROR_CURRENCYCODE_ISNULL(50004006),

    ERROR_PAYMENTORDERNO_ISNULL(50004007),
    ERROR_PAYMENTTYPE_ISNULL(50004008),
    ERROR_PAYMENTAMOUNT_ISNULL(50004009),


    ERROR_PERMANENT_ISNULL(50004010),
    ERROR_CREDITLIMIT_ISNULL(50004011),
    ERROR_EFFECTIVESTARTDATE_ISNULL(50004012),
    ERROR_EFFECTIVEENDDATE_ISNULL(50004013),

    ERROR_ACTIONCODE_ISNULL(50004014);


    public final int ERROR_CODE;
    public final String MESSAGE_CODE;
    public final Number MIN;
    public final Number MAX;

    ArgumentMessageEnum(int code) {
        this(code, null, null);
    }

    ArgumentMessageEnum(int code, Number min, Number max) {
        this.ERROR_CODE =code;
        this.MESSAGE_CODE =this.name();
        this.MIN=min;
        this.MAX=max;
    }
}
