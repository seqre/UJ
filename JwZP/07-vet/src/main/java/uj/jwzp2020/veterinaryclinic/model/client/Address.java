package uj.jwzp2020.veterinaryclinic.model.client;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

@Data
@Embeddable
@Table(name = "address")
public class Address {

    @Column(nullable = false, length = 96)
    private String street;

    @Column(nullable = false)
    private Integer parcelNumber;

    private Integer flatNumber;

    @Column(nullable = false, length = 64)
    private String city;

    @Column(nullable = false, length = 8)
    private String zipcode;

    @Column(nullable = false, length = 64)
    private String country;
}
