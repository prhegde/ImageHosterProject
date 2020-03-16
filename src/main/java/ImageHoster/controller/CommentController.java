package ImageHoster.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	 
	@Autowired
    private ImageService imageService;
	
	//This controller method is called when the request pattern is of type 'images/upload' and also the incoming request is of POST type
    //The method receives all the details of the image to be stored in the database, and now the image will be sent to the business logic to be persisted in the database
    //After you get the imageFile, set the user of the image by getting the logged in user from the Http Session
    //Convert the image to Base64 format and store it as a string in the 'imageFile' attribute
    //Set the date on which the image is posted
    //After storing the image, this method directs to the logged in user homepage displaying all the images

    //Get the 'tags' request parameter using @RequestParam annotation which is just a string of all the tags
    //Store all the tags in the database and make a list of all the tags using the findOrCreateTags() method
    //set the tags attribute of the image as a list of all the tags returned by the findOrCreateTags() method
    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String createComment(@PathVariable(name = "imageId") Integer imageId, @PathVariable(name = "imageTitle") String imageTitle, @RequestParam("comments") String comments, HttpSession session) throws IOException {
    	Comment comment = null;
    	if(comments != null) {
    		comment = createComment(comments);
    	}
    	List<Comment> existingComments = commentService.getCommentsByImageId(imageId);
    	existingComments.add(comment);
    	
    	Image image = imageService.getImage(imageId);
    	User user = (User) session.getAttribute("loggeduser");
    	image.setUser(user);
    	image.setComments(existingComments);
    	return "redirect:/images/image";
    }
    
    private Comment createComment(String comment) {
    	Comment returnComment = null;
    	if(comment != null) {
    		Comment newComment = new Comment();
            returnComment = commentService.createComment(newComment);
    	}
        return returnComment;
    }
}
