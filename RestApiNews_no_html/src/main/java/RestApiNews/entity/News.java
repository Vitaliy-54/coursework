package RestApiNews.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "news")
public class News extends AbstractEntity {
    @Column(name = "Title")
    private String title;
    @Column(name = "Content", columnDefinition = "text")
    private String content;
    @Column(name = "PublishDate")
    private Date publishDate;

    @OneToMany(mappedBy = "news")
    @JsonIgnore
    private List<NewsCategories> newsCategories;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public List<NewsCategories> getNewsCategories() {
        return newsCategories;
    }

    public void setNewsCategories(List<NewsCategories> newsCategories) {
        this.newsCategories = newsCategories;
    }
}
