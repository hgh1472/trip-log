package toy.triplog.storage.ticket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import toy.triplog.StorageContextTest;
import toy.triplog.domain.ticket.Ticket;
import toy.triplog.domain.ticket.TicketStatus;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TicketRepositoryImplTest extends StorageContextTest {

    @Autowired
    private TicketRepositoryImpl ticketRepository;

    @Autowired
    private JpaTicketRepository jpaTicketRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @DisplayName("락을 걸고 조회하면, 다른 트랜잭션에서 조회할 수 없다.")
    @Test
    void findTicketWithLock() throws InterruptedException {
        // given
        TicketEntity save = jpaTicketRepository.save(createTicketEntity(TicketStatus.AVAILABLE));

        TransactionStatus tx1 = transactionManager.getTransaction(TransactionDefinition.withDefaults());

        ticketRepository.findTicketWithLock(save.getId());

        Thread other = new Thread(() -> {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus tx2 = transactionManager.getTransaction(def);
            try {
                Ticket ticket = ticketRepository.findTicketWithLock(save.getId());
            } finally {
                transactionManager.rollback(tx2);
            }
        });

        // when
        other.start();
        assertThat(other.isAlive()).isTrue();
        transactionManager.commit(tx1);
        other.join(1000);
        assertThat(other.isAlive()).isFalse();
    }

    private TicketEntity createTicketEntity(TicketStatus ticketStatus) {
        return TicketEntity.builder()
                .eventId(1L)
                .departure(EmbeddedAirport.builder().nationId(1L).name("ICN").build())
                .arrival(EmbeddedAirport.builder().nationId(2L).name("KIX").build())
                .departureTime(LocalDateTime.of(2023, 10, 1, 10, 0))
                .arrivalTime(LocalDateTime.of(2023, 10, 1, 12, 0))
                .seatNumber("12A")
                .ticketStatus(ticketStatus)
                .build();
    }

}