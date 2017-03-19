package io.fourfinance.pos.dataprovider.service;

import io.fourfinance.pos.dataprovider.event.StoredEvent;
import io.fourfinance.pos.dataprovider.repository.EventsRepository;
import io.fourfinance.pos.dataprovider.service.client.ApplicationBeanPresenter;
import io.fourfinance.pos.dataprovider.service.client.ApplicationDataRetriever;
import io.fourfinance.pos.dataprovider.service.client.details.ApplicationBean;
import io.fourfinanceit.dataprovider.ApplicationResource;
import io.fourfinanceit.dataprovider.dto.ApplicationDetails;
import io.fourfinanceit.pos.dtos.RetailerConfigurationBean;
import io.fourfinanceit.pos.dtos.RiskBean;
import io.fourfinanceit.pos.serializer.EventSerializer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static io.fourfinanceit.pos.application.events.ApplicationStateChanged.ApplicationCreated;

@Component
@AllArgsConstructor
@Slf4j
public class ApplicationResourceImpl implements ApplicationResource {

    private final ApplicationDataRetriever applicationDataRetriever;
    private final ApplicationBeanPresenter applicationBeanPresenter;
    private final EventsRepository repository;

    @Override
    public List<ApplicationDetails> getApplications(List<String> applicationIds) {
        return applicationIds.stream().map(this::get).collect(Collectors.toList());
    }

    @Override
    public ApplicationDetails get(String id) {
        ApplicationDetails applicationDetails = new ApplicationDetails();
        for (StoredEvent storedEvent : getEvents(id)) {
            Object event = EventSerializer.getINSTANCE()
                    .deserialize(storedEvent.getOrigin(), storedEvent.getType(), storedEvent.getJsonPayload());
            if (event instanceof ApplicationCreated) {
                ApplicationCreated applicationCreated = (ApplicationCreated) event;
                applicationDetails = fillApplicationDetails(applicationCreated);
                break;
            }
        }
        log.info("Application with id: {} ApplicationDetails: {}", id, applicationDetails);
        return applicationDetails;
    }

    private ApplicationDetails fillApplicationDetails(final ApplicationCreated applicationCreated) {
        ApplicationDetails applicationDetails =
                ApplicationDetails.builder().applicationId(applicationCreated.getApplicationId()).build();
        if (applicationCreated.getRetailerConf() != null) {
            RetailerConfigurationBean retailerConfiguration = applicationCreated.getRetailerConf();
            applicationDetails.setRetailerBlacklisted(retailerConfiguration.isBlacklisted());
            if (retailerConfiguration.getRisk() != null) {
                RiskBean riskBean = retailerConfiguration.getRisk();
                if (riskBean.getIndustry() != null) {
                    applicationDetails.setIndustry(riskBean.getIndustry().toString());
                }
            }
        }
        return applicationDetails;
    }

    @Override
    public String getApplicationPresentation(String id) {
        List<StoredEvent> events = getEvents(id);
        ApplicationBean applicationBean = applicationDataRetriever.retrieveValues(events);
        return applicationBeanPresenter.toHtmlPresentation(applicationBean);
    }

    private List<StoredEvent> getEvents(String id) {
        List<StoredEvent> events = repository.findByAggregateIdOrderBySeqAsc(id);
        if (events.isEmpty()) {
            log.error("No events were found for application with [{}]", id);
            String message = "there is no application with id: [" + id + "]";
            throw new WebApplicationException(message, Response.Status.NOT_FOUND);
        }
        return events;
    }
}