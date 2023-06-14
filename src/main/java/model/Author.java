package model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Integer id;

    @ToString.Include
    private String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "authorId", cascade = CascadeType.ALL)
    private List<Book> books;
}
