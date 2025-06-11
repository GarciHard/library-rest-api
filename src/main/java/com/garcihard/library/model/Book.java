package com.garcihard.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.UUID;

//@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book extends BaseEntity implements Serializable {

    private static final String ISBN_PATTERN = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Title cannot be blank.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Author cannot be blank.")
    @Column(nullable = false)
    private String author;

    @Pattern(regexp = ISBN_PATTERN, message = "Invalid ISBN Format.")
    @Column(unique = true, length = 20)
    private String isbn;
}









