package com.example.demo.sevices;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Comment;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;

@Service
public class CommentServiceImplementation implements CommentService{

	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception{
		// TODO Auto-generated method stub
		
		User user = userService.findUserById(userId);
		
		Post post = postService.findPostById(postId);
		
		comment.setUser(user);
		comment.setContent(comment.getContent());
		comment.setCreatedAt(LocalDateTime.now());
		
		Comment savedComment = commentRepository.save(comment);
		
		post.getComments().add(savedComment);
		
		postRepository.save(post);
		return savedComment;
	}

	@Override
	public Comment likeComment(Integer commentId, Integer userId) throws Exception {
		Comment comment = findCommentById(commentId);
		User user = userService.findUserById(userId);
		
		if(!comment.getLiked().contains(user)) {
			comment.getLiked().add(user);
		}
		else {
			comment.getLiked().remove(user);
		}
		return commentRepository.save(comment);
	}

	@Override
	public Comment findCommentById(Integer commentId) throws Exception{
	
		// TODO Auto-generated method stub
		Optional<Comment> opt = commentRepository.findById(commentId);
		//the record can exist or not that is why optional
				
		if(opt.isEmpty()) {
			throw new Exception("comment not exist");
		}
		return opt.get();
		
	}

	

}
