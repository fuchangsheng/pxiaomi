package pxm.com.pxm.source.models;

import java.io.Serializable;

import pxm.com.pxm.source.utils.Constant;

/**
 * Created by dmtec on 2016/7/7.
 * the model of tax information
 */
public class TaxInformation implements Serializable {
    /**
     * id is used to distinguish taxinformation
     */
    private String id;
    /**
     * The title of a invoice.
     */
    private String invTitle;
    /**
     * The tax number
     */
    private String taxNum;
    /**
     * The bank in which you have a account
     */
    private String bank;
    /**
     * The account number in the bank
     */
    private String bankAccount;
    /**
     * The address
     */
    private String address;
    /**
     * The telephone number
     */
    private String tel;

    public TaxInformation() {
        this.id="0";
        this.invTitle = "";
        this.taxNum ="";
        this.bank = "";
        this.bankAccount ="";
        this.address = "";
        this.tel = "";

    }

    /**
     * Constructor
     * @param invTitle The title of a invoice.
     * @param taxNum The tax number
     * @param bank The bank in which you have a account
     * @param bankAccount The account number in the bank
     * @param address The address
     * @param tel The telephone number
     */
    public TaxInformation(String id,String invTitle, String taxNum, String bank, String bankAccount, String address, String tel) {
        this.id=id;
        this.invTitle = invTitle;
        this.taxNum = taxNum;
        this.bank = bank;
        this.bankAccount = bankAccount;
        this.address = address;
        this.tel = tel;
    }

    /**
     * Constructor
     * @param invTitle The title of a invoice.
     */
    public TaxInformation(String invTitle) {
        this.invTitle = invTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvTitle() {
        return invTitle;
    }

    public void setInvTitle(String invTitle) {
        this.invTitle = invTitle;
    }

    public String getTaxNum() {
        return taxNum;
    }

    public void setTaxNum(String taxNum) {
        this.taxNum = taxNum;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
