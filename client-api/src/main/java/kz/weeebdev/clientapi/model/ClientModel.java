package kz.weeebdev.clientapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ClientModel {
    @Id
    private String id;

    @NotNull
    @Size(min = 1, message = "Please, enter name")
    private String name;

    @NotNull
    @Email(message = "Please, enter email")
    private  String email;
}
