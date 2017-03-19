package io.fourfinance.pos.dataprovider.service

import io.fourfinance.pos.dataprovider.event.StoredEvent
import io.fourfinance.pos.dataprovider.repository.EventsRepository
import io.fourfinance.pos.dataprovider.service.client.ApplicationBeanPresenter
import io.fourfinance.pos.dataprovider.service.client.ApplicationDataRetriever
import io.fourfinanceit.dataprovider.ApplicationResource
import io.fourfinanceit.dataprovider.dto.ApplicationDetails
import spock.lang.Specification

import javax.ws.rs.WebApplicationException

class ApplicationResourceSpec extends Specification {

    private final EventsRepository repository = Mock()
    private ApplicationDataRetriever clientDetailsRetriever = Mock()
    private ApplicationBeanPresenter clientDetailsPresenter = Mock()

    def "no events for application were found" () {
        given:
            String applicationId = "no-application-was-found"
            repository.findByAggregateIdOrderBySeqAsc(applicationId) >> { Collections.emptyList() }
            ApplicationResource applicationResource = new ApplicationResourceImpl(clientDetailsRetriever, clientDetailsPresenter, repository)
        when:
            ApplicationDetails actualResult = applicationResource.get(applicationId)
        then:
            actualResult == null
            WebApplicationException exeption = thrown()
            exeption.response.status == 404
    }

    def "get details for application" () {
        given:
            String applicationId = "35c5a95e229e42cea5a605472cb86e84"
            repository.findByAggregateIdOrderBySeqAsc(applicationId) >> { events }
            ApplicationResource applicationResource = new ApplicationResourceImpl(clientDetailsRetriever, clientDetailsPresenter, repository)
        when:
            ApplicationDetails actualResult = applicationResource.get(applicationId)
        then:
            actualResult
            actualResult.industry == null
            actualResult.applicationId == applicationId
            actualResult.retailerBlacklisted == false
    }

    def "get details for application list" () {
        given:
            List<String> applicationIds = ['35c5a95e229e42cea5a605472cb86e84', 'a5777a55b7744abea0a8490ddf98a877']
            repository.findByAggregateIdOrderBySeqAsc(applicationIds[0]) >> { events }
            repository.findByAggregateIdOrderBySeqAsc(applicationIds[1]) >> { events }
            ApplicationResource applicationResource = new ApplicationResourceImpl(clientDetailsRetriever, clientDetailsPresenter, repository)
        when:
            List<ApplicationDetails> actualResult = applicationResource.getApplications(applicationIds)
        then:
            actualResult
            actualResult.size() == 2
    }

    private List<StoredEvent> events = Arrays.asList(
            StoredEvent.builder()
                    .id("35c5a95e229e42cea5a605472cb86e84")
                    .aggregateId("35c5a95e229e42cea5a605472cb86e84")
                    .origin("local")
                    .type("application.created")
                    .jsonPayload('{"applicationId":"35c5a95e229e42cea5a605472cb86e84","retailerId":"lendismart","application":{"order":{"id":"TEST-allfields","client":{"firstName":"Пенева","lastName":"Андон","lastName2":"Tesla","personalId":"000000123","company":"E&M","email":"nicola.tesla@future.com","phone":"2223311","dateOfBirth":null,"registrationDate":"2015-10-12","orderHistory":[{"amount":200.0,"currency":"EUR","created":"2015-11-12T00:00:00Z","items":[{"name":"Red brick","category":null,"price":10.0001,"quantity":12},{"name":"Socks","category":null,"price":2.99,"quantity":22}],"state":null,"country":null},{"amount":100.0,"currency":"EUR","created":"2016-01-01T00:00:00Z","items":[{"name":"Blue brick","category":null,"price":10.0001,"quantity":5},{"name":"Socks","category":null,"price":2.99,"quantity":10}],"state":null,"country":null}]},"shippingAddress":{"street":"Dark Street","number":"1052","flat":"9","letter":"C","postalCode":"28001","city":"Madrid","province":"dskdkd","country":"Spain"},"billingAddress":{"street":"Blue Street","number":"1092","flat":"5","letter":"A","postalCode":"28001","city":"Madrid","province":"dskdkd","country":"Spain"},"clientIP":"1.2.3.4.5","country":null,"items":[{"name":"Yellow brick","category":null,"price":1000.000001,"quantity":2},{"name":"Pants","category":null,"price":1.99,"quantity":22}],"amount":123.55,"currency":"EUR","created":"2016-10-02T00:00:00Z","callbacks":null},"address":{"street":"Light Street","number":"1022","flat":"8","letter":"D","postalCode":"28001","city":"Madrid","province":"dskdkd","country":"Spain"},"details":{"monthlyIncome":100.0,"monthlyCreditPayments":20.0,"employmentStatus":null},"iovation":{"rawResponse":"asd45wqe12asd45qwe12sas45"},"clientData":{"firstName":"Maria","lastName":"Coil","lastName2":"Tesla","personalId":"95091255D","company":"E&M","email":"nicola.tesla@future.com","phone":"764597543","phone2":null,"dateOfBirth":"1990-10-02","promotions":null},"loanSpec":{"productId":"pos","downpaymentRatio":0,"downpaymentAmount":null,"term":5},"icar":{"rawResponse":"sddlk15dds6sdf8defwe4sf99"}}}')
                    .seq(0)
                    .build(),
            StoredEvent.builder()
                    .id("445dc157-812e-4975-895a-43b1189ceea3")
                    .aggregateId("a5777a55b7744abea0a8490ddf98a877")
                    .origin("local")
                    .type("application.created")
                    .jsonPayload('{"applicationId":"a5777a55b7744abea0a8490ddf98a877","loanOffer":{"interestRate":26.525,"apr":30.0,"downpaymentAmount":0.00,"monthlyAmount":26.37,"totalPayableAmount":131.85,"totalPrincipal":123.55,"totalInterestAmount":8.30,"durationInMonths":5}}')
                    .seq(1)
                    .build()

    )
}
