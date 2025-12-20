package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.enumFile.BookingStatus;
import com.sdcProject.busReservationSystem.enumFile.BusType;
import com.sdcProject.busReservationSystem.enumFile.PaymentStatus;
import com.sdcProject.busReservationSystem.modal.*;
import com.sdcProject.busReservationSystem.repository.*;
import com.sdcProject.busReservationSystem.serviceImplementation.BookingInterface;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BookingImplementation implements BookingInterface {
    private final DriverRepository driverRepository;
    private BookingRepository bookingRepository;
    private BusRepository busRepository;
    private UserRepository userRepository;
    private TravelAgencyRepository travelAgencyRepository;

    public Bookings bookSeats(Bookings bookings, Authentication auth, int busId) {

        Users users=userRepository.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found"));

        Bus bus=busRepository.findById(busId).orElseThrow(()->new RuntimeException("Bus not found"));

        LocalDate today = LocalDate.now();

        bookings.setUser(users);
        bookings.setBusId(bus);
        bookings.setBookingDate(today);
        bookings.setStatus(BookingStatus.PENDING);
        bookings.setPaymentStatus(PaymentStatus.PENDING);
        bookings.setBoard(false);
        bookings.setJourneyStarted(false);

        return bookingRepository.save(bookings);
    }

    @Override
    public Bookings cancelBooking(Bookings bookings) {
        Bookings bookings1=bookingRepository.findById(bookings.getBookingId()).orElseThrow(()->new RuntimeException("Booking not found"));
        bookings1.setStatus(BookingStatus.CANCELLED);
        bookings1.setCancellationReason(bookings.getCancellationReason());

        return bookingRepository.save(bookings1);
    }

    @Override
    public List<Bookings> getUserBookings(Authentication auth) {
        Users user=userRepository.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found"));
        return bookingRepository.findByUser(user);
    }

    @Override
    public List<Bookings> getBookingsByBusIdAndDate(int busId, LocalDate bookingDate) {
        return bookingRepository.findBookingsByBusIdAndTripDate(busId,bookingDate);
    }

    @Override
    public Bookings updateBoardStatus(int bookingId) {
        Bookings bookings=bookingRepository.findById(bookingId).orElseThrow(()->new RuntimeException("Booking not found"));
        bookings.setBoard(true);
        return bookingRepository.save(bookings);
    }

    @Override
    public List<Bookings> getBookingsForAgency(Authentication auth,LocalDate bookingDate,int busId) {
        Users users=userRepository.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found"));
        TravelAgency travelAgency=travelAgencyRepository.findByUser(users);
        List<Bus> buses=busRepository.findByTravelAgency(travelAgency);
//        Adding list of bookings
        List<Bookings> bookingsList=new ArrayList<>();

//        find bookings of each bus
        if(busId==0) {
            for (Bus bus : buses) {
                List<Bookings> bookings=bookingRepository.findByBookingDate(bus,bookingDate);
                bookingsList.addAll(bookings);
            }
        }else {
            Bus bus=busRepository.findById(busId).orElseThrow(()->new RuntimeException("Bus not found"));
                List<Bookings> bookings=bookingRepository.findByBookingDate(bus,bookingDate);
                bookingsList.addAll(bookings);
        }

        return bookingsList;
    }

    @Override
    public Bookings cancelBooking(int bookingId,String reason) {
        Bookings bookings = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Trip date and time
        LocalDate tripDate = bookings.getTripDate();
        LocalTime departureTime = bookings.getBusId()
                .getBusSchedules()
                .getDepartureTime();

        // Current date and time
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        // Combine date + time into LocalDateTime
        LocalDateTime tripDateTime = LocalDateTime.of(tripDate, departureTime);
        LocalDateTime currentDateTime = LocalDateTime.of(currentDate, currentTime);

        // Calculate difference in hours
        long hoursDifference = Math.abs(Duration.between(currentDateTime, tripDateTime).toHours());

        if (hoursDifference > 48) {
            bookings.setStatus(BookingStatus.CANCELLED);
            bookings.setCancellationReason(bookings.getCancellationReason());
            bookings.setFineInPercentage(0);

        }
        else if (hoursDifference >= 24 && hoursDifference <= 48) {
            bookings.setStatus(BookingStatus.CANCELLED);
            bookings.setCancellationReason(bookings.getCancellationReason());
            bookings.setFineInPercentage(25);
        }
        else if (hoursDifference >= 14 && hoursDifference < 24) {
            bookings.setStatus(BookingStatus.CANCELLED);
            bookings.setCancellationReason(bookings.getCancellationReason());
            bookings.setFineInPercentage(50);
        }
        else {
            bookings.setStatus(BookingStatus.CANCELLED);
            bookings.setCancellationReason(bookings.getCancellationReason());
            bookings.setFineInPercentage(100);
        }
        bookings.setCancellationReason(reason);
        bookings.setPaymentStatus(PaymentStatus.REFUNDED);

        return bookingRepository.save(bookings);
    }

    @Override
    public List<Bookings> updateJourney(Authentication auth, LocalDate date) {

        Driver driver = driverRepository.findByDriverEmail(auth.getName());

        List<Bookings> bookingsList =
                bookingRepository.findBookingsByBusIdAndTripDate(
                        driver.getBus().getBusId(), date
                );

        List<Bookings> activeBookings = bookingsList.stream()
                .filter(b -> b.getStatus() != BookingStatus.CANCELLED)
                .toList();

        activeBookings.forEach(
                booking -> booking.setStatus(BookingStatus.COMPLETED)
        );

        bookingRepository.saveAll(activeBookings);

        return bookingsList;
    }

    @Override
    public List<Bookings> getActiveBookingsOfAgency(TravelAgency travelAgency) {
        return  bookingRepository.findBookingsByTravelAgencyAndStatus(travelAgency.getTravel_agency_id(),BookingStatus.CONFIRMED);
    }

    @Override
    public Float calculateTotalRevenue(TravelAgency travelAgency) {

        List<Bookings> completedBookings =
                bookingRepository.findBookingsByTravelAgencyAndStatus(
                        travelAgency.getTravel_agency_id(),
                        BookingStatus.COMPLETED
                );

        List<Bus> busList = busRepository.findByTravelAgency(travelAgency);

        float totalRevenue = 0;

        for (Bus bus : busList) {
            List<Bookings> bookingsOfBus = new ArrayList<>();

            for (Bookings booking : completedBookings) {
                if (booking.getBusId().getBusId() == bus.getBusId()) {
                    bookingsOfBus.add(booking);
                }
            }

            totalRevenue += calculateTotalRevenue(bus, bookingsOfBus);
        }

        return totalRevenue;
    }

    @Override
    public Map<String, Integer> dataForPie(TravelAgency travelAgency) {

        Map<String, Integer> map = new HashMap<>();

        for (BookingStatus status : BookingStatus.values()) {
            List<Bookings> bookingsList =
                    bookingRepository.findBookingsByTravelAgencyAndStatus(
                            travelAgency.getTravel_agency_id(),
                            status
                    );

            map.put(status.name(), bookingsList.size());
        }

        return map;
    }

    @Override
    public Map<LocalDate, Integer> dataForBarGraph(TravelAgency travelAgency, LocalDate date) {
        Map<LocalDate, Integer> map = new HashMap<>();

        LocalDate[] weekBoundaries = getAllWeekDays(date);
        LocalDate sunday = weekBoundaries[0];
        LocalDate saturday = weekBoundaries[6];

        for (LocalDate day : weekBoundaries) {
            map.put(day, 0);
        }

        List<Bookings> bookingsList = bookingRepository.findBookingsBySundayAndSaturday(
                travelAgency.getTravel_agency_id(), sunday, saturday
        );

        for (Bookings booking : bookingsList) {
            LocalDate tripDate = booking.getTripDate();
            if (map.containsKey(tripDate)) {
                map.put(tripDate, map.get(tripDate) + 1);
            }
        }

        return map;
    }

    public static LocalDate[] getAllWeekDays(LocalDate date) {
        int daysFromSunday = date.getDayOfWeek().getValue() % 7;
        LocalDate sunday = date.minusDays(daysFromSunday);

        return new LocalDate[]{
                sunday,                 // [0] Sunday
                sunday.plusDays(1),     // [1] Monday
                sunday.plusDays(2),     // [2] Tuesday
                sunday.plusDays(3),     // [3] Wednesday
                sunday.plusDays(4),     // [4] Thursday
                sunday.plusDays(5),     // [5] Friday
                sunday.plusDays(6)      // [6] Saturday
        };
    }

    private float calculateTotalRevenue(Bus bus, List<Bookings> bookings) {

        float totalRevenue = 0;

        if (bus.getBusType() == BusType.SEMI_SLEEPER) {

            float seatPrice = bus.getSeatPrice();
            float sleeperPrice = bus.getSleeperPrice();

            for (Bookings booking : bookings) {

                if (booking.getSeatName() == null) continue;

                for (String seatName : booking.getSeatName()) {
                    if (seatName.startsWith("S")) {
                        totalRevenue += sleeperPrice;
                    } else {
                        totalRevenue += seatPrice;
                    }
                }
            }

        }
        else {

            float seatPrice = bus.getSeatPrice();

            for (Bookings booking : bookings) {
                totalRevenue += seatPrice * booking.getTotalSeats();
            }
        }

        return totalRevenue;
    }



}
