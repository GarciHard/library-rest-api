package com.garcihard.library.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.UUID;

@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(unique = true, length = 20)
    private String isbn;
}









