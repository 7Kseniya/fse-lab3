package se.ifmo.lab4.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="results")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Element {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="results_id_seq")
    @SequenceGenerator(name = "results_id_seq", sequenceName = "results_id_seq", allocationSize = 1)
    private Long id;

    private float x;

    private float y;

    private float r;

    private boolean result;

    private String date;

    private String exec;

    private String creator;
}
