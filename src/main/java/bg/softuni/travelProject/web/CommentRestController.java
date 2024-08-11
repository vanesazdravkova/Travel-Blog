package bg.softuni.travelProject.web;

import bg.softuni.travelProject.model.dto.CommentAddDto;
import bg.softuni.travelProject.model.dto.CommentServiceDto;
import bg.softuni.travelProject.model.validation.ApiError;
import bg.softuni.travelProject.model.view.CommentViewModel;
import bg.softuni.travelProject.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class CommentRestController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public CommentRestController(CommentService commentService,
                                 ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/{tripId}/comments")
    public ResponseEntity<List<CommentViewModel>> getComments(
            @PathVariable Long tripId,
            @AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.ok(commentService.getComments(tripId, principal != null ? principal.getUsername() : null));
    }

    @PostMapping("/api/{tripId}/comments")
    public ResponseEntity<CommentViewModel> newComment(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long tripId,
            @RequestBody @Valid CommentAddDto commentAddDto) {

        if (principal == null) {
            return ResponseEntity.status(403).build();
        }

        CommentServiceDto commentServiceDto = modelMapper.map(commentAddDto, CommentServiceDto.class);
        commentServiceDto.setCreator(principal.getUsername());
        commentServiceDto.setTripId(tripId);

        CommentViewModel newComment = commentService.createComment(commentServiceDto);
        URI locationOfNewComment =
                URI.create(String.format("/api/%s/comments/%s", tripId, newComment.getCommentId()));

        return ResponseEntity
                .created(locationOfNewComment)
                .body(newComment);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> onValidationFailure(MethodArgumentNotValidException exc) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        exc.getFieldErrors().forEach(fe -> apiError.addFieldWithError(fe.getField()));

        return ResponseEntity.badRequest().body(apiError);
    }

    @DeleteMapping("/api/{tripId}/comments/{commentId}")
    public ResponseEntity<CommentViewModel> deleteComment(
            @PathVariable("commentId") Long commentId, @PathVariable("tripId") Long tripId,
            @AuthenticationPrincipal UserDetails principal) {

        if (principal != null && commentService.isAuthorOrAdmin(principal.getUsername(), commentId)) {
            CommentViewModel deleted = commentService.deleteComment(commentId, principal.getUsername());
            return ResponseEntity.ok(deleted);
        }
        return ResponseEntity.status(403).build();
    }
}
