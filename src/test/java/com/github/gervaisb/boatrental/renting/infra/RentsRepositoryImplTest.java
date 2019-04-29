package com.github.gervaisb.boatrental.renting.infra;

import java.time.LocalDateTime;

import com.github.gervaisb.boatrental.renting.domain.Customer;
import com.github.gervaisb.boatrental.renting.domain.Rent;
import com.github.gervaisb.boatrental.renting.domain.RentId;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = RentsRepositoryImpl.class)
public class RentsRepositoryImplTest {

    @Autowired
    private RentsRepositoryImpl subject;


    @Test
    public void getById_must_return_rent_for_id() {
        LocalDateTime dateTime = LocalDateTime.now();
        Rent rent = subject.save(
                new Rent(new RentId(), new Customer("Fake", "Customer"), dateTime)
        );

        assertThat(subject.getById(rent.id))
                .hasFieldOrPropertyWithValue("id", rent.id)
                .hasFieldOrPropertyWithValue("Customer.FirstName", "Fake")
                .hasFieldOrPropertyWithValue("Customer.LastName", "Customer")
                .hasFieldOrPropertyWithValue("Start", dateTime);
    }
}
