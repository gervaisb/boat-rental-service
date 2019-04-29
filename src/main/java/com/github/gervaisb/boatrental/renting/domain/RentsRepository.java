package com.github.gervaisb.boatrental.renting.domain;

/**
 * @author Blaise Gervais
 */
public interface RentsRepository {
    Rent getById(RentId id);

    Rent save(Rent rent);
}
