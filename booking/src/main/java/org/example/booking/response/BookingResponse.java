package org.example.booking.response;

public class BookingResponse {
    private int bookingId;
    private String bookingDate;
    private String details;

    private Object userInfor;
    private Object carInfor;
    private Object componentsInfor;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Object getCarInfor() {
        return carInfor;
    }

    public void setCarInfor(Object carInfor) {
        this.carInfor = carInfor;
    }

    public Object getUserInfor() {
        return userInfor;
    }

    public void setUserInfor(Object userInfor) {
        this.userInfor = userInfor;
    }

    public Object getComponentsInfor() {
        return componentsInfor;
    }

    public void setComponentsInfor(Object componentsInfor) {
        this.componentsInfor = componentsInfor;
    }
}
