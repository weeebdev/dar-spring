package kz.weeebdev.postapi.service;

import kz.weeebdev.postapi.model.PostModel;
import kz.weeebdev.postapi.model.PostStatus;
import kz.weeebdev.postapi.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final List<String> whitelist;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
        this.whitelist = new ArrayList<String>();
        whitelist.add("a.akhmetov@dar.kz");
        whitelist.add("mmagzom@dar.kz");
        whitelist.add("kate@google.com");
    }

    public List<PostModel> getAllCorrectPosts() {
        return postRepository.findCorrect();
    }

    public List<PostModel> getAllPosts() {
        return postRepository.findAll();
    }

    public PostModel addPost(PostModel post) {
        checkEmail(post);
        return postRepository.insert(post);
    }

    public Optional<PostModel> getPost(String id) {
        return postRepository.findById(id);
    }

    public PostModel updatePost(PostModel post, String id) {
        PostModel savedPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Cannot find post by id %s", id)));

        savedPost.setEmail(post.getEmail());
        savedPost.setMessage(post.getMessage());

        checkEmail(savedPost);

        return postRepository.save(savedPost);
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    private void checkEmail(PostModel post) {
        if (this.whitelist.contains(post.getEmail())) {
            post.setStatus(PostStatus.CORRECT);
        } else {
            post.setStatus(PostStatus.INCORRECT);
        }
    }
}
