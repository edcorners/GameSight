
package com.eddev.android.gamesight.client.giantbomb.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Publisher {

    @SerializedName("api_detail_url")
    @Expose
    private String apiDetailUrl;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("site_detail_url")
    @Expose
    private String siteDetailUrl;

    /**
     * 
     * @return
     *     The apiDetailUrl
     */
    public String getApiDetailUrl() {
        return apiDetailUrl;
    }

    /**
     * 
     * @param apiDetailUrl
     *     The api_detail_url
     */
    public void setApiDetailUrl(String apiDetailUrl) {
        this.apiDetailUrl = apiDetailUrl;
    }

    /**
     * 
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The siteDetailUrl
     */
    public String getSiteDetailUrl() {
        return siteDetailUrl;
    }

    /**
     * 
     * @param siteDetailUrl
     *     The site_detail_url
     */
    public void setSiteDetailUrl(String siteDetailUrl) {
        this.siteDetailUrl = siteDetailUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(apiDetailUrl).append(id).append(name).append(siteDetailUrl).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Publisher) == false) {
            return false;
        }
        Publisher rhs = ((Publisher) other);
        return new EqualsBuilder().append(apiDetailUrl, rhs.apiDetailUrl).append(id, rhs.id).append(name, rhs.name).append(siteDetailUrl, rhs.siteDetailUrl).isEquals();
    }

}
