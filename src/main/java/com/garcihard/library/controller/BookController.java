package com.garcihard.library.controller;

import com.garcihard.library.model.dto.BookRequestDTO;
import com.garcihard.library.model.dto.BookResponseDTO;
import com.garcihard.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(BookController.BASE_URI)
@RestController
public class BookController {

    static final String BASE_URI = "api/v1/books";

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> createNewBook(@Valid @RequestBody BookRequestDTO dto) {
        BookResponseDTO response = bookService.create(dto);

        // Construye la URI del nuevo recurso: /api/v1/books/{id_del_nuevo_libro}
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.ok()
                .body(bookService.getALl());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable UUID id) {
        return ResponseEntity.ok()
                .body(bookService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookById(@PathVariable UUID id, @Valid @RequestBody BookRequestDTO dto) {
        return ResponseEntity.ok()
                .body(bookService.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable UUID id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
