package pact.consumer;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "IncidentDetails")
public class ConsumerTest {

    public String PathVal = "/api/dv-prc-finctrl-trsy-api/1/incidents/1";

    @Autowired
    @Pact(consumer = "IncidentConsumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
            .given("incident details are available")
            .uponReceiving("a request for incident details")
                .path(PathVal)
                .method("GET")
            .willRespondWith()
                .status(200)
                .headers(Collections.singletonMap("Content-Type", "application/json;charset=UTF-8"))
                .body(new PactDslJsonBody()
                    .integerType("incidentId", 1)
                    .stringValue("programme", "Nestlé Global Property and Liability")
                    .stringValue("policyType", "General Liability (Public)")
                    .stringValue("policyCountry", "France")
                    .stringValue("incidentStatus", "Incident Only")
                    .stringValue("fnolReference", "Test123")
                    .stringType("notifiedDate", "2025-04-29")
                    .stringType("incidentDate", "2025-04-25")
                    .stringValue("description", "Test Incident")
                    .minArrayLike("extendedFields", 1)
                        .stringType("identifier", "Global_Incident_Form:Summary_Claim_Type")
                        .stringType("value", null)
                    .closeArray()
                )
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createPact")
    void testGetIncident(MockServer mockServer) {
        String url = mockServer.getUrl() + PathVal;
        Incident incident = new RestTemplate().getForObject(url, Incident.class);

        Assertions.assertEquals(1, incident.getIncidentId());
        Assertions.assertEquals("Nestlé Global Property and Liability", incident.getProgramme());
        Assertions.assertEquals("General Liability (Public)", incident.getPolicyType());
        Assertions.assertEquals("France", incident.getPolicyCountry());
        Assertions.assertEquals("Incident Only", incident.getIncidentStatus());
        Assertions.assertEquals("Test123", incident.getFnolReference());
        Assertions.assertEquals("2025-04-29", incident.getNotifiedDate());
        Assertions.assertEquals("2025-04-25", incident.getIncidentDate());
        Assertions.assertEquals("Test Incident", incident.getDescription());

        Assertions.assertNotNull(incident.getExtendedFields());
        Assertions.assertEquals(1, incident.getExtendedFields().size());
        Assertions.assertEquals("Global_Incident_Form:Summary_Claim_Type", incident.getExtendedFields().get(0).getIdentifier());
        Assertions.assertNull(incident.getExtendedFields().get(0).getValue());
    }

    // Incident class
    static class Incident {
        private int incidentId;
        private String programme;
        private String policyType;
        private String policyCountry;
        private String incidentStatus;
        private String fnolReference;
        private String notifiedDate;
        private String incidentDate;
        private String description;
        private List<ExtendedField> extendedFields;

        // Getters and setters
        public int getIncidentId() {
            return incidentId;
        }

        public void setIncidentId(int incidentId) {
            this.incidentId = incidentId;
        }

        public String getProgramme() {
            return programme;
        }

        public void setProgramme(String programme) {
            this.programme = programme;
        }

        public String getPolicyType() {
            return policyType;
        }

        public void setPolicyType(String policyType) {
            this.policyType = policyType;
        }

        public String getPolicyCountry() {
            return policyCountry;
        }

        public void setPolicyCountry(String policyCountry) {
            this.policyCountry = policyCountry;
        }

        public String getIncidentStatus() {
            return incidentStatus;
        }

        public void setIncidentStatus(String incidentStatus) {
            this.incidentStatus = incidentStatus;
        }

        public String getFnolReference() {
            return fnolReference;
        }

        public void setFnolReference(String fnolReference) {
            this.fnolReference = fnolReference;
        }

        public String getNotifiedDate() {
            return notifiedDate;
        }

        public void setNotifiedDate(String notifiedDate) {
            this.notifiedDate = notifiedDate;
        }

        public String getIncidentDate() {
            return incidentDate;
        }

        public void setIncidentDate(String incidentDate) {
            this.incidentDate = incidentDate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ExtendedField> getExtendedFields() {
            return extendedFields;
        }

        public void setExtendedFields(List<ExtendedField> extendedFields) {
            this.extendedFields = extendedFields;
        }
    }

    // ExtendedField class
    static class ExtendedField {
        private String identifier;
        private String value;

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
