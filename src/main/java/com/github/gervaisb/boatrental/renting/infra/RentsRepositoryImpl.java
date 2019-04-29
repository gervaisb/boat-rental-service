package com.github.gervaisb.boatrental.renting.infra;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.github.gervaisb.boatrental.renting.domain.Customer;
import com.github.gervaisb.boatrental.renting.domain.Rent;
import com.github.gervaisb.boatrental.renting.domain.RentId;
import com.github.gervaisb.boatrental.renting.domain.RentsRepository;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RentsRepositoryImpl implements RentsRepository {

    @Autowired
    private Support support;

    @Override
    public Rent getById(RentId id) {
        return support.findByRentId(id.value).unwrap();
    }

    @Override
    public Rent save(Rent rent) {
        return support.save(new RentRecord(rent)).unwrap();
    }

}

@Data @Entity(name = "rents")
class RentRecord {
    @Id @GeneratedValue
    Long id;

    String rentId;
    String customerFirstName;
    String customerLastName;
    LocalDateTime date;

    public RentRecord(Rent rent) {
        this.rentId = rent.id.value;
        this.customerFirstName = rent.getCustomer().getFirstName();
        this.customerLastName = rent.getCustomer().getLastName();
        this.date = rent.getStart();
    }

    Rent unwrap() {
        return new Rent(
                new RentId(rentId),
                new Customer(customerFirstName, customerLastName),
                date
        );
    }
}


@Repository
interface Support extends CrudRepository<RentRecord, Long> {
    RentRecord findByRentId(String id);
}
