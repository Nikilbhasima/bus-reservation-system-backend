package com.sdcProject.busReservationSystem.service;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.sdcProject.busReservationSystem.dto.BookingDTO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfTicketService {

    // Define colors
    private static final Color PRIMARY_COLOR = new DeviceRgb(7, 141, 215); // #078DD7
    private static final Color SUCCESS_COLOR = new DeviceRgb(34, 197, 94); // Green
    private static final Color GRAY_LIGHT = new DeviceRgb(243, 244, 246);
    private static final Color GRAY_DARK = new DeviceRgb(107, 114, 128);

    public ByteArrayInputStream generateTicketPdf(BookingDTO bookingDTO) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);
            document.setMargins(40, 40, 40, 40);

            // ============ HEADER SECTION ============
            addHeader(document, bookingDTO);

            document.add(new Paragraph("\n"));

            // ============ BOOKING INFO SECTION ============
            addBookingInfo(document, bookingDTO);

            document.add(new Paragraph("\n"));

            // ============ JOURNEY DETAILS SECTION ============
            addJourneyDetails(document, bookingDTO);

            document.add(new Paragraph("\n"));

            // ============ BUS DETAILS SECTION ============
            addBusDetails(document, bookingDTO);

            document.add(new Paragraph("\n"));

            // ============ PASSENGER DETAILS SECTION ============
            addPassengerDetails(document, bookingDTO);

            document.add(new Paragraph("\n"));

            // ============ FARE DETAILS SECTION ============
            addFareDetails(document, bookingDTO);

            document.add(new Paragraph("\n").setMarginTop(20));

            // ============ FOOTER SECTION ============
            addFooter(document);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addHeader(Document document, BookingDTO bookingDTO) {
        // Header table with ticket title and status
        Table headerTable = new Table(2);
        headerTable.setWidth(UnitValue.createPercentValue(100));

        // Left: BUS TICKET title
        Cell titleCell = new Cell()
                .add(new Paragraph("BUS YATRA")
                        .setFontSize(28)
                        .setBold()
                        .setFontColor(PRIMARY_COLOR))
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);

        // Right: Status badge
        String statusText = bookingDTO.getStatus() != null ?
                bookingDTO.getStatus().toString() : "CONFIRMED";

        Cell statusCell = new Cell()
                .add(new Paragraph(statusText)
                        .setFontSize(14)
                        .setBold()
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(SUCCESS_COLOR)
                .setBorder(Border.NO_BORDER)
                .setPadding(8)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);

        headerTable.addCell(titleCell);
        headerTable.addCell(statusCell);

        document.add(headerTable);

        // Divider line
        Table divider = new Table(1);
        divider.setWidth(UnitValue.createPercentValue(100));
        divider.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .setBorderBottom(new SolidBorder(PRIMARY_COLOR, 2))
                .setHeight(5));
        document.add(divider);
    }

    private void addBookingInfo(Document document, BookingDTO bookingDTO) {
        Table infoTable = new Table(2);
        infoTable.setWidth(UnitValue.createPercentValue(100));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        // Booking ID
        infoTable.addCell(createInfoCell("Booking ID", false));
        infoTable.addCell(createInfoCell("#" + bookingDTO.getBookingId(), true));

        // Booking Date
        infoTable.addCell(createInfoCell("Booking Date", false));
        infoTable.addCell(createInfoCell(
                bookingDTO.getBookingDate().format(dateFormatter), true));

        // Travel Agency
        if (bookingDTO.getBusId() != null && bookingDTO.getBusId().getTravelAgency() != null) {
            infoTable.addCell(createInfoCell("Travel Agency", false));
            infoTable.addCell(createInfoCell(
                    bookingDTO.getBusId().getTravelAgency().getTravel_agency_name(), true));
        }

        document.add(infoTable);
    }

    private void addJourneyDetails(Document document, BookingDTO bookingDTO) {
        // Section title
        document.add(new Paragraph("Journey Details")
                .setFontSize(16)
                .setBold()
                .setFontColor(PRIMARY_COLOR)
                .setMarginBottom(5));

        // Journey box with border
        Table journeyBox = new Table(1);
        journeyBox.setWidth(UnitValue.createPercentValue(100));
        journeyBox.setBorder(new SolidBorder(GRAY_LIGHT, 2));
        journeyBox.setBackgroundColor(GRAY_LIGHT);

        // Journey content
        Table journeyTable = new Table(new float[]{1, 0.3f, 1});
        journeyTable.setWidth(UnitValue.createPercentValue(100));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        // Calculate arrival date
        int daysToAdd = 0;
        String arrivalDate = bookingDTO.getTripDate().format(dateFormatter);

        if (bookingDTO.getBusId() != null &&
                bookingDTO.getBusId().getBusSchedules() != null &&
                bookingDTO.getBusId().getRoutes() != null) {

            LocalTime departureTime = bookingDTO.getBusId().getBusSchedules().getDepartureTime();
            Integer durationMinutes = bookingDTO.getBusId().getRoutes().getDuration(); // Changed to Integer

            if (departureTime != null && durationMinutes != null && durationMinutes > 0) {
                LocalTime arrivalTime = departureTime.plusMinutes(durationMinutes);
                // If arrival time is earlier than departure time, it means next day
                daysToAdd = arrivalTime.isBefore(departureTime) ? 1 : 0;
                arrivalDate = bookingDTO.getTripDate()
                        .plusDays(daysToAdd)
                        .format(dateFormatter);
            }
        }

        // Get departure time string - with null checks
        String departureTimeStr = "N/A";
        if (bookingDTO.getBusId() != null &&
                bookingDTO.getBusId().getBusSchedules() != null &&
                bookingDTO.getBusId().getBusSchedules().getDepartureTime() != null) {
            departureTimeStr = com.sdcProject.busReservationSystem.util.TimeFormatter
                    .formatTimeTo12Hr(bookingDTO.getBusId().getBusSchedules().getDepartureTime());
        }

        // Get arrival time string - with null checks
        String arrivalTimeStr = "N/A";
        if (bookingDTO.getBusId() != null &&
                bookingDTO.getBusId().getBusSchedules() != null &&
                bookingDTO.getBusId().getRoutes() != null &&
                bookingDTO.getBusId().getBusSchedules().getDepartureTime() != null &&
                bookingDTO.getBusId().getRoutes().getDuration() != 0) {

            arrivalTimeStr = com.sdcProject.busReservationSystem.util.TimeFormatter
                    .calculateArrivalTime12Hr(
                            bookingDTO.getBusId().getBusSchedules().getDepartureTime(),
                            bookingDTO.getBusId().getRoutes().getDuration()
                    );
        }

        // From Cell
        Cell fromCell = new Cell()
                .add(new Paragraph(bookingDTO.getSourceCity() != null ?
                        bookingDTO.getSourceCity() : "N/A")
                        .setFontSize(16)
                        .setBold()
                        .setMarginBottom(5))
                .add(new Paragraph(departureTimeStr)
                        .setFontSize(14)
                        .setFontColor(GRAY_DARK))
                .add(new Paragraph(bookingDTO.getTripDate().format(dateFormatter))
                        .setFontSize(11)
                        .setFontColor(GRAY_DARK))
                .setBorder(Border.NO_BORDER)
                .setPadding(10);

        // Arrow Cell
        Cell arrowCell = new Cell()
                .add(new Paragraph("→")
                        .setFontSize(24)
                        .setBold()
                        .setFontColor(PRIMARY_COLOR)
                        .setTextAlignment(TextAlignment.CENTER))
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);

        // To Cell
        Cell toCell = new Cell()
                .add(new Paragraph(bookingDTO.getDestinationCity() != null ?
                        bookingDTO.getDestinationCity() : "N/A")
                        .setFontSize(16)
                        .setBold()
                        .setMarginBottom(5))
                .add(new Paragraph(arrivalTimeStr)
                        .setFontSize(14)
                        .setFontColor(GRAY_DARK))
                .add(new Paragraph(arrivalDate)
                        .setFontSize(11)
                        .setFontColor(GRAY_DARK))
                .setBorder(Border.NO_BORDER)
                .setPadding(10)
                .setTextAlignment(TextAlignment.RIGHT);

        journeyTable.addCell(fromCell);
        journeyTable.addCell(arrowCell);
        journeyTable.addCell(toCell);

        journeyBox.addCell(new Cell().add(journeyTable).setBorder(Border.NO_BORDER));
        document.add(journeyBox);
    }

    private void addBusDetails(Document document, BookingDTO bookingDTO) {
        // Section title
        document.add(new Paragraph("Bus Details")
                .setFontSize(16)
                .setBold()
                .setFontColor(PRIMARY_COLOR)
                .setMarginBottom(5));

        Table busTable = new Table(2);
        busTable.setWidth(UnitValue.createPercentValue(100));
        busTable.setBorder(new SolidBorder(GRAY_LIGHT, 1));

        if (bookingDTO.getBusId() != null) {
            // Bus Name
            busTable.addCell(createDetailCell("Bus Name", false));
            busTable.addCell(createDetailCell(bookingDTO.getBusId().getBusName(), true));

            // Bus Registration Number
            busTable.addCell(createDetailCell("Bus Number", false));
            busTable.addCell(createDetailCell(
                    bookingDTO.getBusId().getBusRegistrationNumber(), true));

            // Bus Type
            busTable.addCell(createDetailCell("Bus Type", false));
            busTable.addCell(createDetailCell(String.valueOf(bookingDTO.getBusId().getBusType()), true));
        } else {
            busTable.addCell(createDetailCell("Bus Information", false));
            busTable.addCell(createDetailCell("N/A", true));
        }

        document.add(busTable);
    }

    private void addPassengerDetails(Document document, BookingDTO bookingDTO) {
        // Section title
        document.add(new Paragraph("Passenger Details")
                .setFontSize(16)
                .setBold()
                .setFontColor(PRIMARY_COLOR)
                .setMarginBottom(5));

        Table passengerTable = new Table(2);
        passengerTable.setWidth(UnitValue.createPercentValue(100));
        passengerTable.setBorder(new SolidBorder(GRAY_LIGHT, 1));

        // Passenger Name
        passengerTable.addCell(createDetailCell("Passenger Name", false));
        passengerTable.addCell(createDetailCell(
                bookingDTO.getUser() != null ? bookingDTO.getUser().getUsername() : "N/A",
                true));

        // Contact Number
        if (bookingDTO.getUser() != null && bookingDTO.getUser().getPhoneNumber() != null) {
            passengerTable.addCell(createDetailCell("Contact Number", false));
            passengerTable.addCell(createDetailCell(bookingDTO.getUser().getPhoneNumber(), true));
        }

        // Seat Numbers
        List<String> seatList = bookingDTO.getSeatName();

        passengerTable.addCell(createDetailCell("Seat Number(s)", false));

        // Create seat badges
        Cell seatCell = new Cell().setBorder(Border.NO_BORDER).setPadding(10);
        if (seatList != null && !seatList.isEmpty()) {
            Table seatBadgeTable = new Table(seatList.size());
            for (String seat : seatList) {
                seatBadgeTable.addCell(new Cell()
                        .add(new Paragraph(seat)
                                .setFontSize(12)
                                .setBold()
                                .setFontColor(ColorConstants.WHITE))
                        .setBackgroundColor(PRIMARY_COLOR)
                        .setBorder(Border.NO_BORDER)
                        .setPadding(5)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginRight(5));
            }
            seatCell.add(seatBadgeTable);
        } else {
            seatCell.add(new Paragraph("N/A"));
        }
        passengerTable.addCell(seatCell);

        // Number of Seats
        passengerTable.addCell(createDetailCell("Number of Seats", false));
        passengerTable.addCell(createDetailCell(
                String.valueOf(seatList != null ? seatList.size() : 0),
                true));

        document.add(passengerTable);
    }

    private void addFareDetails(Document document, BookingDTO bookingDTO) {
        // Section title
        document.add(new Paragraph("Fare Details")
                .setFontSize(16)
                .setBold()
                .setFontColor(PRIMARY_COLOR)
                .setMarginBottom(5));

        Table fareTable = new Table(2);
        fareTable.setWidth(UnitValue.createPercentValue(100));
        fareTable.setBorder(new SolidBorder(GRAY_LIGHT, 1));

        List<String> seatList = bookingDTO.getSeatName();
        int numberOfSeats = seatList != null ? seatList.size() : 0;
        double pricePerSeat = bookingDTO.getBusId() != null &&
                bookingDTO.getBusId().getRoutes() != null ?
                bookingDTO.getBusId().getRoutes().getPrice() : 0.0;
        double totalFare = numberOfSeats * pricePerSeat;

        // Price per seat
        fareTable.addCell(createDetailCell("Price per Seat", false));
        fareTable.addCell(createDetailCell("Rs. " + String.format("%.2f", pricePerSeat), true));

        // Total fare (highlighted)
        fareTable.addCell(new Cell()
                .add(new Paragraph("Total Fare")
                        .setFontSize(14)
                        .setBold())
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(GRAY_LIGHT)
                .setPadding(10));

        fareTable.addCell(new Cell()
                .add(new Paragraph("Rs. " + String.format("%.2f", totalFare))
                        .setFontSize(16)
                        .setBold()
                        .setFontColor(PRIMARY_COLOR))
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(GRAY_LIGHT)
                .setPadding(10)
                .setTextAlignment(TextAlignment.RIGHT));

        document.add(fareTable);
    }

    private void addFooter(Document document) {
        // Separator line
        Table divider = new Table(1);
        divider.setWidth(UnitValue.createPercentValue(100));
        divider.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .setBorderTop(new SolidBorder(GRAY_LIGHT, 1))
                .setHeight(20));
        document.add(divider);

        // Important notes
        document.add(new Paragraph("Important Information")
                .setFontSize(12)
                .setBold()
                .setMarginBottom(5));

        document.add(new Paragraph("• Please carry a valid photo ID proof while traveling")
                .setFontSize(9)
                .setFontColor(GRAY_DARK));
        document.add(new Paragraph("• Arrive at the boarding point at least 15 minutes before departure")
                .setFontSize(9)
                .setFontColor(GRAY_DARK));
        document.add(new Paragraph("• This is a computer-generated ticket and does not require a signature")
                .setFontSize(9)
                .setFontColor(GRAY_DARK));

        // Thank you message
        document.add(new Paragraph("\nThank you for choosing our service. Have a safe journey!")
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(PRIMARY_COLOR)
                .setMarginTop(15));
    }

    private Cell createInfoCell(String content, boolean isBold) {
        Paragraph p = new Paragraph(content).setFontSize(11);
        if (isBold) {
            p.setBold();
        } else {
            p.setFontColor(GRAY_DARK);
        }
        return new Cell()
                .add(p)
                .setBorder(Border.NO_BORDER)
                .setPadding(5);
    }

    private Cell createDetailCell(String content, boolean isBold) {
        Paragraph p = new Paragraph(content).setFontSize(12);
        if (isBold) {
            p.setBold();
        } else {
            p.setFontColor(GRAY_DARK);
        }
        return new Cell()
                .add(p)
                .setBorder(Border.NO_BORDER)
                .setPadding(10);
    }
}