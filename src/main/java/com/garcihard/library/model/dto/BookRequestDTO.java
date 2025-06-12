package com.garcihard.library.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record BookRequestDTO(
        @NotBlank(message = "Title cannot be blank.")
        String title,

        @NotBlank(message = "Author cannot be blank.")
        String author,

        @Pattern(regexp = ISBN_PATTERN,
                message = "Invalid ISBN Format.")
        @Size(min = 13, max = 20,
                message = "Invalid ISBN length.")
        String isbn
) {
        private static final String ISBN_PATTERN = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";
}