package pact.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/dv-prc-finctrl-trsy-api/1/incidents")
public class IncidentController {

    @GetMapping("/{incidentId}")
    public ResponseEntity<IncidentResponse> getIncident(@PathVariable long incidentId) {
        if (incidentId != 1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incident not found");
        }

        IncidentResponse incidentResponse = new IncidentResponse(
            incidentId,
            "Nestlé Global Property and Liability",
            "General Liability (Public)",
            "France",
            "Incident Only",
            "Test123",
            LocalDate.of(2025, 4, 29),
            LocalDate.of(2025, 4, 25),
            "Test Incident",
            Arrays.asList(
                new ExtendedField("Global_Incident_Form:Summary_Claim_Type", null),
                new ExtendedField("Global_Incident_Form:Summary_Incident_Status", "Incident Only"),
                new ExtendedField("Global_Incident_Form:DelegatedAuthorityExceeded", "false"),
                new ExtendedField("Global_Incident_Form:When_Incident_Was_Closed", null)
            )
        );

        // Return response with the application/json content type header
        return ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8")
                             .body(incidentResponse);
    }

    static class IncidentResponse {
        private long incidentId;
        private String programme;
        private String policyType;
        private String policyCountry;
        private String incidentStatus;
        private String fnolReference;
        private LocalDate notifiedDate;
        private LocalDate incidentDate;
        private String description;
        private List<ExtendedField> extendedFields;

        public IncidentResponse(long incidentId, String programme, String policyType,
                                String policyCountry, String incidentStatus, String fnolReference,
                                LocalDate notifiedDate, LocalDate incidentDate, String description,
                                List<ExtendedField> extendedFields) {
            this.incidentId = incidentId;
            this.programme = programme;
            this.policyType = policyType;
            this.policyCountry = policyCountry;
            this.incidentStatus = incidentStatus;
            this.fnolReference = fnolReference;
            this.notifiedDate = notifiedDate;
            this.incidentDate = incidentDate;
            this.description = description;
            this.extendedFields = extendedFields;
        }

        // Getters (needed for JSON serialization)
        public long getIncidentId() { return incidentId; }
        public String getProgramme() { return programme; }
        public String getPolicyType() { return policyType; }
        public String getPolicyCountry() { return policyCountry; }
        public String getIncidentStatus() { return incidentStatus; }
        public String getFnolReference() { return fnolReference; }
        public LocalDate getNotifiedDate() { return notifiedDate; }
        public LocalDate getIncidentDate() { return incidentDate; }
        public String getDescription() { return description; }
        public List<ExtendedField> getExtendedFields() { return extendedFields; }
    }

    static class ExtendedField {
        private String identifier;
        private String value;

        public ExtendedField(String identifier, String value) {
            this.identifier = identifier;
            this.value = value;
        }

        // Getters
        public String getIdentifier() { return identifier; }
        public String getValue() { return value; }
    }
}
