package io.fourfinance.pos.dataprovider.service.client.events

import io.fourfinance.pos.dataprovider.service.client.details.ApplicationBean
import io.fourfinanceit.pos.application.events.ApplicationStateChanged.ApplicationCreated
import io.fourfinanceit.pos.application.events.instantcredit.CreditRequestReceived
import io.fourfinanceit.pos.dtos.RetailerConfigurationBean
import io.fourfinanceit.pos.gateway.dtos.Address
import io.fourfinanceit.pos.gateway.dtos.application.Application
import io.fourfinanceit.pos.gateway.dtos.application.ApplicationDetails
import io.fourfinanceit.pos.gateway.dtos.application.ClientData
import io.fourfinanceit.pos.gateway.dtos.application.LoanSpec
import io.fourfinanceit.pos.gateway.dtos.instantcredit.CreditRequest
import io.fourfinanceit.pos.gateway.dtos.instantcredit.EndUser
import io.fourfinanceit.pos.gateway.dtos.merchant.Order
import spock.lang.Specification

class EventProcessorSpec extends Specification {

    private static final String EMAIL = "jon.doe@gmail.com"
    private static final String NAME = "Jon"

    private final EventProcessor eventProcessor = new EventProcessor()

    def "Should populate application bean from non-existing event"() {
        given:
            Object nonExistingEvent = new Object()
            ApplicationBean clientDetails = new ApplicationBean()

        when:
            eventProcessor.populate(clientDetails, nonExistingEvent)

        then:
            clientDetails.getAddress() == null
            clientDetails.getApplicationDetails() == null
            clientDetails.getClientData() == null
            clientDetails.getEndUser() == null
            clientDetails.getOrder() == null
    }

    def "Should populate application bean from instant credit event"() {
        given:
            EndUser endUser = Mock(EndUser) {
                getEmail() >> EMAIL
                getFirstName() >> NAME
            }
            CreditRequest request = Mock(CreditRequest) {
                getEndUser() >> endUser
            }
            Object instantCreditEvent = new CreditRequestReceived(request)
            ApplicationBean clientDetails = new ApplicationBean()

        when:
            eventProcessor.populate(clientDetails, instantCreditEvent)

        then:
            clientDetails.getAddress() == null
            clientDetails.getApplicationDetails() == null
            clientDetails.getClientData() == null
            clientDetails.getOrder() == null

            EndUser actualEndUser = clientDetails.getEndUser()
            actualEndUser != null
            actualEndUser.getFirstName() == NAME
            actualEndUser.getEmail() == EMAIL
    }

    def "Should populate application bean from non instant credit merchant application created event"() {
        given:
            RetailerConfigurationBean retailConfig = Mock(RetailerConfigurationBean)
            Application application = Mock(Application) {
                getClientData() >> Mock(ClientData) {
                    getEmail() >> EMAIL
                    getFirstName() >> NAME
                }
                getOrder() >> Mock(Order)
                getAddress() >> Mock(Address)
                getLoanSpec() >> Mock(LoanSpec)
                getDetails() >> Mock(ApplicationDetails)
            }

            Object instantCreditEvent = new ApplicationCreated("applicationId", "retailerId", application, retailConfig)
            ApplicationBean applicationBean = new ApplicationBean()

        when:
            eventProcessor.populate(applicationBean, instantCreditEvent)

        then:
            applicationBean.getEndUser() == null
            applicationBean.getAddress() != null
            applicationBean.getApplicationDetails() != null
            applicationBean.getOrder() != null

            ClientData clientData = applicationBean.getClientData()
            clientData != null
            clientData.getFirstName() == NAME
            clientData.getEmail() == EMAIL
    }
}
