package rakeshne.com.fetchgitrepo.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {

    @GET("search/repositories?sort=stars")
    Call<RepoSearchResponse> searchRepos(@Query("q") String query, @Query("page") int page, @Query("per_page") int itemsPerPage);
}
