
package com.eddev.android.gamesight.client.giantbomb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public abstract class GBResponse {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("limit")
    @Expose
    private int limit;
    @SerializedName("offset")
    @Expose
    private int offset;
    @SerializedName("number_of_page_results")
    @Expose
    private int numberOfPageResults;
    @SerializedName("number_of_total_results")
    @Expose
    private int numberOfTotalResults;
    @SerializedName("status_code")
    @Expose
    private int statusCode;

    @SerializedName("version")
    @Expose
    private String version;

    /**
     * 
     * @return
     *     The error
     */
    public String getError() {
        return error;
    }

    /**
     * 
     * @param error
     *     The error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * 
     * @return
     *     The limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * 
     * @param limit
     *     The limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 
     * @return
     *     The offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * 
     * @param offset
     *     The offset
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * 
     * @return
     *     The numberOfPageResults
     */
    public int getNumberOfPageResults() {
        return numberOfPageResults;
    }

    /**
     * 
     * @param numberOfPageResults
     *     The number_of_page_results
     */
    public void setNumberOfPageResults(int numberOfPageResults) {
        this.numberOfPageResults = numberOfPageResults;
    }

    /**
     * 
     * @return
     *     The numberOfTotalResults
     */
    public int getNumberOfTotalResults() {
        return numberOfTotalResults;
    }

    /**
     * 
     * @param numberOfTotalResults
     *     The number_of_total_results
     */
    public void setNumberOfTotalResults(int numberOfTotalResults) {
        this.numberOfTotalResults = numberOfTotalResults;
    }

    /**
     * 
     * @return
     *     The statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * 
     * @param statusCode
     *     The status_code
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }



    /**
     * 
     * @return
     *     The version
     */
    public String getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "GBResponse{" +
                "error='" + error + '\'' +
                ", limit=" + limit +
                ", offset=" + offset +
                ", numberOfPageResults=" + numberOfPageResults +
                ", numberOfTotalResults=" + numberOfTotalResults +
                ", statusCode=" + statusCode +
                ", version='" + version + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GBResponse)) return false;

        GBResponse GBResponse = (GBResponse) o;

        if (limit != GBResponse.limit) return false;
        if (offset != GBResponse.offset) return false;
        if (numberOfPageResults != GBResponse.numberOfPageResults) return false;
        if (numberOfTotalResults != GBResponse.numberOfTotalResults) return false;
        if (statusCode != GBResponse.statusCode) return false;
        if (error != null ? !error.equals(GBResponse.error) : GBResponse.error != null) return false;
        return version != null ? version.equals(GBResponse.version) : GBResponse.version == null;

    }

    @Override
    public int hashCode() {
        int result = error != null ? error.hashCode() : 0;
        result = 31 * result + limit;
        result = 31 * result + offset;
        result = 31 * result + numberOfPageResults;
        result = 31 * result + numberOfTotalResults;
        result = 31 * result + statusCode;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
