package com.braincache_api.repository;

import com.braincache_api.entity.NotePage;
import com.braincache_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotePageRepository extends JpaRepository<NotePage, Long> {
    List<NotePage> findByUser(User user);
    
    List<NotePage> findByUserAndIsPublic(User user, boolean isPublic);
    
    List<NotePage> findByIsPublic(boolean isPublic);
    
    @Query("SELECT n FROM NotePage n WHERE n.isPublic = true AND LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<NotePage> findByPublicTitleContaining(@Param("title") String title);
    
    @Query("SELECT n FROM NotePage n WHERE n.user = :user AND LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<NotePage> findByUserAndTitleContaining(@Param("user") User user, @Param("title") String title);
}
