package ru.mycompany.restapinews.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "newscategories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "NewsID", nullable = false)
    private News news;

    @ManyToOne
    @JoinColumn(name = "CategoriesID", nullable = false)
    private Categories categories;
}


