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
import com.sdcProject.busReservationSystem.enumFile.BusType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfTicketService {

    private static final Color PRIMARY_COLOR = new DeviceRgb(7, 141, 215);
    private static final Color SUCCESS_COLOR = new DeviceRgb(34, 197, 94);
    private static final Color GRAY_LIGHT = new DeviceRgb(243, 244, 246);
    private static final Color GRAY_DARK = new DeviceRgb(107, 114, 128);

    public ByteArrayInputStream generateTicketPdf(BookingDTO bookingDTO) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);
            document.setMargins(40, 40, 40, 40);

            addHeader(document, bookingDTO);
            document.add(new Paragraph("\n"));
            addBookingInfo(document, bookingDTO);
            document.add(new Paragraph("\n"));
            addJourneyDetails(document, bookingDTO);
            document.add(new Paragraph("\n"));
            addBusDetails(document, bookingDTO);
            document.add(new Paragraph("\n"));
            addPassengerDetails(document, bookingDTO);
            document.add(new Paragraph("\n"));
            addFareDetails(document, bookingDTO);
            document.add(new Paragraph("\n").setMarginTop(20));
            addFooter(document);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    /* ================= HEADER ================= */

    private void addHeader(Document document, BookingDTO bookingDTO) {
        Table headerTable = new Table(2);
        headerTable.setWidth(UnitValue.createPercentValue(100));

        Cell titleCell = new Cell()
                .add(new Paragraph("BUS YATRA")
                        .setFontSize(28)
                        .setBold()
                        .setFontColor(PRIMARY_COLOR))
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);

        String statusText = bookingDTO.getStatus() != null
                ? bookingDTO.getStatus().toString()
                : "CONFIRMED";

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

        Table divider = new Table(1);
        divider.setWidth(UnitValue.createPercentValue(100));
        divider.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .setBorderBottom(new SolidBorder(PRIMARY_COLOR, 2))
                .setHeight(5));
        document.add(divider);
    }

    /* ================= BOOKING INFO ================= */

    private void addBookingInfo(Document document, BookingDTO bookingDTO) {
        Table infoTable = new Table(2);
        infoTable.setWidth(UnitValue.createPercentValue(100));

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd MMM yyyy");

        infoTable.addCell(createInfoCell("Booking ID", false));
        infoTable.addCell(createInfoCell("#" + bookingDTO.getBookingId(), true));

        infoTable.addCell(createInfoCell("Booking Date", false));
        infoTable.addCell(createInfoCell(
                bookingDTO.getBookingDate().format(df), true));

        if (bookingDTO.getBusId() != null &&
                bookingDTO.getBusId().getTravelAgency() != null) {

            infoTable.addCell(createInfoCell("Travel Agency", false));
            infoTable.addCell(createInfoCell(
                    bookingDTO.getBusId()
                            .getTravelAgency()
                            .getTravel_agency_name(), true));
        }

        document.add(infoTable);
    }

    /* ================= JOURNEY ================= */

    private void addJourneyDetails(Document document, BookingDTO bookingDTO) {
        document.add(new Paragraph("Journey Details")
                .setFontSize(16)
                .setBold()
                .setFontColor(PRIMARY_COLOR)
                .setMarginBottom(5));

        Table journeyBox = new Table(1);
        journeyBox.setWidth(UnitValue.createPercentValue(100));
        journeyBox.setBorder(new SolidBorder(GRAY_LIGHT, 2));
        journeyBox.setBackgroundColor(GRAY_LIGHT);

        Table journeyTable = new Table(new float[]{1, 0.3f, 1});
        journeyTable.setWidth(UnitValue.createPercentValue(100));

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd MMM yyyy");

        String departureTime = "N/A";
        String arrivalTime = "N/A";

        if (bookingDTO.getBusId() != null &&
                bookingDTO.getBusId().getBusSchedules() != null &&
                bookingDTO.getBusId().getRoutes() != null) {

            LocalTime dep = bookingDTO.getBusId()
                    .getBusSchedules()
                    .getDepartureTime();

            Integer duration = bookingDTO.getBusId()
                    .getRoutes()
                    .getDuration();

            if (dep != null && duration != null) {
                departureTime = com.sdcProject.busReservationSystem.util.TimeFormatter
                        .formatTimeTo12Hr(dep);

                arrivalTime = com.sdcProject.busReservationSystem.util.TimeFormatter
                        .calculateArrivalTime12Hr(dep, duration);
            }
        }

        Cell fromCell = new Cell()
                .add(new Paragraph(bookingDTO.getSourceCity()).setBold())
                .add(new Paragraph(departureTime).setFontColor(GRAY_DARK))
                .add(new Paragraph(bookingDTO.getTripDate().format(df))
                        .setFontColor(GRAY_DARK))
                .setBorder(Border.NO_BORDER)
                .setPadding(10);

        Cell arrowCell = new Cell()
                .add(new Paragraph("→")
                        .setFontSize(24)
                        .setBold()
                        .setFontColor(PRIMARY_COLOR)
                        .setTextAlignment(TextAlignment.CENTER))
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);

        Cell toCell = new Cell()
                .add(new Paragraph(bookingDTO.getDestinationCity()).setBold())
                .add(new Paragraph(arrivalTime).setFontColor(GRAY_DARK))
                .setBorder(Border.NO_BORDER)
                .setPadding(10)
                .setTextAlignment(TextAlignment.RIGHT);

        journeyTable.addCell(fromCell);
        journeyTable.addCell(arrowCell);
        journeyTable.addCell(toCell);

        journeyBox.addCell(new Cell()
                .add(journeyTable)
                .setBorder(Border.NO_BORDER));

        document.add(journeyBox);
    }

    /* ================= BUS DETAILS ================= */

    private void addBusDetails(Document document, BookingDTO bookingDTO) {
        document.add(new Paragraph("Bus Details")
                .setFontSize(16)
                .setBold()
                .setFontColor(PRIMARY_COLOR)
                .setMarginBottom(5));

        Table busTable = new Table(2);
        busTable.setWidth(UnitValue.createPercentValue(100));
        busTable.setBorder(new SolidBorder(GRAY_LIGHT, 1));

        if (bookingDTO.getBusId() != null) {
            busTable.addCell(createDetailCell("Bus Name", false));
            busTable.addCell(createDetailCell(
                    bookingDTO.getBusId().getBusName(), true));

            busTable.addCell(createDetailCell("Bus Number", false));
            busTable.addCell(createDetailCell(
                    bookingDTO.getBusId().getBusRegistrationNumber(), true));

            busTable.addCell(createDetailCell("Bus Type", false));
            busTable.addCell(createDetailCell(
                    bookingDTO.getBusId().getBusType().toString(), true));
        }

        document.add(busTable);
    }

    /* ================= PASSENGER ================= */

    private void addPassengerDetails(Document document, BookingDTO bookingDTO) {
        document.add(new Paragraph("Passenger Details")
                .setFontSize(16)
                .setBold()
                .setFontColor(PRIMARY_COLOR)
                .setMarginBottom(5));

        Table passengerTable = new Table(2);
        passengerTable.setWidth(UnitValue.createPercentValue(100));
        passengerTable.setBorder(new SolidBorder(GRAY_LIGHT, 1));

        passengerTable.addCell(createDetailCell("Passenger Name", false));
        passengerTable.addCell(createDetailCell(
                bookingDTO.getUser() != null
                        ? bookingDTO.getUser().getUsername()
                        : "N/A",
                true));

        passengerTable.addCell(createDetailCell("Seat Number(s)", false));
        passengerTable.addCell(createDetailCell(
                bookingDTO.getSeatName() != null
                        ? String.join(", ", bookingDTO.getSeatName())
                        : "N/A",
                true));

        passengerTable.addCell(createDetailCell("Number of Seats", false));
        passengerTable.addCell(createDetailCell(
                String.valueOf(
                        bookingDTO.getSeatName() != null
                                ? bookingDTO.getSeatName().size()
                                : 0),
                true));

        document.add(passengerTable);
    }

    /* ================= FARE DETAILS ================= */

    private void addFareDetails(Document document, BookingDTO bookingDTO) {

        document.add(new Paragraph("Fare Details")
                .setFontSize(16)
                .setBold()
                .setFontColor(PRIMARY_COLOR)
                .setMarginBottom(5));

        List<String> seatList = bookingDTO.getSeatName();

        double seatPrice = 0.0;
        double sleeperPrice = 0.0;
        double totalFare = 0.0;

        if (bookingDTO.getBusId() != null) {

            seatPrice = bookingDTO.getBusId().getSeatPrice() != 0
                    ? bookingDTO.getBusId().getSeatPrice()
                    : bookingDTO.getBusId().getRoutes() != null
                    ? bookingDTO.getBusId().getRoutes().getPrice()
                    : 0.0;

            sleeperPrice = bookingDTO.getBusId().getSleeperPrice();
        }

        if (seatList != null) {
            for (String seat : seatList) {
                if (seat != null && seat.startsWith("S")
                        && bookingDTO.getBusId().getBusType() == BusType.SEMI_SLEEPER) {
                    totalFare += sleeperPrice;
                } else {
                    totalFare += seatPrice;
                }
            }
        }

        // Check if bus type is SEMI_SLEEPER
        boolean isSemiSleeper = bookingDTO.getBusId() != null
                && bookingDTO.getBusId().getBusType() == BusType.SEMI_SLEEPER;

        // Check which types of seats are selected
        boolean hasRegularSeats = false;
        boolean hasSleeperSeats = false;

        if (seatList != null) {
            for (String seat : seatList) {
                if (seat != null) {
                    if (seat.startsWith("S") && isSemiSleeper) {
                        hasSleeperSeats = true;
                    } else {
                        hasRegularSeats = true;
                    }
                }
            }
        }

        // First Box: Price per Seat and Price per Sleeper
        Table priceBox = new Table(1);
        priceBox.setWidth(UnitValue.createPercentValue(100));
        priceBox.setBorder(new SolidBorder(GRAY_LIGHT, 2));

        Table priceTable = new Table(2);
        priceTable.setWidth(UnitValue.createPercentValue(100));

        // Add Price per Seat only if regular seats are selected
        if (hasRegularSeats) {
            priceTable.addCell(createDetailCell("Price per Seat", false));
            priceTable.addCell(createDetailCell("Rs. " + String.format("%.2f", seatPrice), true));
        }

        // Add Price per Sleeper only if sleeper seats are selected
        if (hasSleeperSeats) {
            priceTable.addCell(createDetailCell("Price per Sleeper", false));
            priceTable.addCell(createDetailCell("Rs. " + String.format("%.2f", sleeperPrice), true));
        }

        priceBox.addCell(new Cell()
                .add(priceTable)
                .setBorder(Border.NO_BORDER)
                .setPadding(10));

        document.add(priceBox);

        // Add spacing between boxes
        document.add(new Paragraph("\n").setMarginTop(10).setMarginBottom(10));

        // Second Box: Total Fare
        Table totalBox = new Table(1);
        totalBox.setWidth(UnitValue.createPercentValue(100));
        totalBox.setBorder(new SolidBorder(GRAY_LIGHT, 2));
        totalBox.setBackgroundColor(GRAY_LIGHT);

        Table totalTable = new Table(2);
        totalTable.setWidth(UnitValue.createPercentValue(100));

        totalTable.addCell(new Cell()
                .add(new Paragraph("Total Fare").setBold().setFontSize(14))
                .setBorder(Border.NO_BORDER)
                .setPadding(10));

        totalTable.addCell(new Cell()
                .add(new Paragraph("Rs. " + String.format("%.2f", totalFare))
                        .setFontSize(16)
                        .setBold()
                        .setFontColor(PRIMARY_COLOR))
                .setBorder(Border.NO_BORDER)
                .setPadding(10)
                .setTextAlignment(TextAlignment.RIGHT));

        totalBox.addCell(new Cell()
                .add(totalTable)
                .setBorder(Border.NO_BORDER));

        document.add(totalBox);
    }

    /* ================= FOOTER ================= */

    private void addFooter(Document document) {
        document.add(new Paragraph("\nThank you for choosing our service. Have a safe journey!")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(PRIMARY_COLOR));
    }

    /* ================= HELPERS ================= */

    private Cell createInfoCell(String content, boolean bold) {
        Paragraph p = new Paragraph(content).setFontSize(11);
        if (bold) p.setBold();
        else p.setFontColor(GRAY_DARK);
        return new Cell().add(p).setBorder(Border.NO_BORDER).setPadding(5);
    }

    private Cell createDetailCell(String content, boolean bold) {
        Paragraph p = new Paragraph(content).setFontSize(12);
        if (bold) p.setBold();
        else p.setFontColor(GRAY_DARK);
        return new Cell().add(p).setBorder(Border.NO_BORDER).setPadding(10);
    }
}