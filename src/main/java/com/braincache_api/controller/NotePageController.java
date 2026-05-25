package com.braincache_api.controller;

import com.braincache_api.entity.NotePage;
import com.braincache_api.entity.User;
import com.braincache_api.service.NotePageService;
import com.braincache_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notepages")
@RequiredArgsConstructor
public class NotePageController {
    private final NotePageService notePageService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Crear nueva página de notas")
    @ApiResponse(responseCode = "200", description = "Página creada exitosamente")
    @ApiResponse(responseCode = "400", description = "Error en la creación")
    public ResponseEntity<?> createNotePage(@RequestBody Map<String, Object> request) {
        try {
            String username = (String) request.get("username");
            String title = (String) request.get("title");
            String content = (String) request.get("content");
            Boolean isPublic = request.get("isPublic") != null ? 
                Boolean.valueOf(request.get("isPublic").toString()) : false;
            
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            NotePage notePage = notePageService.createNotePage(user, title, content, isPublic);
            return ResponseEntity.ok(notePage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener página por ID")
    @ApiResponse(responseCode = "200", description = "Página encontrada")
    @ApiResponse(responseCode = "400", description = "Error (página no encontrada o sin permisos)")
    public ResponseEntity<?> getNotePage(
            @Parameter(description = "ID de la página") @PathVariable Long id,
            @Parameter(description = "Username del usuario") @RequestParam String username) {
        try {
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            NotePage notePage = notePageService.getNotePageById(id, user);
            return ResponseEntity.ok(notePage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{username}")
    @Operation(summary = "Obtener páginas de un usuario")
    @ApiResponse(responseCode = "200", description = "Lista de páginas del usuario")
    @ApiResponse(responseCode = "400", description = "Usuario no encontrado")
    public ResponseEntity<?> getUserNotePages(
            @Parameter(description = "Username del usuario") @PathVariable String username) {
        try {
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            List<NotePage> notePages = notePageService.getUserNotePages(user);
            return ResponseEntity.ok(notePages);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/public")
    @Operation(summary = "Obtener páginas públicas")
    @ApiResponse(responseCode = "200", description = "Lista de páginas públicas")
    public ResponseEntity<List<NotePage>> getPublicNotePages() {
        return ResponseEntity.ok(notePageService.getPublicNotePages());
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar páginas por título")
    @ApiResponse(responseCode = "200", description = "Lista de páginas que coinciden")
    public ResponseEntity<List<NotePage>> searchNotePages(
            @Parameter(description = "Título a buscar") @RequestParam String title,
            @Parameter(description = "Username del usuario (opcional)") @RequestParam(required = false) String username) {
        if (username != null) {
            User user = userService.findByUsername(username).orElse(null);
            if (user != null) {
                return ResponseEntity.ok(notePageService.searchUserNotePagesByTitle(user, title));
            }
        }
        return ResponseEntity.ok(notePageService.searchPublicNotePagesByTitle(title));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar página")
    @ApiResponse(responseCode = "200", description = "Página actualizada exitosamente")
    @ApiResponse(responseCode = "400", description = "Error (página no encontrada o sin permisos)")
    public ResponseEntity<?> updateNotePage(
            @Parameter(description = "ID de la página") @PathVariable Long id,
            @RequestBody Map<String, Object> request) {
        try {
            String username = (String) request.get("username");
            String title = (String) request.get("title");
            String content = (String) request.get("content");
            Boolean isPublic = Boolean.valueOf(request.get("isPublic").toString());
            
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            NotePage notePage = notePageService.updateNotePage(id, title, content, isPublic, user);
            return ResponseEntity.ok(notePage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar página")
    @ApiResponse(responseCode = "200", description = "Página eliminada exitosamente")
    @ApiResponse(responseCode = "400", description = "Error (página no encontrada o sin permisos)")
    public ResponseEntity<?> deleteNotePage(
            @Parameter(description = "ID de la página") @PathVariable Long id,
            @Parameter(description = "Username del usuario") @RequestParam String username) {
        try {
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            notePageService.deleteNotePage(id, user);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
