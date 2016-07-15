package com.example.domalt.entity;

import org.seasar.doma.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "CUSTOMER_SEQ")
    public Integer id;

    public String name;

    @Version
    public Integer version;

    @Override
    public String toString(){

        return String.format(
                "Customer [id=%s, version=%s, name=%s]",
                id,
                version,
                name
        );
    }
}
