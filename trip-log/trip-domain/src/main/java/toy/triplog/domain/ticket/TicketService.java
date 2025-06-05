package toy.triplog.domain.ticket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private static final int ENTER_USER_UNIT = 10;
    private static final int EXPIRE_MINUTE = 5;

    private final WaitingManager waitingManager;
    private final TicketFinder ticketFinder;
    private final TicketUpdater ticketUpdater;

    public Token joinQueue() {
        return waitingManager.joinWaitingQueue();
    }

    public WaitingInfo getWaitingOrder(Token token) {
        return waitingManager.findWaitingOrder(token);
    }

    @Transactional
    public Ticket bookTicket(Token token, Long ticketId, Long userId) {
        waitingManager.checkUserEntered(token);
        waitingManager.updateAvailableTime(token, EXPIRE_MINUTE);
        Ticket ticket = ticketFinder.findTicketExclusively(ticketId);
        if (ticket.isNotAvailable()) {
            throw new RuntimeException("이미 선점된 티켓입니다.");
        }
        ticketUpdater.bookTicket(ticket, userId);
        return ticket;
    }

    @Transactional
    public List<Ticket> findTickets(Long eventId, Token token) {
        waitingManager.checkUserEntered(token);
        waitingManager.updateAvailableTime(token, EXPIRE_MINUTE);
        return ticketFinder.findTickets(eventId);
    }

    @Scheduled(fixedDelay = 5000)
    public void enterUsers() {
        log.info("티켓 대기열에 유저를 입장시킵니다.");
        waitingManager.enterUsers(ENTER_USER_UNIT, EXPIRE_MINUTE);
    }

}
