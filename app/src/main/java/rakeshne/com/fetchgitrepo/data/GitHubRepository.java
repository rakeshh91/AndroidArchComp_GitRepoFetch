package rakeshne.com.fetchgitrepo.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rakeshne.com.fetchgitrepo.api.GitHubService;
import rakeshne.com.fetchgitrepo.api.RepoSearchResponse;
import rakeshne.com.fetchgitrepo.db.GithubLocalCache;
import rakeshne.com.fetchgitrepo.model.Repo;
import rakeshne.com.fetchgitrepo.model.RepoSearchResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GitHubRepository {

    private GithubLocalCache cache;
    private GitHubService service;
    private static final int NETWORK_PAGE_SIZE = 50;
    private static final String IN_QUALIFIER = "in:name,description";

    public GitHubRepository(GitHubService service, GithubLocalCache cache) {
        this.cache = cache;
        this.service = service;
    }

    private int lastRequestedPage = 1;
    private MutableLiveData<String> networkErrors = new MutableLiveData<>();
    private boolean isRequestInProgress = false;

    public RepoSearchResult search(String query) {
        Log.d("GithubRepository", "New query: " + query);
        lastRequestedPage = 1;
        requestAndSaveData(query);

        LiveData<List<Repo>> data = cache.reposByName(query);
        return new RepoSearchResult(data, networkErrors);
    }

    public void requestMore(String query) {
        requestAndSaveData(query);
    }

    private void requestAndSaveData(String query) {
        if (isRequestInProgress) return;

        isRequestInProgress = true;

        String apiQuery = query + IN_QUALIFIER;
        service.searchRepos(apiQuery, lastRequestedPage, NETWORK_PAGE_SIZE).enqueue(new Callback<RepoSearchResponse>() {
            @Override
            public void onResponse(Call<RepoSearchResponse> call, Response<RepoSearchResponse> response) {
                Log.d("GitHubRepository", "Got a reponse " + response);
                if (response.isSuccessful()) {
                    RepoSearchResponse repoSearchResponse = response.body();
                    List<Repo> data;
                    if (repoSearchResponse != null) {
                        data = repoSearchResponse.getItems();
                    } else {
                        data = new ArrayList<Repo>();
                    }
                    cache.insert(data);
                    lastRequestedPage++;
                    isRequestInProgress = false;
                } else {
                    networkErrors.postValue(response.errorBody() == null ? "Unknown Error" : response.errorBody().toString());
                    isRequestInProgress = false;
                }
            }

            @Override
            public void onFailure(Call<RepoSearchResponse> call, Throwable t) {
                Log.d("GitHubRepository", "Fail to get data");
                networkErrors.postValue(t.getMessage());
            }
        });

    }

}
