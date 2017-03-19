package io.fourfinance.pos.dataprovider.service.client.events;

import io.fourfinance.pos.dataprovider.service.client.details.ApplicationBean;
import io.fourfinanceit.pos.application.events.ApplicationAddressUpdated;
import io.fourfinanceit.pos.application.events.ApplicationClientUpdated;
import io.fourfinanceit.pos.application.events.ApplicationDetailsUpdated;
import io.fourfinanceit.pos.application.events.ApplicationOrderUpdated;
import io.fourfinanceit.pos.application.events.ApplicationStateChanged.ApplicationCreated;
import io.fourfinanceit.pos.application.events.instantcredit.CreditRequestReceived;
import io.fourfinanceit.pos.gateway.dtos.application.Application;
import org.springframework.stereotype.Component;

@Component
public class EventProcessor {

    public void populate(ApplicationBean applicationBean, Object event) {
        if (event instanceof ApplicationCreated) {
            retrieve(applicationBean, (ApplicationCreated) event);
        } else if (event instanceof ApplicationAddressUpdated) {
            retrieve(applicationBean, (ApplicationAddressUpdated) event);
        } else if (event instanceof ApplicationClientUpdated) {
            retrieve(applicationBean, (ApplicationClientUpdated) event);
        } else if (event instanceof ApplicationDetailsUpdated) {
            retrieve(applicationBean, (ApplicationDetailsUpdated) event);
        } else if (event instanceof ApplicationOrderUpdated) {
            retrieve(applicationBean, (ApplicationOrderUpdated) event);
        } else if (event instanceof CreditRequestReceived) {
            retrieve(applicationBean, (CreditRequestReceived) event);
        }
    }

    private void retrieve(ApplicationBean applicationBean, ApplicationCreated event) {
        retrieveFromApplication(applicationBean, event.getApplication());
    }

    private void retrieve(ApplicationBean applicationBean, ApplicationAddressUpdated event) {
        applicationBean.setAddress(event.getAddress());
    }

    private void retrieve(ApplicationBean applicationBean, ApplicationClientUpdated event) {
        applicationBean.setClientData(event.getClient());
    }

    private void retrieve(ApplicationBean applicationBean, ApplicationDetailsUpdated event) {
        applicationBean.setApplicationDetails(event.getDetails());
    }

    private void retrieve(ApplicationBean applicationBean, ApplicationOrderUpdated event) {
        applicationBean.setOrder(event.getOrder());
    }

    private void retrieve(ApplicationBean applicationBean, CreditRequestReceived event) {
        applicationBean.setEndUser(event.getRequest().getEndUser());
    }

    private void retrieveFromApplication(ApplicationBean applicationBean, Application application) {
        applicationBean.setOrder(application.getOrder());
        applicationBean.setAddress(application.getAddress());
        applicationBean.setClientData(application.getClientData());
        applicationBean.setApplicationDetails(application.getDetails());
    }
}
