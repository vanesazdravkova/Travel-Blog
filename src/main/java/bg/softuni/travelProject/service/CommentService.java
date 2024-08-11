package bg.softuni.travelProject.service;

import bg.softuni.travelProject.model.dto.CommentServiceDto;
import bg.softuni.travelProject.model.view.CommentViewModel;

import java.util.List;

public interface CommentService {

    List<CommentViewModel> getComments(Long tripId, String principalName);

    CommentViewModel createComment(CommentServiceDto commentServiceModel);

    CommentViewModel deleteComment(Long commentId, String principalName);

    boolean isAuthorOrAdmin(String userName, Long commentId);
}
