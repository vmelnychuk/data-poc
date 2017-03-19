package io.fourfinance.pos.dataprovider.service.client;

import io.fourfinance.pos.dataprovider.event.StoredEvent;
import io.fourfinance.pos.dataprovider.repository.EventsRepository;
import io.fourfinance.pos.dataprovider.service.client.details.ApplicationBean;
import io.fourfinance.pos.dataprovider.service.client.events.EventProcessor;
import io.fourfinanceit.pos.gateway.dtos.merchant.Order;
import io.fourfinanceit.pos.serializer.EventSerializer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Component
public class ApplicationDataRetriever {

    private final EventProcessor eventProcessor;
    private final EventsRepository repository;

    public ApplicationBean retrieveValues(List<StoredEvent> events) {
        ApplicationBean applicationBean = new ApplicationBean();
        populateFromEvents(applicationBean, events);
        retrieveDataFromOrder(applicationBean);
        return applicationBean;
    }

    private void populateFromEvents(ApplicationBean applicationBean, List<StoredEvent> events) {
        for (StoredEvent storedEvent : events) {
            populateApplicationData(applicationBean, storedEvent);
        }
    }

    private void populateApplicationData(ApplicationBean applicationBean, StoredEvent storedEvent) {
        Object event = EventSerializer.getINSTANCE().deserialize(
                storedEvent.getOrigin(), storedEvent.getType(), storedEvent.getJsonPayload());
        eventProcessor.populate(applicationBean, event);
    }

    private void retrieveDataFromOrder(ApplicationBean applicationBean) {
        String orderId = retrieveOrderId(applicationBean.getOrder());
        if (orderId == null) {
            return;
        }

        List<StoredEvent> events = repository.findByAggregateIdOrderBySeqAsc(orderId);
        populateFromEvents(applicationBean, events);
    }

    private String retrieveOrderId(Order order) {
        return order == null ? null : order.getId();
    }
}
