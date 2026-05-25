package com.braincache_api.service;

import com.braincache_api.entity.NotePage;
import com.braincache_api.entity.User;
import com.braincache_api.repository.NotePageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotePageService {
    private final NotePageRepository notePageRepository;

    public NotePage createNotePage(User user, String title, String content, boolean isPublic) {
        NotePage notePage = new NotePage();
        notePage.setUser(user);
        notePage.setTitle(title);
        notePage.setContent(content);
        notePage.setPublic(isPublic);
        return notePageRepository.save(notePage);
    }

    public NotePage updateNotePage(Long id, String title, String content, boolean isPublic, User user) {
        NotePage notePage = notePageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotePage not found"));
        
        if (!notePage.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only update your own note pages");
        }
        
        notePage.setTitle(title);
        notePage.setContent(content);
        notePage.setPublic(isPublic);
        return notePageRepository.save(notePage);
    }

    public void deleteNotePage(Long id, User user) {
        NotePage notePage = notePageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotePage not found"));
        
        if (!notePage.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only delete your own note pages");
        }
        
        notePageRepository.delete(notePage);
    }

    public List<NotePage> getUserNotePages(User user) {
        return notePageRepository.findByUser(user);
    }

    public List<NotePage> getPublicNotePages() {
        return notePageRepository.findByIsPublic(true);
    }

    public List<NotePage> searchPublicNotePagesByTitle(String title) {
        return notePageRepository.findByPublicTitleContaining(title);
    }

    public List<NotePage> searchUserNotePagesByTitle(User user, String title) {
        return notePageRepository.findByUserAndTitleContaining(user, title);
    }

    public NotePage getNotePageById(Long id, User user) {
        NotePage notePage = notePageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotePage not found"));
        
        if (!notePage.isPublic() && !notePage.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only view public note pages or your own");
        }
        
        return notePage;
    }
}
