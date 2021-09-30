package kz.weeebdev.kafkamailer.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Document(indexName = "employee", createIndex = true)
public class EmployeeEntity {
    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Keyword)
    private String email;
}
