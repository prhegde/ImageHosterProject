package ImageHoster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.repository.CommentRepository;
import ImageHoster.repository.ImageRepository;

@Service
public class CommentService {
	@Autowired
    private CommentRepository commentRepository;
	
	@Autowired
    private ImageRepository imageRepository;

	public List<Comment> getCommentsByImageId(Integer imageId) {
		Image image = imageRepository.getImage(imageId);
		return commentRepository.getCommentsByImage(image);
	}
	
    public Comment createComment(Comment comment) {
        return commentRepository.createComment(comment);
    }
}
