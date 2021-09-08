package kz.weeebdev.postapi.controller;

import kz.weeebdev.postapi.model.PostModel;
import kz.weeebdev.postapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Autowired
    Environment env;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return String.format("It's working on port %s!", env.getProperty("local.server.port"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostModel>> getAllCorrectPosts() {
        return ResponseEntity.ok(postService.getAllCorrectPosts());
    }

    @GetMapping
    public ResponseEntity<List<PostModel>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping
    public ResponseEntity<PostModel> addPost(@RequestBody @Valid PostModel post) {
        return new ResponseEntity<PostModel>(postService.addPost(post), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPost(@PathVariable String id) {
        Optional<PostModel> post = postService.getPost(id);
        if (post.isPresent()) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostModel> updatePost(@RequestBody PostModel post, @PathVariable String id) {
        return ResponseEntity.ok(postService.updatePost(post, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable String id) {
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
