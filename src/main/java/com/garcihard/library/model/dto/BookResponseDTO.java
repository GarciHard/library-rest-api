package com.garcihard.library.model.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record BookResponseDTO(
        UUID id,
        String title,
        String author,
        String isbn,
        Instant createdAt,
        Instant updatedAt
) {
}
