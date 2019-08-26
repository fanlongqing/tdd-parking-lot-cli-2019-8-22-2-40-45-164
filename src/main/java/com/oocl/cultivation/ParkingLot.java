package com.oocl.cultivation;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private final int capacity;
    private int id;
    private Map<ParkingTicket, Car> cars = new HashMap<>();

    public ParkingLot() {
        this(10);
    }

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    public ParkingLot(int capacity, int id) {
        this.capacity = capacity;
        this.id = id;
    }

    public int getAvailableParkingPosition() {
        return  capacity - cars.size();
    }

    public ParkingTicket park(Car car) {
        ParkingTicket ticket=new ParkingTicket();
        cars.put(ticket,car);
        return ticket;
    }

    public Car fetch(ParkingTicket ticket) {
        // TODO Auto-generated method stub
        if (!cars.containsKey(ticket)) {
            return null;
        }
        return cars.remove(ticket);
    }

    public int getCapacity() {
        return capacity;
    }

    public Map<ParkingTicket, Car> getCars() {
        return cars;
    }

    public void setCars(Map<ParkingTicket, Car> cars) {
        this.cars = cars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
