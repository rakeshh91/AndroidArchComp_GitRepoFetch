package rakeshne.com.fetchgitrepo.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "repos")
public final class Repo {

    @PrimaryKey
    @SerializedName("id")
    @NonNull
    private final long id;

    @SerializedName("name")
    @NonNull
    private final String name;

    @SerializedName("full_name")
    @NonNull
    private final String fullName;

    @SerializedName("description")
    @Nullable
    private final String description;

    @SerializedName("html_url")
    @NonNull
    private final String url;

    @SerializedName("stargazers_count")
    private final int stars;

    @SerializedName("forks_count")
    private final int forks;

    @SerializedName("language")
    @Nullable
    private final String language;

    public Repo(@NonNull long id, String name, String fullName, String description, String url, int stars, int forks, String language) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.url = url;
        this.stars = stars;
        this.forks = forks;
        this.language = language;
    }

    @NonNull
    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getFullName() {
        return fullName;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public int getStars() {
        return stars;
    }

    public int getForks() {
        return forks;
    }

    @Nullable
    public String getLanguage() {
        return language;
    }

    public String toString() {
        return "Repo(id=" + this.id + ", name=" + this.name + ", fullName=" + this.fullName + ", description=" + this.description + ", url=" + this.url + ", stars=" + this.stars + ", forks=" + this.forks + ", language=" + this.language + ")";
    }

    public int hashCode() {
        return (((((((int) (this.id ^ this.id >>> 32) * 31 + (this.name != null ? this.name.hashCode() : 0)) * 31 + (this.fullName != null ? this.fullName.hashCode() : 0)) * 31 + (this.description != null ? this.description.hashCode() : 0)) * 31 + (this.url != null ? this.url.hashCode() : 0)) * 31 + this.stars) * 31 + this.forks) * 31 + (this.language != null ? this.language.hashCode() : 0);
    }

    public boolean equals(Object var1) {
        if (var1 != null && var1 instanceof Repo) {
            Repo var2 = (Repo) var1;
            if (this.id == var2.getId() && this.name.equals(var2.getName()) && this.fullName.equals(var2.getFullName()) && this.description.equals(var2.getDescription()) && this.url.equals(var2.getUrl()) && this.stars == var2.getStars() && this.forks == var2.forks && this.language.equals(var2.language)) {
                return true;
            }
        }

        return false;
    }
}

