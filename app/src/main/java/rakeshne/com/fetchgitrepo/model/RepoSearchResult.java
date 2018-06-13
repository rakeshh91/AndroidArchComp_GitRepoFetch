package rakeshne.com.fetchgitrepo.model;

import android.arch.lifecycle.LiveData;

import java.util.List;

public final class RepoSearchResult {

    private final LiveData<List<Repo>> data;
    private final LiveData<String> networkErrors;

    public RepoSearchResult(LiveData<List<Repo>> data, LiveData<String> networkErrors) {
        this.data = data;
        this.networkErrors = networkErrors;
    }

    public LiveData<List<Repo>> getSearchResultData() {
        return this.data;
    }

    public LiveData<String> getNetworkErrors() {
        return this.networkErrors;
    }

    public String toString() {
        return "RepoSearchResult(data=" + this.data + ", networkErrors=" + this.networkErrors + ")";
    }

    public int hashCode() {
        return (this.data != null ? this.data.hashCode() : 0) * 31 + (this.networkErrors != null ? this.networkErrors.hashCode() : 0);
    }

}
