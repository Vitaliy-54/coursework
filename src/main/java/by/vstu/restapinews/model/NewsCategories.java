package by.vstu.restapinews.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "newscategories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "NewsID", nullable = false)
    private News news;

    @ManyToOne
    @JoinColumn(name = "CategoriesID", nullable = false)
    private Categories categories;

}


