package kz.weeebdev.clientpayment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWithPage<T> {
    private T response;

    private int pageNumber;

    private int pageSize;

    private int totalPages;
}
