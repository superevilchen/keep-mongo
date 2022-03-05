package com.example.keepmock.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(value="tasks")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Task {

    @Id
    private String id;

    private String text;

    private LocalDateTime dueDate;

//    @CreatedDate
//    @Column(updatable=false)
    @Indexed(direction= IndexDirection.ASCENDING)
    // add un updatable
    private LocalDateTime addedAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Label label;

    private String picture;

    private boolean isArchived;

    private boolean isDiscarded;

//    @CreatedBy
    @DBRef(db="users")
//    // un updatable
    private User user;

    public Task(String text, LocalDateTime dueDate, LocalDateTime addedAt, Label label, String picture, boolean isArchived, boolean isDiscarded, User user) {
        this(null, text, dueDate, addedAt, null, label, picture, isArchived, isDiscarded, user);
    }

    public Task(String text, LocalDateTime dueDate, LocalDateTime addedAt, Label label, String picture, User user) {
        this(null, text, dueDate, addedAt, null, label, picture, false, false, user);
    }

    public Task(String text, LocalDateTime dueDate, LocalDateTime addedAt, Label label, String picture) {
        this(null, text, dueDate, addedAt, null, label, picture, false, false, null);
    }

    public Task(String text, LocalDateTime dueDate, LocalDateTime addedAt, Label label) {
        this(null, text, dueDate, addedAt, null, label, null, false, false, null);
    }
}
