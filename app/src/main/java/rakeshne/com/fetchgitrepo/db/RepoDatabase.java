package rakeshne.com.fetchgitrepo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import rakeshne.com.fetchgitrepo.model.Repo;

@Database(
        entities = {Repo.class},
        version = 1,
        exportSchema = false
)
public abstract class RepoDatabase extends RoomDatabase {
    public abstract RepoDao repoDao();

    private static final String DB_NAME = "Github.db";
    private static volatile RepoDatabase INSTANCE = null;

    public static synchronized RepoDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static RepoDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context, RepoDatabase.class, DB_NAME).build();
    }
}
