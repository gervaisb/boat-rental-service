package com.github.gervaisb.boatrental.renting.domain;

/**
 * @author Blaise Gervais
 */
class InvalidRentingException extends Throwable {
    public InvalidRentingException(String message) {
        super(message);
    }
}
