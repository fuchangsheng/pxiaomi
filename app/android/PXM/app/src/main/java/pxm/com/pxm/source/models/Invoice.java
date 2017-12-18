package pxm.com.pxm.source.models;

import java.io.Serializable;

import pxm.com.pxm.source.utils.Constant;

/**
 * Created by dmtec on 2016/7/7.
 * the model of a invoice
 */
public class Invoice implements Serializable{
    /*
    id is used to distinguish an invoice
     */
    private String id;

    /*
    The invoice has two types:
        NORMAL --means normal invoice.
        SPECIAL --means special invoice.
     */
    private int type;

    /*
    The invoice should be given by a hotel
    and it has a hotel name.
     */
    private String businessName;

    /*
    The amount on the invoice.
     */
    private String  amount;

    /*
    The invoice has two results:
    SUCCESSFUL --means the invoice has been wrote successfully.
    FAILED --means the invoice failed
     */
    private int result;

    private int content;

    private float rate;

    /*
    The tax information used on the invoice
     */
    private TaxInformation taxInfo;

    public Invoice(String id, int type, String businessName, String amount, int result,int content,float rate,TaxInformation taxInfo) {
        this.id=id;
        this.type = type;
        this.businessName = businessName;
        this.amount = amount;
        this.result = result;
        this.content=content;
        this.rate=rate;
        this.taxInfo = taxInfo;
    }

    public Invoice() {
        this.id="0";
        this.type = Constant.NORMAL;
        this.businessName = "";
        this.amount = "0";
        this.result = Constant.FAILED;
        this.content=Constant.INVOICE_STAY;
        this.rate=0.17f;
        this.taxInfo = new TaxInformation();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public TaxInformation getTaxInfo() {
        return taxInfo;
    }

    public void setTaxInfo(TaxInformation taxInfo) {
        this.taxInfo = taxInfo;
    }
}
