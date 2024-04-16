package ru.mycompany.restapinews.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "news")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NewsID")
    private Long id;
    @Column(name = "Title")
    private String title;
    @Column(name = "Content", columnDefinition = "text")
    private String content;
    @Column(name = "PublishDate")
    private Date publishDate;
}