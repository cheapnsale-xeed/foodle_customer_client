package com.xeed.cheapnsale.service.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

public class Payment implements Serializable {
    @SerializedName("impUid")
    String impUid;

    @SerializedName("merchantUid")
    String merchantUid;

    @SerializedName("payMethod")
    String payMethod;

    @SerializedName("pgProvider")
    String pgProvider;

    @SerializedName("pgTid")
    String pgTid;

    @SerializedName("escrow")
    boolean escrow;

    @SerializedName("applyNum")
    String applyNum;

    @SerializedName("cardName")
    String cardName;

    @SerializedName("cardQuota")
    int cardQuota;

    @SerializedName("vbankName")
    String vbankName;

    @SerializedName("vbankNum")
    String vbankNum;

    @SerializedName("vbankHolder")
    String vbankHolder;

    @SerializedName("vbankDate")
    long vbankDate;

    @SerializedName("name")
    String name;

    @SerializedName("amount")
    BigDecimal amount;

    @SerializedName("cancelAmount")
    BigDecimal cancelAmount;

    @SerializedName("buyerName")
    String buyerName;

    @SerializedName("buyerEmail")
    String buyerEmail;

    @SerializedName("buyerTel")
    String buyerTel;

    @SerializedName("buyerAddr")
    String buyerAddr;

    @SerializedName("buyerPostcode")
    String buyerPostcode;

    @SerializedName("customData")
    String customData;

    @SerializedName("status")
    String status;

    @SerializedName("paidAt")
    long paidAt;

    @SerializedName("failedAt")
    long failedAt;

    @SerializedName("cancelledAt")
    long cancelledAt;

    @SerializedName("failReason")
    String failReason;

    @SerializedName("cancelReason")
    String cancelReason;

    @SerializedName("receiptUrl")
    String receiptUrl;

    public String getImpUid() {
        return impUid;
    }

    public void setImpUid(String impUid) {
        this.impUid = impUid;
    }

    public String getMerchantUid() {
        return merchantUid;
    }

    public void setMerchantUid(String merchantUid) {
        this.merchantUid = merchantUid;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getPgProvider() {
        return pgProvider;
    }

    public void setPgProvider(String pgProvider) {
        this.pgProvider = pgProvider;
    }

    public String getPgTid() {
        return pgTid;
    }

    public void setPgTid(String pgTid) {
        this.pgTid = pgTid;
    }

    public boolean isEscrow() {
        return escrow;
    }

    public void setEscrow(boolean escrow) {
        this.escrow = escrow;
    }

    public String getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(String applyNum) {
        this.applyNum = applyNum;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCardQuota() {
        return cardQuota;
    }

    public void setCardQuota(int cardQuota) {
        this.cardQuota = cardQuota;
    }

    public String getVbankName() {
        return vbankName;
    }

    public void setVbankName(String vbankName) {
        this.vbankName = vbankName;
    }

    public String getVbankNum() {
        return vbankNum;
    }

    public void setVbankNum(String vbankNum) {
        this.vbankNum = vbankNum;
    }

    public String getVbankHolder() {
        return vbankHolder;
    }

    public void setVbankHolder(String vbankHolder) {
        this.vbankHolder = vbankHolder;
    }

    public long getVbankDate() {
        return vbankDate;
    }

    public void setVbankDate(long vbankDate) {
        this.vbankDate = vbankDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCancelAmount() {
        return cancelAmount;
    }

    public void setCancelAmount(BigDecimal cancelAmount) {
        this.cancelAmount = cancelAmount;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerTel() {
        return buyerTel;
    }

    public void setBuyerTel(String buyerTel) {
        this.buyerTel = buyerTel;
    }

    public String getBuyerAddr() {
        return buyerAddr;
    }

    public void setBuyerAddr(String buyerAddr) {
        this.buyerAddr = buyerAddr;
    }

    public String getBuyerPostcode() {
        return buyerPostcode;
    }

    public void setBuyerPostcode(String buyerPostcode) {
        this.buyerPostcode = buyerPostcode;
    }

    public String getCustomData() {
        return customData;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(long paidAt) {
        this.paidAt = paidAt;
    }

    public long getFailedAt() {
        return failedAt;
    }

    public void setFailedAt(long failedAt) {
        this.failedAt = failedAt;
    }

    public long getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(long cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }
}
