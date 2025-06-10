package com.garcihard.library.controller;

import com.garcihard.library.model.dto.BookRequestDTO;
import com.garcihard.library.model.dto.BookResponseDTO;
import com.garcihard.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createNewBook(@RequestBody BookRequestDTO dto) {
        BookResponseDTO response = bookService.create(dto);
        return ResponseEntity.created(URI.create(BASE_URI)).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        List<BookResponseDTO> response = bookService.getALl();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable UUID id) {
        Optional<BookResponseDTO> response;
        try {
            response = bookService.getById(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookById(@PathVariable UUID id, @RequestBody BookRequestDTO dto) {
        BookResponseDTO response;
        try {
            response = bookService.updateById(id,dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable UUID id) {
        try {
            bookService.deleteById(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
