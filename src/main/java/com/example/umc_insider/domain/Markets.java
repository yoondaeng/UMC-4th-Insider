package com.example.umc_insider.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "markets")
public class Markets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; // PK

    @ManyToOne
    @JoinColumn(name = "areas_id")
    private Users areas_id; // PK, FK

    @Column(nullable = true)
    private String name;

}
