package kz.weeebdev.postapi.repository;

import kz.weeebdev.postapi.model.PostModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PostRepository extends MongoRepository<PostModel, String> {
    @Query("{'status': 'CORRECT'}")
    List<PostModel> findCorrect();
}
