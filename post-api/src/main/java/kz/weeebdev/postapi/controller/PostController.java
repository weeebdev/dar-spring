package kz.weeebdev.postapi.controller;

import kz.weeebdev.postapi.feign.ClientFeign;
import kz.weeebdev.postapi.model.ClientResponse;
import kz.weeebdev.postapi.model.PostModel;
import kz.weeebdev.postapi.model.ViewPostModel;
import kz.weeebdev.postapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Autowired
    Environment env;

    @Autowired
    ClientFeign clientFeign;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    private ViewPostModel makeViewPostModel(PostModel post) {
        ViewPostModel newPost = new ViewPostModel();

        ClientResponse recipient = clientFeign.getClient(post.getRecipientId());
        ClientResponse client = clientFeign.getClient(post.getClientId());

        newPost.setMessage(post.getMessage());

        if (recipient != null) {
            newPost.setRecipient(recipient.getName());
        } else {
            newPost.setRecipient("NOT FOUND");
        }

        if (client != null) {
            newPost.setClient(client.getName());
        } else {
            newPost.setClient("NOT FOUND");
        }

        return newPost;
    }

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return String.format("It's working on port %s!", env.getProperty("local.server.port"));
    }

    @GetMapping("/check/openfeign/client")
    public String checkClientFeignClient() {
        return clientFeign.healthCheck();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ViewPostModel>> getAllCorrectPosts() {
        List<ViewPostModel> newPosts = new ArrayList<>();
        List<PostModel> posts = postService.getAllCorrectPosts();

        for (PostModel post : posts) {
            newPosts.add(makeViewPostModel(post));
        }

        return ResponseEntity.ok(newPosts);
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
            return ResponseEntity.ok(makeViewPostModel(post.get()));
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
