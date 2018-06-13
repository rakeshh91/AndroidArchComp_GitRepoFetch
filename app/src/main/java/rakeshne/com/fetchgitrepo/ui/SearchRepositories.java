package rakeshne.com.fetchgitrepo.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import java.util.List;

import rakeshne.com.fetchgitrepo.Injections;
import rakeshne.com.fetchgitrepo.R;
import rakeshne.com.fetchgitrepo.model.Repo;

public class SearchRepositories extends AppCompatActivity {

    private SearchRepositoriesViewModel viewModel;
    private ReposAdapter adapter = new ReposAdapter();
    private RecyclerView list;
    private static final String LAST_SEARCH_QUERY = "last_search_query";
    private static final String DEFAULT_QUERY = "Android";
    private TextView search_repo_text;
    private String queryFromUser;
    private TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_repositories);

        viewModel = ViewModelProviders.of(this, Injections.provideViewModelFactory(this)).get(SearchRepositoriesViewModel.class);
        list = (RecyclerView) findViewById(R.id.list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        list.addItemDecoration(dividerItemDecoration);
        search_repo_text = (TextView) findViewById(R.id.search_repo);
        emptyList = (TextView) findViewById(R.id.emptyList);
        setupScrollListener();

        initAdapter();

        String query = (savedInstanceState == null) ? DEFAULT_QUERY : LAST_SEARCH_QUERY;
        viewModel.searchRepo(query);
        initSearch(query);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(LAST_SEARCH_QUERY, viewModel.lastQueryValue());
    }

    private void initAdapter() {
        list.setAdapter(adapter);
        viewModel.getRepos().observe(this, new Observer<List<Repo>>() {
            @Override
            public void onChanged(@Nullable List<Repo> repos) {
                if (repos.size() == 0) {
                    showEmptyList(true);
                } else {
                    showEmptyList(false);
                    adapter.submitList(repos);
                }
            }
        });
    }

    private void initSearch(String query) {
        search_repo_text.setText(query);

        search_repo_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    updateRepoListFromInput();
                    return true;
                } else {
                    return false;
                }
            }
        });

        search_repo_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    updateRepoListFromInput();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void updateRepoListFromInput() {
        queryFromUser = search_repo_text.getText().toString().trim();
        if (!queryFromUser.isEmpty()) {
            list.scrollToPosition(0);
            viewModel.searchRepo(queryFromUser);
            adapter.submitList(null);
        }
    }

    private void showEmptyList(boolean show) {
        if (show) {
            emptyList.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        } else {
            emptyList.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        }
    }

    private void setupScrollListener() {
        final LinearLayoutManager layoutManager = (LinearLayoutManager) list.getLayoutManager();
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = layoutManager.getItemCount();
                int visibleItemCount = layoutManager.getChildCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount);
            }
        });
    }
}
