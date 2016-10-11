
package com.eddev.android.gamesight.client.giantbomb.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class Image {

    @SerializedName("icon_url")
    @Expose
    private String iconUrl;
    @SerializedName("medium_url")
    @Expose
    private String mediumUrl;
    @SerializedName("screen_url")
    @Expose
    private String screenUrl;
    @SerializedName("small_url")
    @Expose
    private String smallUrl;
    @SerializedName("super_url")
    @Expose
    private String superUrl;
    @SerializedName("thumb_url")
    @Expose
    private String thumbUrl;
    @SerializedName("tiny_url")
    @Expose
    private String tinyUrl;

    /**
     * 
     * @return
     *     The iconUrl
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * 
     * @param iconUrl
     *     The icon_url
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * 
     * @return
     *     The mediumUrl
     */
    public String getMediumUrl() {
        return mediumUrl;
    }

    /**
     * 
     * @param mediumUrl
     *     The medium_url
     */
    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    /**
     * 
     * @return
     *     The screenUrl
     */
    public String getScreenUrl() {
        return screenUrl;
    }

    /**
     * 
     * @param screenUrl
     *     The screen_url
     */
    public void setScreenUrl(String screenUrl) {
        this.screenUrl = screenUrl;
    }

    /**
     * 
     * @return
     *     The smallUrl
     */
    public String getSmallUrl() {
        return smallUrl;
    }

    /**
     * 
     * @param smallUrl
     *     The small_url
     */
    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    /**
     * 
     * @return
     *     The superUrl
     */
    public String getSuperUrl() {
        return superUrl;
    }

    /**
     * 
     * @param superUrl
     *     The super_url
     */
    public void setSuperUrl(String superUrl) {
        this.superUrl = superUrl;
    }

    /**
     * 
     * @return
     *     The thumbUrl
     */
    public String getThumbUrl() {
        return thumbUrl;
    }

    /**
     * 
     * @param thumbUrl
     *     The thumb_url
     */
    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    /**
     * 
     * @return
     *     The tinyUrl
     */
    public String getTinyUrl() {
        return tinyUrl;
    }

    /**
     * 
     * @param tinyUrl
     *     The tiny_url
     */
    public void setTinyUrl(String tinyUrl) {
        this.tinyUrl = tinyUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(iconUrl).append(mediumUrl).append(screenUrl).append(smallUrl).append(superUrl).append(thumbUrl).append(tinyUrl).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Image) == false) {
            return false;
        }
        Image rhs = ((Image) other);
        return new EqualsBuilder().append(iconUrl, rhs.iconUrl).append(mediumUrl, rhs.mediumUrl).append(screenUrl, rhs.screenUrl).append(smallUrl, rhs.smallUrl).append(superUrl, rhs.superUrl).append(thumbUrl, rhs.thumbUrl).append(tinyUrl, rhs.tinyUrl).isEquals();
    }

}
