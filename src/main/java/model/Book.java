package model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Integer id;

    @ToString.Include
    private String name;

    @ToString.Exclude
    @ManyToMany
    @JoinColumn(name = "id_author")
    private List<Author> authorId;
}
