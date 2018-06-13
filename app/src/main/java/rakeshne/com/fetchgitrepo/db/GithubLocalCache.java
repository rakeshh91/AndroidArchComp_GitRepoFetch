package rakeshne.com.fetchgitrepo.db;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;

import rakeshne.com.fetchgitrepo.model.Repo;

public class GithubLocalCache {

    private final RepoDao repoDao;
    private final Executor ioExecutor;

    public GithubLocalCache(RepoDao repoDao, Executor ioExecutor) {
        this.repoDao = repoDao;
        this.ioExecutor = ioExecutor;
    }

    public void insert(final List<Repo> repos) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("GithubLocalCache", "Inserting " + repos.size() + " repos");
                repoDao.insert(repos);
            }
        });
    }

    public LiveData<List<Repo>> reposByName(String name) {
        String query = '%' + name.replace(' ', '%');
        return repoDao.reposByName(query);
    }

}
