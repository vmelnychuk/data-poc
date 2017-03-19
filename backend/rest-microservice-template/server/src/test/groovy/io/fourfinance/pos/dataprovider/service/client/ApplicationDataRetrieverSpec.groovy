package io.fourfinance.pos.dataprovider.service.client

import io.fourfinance.pos.dataprovider.event.StoredEvent
import io.fourfinance.pos.dataprovider.repository.EventsRepository
import io.fourfinance.pos.dataprovider.service.client.details.ApplicationBean
import io.fourfinance.pos.dataprovider.service.client.events.EventProcessor
import io.fourfinanceit.pos.gateway.dtos.Address
import io.fourfinanceit.pos.gateway.dtos.application.ApplicationDetails
import io.fourfinanceit.pos.gateway.dtos.application.ClientData
import io.fourfinanceit.pos.gateway.dtos.merchant.MerchantClient
import io.fourfinanceit.pos.gateway.dtos.merchant.Order
import spock.lang.Specification

import java.time.LocalDate

class ApplicationDataRetrieverSpec extends Specification {

    private static final String AGREGATE_ID = "0964a062693d45b78082184866347f19"
    private static final String ORIGIN = "local"

    private static long sequence = 0L

    private final EventsRepository eventsRepository = Mock(EventsRepository) {

        findByAggregateIdOrderBySeqAsc(_) >> Collections.emptyList()
    }

    private final EventProcessor eventProcessor = new EventProcessor()
    private
    final ApplicationDataRetriever applicationDataRetriever = new ApplicationDataRetriever(eventProcessor, eventsRepository)


    def "Should retrieve application data from events"() {
        given:
            List<StoredEvent> events = prepareEvents()

        when:
            ApplicationBean applicationBean = applicationDataRetriever.retrieveValues(events)

        then:
            applicationBean.getEndUser() == null
            Order order = applicationBean.getOrder()
            order != null
            order.getId() == "3711"
            order.getCountry() == "ES"
            order.getAmount() == new BigDecimal("950.00")
            order.getCurrency() == "EUR"

            MerchantClient merchantClient = order.getClient()
            merchantClient != null
            merchantClient.getRegistrationDate() == LocalDate.of(2013, 11, 11)
            merchantClient.getOrderHistory().size() == 3

            Address address = applicationBean.getAddress()
            address != null
            address.getStreet() == "Diagonal 213"
            address.getPostalCode() == "08080"
            address.getCity() == "Barcelona"

            ApplicationDetails applicationDetails = applicationBean.getApplicationDetails()
            applicationDetails != null
            applicationDetails.getEmploymentStatus() == "EMPLOYED"
            applicationDetails.getMonthlyIncome() == new BigDecimal("2500")

            ClientData clientData = applicationBean.getClientData()
            clientData != null
            clientData.getFirstName() == "Carmen Carmen"
            clientData.getLastName() == "Espanola Espanola"
            clientData.getPersonalId() == "99999999R"
            clientData.getDateOfBirth() == LocalDate.of(1980, 1, 1)
    }

    private List<StoredEvent> prepareEvents() {
        List<StoredEvent> events = new ArrayList<>()
        events.add(StoredEvent.builder()
                .aggregateId(AGREGATE_ID)
                .id("d00305e3-4af8-4b01-a7a2-be69539f6ab3")
                .seq(sequence++)
                .origin(ORIGIN)
                .type("application.created")
                .jsonPayload("""{"applicationId":"0964a062693d45b78082184866347f19","retailerId":"ic.22","application":{"order":{"id":"3711","client":{"firstName":null,"lastName":null,"lastName2":null,"personalId":null,"company":null,"email":null,"phone":null,"dateOfBirth":null,"registrationDate":"2013-11-11","orderHistory":[{"amount":24.01,"currency":"ES","created":"2016-07-09T09:11:11Z","items":null,"state":"AUTHORIZED","country":"ES"},{"amount":128.50,"currency":"ES","created":"2016-04-12T09:11:11Z","items":null,"state":"AUTHORIZED","country":"ES"},{"amount":920.10,"currency":"ES","created":"2016-11-14T10:11:11Z","items":null,"state":"AUTHORIZED","country":"ES"}]},"shippingAddress":null,"billingAddress":null,"clientIP":"","country":"ES","items":null,"amount":950.00,"purchasedTotalAmount":null,"currency":"EUR","created":null,"callbacks":null},"address":{"street":"Diagonal 213","number":"","flat":null,"letter":null,"postalCode":"08080","city":"Barcelona","province":"Barcelona","country":"ES"},"details":{"monthlyIncome":2500,"monthlyCreditPayments":null,"employmentStatus":"EMPLOYED","industry":null,"profession":null},"iovation":{"rawResponse":"<?xml version=\\"1.0\\" encoding=\\"utf-8\\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\\"http://schemas.xmlsoap.org/soap/envelope/\\" xmlns:SOAP-ENC=\\"http://schemas.xmlsoap.org/soap/encoding/\\" xmlns:xsd=\\"http://www.w3.org/2001/XMLSchema\\" xmlns:xsi=\\"http://www.w3.org/2001/XMLSchema-instance\\" SOAP-ENV:encodingStyle=\\"http://schemas.xmlsoap.org/soap/encoding/\\"><SOAP-ENV:Body><namesp1:CheckTransactionDetailsResponse xmlns:namesp1=\\"http://www.iesnare.com/dra/api/CheckTransactionDetails\\"><namesp1:result xsi:type=\\"xsd:string\\">A</namesp1:result><namesp1:reason xsi:type=\\"xsd:string\\"/><namesp1:trackingnumber xsi:type=\\"xsd:string\\">348973180120182724</namesp1:trackingnumber><namesp1:endblackbox xsi:type=\\"xsd:string\\">bLqMq9zYqKdX1uPVIwsfS8PUJtpEH5G3C5CZ5K+OvREd93xlaFqVOUa0uiGeBHB4lnxk++77IWVwxfJmHba9MSb5o2093hHy4reNln5KUY1WOriaVrVoWQ==</namesp1:endblackbox><namesp1:details><namesp1:detail><namesp1:name>device.alias</namesp1:name><namesp1:value>810017018272285652</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.bb.age</namesp1:name><namesp1:value>134</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.bb.timestamp</namesp1:name><namesp1:value>2017-01-30T12:35:34Z</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.browser.configuredlang</namesp1:name><namesp1:value>EN-US,EN;Q=0.8</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.browser.lang</namesp1:name><namesp1:value>EN-US</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.browser.type</namesp1:name><namesp1:value>CHROME</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.browser.version</namesp1:name><namesp1:value>53.0.2785.116</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.cookie.enabled</namesp1:name><namesp1:value>1</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.firstseen</namesp1:name><namesp1:value>2016-09-23T08:24:14.935Z</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.flash.enabled</namesp1:name><namesp1:value>0</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.flash.installed</namesp1:name><namesp1:value>1</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.flash.version</namesp1:name><namesp1:value>24.0.0</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.js.enabled</namesp1:name><namesp1:value>1</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.new</namesp1:name><namesp1:value>0</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.os</namesp1:name><namesp1:value>WINDOWS NT 6.1</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.screen</namesp1:name><namesp1:value>1080X1920</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.type</namesp1:name><namesp1:value>WINDOWS</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>device.tz</namesp1:name><namesp1:value>-60</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>ipaddress</namesp1:name><namesp1:value>195.122.202.194</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>realipaddress</namesp1:name><namesp1:value>195.122.202.194</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>realipaddress.source</namesp1:name><namesp1:value>subscriber</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>ruleset.rulesmatched</namesp1:name><namesp1:value>0</namesp1:value></namesp1:detail><namesp1:detail><namesp1:name>ruleset.score</namesp1:name><namesp1:value>0</namesp1:value></namesp1:detail></namesp1:details></namesp1:CheckTransactionDetailsResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>"},"clientData":{"firstName":"Carmen Carmen","lastName":"Espanola Espanola","lastName2":null,"personalId":"99999999R","company":null,"email":"martin.lamr@4finance.com","phone":"782123456","phone2":"964556699","dateOfBirth":"1980-01-01","promotions":null,"bankAccount":null},"loanSpec":{"productId":"TODO","downpaymentRatio":null,"downpaymentAmount":89.03,"term":11},"icar":{"rawResponse":"{\\"AnalyzeDocumentV2ExResult\\":{\\"Fields\\":{\\"Field\\":[{\\"Value\\":\\"IDENTITY\\",\\"Code\\":\\"TYPE\\"},{\\"Value\\":\\"0\\",\\"Code\\":\\"SIDE\\"},{\\"Value\\":\\"ESP\\",\\"Code\\":\\"EXPEDITOR\\"},{\\"Value\\":\\"ESP\\",\\"Code\\":\\"NATIONALITY\\"},{\\"Value\\":\\"CARMEN\\",\\"Code\\":\\"NAME\\"},{\\"Value\\":\\"ESPAÑOLA\\",\\"Code\\":\\"SURNAME\\"},{\\"Value\\":\\"ESPAÑOLA\\",\\"Code\\":\\"SURNAME\\"},{\\"Value\\":\\"99999999R\\",\\"Code\\":\\"ID_NUMBER\\"},{\\"Value\\":\\"BAA000589\\",\\"Code\\":\\"DOC_NUMBER\\"},{\\"Value\\":\\"AVADEMADRID N\\",\\"Code\\":\\"STREET_ADDRESS\\"},{\\"Value\\":\\"MA\\",\\"Code\\":\\"CITY_ADDRESS\\"},{\\"Value\\":\\"MA\\",\\"Code\\":\\"STATE_ADDRESS\\"},{\\"Value\\":\\"AVADEMADRID N\\",\\"Code\\":\\"ADDRESS\\"},{\\"Value\\":\\"MA\\",\\"Code\\":\\"ADDRESS\\"},{\\"Value\\":\\"MA\\",\\"Code\\":\\"ADDRESS\\"},{\\"Value\\":\\"MAD\\",\\"Code\\":\\"BIRTHPLACE\\"},{\\"Value\\":\\"MA\\",\\"Code\\":\\"BIRTHPLACE\\"},{\\"Value\\":\\"UAN AMN\\",\\"Code\\":\\"PARENTS\\"},{\\"Value\\":\\"1980-01-01\\",\\"Code\\":\\"BIRTHDATE\\"},{\\"Value\\":\\"2025-01-01\\",\\"Code\\":\\"EXPIRY\\"},{\\"Value\\":\\"F\\",\\"Code\\":\\"SEX\\"},{\\"Value\\":\\"28391A6DK\\",\\"Code\\":\\"AUTHORITY\\"},{\\"Value\\":\\"OK\\",\\"Code\\":\\"TEST_MRZ_GLOBAL_INTEGRITY\\"},{\\"Value\\":\\"OK DOC_NUMBER\\",\\"Code\\":\\"TEST_MRZ_FIELDS_INTEGRITY\\"},{\\"Value\\":\\"OK BIRTHDATE\\",\\"Code\\":\\"TEST_MRZ_FIELDS_INTEGRITY\\"},{\\"Value\\":\\"OK EXPIRY\\",\\"Code\\":\\"TEST_MRZ_FIELDS_INTEGRITY\\"},{\\"Value\\":\\"OK\\",\\"Code\\":\\"TEST_COLOR_IMAGE\\"},{\\"Value\\":\\"OK\\",\\"Code\\":\\"TEST_EXPIRY_DATE\\"},{\\"Value\\":\\"OK NAME\\",\\"Code\\":\\"TEST_CORRESPONDENCE_VISIBLE_MRZ\\"},{\\"Value\\":\\"OK SURNAME\\",\\"Code\\":\\"TEST_CORRESPONDENCE_VISIBLE_MRZ\\"},{\\"Value\\":\\"OK ID_NUMBER\\",\\"Code\\":\\"TEST_CORRESPONDENCE_VISIBLE_MRZ\\"},{\\"Value\\":\\"OK EXPIRY\\",\\"Code\\":\\"TEST_CORRESPONDENCE_VISIBLE_MRZ\\"},{\\"Value\\":\\"OK BIRTHDATE\\",\\"Code\\":\\"TEST_CORRESPONDENCE_VISIBLE_MRZ\\"},{\\"Value\\":\\"OK SEX\\",\\"Code\\":\\"TEST_CORRESPONDENCE_VISIBLE_MRZ\\"},{\\"Value\\":\\"OK\\",\\"Code\\":\\"TEST_SIDE_CORRESPONDENCE\\"},{\\"Value\\":\\"0.990000\\",\\"Code\\":\\"TEST_GLOBAL_AUTHENTICITY_RATIO\\"},{\\"Value\\":\\"OK\\",\\"Code\\":\\"TEST_GLOBAL_AUTHENTICITY_VALUE\\"},{\\"Value\\":\\"IDESPBAA000589599999999R<<<<<<\\",\\"Code\\":\\"MRZ\\"},{\\"Value\\":\\"8001014F2501017ESP<<<<<<<<<<<7\\",\\"Code\\":\\"MRZ\\"},{\\"Value\\":\\"ESPANOLA<ESPANOLA<<CARMEN<<<<<\\",\\"Code\\":\\"MRZ\\"},{\\"Value\\":\\"IDESPE2\\",\\"Code\\":\\"MODEL_ID\\"},{\\"Value\\":\\"2\\",\\"Code\\":\\"SIDES_NUMBER\\"}]},\\"Result\\":\\"Correct\\"},\\"RequestReference\\":\\"agdup8li5duucvubgt200fqceoppujsr3tm04d8fd96dl91puehgphp1lmdh0m77borl513bfkak9ge7\\",\\"MessageType\\":\\"Result\\"}"}},"retailerConf":{"id":"ic.22","name":"Merchant1","alias":"Miro.es","partner":"instantCredit","url":null,"email":"merchant1@fakemail.com","companyNumber":"1234444","address":"Barcelona 111, 222, 333","bankAccountNumber":null,"products":["ic"],"disableNotifications":false,"documents":{"product":"pos-ic","mapping":{"pre_contractual_conditions":["special-terms","secci"],"general_and_private_terms":["terms-and-conditions","privacy-policy"]}},"risk":{"highTicketThreshold":500,"industry":"PHONE_TABLET_ONLY"},"blacklisted":false}}""")
                .build())
        events.add(StoredEvent.builder()
                .aggregateId(AGREGATE_ID)
                .id("e4f717f5-9190-443a-9724-4e7e7ca14ea2")
                .seq(sequence++)
                .origin(ORIGIN)
                .type("application.client.number.updated")
                .jsonPayload("""{"applicationId":"0964a062693d45b78082184866347f19","clientNumber":"997919"}""")
                .build())
        return events
    }
}
