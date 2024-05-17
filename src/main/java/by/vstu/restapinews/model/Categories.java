package by.vstu.restapinews.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoriesID")
    private Long id;
    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "categories")
    private List<NewsCategories> newsCategories;
}

