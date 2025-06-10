package com.garcihard.library.model.dto;

import lombok.Builder;

@Builder
public record BookRequestDTO(
        String title,
        String author,
        String isbn
) {
}
