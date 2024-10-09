package ru.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    private String description;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created;
}
