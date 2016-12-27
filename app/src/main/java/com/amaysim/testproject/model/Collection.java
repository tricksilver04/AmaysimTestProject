package com.amaysim.testproject.model;

import com.orm.SugarRecord;

/**
 * Created by korn on 12/27/2016.
 */

public class Collection extends SugarRecord {

    private String accounts;
    private String subscription;
    private String products;
    private String services;


    public String getAccounts() {
        return accounts;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
}
