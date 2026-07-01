package com.notapp.not_uygulamasi.repository;

import com.notapp.not_uygulamasi.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
}