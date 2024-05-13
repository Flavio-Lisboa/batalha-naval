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
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] imgAvatar;

    @OneToOne()
    @JoinColumn(name="tema_id", referencedColumnName="id",nullable=false)
    private Tema tema;
}
