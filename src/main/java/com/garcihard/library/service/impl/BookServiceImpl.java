package com.garcihard.library.service.impl;

import com.garcihard.library.exception.custom.BookNotFoundException;
import com.garcihard.library.mapper.BookMapper;
import com.garcihard.library.model.Book;
import com.garcihard.library.model.dto.BookRequestDTO;
import com.garcihard.library.model.dto.BookResponseDTO;
import com.garcihard.library.repository.BookRepository;
import com.garcihard.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Override
    public BookResponseDTO create(BookRequestDTO request) {
        Book entity = bookMapper.toEntity(request);
        Book responseEntity = bookRepository.save(entity);

        return bookMapper.toDto(responseEntity);
    }

    @Override
    public List<BookResponseDTO> getALl() {
        List<Book> allBooks = bookRepository.findAll();

        return allBooks.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public BookResponseDTO getById(UUID id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

    }

    @Override
    public BookResponseDTO updateById(UUID id, BookRequestDTO dto) {
        Book persistedBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        persistedBook.setTitle(dto.title());
        persistedBook.setAuthor(dto.author());
        persistedBook.setIsbn(dto.isbn());

        Book updatedBook = bookRepository.save(persistedBook);
        return bookMapper.toDto(updatedBook);
    }

    @Override
    public void deleteById(UUID id) {
        getById(id);
        bookRepository.deleteById(id);
    }
}
