package RestApiNews.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Categories extends AbstractEntity {
    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "categories")
    @JsonIgnore
    private List<NewsCategories> newsCategories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NewsCategories> getNewsCategories() {
        return newsCategories;
    }

    public void setNewsCategories(List<NewsCategories> newsCategories) {
        this.newsCategories = newsCategories;
    }
}
