package rakeshne.com.fetchgitrepo.ui;


import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import rakeshne.com.fetchgitrepo.model.Repo;

public class ReposAdapter extends ListAdapter<Repo, RecyclerView.ViewHolder> {

    private static final DiffUtil.ItemCallback<Repo> REPO_COMPARATOR = new DiffUtil.ItemCallback<Repo>() {
        @Override
        public boolean areItemsTheSame(Repo oldItem, Repo newItem) {
            return oldItem.getFullName().equals(newItem.getFullName());
        }

        @Override
        public boolean areContentsTheSame(Repo oldItem, Repo newItem) {
            return oldItem == newItem;
        }
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RepoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Repo repoItem = getItem(position);
        if (repoItem != null) {
            ((RepoViewHolder) holder).bindRepo(repoItem);
        }
    }

    public ReposAdapter() {
        super((DiffUtil.ItemCallback) REPO_COMPARATOR);
    }
}
