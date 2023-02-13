package com.emontazysta.controller;

import com.emontazysta.model.dto.CommentDto;
import com.emontazysta.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@AllArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Comments.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<CommentDto>> getAll() {
        return ResponseEntity.ok().body(commentService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Comment by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<CommentDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(commentService.getById(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Comment.", security = @SecurityRequirement(name = "bearer-key"))
    public CommentDto add(@Valid @RequestBody CommentDto commentDto) {
        return commentService.add(commentDto);
    }

    @DeleteMapping("/{id}/update")
    @Operation(description = "Allows to delete Comment by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable("id") Long id) {
        commentService.delete(id);
    }
}
