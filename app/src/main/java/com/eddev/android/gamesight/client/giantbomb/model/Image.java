
package com.eddev.android.gamesight.client.giantbomb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
        return "Image{" +
                "iconUrl='" + iconUrl + '\'' +
                ", mediumUrl='" + mediumUrl + '\'' +
                ", screenUrl='" + screenUrl + '\'' +
                ", smallUrl='" + smallUrl + '\'' +
                ", superUrl='" + superUrl + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", tinyUrl='" + tinyUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;

        Image image = (Image) o;

        if (iconUrl != null ? !iconUrl.equals(image.iconUrl) : image.iconUrl != null) return false;
        if (mediumUrl != null ? !mediumUrl.equals(image.mediumUrl) : image.mediumUrl != null)
            return false;
        if (screenUrl != null ? !screenUrl.equals(image.screenUrl) : image.screenUrl != null)
            return false;
        if (smallUrl != null ? !smallUrl.equals(image.smallUrl) : image.smallUrl != null)
            return false;
        if (superUrl != null ? !superUrl.equals(image.superUrl) : image.superUrl != null)
            return false;
        if (thumbUrl != null ? !thumbUrl.equals(image.thumbUrl) : image.thumbUrl != null)
            return false;
        return tinyUrl != null ? tinyUrl.equals(image.tinyUrl) : image.tinyUrl == null;

    }

    @Override
    public int hashCode() {
        int result = iconUrl != null ? iconUrl.hashCode() : 0;
        result = 31 * result + (mediumUrl != null ? mediumUrl.hashCode() : 0);
        result = 31 * result + (screenUrl != null ? screenUrl.hashCode() : 0);
        result = 31 * result + (smallUrl != null ? smallUrl.hashCode() : 0);
        result = 31 * result + (superUrl != null ? superUrl.hashCode() : 0);
        result = 31 * result + (thumbUrl != null ? thumbUrl.hashCode() : 0);
        result = 31 * result + (tinyUrl != null ? tinyUrl.hashCode() : 0);
        return result;
    }
}
