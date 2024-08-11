package bg.softuni.travelProject.service.impl;

import bg.softuni.travelProject.model.dto.CommentServiceDto;
import bg.softuni.travelProject.model.entity.CommentEntity;
import bg.softuni.travelProject.model.entity.TripEntity;
import bg.softuni.travelProject.model.entity.UserEntity;
import bg.softuni.travelProject.model.enums.RoleNameEnum;
import bg.softuni.travelProject.model.view.CommentViewModel;
import bg.softuni.travelProject.repository.CommentRepository;
import bg.softuni.travelProject.repository.TripRepository;
import bg.softuni.travelProject.repository.UserRepository;
import bg.softuni.travelProject.service.CommentService;
import bg.softuni.travelProject.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(TripRepository tripRepository,
                          UserRepository userRepository,
                          CommentRepository commentRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public List<CommentViewModel> getComments(Long tripId, String principalName) {
        Optional<TripEntity> tripOptional = this.tripRepository.findById(tripId);

        if (tripOptional.isEmpty()) {
            throw new ObjectNotFoundException("Trip with id " + tripId + " was not found!");
        }
        return tripOptional.get()
                .getComments().stream()
                .map(comment -> mapAsComment(comment, principalName))
                .collect(Collectors.toList());
    }

    private CommentViewModel mapAsComment(CommentEntity commentEntity, String principalName) {
        CommentViewModel commentViewModel = new CommentViewModel();

        commentViewModel.setCommentId(commentEntity.getId())
                .setMessage(commentEntity.getTextContent())
                .setUser(commentEntity.getAuthor().getFirstName() + " " + commentEntity.getAuthor().getLastName())
                .setCreated(commentEntity.getCreated())
                .setCanDelete(principalName != null && isAuthorOrAdmin(principalName, commentEntity.getId()));

        return commentViewModel;
    }

    @Override
    public CommentViewModel createComment(CommentServiceDto commentServiceDto) {
        TripEntity trip = tripRepository.findById(commentServiceDto.getTripId())
                .orElseThrow(() -> new UnsupportedOperationException("Trip with id " + commentServiceDto.getTripId() + " not found!"));

        UserEntity creator = userRepository.findByUsername(commentServiceDto.getCreator())
                .orElseThrow(() -> new UnsupportedOperationException("User with username " + commentServiceDto.getCreator() + " not found!"));

        CommentEntity newComment = new CommentEntity();
        newComment.setTextContent(commentServiceDto.getMessage());
        newComment.setCreated(LocalDateTime.now());
        newComment.setAuthor(creator);
        newComment.setTrip(trip);

        CommentEntity savedComment = commentRepository.save(newComment);
        return mapAsComment(savedComment, creator.getUsername());
    }

    @Override
    public CommentViewModel deleteComment(Long commentId, String principalName) {
        CommentEntity deleted = commentRepository.findById(commentId)
                .orElseThrow(() -> new ObjectNotFoundException("Comment with id " + commentId + " not found!"));

        commentRepository.deleteById(commentId);
        return mapAsComment(deleted, principalName);
    }

    @Override
    public boolean isAuthorOrAdmin(String userName, Long commentId) {
        boolean isOwner = commentRepository.
                findById(commentId).
                filter(c -> c.getAuthor().getUsername().equals(userName)).
                isPresent();

        if (isOwner) {
            return true;
        }

        return userRepository
                .findByUsername(userName)
                .filter(this::isAdmin)
                .isPresent();
    }

    private boolean isAdmin(UserEntity user) {
        return user.getRoles().
                stream().
                anyMatch(r -> r.getRole() == RoleNameEnum.ADMIN);
    }
}
