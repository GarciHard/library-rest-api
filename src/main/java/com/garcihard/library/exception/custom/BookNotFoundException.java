package com.garcihard.library.exception.custom;

import java.util.UUID;

public class BookNotFoundException extends RuntimeException {

    private static final String NOT_FOUND_MSG = "Book Not Found With id: ";

    public BookNotFoundException(UUID id) {
        super(BookNotFoundException.NOT_FOUND_MSG + id);
    }
}
