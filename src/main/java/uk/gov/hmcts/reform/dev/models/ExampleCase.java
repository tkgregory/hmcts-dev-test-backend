package uk.gov.hmcts.reform.dev.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cases")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExampleCase {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String caseNumber;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdDate;
}
