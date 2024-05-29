package RestApiNews.entity;

import javax.persistence.*;


@Entity
@Table(name = "newscategories")
public class NewsCategories extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "NewsID", nullable = false)
    private News news;

    @ManyToOne
    @JoinColumn(name = "CategoriesID", nullable = false)
    private Categories categories;

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }
}
