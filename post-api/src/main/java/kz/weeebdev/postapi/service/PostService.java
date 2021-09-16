package kz.weeebdev.postapi.service;

import kz.weeebdev.postapi.feign.ClientFeign;
import kz.weeebdev.postapi.model.ClientResponse;
import kz.weeebdev.postapi.model.PostModel;
import kz.weeebdev.postapi.model.PostStatus;
import kz.weeebdev.postapi.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final ClientFeign clientFeign;

    public PostService(PostRepository postRepository, ClientFeign clientFeign) {
        this.postRepository = postRepository;
        this.clientFeign = clientFeign;
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

        savedPost.setRecipientId(post.getRecipientId());
        savedPost.setClientId(post.getClientId());
        savedPost.setMessage(post.getMessage());

        checkEmail(savedPost);

        return postRepository.save(savedPost);
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    private void checkEmail(PostModel post) {
        ClientResponse recipient = clientFeign.getClient(post.getRecipientId());
        ClientResponse client = clientFeign.getClient(post.getClientId());

        if (recipient != null && client != null) {
            post.setStatus(PostStatus.CORRECT);
        } else {
            post.setStatus(PostStatus.INCORRECT);
        }
    }
}
