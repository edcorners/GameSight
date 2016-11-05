package com.eddev.android.gamesight.client.giantbomb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ed on 11/5/16.
 */

public class GBGameResponse extends GBResponse{
    @SerializedName("results")
    @Expose
    private List<GBGame> results = new ArrayList<GBGame>();

    /**
     *
     * @return
     *     The results
     */
    public List<GBGame> getResults() {
        return results;
    }

    /**
     *
     * @param results
     *     The results
     */
    public void setResults(List<GBGame> results) {
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
        return "GBGameResponse{" +
                "results=" + results +
                '}';
    }
}
