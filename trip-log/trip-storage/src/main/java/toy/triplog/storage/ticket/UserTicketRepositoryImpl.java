package toy.triplog.storage.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.triplog.domain.ticket.UserTicket;
import toy.triplog.domain.ticket.UserTicketRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserTicketRepositoryImpl implements UserTicketRepository {

    private final JpaUserTicketRepository jpaUserTicketRepository;

    @Override
    public void save(UserTicket userTicket) {
        jpaUserTicketRepository.save(UserTicketEntity.from(userTicket));
    }

    @Override
    public List<UserTicket> findUserTickets(Long userId) {
        List<UserTicketEntity> userTickets = jpaUserTicketRepository.findByUserId(userId);
        return userTickets.stream()
                .map(UserTicketEntity::toDomain)
                .toList();
    }
}
