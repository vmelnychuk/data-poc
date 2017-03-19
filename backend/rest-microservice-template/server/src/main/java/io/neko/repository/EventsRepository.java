package io.fourfinance.pos.dataprovider.repository;

import io.fourfinance.pos.dataprovider.event.StoredEvent;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EventsRepository extends PagingAndSortingRepository<StoredEvent, String> {

    List<StoredEvent> findByAggregateIdOrderBySeqAsc(String aggregateId);

}
