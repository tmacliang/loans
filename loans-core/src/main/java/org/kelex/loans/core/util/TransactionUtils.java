package org.kelex.loans.core.util;

import org.kelex.loans.core.entity.*;
import org.kelex.loans.core.enumeration.FeePayMethod;
import org.kelex.loans.core.enumeration.FeeRateType;
import org.kelex.loans.core.enumeration.RemAdjustMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by hechao on 2017/9/21.
 */
public abstract class TransactionUtils {

    private static final Integer MAX_TERMS = 99;

    public static String toRetailPlanId(int totalTerms) {
        if (totalTerms < 0 || totalTerms > MAX_TERMS) {
            throw new IllegalArgumentException("0 < totalTerms < 99");
        }
        return "RIP" + (totalTerms < 10 ? "0" : "") + Integer.toString(totalTerms);
    }

    public final static InstalmentSample getInstalmentSample(
            BigDecimal amount
            , AccountEntity account
            , PlanProfileEntity planProfile
            , PlanProcessCtrlEntity planCtrl
            , TxnProcessCtrlEntity txnCtrl
            , CurrProcessCtrlEntity currCtrl
            , LocalDate businessDate) {

        if (amount.compareTo(BigDecimal.TEN) < 0) {
            throw new IllegalArgumentException("instalment amount < 10");
        }

        PlanProcessCtrlId planCtrlId = planCtrl.getId();
        if (!Objects.equals(planProfile.getPlanId(), planCtrlId.getPlanId())) {
            throw new IllegalArgumentException("planProfile.planId != planCtrl.planId");
        }

        if (!Objects.equals(account.getProductId(), planCtrlId.getProductId())
                || !Objects.equals(account.getActTypeId(), planCtrlId.getActTypeId())) {
            throw new IllegalArgumentException("account.project != planCtrl.project");
        }

        if (!Objects.equals(account.getProductId(), txnCtrl.getId().getProductId())
                || !Objects.equals(account.getActTypeId(), txnCtrl.getId().getActTypeId())) {
            throw new IllegalArgumentException("account.project != txnCtrl.project");
        }

        Integer planTerms = planProfile.getPlanTerms();
        if (planTerms < 1) {
            throw new IllegalArgumentException("terms > 0");
        }

        int precision = Math.abs(currCtrl.getPower());
        //计算分期金额
        InstalmentSample sample = new InstalmentSample();
        BigDecimal terms = new BigDecimal(planTerms);
        BigDecimal fixedAmt = amount.divide(terms, precision, BigDecimal.ROUND_DOWN);
        BigDecimal remAmt = amount.subtract(fixedAmt.multiply(terms)).add(fixedAmt);
        if (remAmt.compareTo(amount) == 0) {
            fixedAmt = BigDecimal.ZERO;
        }

        //设置样例
        RemAdjustMethod remMethod = planCtrl.getRemAdjustMethod();
        sample.setFixedTermAmt(fixedAmt);
        if (remMethod.equals(RemAdjustMethod.FRST)) {
            sample.setFirstTermAmt(remAmt);
            sample.setLastTermAmt(fixedAmt);
        } else if (remMethod.equals(RemAdjustMethod.LAST)) {
            sample.setFirstTermAmt(fixedAmt);
            sample.setLastTermAmt(remAmt);
        } else {
            throw new RuntimeException(remMethod.name() + " rem adjust method is no such");
        }

        //计算分期手续费率
        if (isFreeTxnFee(account, txnCtrl, businessDate)) {
            sample.setFeeRate(BigDecimal.ZERO);
        } else {
            FeeRateType rateType = account.getTxnFeeRateType();
            if (rateType == FeeRateType.NORM) {
                sample.setFeeRate(account.getTxnFeeRate());
                sample.setFeeRateTimes(terms);
            } else if (rateType == FeeRateType.FIX) {
                sample.setFeeRate(planCtrl.getTxnFeeRate());
                sample.setFeeRateTimes(BigDecimal.ONE);
            } else if (rateType == FeeRateType.LVL) {
                throw new RuntimeException("LVL method building...");
            } else {
                throw new RuntimeException(rateType.name() + " fee rate type is no such.");
            }
        }

        //计算分期手续费
        BigDecimal realFeeRate = sample.getFeeRateTimes().multiply(sample.getFeeRate());
        BigDecimal totalFee = amount.multiply(realFeeRate).setScale(precision, BigDecimal.ROUND_DOWN);
        BigDecimal fixedFee;
        BigDecimal firstFee;
        FeePayMethod payMethod = planCtrl.getFeePayMethod();
        if (payMethod == FeePayMethod.TERM) {
            fixedFee = totalFee.divide(terms, precision, BigDecimal.ROUND_DOWN);
            firstFee = totalFee.subtract(fixedFee.multiply(terms)).add(fixedFee);
        } else {
            firstFee = totalFee;
            fixedFee = BigDecimal.ZERO;
        }
        sample.setFirstTermFeeAmt(firstFee);
        sample.setFixedTermFeeAmt(fixedFee);
        sample.setLastTermFeeAmt(fixedFee);

        return sample;
    }

    /**
     * 是否免除账户的交易手续费
     *
     * @param account      账户
     * @param txnCtrl      交易控制
     * @param businessDate 交易时间
     * @return
     */
    public static boolean isFreeTxnFee(AccountEntity account, TxnProcessCtrlEntity txnCtrl, LocalDate businessDate) {
        TxnProcessCtrlId ctrlId = txnCtrl.getId();
        if (!Objects.equals(account.getProductId(), ctrlId.getProductId())
                || !Objects.equals(account.getActTypeId(), ctrlId.getActTypeId())) {
            throw new IllegalArgumentException("account and txnProcessCtrl not in a project");
        }
        if (account.getWaiveTxnFeeFlag()) {
            if (businessDate.isBefore(account.getWaiveTxnFeeEndDate()) && businessDate.isAfter(account.getWaiveTxnFeeStartDate())) {
                return true;
            }
        }
        return txnCtrl.getFeeCode() == null;
    }

    public static Map<String, BalCompValEntity> toMap(Collection<BalCompValEntity> collection) {
        Map<String, BalCompValEntity> map = new HashMap<>();
        for (BalCompValEntity entity : collection) {
            map.put(entity.getId().getBcpId(), entity);
        }
        return map;
    }
}
