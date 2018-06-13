package rakeshne.com.fetchgitrepo.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import rakeshne.com.fetchgitrepo.model.Repo;

/**
 * Room data access object for accessing the [Repo] table.
 */
@Dao
public interface RepoDao {

    String QUERY = "SELECT * FROM repos WHERE (name LIKE :query) OR (description LIKE " +
            ":query) ORDER BY stars DESC, name ASC";

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Repo> posts);

    @Query(QUERY)
    LiveData<List<Repo>> reposByName(String query);
}
