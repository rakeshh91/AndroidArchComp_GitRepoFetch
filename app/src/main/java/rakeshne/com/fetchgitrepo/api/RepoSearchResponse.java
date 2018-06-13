package rakeshne.com.fetchgitrepo.api;

import com.google.gson.annotations.SerializedName;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import rakeshne.com.fetchgitrepo.model.Repo;

public final class RepoSearchResponse {
    @SerializedName("total_count")
    private final int total;

    @SerializedName("items")
    @NonNull
    private final List<Repo> items;

    @Nullable
    private final Integer nextPage;

    public int getTotal() {
        return total;
    }

    @NonNull
    public List<Repo> getItems() {
        return items;
    }

    @Nullable
    public Integer getNextPage() {
        return nextPage;
    }

    public RepoSearchResponse(int total, List<Repo> items, int nextPage) {
        this.items = items;
        this.total = total;
        this.nextPage = nextPage;
    }

    public String toString() {
        return "RepoSearchResponse(total=" + this.total + ", items=" + this.items + ", nextPage=" + this.nextPage + ")";
    }

    public int hashCode() {
        return (this.total * 31 + (this.items != null ? this.items.hashCode() : 0)) * 31 + (this.nextPage != null ? this.nextPage.hashCode() : 0);
    }

}
