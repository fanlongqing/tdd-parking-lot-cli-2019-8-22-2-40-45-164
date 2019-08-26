package com.oocl.cultivation;

import java.util.List;

public class Smartparkingboy {

    private  ParkingLot parkingLot;
    private String lastErrorMessage;

    private List<ParkingLot> parkingLotList;
    public Smartparkingboy(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public Smartparkingboy(List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
    }

    public ParkingTicket park(Car car) {
        // TODO: Please implement the method
        //throw new RuntimeException("Not implemented");
        if (car != null&&parkingLot.getAvailableParkingPosition()>0) {
            ParkingTicket parkingTicket = new ParkingTicket();
            parkingLot.getCars().put(parkingTicket, car);
            lastErrorMessage = null;
            return parkingTicket;
        }else if(parkingLot.getAvailableParkingPosition()<=0){
            lastErrorMessage = "The parking lot is full.";
            return null;
        }else{
            return null;
        }
    }

    public Car fetch(ParkingTicket ticket) {
        // TODO: Please implement the method
        //throw new RuntimeException("Not implemented");
        if (ticket != null && parkingLot.getCars().get(ticket) != null) {
            Car car = this.parkingLot.getCars().get(ticket);
            parkingLot.getCars().remove(ticket);
            lastErrorMessage = null;
            return car;
        }else if(ticket==null){
            lastErrorMessage="Please provide your parking ticket.";
            return null;
        }else{
            lastErrorMessage = "Unrecognized parking ticket.";
            return null;
        }

    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
