package com.garcihard.library.model.dto;

import java.time.Instant;
import java.util.UUID;

public record BookResponseDTO(
        UUID id,
        String title,
        String author,
        String isbn,
        Instant createdAt,
        Instant updatedAt
) {
}
