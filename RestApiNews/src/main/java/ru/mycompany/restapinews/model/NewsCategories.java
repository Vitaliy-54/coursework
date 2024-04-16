package ru.mycompany.restapinews.model;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NewsID")
    private Long newsId;
    @Column(name = "CategoriesID")
    private Long categoriesId;
}
