package pxm.com.pxm.source.models;

import java.io.Serializable;

/**
 * Created by dmtec on 2016-08-22.
 *
 */
public class Business implements Serializable{
    private String id,name,telephone,address;

    public Business() {
    }

    public Business(String id, String name, String telephone, String address) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
