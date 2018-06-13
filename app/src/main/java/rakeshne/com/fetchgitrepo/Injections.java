package rakeshne.com.fetchgitrepo;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import java.util.concurrent.Executors;

import rakeshne.com.fetchgitrepo.api.APIBuilder;
import rakeshne.com.fetchgitrepo.data.GitHubRepository;
import rakeshne.com.fetchgitrepo.db.GithubLocalCache;
import rakeshne.com.fetchgitrepo.db.RepoDatabase;
import rakeshne.com.fetchgitrepo.ui.ViewModelFactory;

public final class Injections {
    private static GithubLocalCache provideCache(Context context) {
        RepoDatabase repoDatabase = RepoDatabase.getInstance(context);
        return new GithubLocalCache(repoDatabase.repoDao(), Executors.newSingleThreadExecutor());
    }

    private static GitHubRepository provideGitHubRepository(Context context) {
        return new GitHubRepository(APIBuilder.create(), provideCache(context));
    }

    public static ViewModelProvider.Factory provideViewModelFactory(Context context) {
        return new ViewModelFactory(provideGitHubRepository(context));
    }
}
