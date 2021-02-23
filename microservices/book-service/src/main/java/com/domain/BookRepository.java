package com.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAllByIsDeleted(boolean b, Pageable pageable);

    Optional<Book> findByIdAndIsDeleted(Long id, boolean b);
}
