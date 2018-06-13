package rakeshne.com.fetchgitrepo.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rakeshne.com.fetchgitrepo.R;
import rakeshne.com.fetchgitrepo.model.Repo;

public class RepoViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView descritpion;
    private TextView stars;
    private TextView language;
    private TextView forks;
    private Repo repo;

    public RepoViewHolder(final View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.repo_name);
        descritpion = (TextView) itemView.findViewById(R.id.repo_description);
        stars = (TextView) itemView.findViewById(R.id.repo_stars);
        language = (TextView) itemView.findViewById(R.id.repo_language);
        forks = (TextView) itemView.findViewById(R.id.repo_forks);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repo != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(repo.getUrl()));
                    itemView.getContext().startActivity(intent);
                }
            }
        });
    }

    public void bindRepo(Repo repo) {
        if (repo == null) {
            Resources resources = itemView.getResources();
            name.setText("Loading");
            descritpion.setVisibility(View.GONE);
            language.setVisibility(View.GONE);
            stars.setText("?");
            forks.setText("?");
        } else {
            showRepoData(repo);
        }
    }

    private void showRepoData(Repo repo) {
        this.repo = repo;
        name.setText(repo.getFullName());

        int descriptionVisibility = View.GONE;
        if (repo.getDescription() != null) {
            descritpion.setText(repo.getDescription());
            descriptionVisibility = View.VISIBLE;
        }
        descritpion.setVisibility(descriptionVisibility);

        stars.setText(repo.getStars() + "");
        forks.setText(repo.getForks() + "");

        int languageVisibilty = View.GONE;
        if (repo.getLanguage() != null && !repo.getLanguage().isEmpty()) {
            Resources resources = this.itemView.getContext().getResources();
            language.setText(resources.getString(R.string.language, repo.getLanguage()));
            languageVisibilty = View.VISIBLE;
        }
        language.setVisibility(languageVisibilty);

    }

    public static RepoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_view_item, parent, false);
        return new RepoViewHolder(view);
    }
}
