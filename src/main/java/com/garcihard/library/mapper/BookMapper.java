package com.garcihard.library.mapper;

import com.garcihard.library.model.Book;
import com.garcihard.library.model.dto.BookRequestDTO;
import com.garcihard.library.model.dto.BookResponseDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {

    BookResponseDTO toDto(Book entity);

    Book toEntity(BookRequestDTO dto);
}
