package io.fourfinance.pos.dataprovider.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "events")
public class StoredEvent {
    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    @Column(name = "aggregate_id", updatable = false)
    private String aggregateId;

    @Column(name = "origin", updatable = false)
    private String origin;

    @Column(name = "type", updatable = false)
    private String type;

    @Column(name = "payload", columnDefinition = "TEXT", updatable = false)
    private String jsonPayload;

    @Column(name = "seq", nullable = false, updatable = false)
    private long seq;
}
