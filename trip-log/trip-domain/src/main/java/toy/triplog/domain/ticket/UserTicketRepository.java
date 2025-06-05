package toy.triplog.domain.ticket;

import java.util.List;

public interface UserTicketRepository {

    void save(UserTicket userTicket);

    List<UserTicket> findUserTickets(Long userId);

}
