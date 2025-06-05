package toy.triplog.domain.ticket;

public enum TicketStatus {
    AVAILABLE("예약가능"),
    CANCELED("예약취소"),
    BOOKED("예약완료");
    private final String status;

    TicketStatus(String status) {
        this.status = status;
    }
}
