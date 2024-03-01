package com.epam.victor;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DefaultHitOrder {

    public static final String YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private Integer hitOrder;

    private Date hitOrderDate;

    private Map<String, String> customOrderProperties = new HashMap();

    public DefaultHitOrder(Integer hitOrder, Date hitOrderDate) {
        this.hitOrder = hitOrder;
        this.hitOrderDate = hitOrderDate;
    }

    public DefaultHitOrder() {
    }

    public Integer getHitOrder() {
        return hitOrder;
    }

    public void setHitOrder(Integer hitOrder) {
        this.hitOrder = hitOrder;
    }

    public Date getHitOrderDate() {
        return hitOrderDate;
    }

    public void setHitOrderDate(Date hitOrderDate) {
        this.hitOrderDate = hitOrderDate;
    }

    public Map<String, String> getCustomOrderProperties() {
        return customOrderProperties;
    }

    public void setCustomOrderProperties(Map<String, String> customOrderProperties) {
        this.customOrderProperties = customOrderProperties;
    }

}