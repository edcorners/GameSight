package com.eddev.android.gamersight.client.giantbomb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ed on 11/5/16.
 */

public class GBGameResponse extends GBResponse{
    @SerializedName("results")
    @Expose
    private GBGame results = new GBGame();

    /**
     *
     * @return
     *     The results
     */
    public GBGame getResults() {
        return results;
    }

    /**
     *
     * @param results
     *     The results
     */
    public void setResults(GBGame results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GBGameResponse that = (GBGameResponse) o;

        return results != null ? results.equals(that.results) : that.results == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GBGamesResponse{" +
                "results=" + results +
                '}';
    }
}
