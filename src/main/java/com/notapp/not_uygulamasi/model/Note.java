package com.notapp.not_uygulamasi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime dueDate;

    public Note() {}
    public Note(String title, String content, LocalDateTime dueDate) {
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
    }
    public boolean isOverdue() {
        if (this.dueDate == null) return false;
        return LocalDateTime.now().isAfter(this.dueDate);
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
}