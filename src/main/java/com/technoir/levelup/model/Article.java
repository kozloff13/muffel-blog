package com.technoir.levelup.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "article")
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    private User author;

    @CreatedDate
    @Column(name = "created")
    private Date created;

    @Column(name = "published", columnDefinition = "false")
    private boolean published;
}
