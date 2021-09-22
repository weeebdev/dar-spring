package kz.weeebdev.clientpayments.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Document(indexName = "client-payment", createIndex = true)
public class ClientPaymentEntity {
    @Id
    @Field(type = FieldType.Keyword)
    private String paymentId;

    @Field(type = FieldType.Keyword)
    private String clientId;

    @Field(type = FieldType.Keyword)
    private String paymentType;

    @Field(type = FieldType.Keyword)
    private double cost;
}
