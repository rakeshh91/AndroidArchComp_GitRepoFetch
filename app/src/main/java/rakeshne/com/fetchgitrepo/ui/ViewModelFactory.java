package rakeshne.com.fetchgitrepo.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import rakeshne.com.fetchgitrepo.data.GitHubRepository;

public final class ViewModelFactory implements ViewModelProvider.Factory {

    private GitHubRepository repository;

    public ViewModelFactory(GitHubRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public ViewModel create(@NonNull Class modelClass) {
        if (modelClass.isAssignableFrom(SearchRepositoriesViewModel.class)) {
            return (ViewModel) new SearchRepositoriesViewModel(this.repository);
        } else {
            try {
                throw (Throwable) (new IllegalArgumentException("Unknown ViewModel class"));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
        }
    }
}
