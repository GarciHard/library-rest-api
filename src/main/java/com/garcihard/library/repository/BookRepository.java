package com.garcihard.library.repository;

import com.garcihard.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    /*
     * ¿Qué hace esto?
     * @Query: Te permite definir una query JPQL (o SQL nativa) personalizada.
     * @Modifying: Le dice a Spring Data que esta operación no es un SELECT. Sin esto, la aplicación lanzará una excepción. Es necesario para UPDATE, DELETE e INSERT.
     * int deleteAndReturnStatus(...): Definimos el metodo.
     * JPA es lo suficientemente inteligente para saber que una query de borrado puede devolver el número de filas afectadas.
     * Este int es la clave de todo el patrón.
     **/
    @Modifying
    @Query("DELETE FROM Book b WHERE b.id = :id")
    int deleteAndReturnStatus(@Param("id") UUID id);

}