package io.dev.deneb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="entities")
public class Notification {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="notifications_id_seq")
    private Long id;

    private String message;

    private LocalDateTime timestamp;

    private String service;
}
