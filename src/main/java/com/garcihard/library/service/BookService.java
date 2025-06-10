package com.garcihard.library.service;

import com.garcihard.library.model.dto.BookRequestDTO;
import com.garcihard.library.model.dto.BookResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {

    BookResponseDTO create(BookRequestDTO request);

    List<BookResponseDTO> getALl();

    Optional<BookResponseDTO> getById(UUID id) throws Exception;

    BookResponseDTO updateById(UUID id, BookRequestDTO dto) throws Exception;

    void deleteById(UUID id) throws Exception;
}
