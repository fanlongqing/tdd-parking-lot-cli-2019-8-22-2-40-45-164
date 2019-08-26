package com.oocl.cultivation.test;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkingBoyFacts {
    @Test
    void should_park_a_car_to_a_parking_lot_and_get_it_back() {
        ParkingLot parkingLot = new ParkingLot();//创建一个停车场
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);//创建一个在停车场的停车男孩
        Car car = new Car();//获得一辆车

        ParkingTicket ticket = parkingBoy.park(car);//停车男孩将车停好并获取停车票
        Car fetched = parkingBoy.fetch(ticket);//由停车票获取车

        assertSame(fetched, car);//期望
    }

    @Test
    void should_park_multiple_cars_to_a_parking_lot_and_get_them_back() {
        ParkingLot parkingLot = new ParkingLot();//创建一个停车场
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);//创建一个在停车场的停车男孩
        Car firstCar = new Car();//获取第一辆车
        Car secondCar = new Car();//获取第二辆车

        ParkingTicket firstTicket = parkingBoy.park(firstCar);//获得第一辆车的停车票
        ParkingTicket secondTicket = parkingBoy.park(secondCar);//获得第二辆车的停车票

        Car fetchedByFirstTicket = parkingBoy.fetch(firstTicket);//由第一辆车的停车票获得第一辆车
        Car fetchedBySecondTicket = parkingBoy.fetch(secondTicket);//由第二辆车的停车票获得第二辆车

        assertSame(firstCar, fetchedByFirstTicket);//判断
        assertSame(secondCar, fetchedBySecondTicket);
    }

    @Test
    void should_not_fetch_any_car_once_ticket_is_wrong() {
        ParkingLot parkingLot = new ParkingLot();//创建一个停车场
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);//创建一个在停车场的停车男孩
        Car car = new Car();//获取第一辆车
        ParkingTicket wrongTicket = new ParkingTicket();//一辆没有车的票

        ParkingTicket ticket = parkingBoy.park(car);//停车男孩将车停好并获取停车票

        assertNull(parkingBoy.fetch(wrongTicket));//判断是否为空
        assertSame(car, parkingBoy.fetch(ticket));
    }

    @Test
    void should_query_message_once_the_ticket_is_wrong() {
        ParkingLot parkingLot = new ParkingLot();//创建一个停车场
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);//创建一个在停车场的停车男孩
        ParkingTicket wrongTicket = new ParkingTicket();//一辆没有车的票

        parkingBoy.fetch(wrongTicket);//取车
        String message = parkingBoy.getLastErrorMessage();//获取错误信息

        assertEquals("Unrecognized parking ticket.", message);
    }

    @Test
    void should_clear_the_message_once_the_operation_is_succeeded() {
        ParkingLot parkingLot = new ParkingLot();//创建一个停车场
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);//创建一个在停车场的停车男孩
        ParkingTicket wrongTicket = new ParkingTicket();//一辆没有车的票

        parkingBoy.fetch(wrongTicket);//取车
        assertNotNull(parkingBoy.getLastErrorMessage());//判定错误信息

        ParkingTicket ticket = parkingBoy.park(new Car());//停一辆车
        assertNotNull(ticket);
        assertNull(parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_not_fetch_any_car_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();//创建一个停车场
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);//创建一个在停车场的停车男孩
        Car car = new Car();//创建一辆车

        ParkingTicket ticket = parkingBoy.park(car);//获取车票

        assertNull(parkingBoy.fetch(null));//获取是空
        assertSame(car, parkingBoy.fetch(ticket));//判断
    }

    @Test
    void should_query_message_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();//创建一个停车场
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);//创建一个在停车场的停车男孩

        parkingBoy.fetch(null);//获取是空

        assertEquals(
            "Please provide your parking ticket.",
            parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_not_fetch_any_car_once_ticket_has_been_used() {
        ParkingLot parkingLot = new ParkingLot();//创建一个停车场
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);//创建一个在停车场的停车男孩
        Car car = new Car();//获得车

        ParkingTicket ticket = parkingBoy.park(car);//获得票
        parkingBoy.fetch(ticket);//取车

        assertNull(parkingBoy.fetch(ticket));
    }

    @Test
    void should_query_error_message_for_used_ticket() {
        ParkingLot parkingLot = new ParkingLot();//创建一个停车场
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);//创建一个在停车场的停车男孩
        Car car = new Car();//获得车

        ParkingTicket ticket = parkingBoy.park(car);//获得票
       //多次取车
        parkingBoy.fetch(ticket);//取车
        parkingBoy.fetch(ticket);//取车

        assertEquals(
            "Unrecognized parking ticket.",
            parkingBoy.getLastErrorMessage()
        );
    }

    @Test
    void should_not_park_cars_to_parking_lot_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);//停车场容量
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);//停车男孩

        parkingBoy.park(new Car());//停车

        assertNull(parkingBoy.park(new Car()));//取票
    }

    @Test
    void should_get_message_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);//停车场容量
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);//停车男孩

        parkingBoy.park(new Car());//停车
        parkingBoy.park(new Car());//停车

        assertEquals("The parking lot is full.", parkingBoy.getLastErrorMessage());
    }


   // void should_
    @Test
    void should_parking_to_secondParkingLot_if_firstParkingLot_in_full_position(){
        //given
        ParkingLot firstParkingLot = new ParkingLot();
        firstParkingLot.setId(1);//停车场编号
        ParkingLot secondParkingLot = new ParkingLot();
        secondParkingLot.setId(2);
        List<ParkingLot> parkingLots = new ArrayList<>();//集合，放的是两个停车场
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);
        Car firstCar = new Car();
        Car secondCar = new Car();

        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);//创建一个停车男孩对象
        firstParkingLot.park(firstCar);//第一个停车场有了一辆车
        //when
        ParkingTicket parkingTicket = parkingBoy.park(secondCar);//停车男孩停第二辆车
        Car fetchCar = parkingBoy.fetch(parkingTicket);//由票取出车
        //then
        assertSame(secondCar,fetchCar);//判断是否一致

    }
    @Test
    void should_parking_to_secondParkingLot_if_secondParkingLot_in_full_position(){

    }
}
