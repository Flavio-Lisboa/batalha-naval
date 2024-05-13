package com.batalhanaval.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Embarcacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] imgBarco1;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] imgBarco2;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] imgBarco3;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] imgBarco4;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] barcosFile;

    @OneToOne()
    @JoinColumn(name="tema_id", referencedColumnName="id",nullable=false)
    private Tema tema;
}
