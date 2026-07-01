package com.notapp.not_uygulamasi.controller;

import com.notapp.not_uygulamasi.model.Note;
import com.notapp.not_uygulamasi.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    // Ana sayfa - Direkt girildiğinde tüm notları listeler
    @GetMapping("/")
    public String index(Model model) {
        return getFilteredNotes(model, "all");
    }

    // HTML'deki @{/filter-all} butonu için rota
    @GetMapping("/filter-all")
    public String filterAll(Model model) {
        return getFilteredNotes(model, "all");
    }

    // HTML'deki @{/filter-active} butonu için rota
    @GetMapping("/filter-active")
    public String filterActive(Model model) {
        return getFilteredNotes(model, "active");
    }

    // HTML'deki @{/filter-overdue} butonu için rota
    @GetMapping("/filter-overdue")
    public String filterOverdue(Model model) {
        return getFilteredNotes(model, "overdue");
    }

    // Filtreleme mantığını tek merkezden yöneten yardımcı metot
    private String getFilteredNotes(Model model, String filter) {
        List<Note> noteList = noteRepository.findAll();
        
        if ("overdue".equals(filter)) {
            noteList = noteList.stream().filter(Note::isOverdue).collect(Collectors.toList());
        } else if ("active".equals(filter)) {
            noteList = noteList.stream().filter(n -> !n.isOverdue()).collect(Collectors.toList());
        }
        
        model.addAttribute("notes", noteList);
        model.addAttribute("currentFilter", filter);
        return "index";
    }

    // Yeni Not Ekleme
    @PostMapping("/addNote")
    public String addNote(@RequestParam String title, 
                          @RequestParam String content,
                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDate) {
        Note newNote = new Note();
        newNote.setTitle(title);
        newNote.setContent(content);
        newNote.setDueDate(dueDate);
        
        noteRepository.save(newNote); 
        return "redirect:/";
    }

    // Not Erteleme
    @PostMapping("/postponeNote")
    public String postponeNote(@RequestParam Long id, 
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newDueDate) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            note.setDueDate(newDueDate);
            noteRepository.save(note);
        }
        return "redirect:/";
    }

    // Not Silme
    @PostMapping("/deleteNote")
    public String deleteNote(@RequestParam Long id) {
        noteRepository.deleteById(id);
        return "redirect:/";
    }
}