package kz.weeebdev.postapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewPostModel {
    private String message;
    private String recipient;
    private String client;
}
