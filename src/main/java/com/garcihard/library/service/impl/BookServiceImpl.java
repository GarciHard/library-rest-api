package com.garcihard.library.service.impl;

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
    public Optional<BookResponseDTO> getById(UUID id) throws Exception {
        return Optional.ofNullable(
                bookMapper.toDto(bookRepository.findById(id)
                        .orElseThrow(Exception::new)
                )
        );
    }

    @Override
    public BookResponseDTO updateById(UUID id, BookRequestDTO dto) throws Exception {
        BookResponseDTO response;

        Optional<BookResponseDTO> persistedDto = Optional.of(getById(id))
                .orElseThrow(Exception::new);

        Book entity = bookMapper.DtoToEntity(persistedDto.get());
        entity.setTitle(dto.title());
        entity.setAuthor(dto.author());
        entity.setIsbn(dto.isbn());

        response = bookMapper.toDto(bookRepository.save(entity));

        return response;
    }

    @Override
    public void deleteById(UUID id) throws Exception {
        Optional<BookResponseDTO> persistedDto = Optional.of(getById(id))
                .orElseThrow(Exception::new);

        bookRepository.deleteById(id);
    }
}
