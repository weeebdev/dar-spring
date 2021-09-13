package kz.weeebdev.postapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PostModel {
    @Id
    private String id;

    @NotNull
    @Size(min = 1, message = "Please, enter meaningful message")
    private String message;

    @NotNull(message = "Please, enter recipient id")
    private String recipientId;

    @NotNull(message = "Please, enter your id")
    private String clientId;

    private PostStatus status;
}
