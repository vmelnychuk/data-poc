package io.fourfinance.pos.dataprovider.service.client.details;

import io.fourfinanceit.pos.gateway.dtos.Address;
import io.fourfinanceit.pos.gateway.dtos.application.ApplicationDetails;
import io.fourfinanceit.pos.gateway.dtos.application.ClientData;
import io.fourfinanceit.pos.gateway.dtos.instantcredit.EndUser;
import io.fourfinanceit.pos.gateway.dtos.merchant.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationBean {

    private Order order;
    private Address address;
    private ApplicationDetails applicationDetails;
    private ClientData clientData;

    private EndUser endUser;

}
