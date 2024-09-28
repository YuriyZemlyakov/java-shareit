package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private Boolean available;
    @Column(name = "user_id")
    private long owner;
    @Transient
    private long request = 0;
}
