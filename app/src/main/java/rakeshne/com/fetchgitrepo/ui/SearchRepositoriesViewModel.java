package rakeshne.com.fetchgitrepo.ui;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import rakeshne.com.fetchgitrepo.data.GitHubRepository;
import rakeshne.com.fetchgitrepo.model.Repo;
import rakeshne.com.fetchgitrepo.model.RepoSearchResult;

/**
 * ViewModel for the [SearchRepositoriesActivity] screen.
 * The ViewModel works with the [GithubRepository] to get the data.
 */
public class SearchRepositoriesViewModel extends ViewModel {

    private final GitHubRepository repository;
    private static final int VISIBLE_THRESHOLD = 5;

    private MutableLiveData<String> queryLiveData;
    private LiveData<RepoSearchResult> repoResult;
    private LiveData<List<Repo>> repos;
    public LiveData<String> networkErrors;


    public SearchRepositoriesViewModel(final GitHubRepository repository1) {
        this.repository = repository1;
        queryLiveData = new MutableLiveData<>();


        repoResult = Transformations.map(queryLiveData, new Function<String, RepoSearchResult>() {
            @Override
            public RepoSearchResult apply(String input) {
                return repository.search(input);
            }
        });

        repos = Transformations.switchMap(repoResult, new Function<RepoSearchResult, LiveData<List<Repo>>>() {
            @Override
            public LiveData<List<Repo>> apply(RepoSearchResult input) {
                return input.getSearchResultData();
            }
        });

        networkErrors = Transformations.switchMap(repoResult, new Function<RepoSearchResult, LiveData<String>>() {
            @Override
            public LiveData<String> apply(RepoSearchResult input) {
                return input.getNetworkErrors();
            }
        });
    }

    public LiveData<List<Repo>> getRepos() {
        return repos;
    }

    public LiveData<String> getNetworkErrors() {
        return networkErrors;
    }

    public void searchRepo(String queryString) {

        queryLiveData.postValue(queryString);
    }

    public void listScrolled(int visibleItemCount, int lastVisibleItemPosition, int totalItemCount) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            String immutableQuery = lastQueryValue();
            if (immutableQuery != null) {
                repository.requestMore(immutableQuery);
            }
        }
    }

    public String lastQueryValue() {
        return queryLiveData.getValue();
    }
}
