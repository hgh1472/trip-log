package toy.triplog.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import toy.triplog.domain.auth.ApiUser;
import toy.triplog.domain.ticket.Ticket;
import toy.triplog.domain.ticket.TicketService;
import toy.triplog.domain.ticket.Token;
import toy.triplog.domain.ticket.WaitingInfo;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/event/{eventId}/waiting")
    public ApiResponse<Token> waitTicket(@PathVariable long eventId) {
        Token token = ticketService.joinQueue();
        return ApiResponse.ok(token);
    }

    @GetMapping("/event/{eventId}")
    public ApiResponse<WaitingInfo> getOrder(@RequestParam String tokenString) {
        Token token = Token.builder().token(UUID.fromString(tokenString)).build();
        WaitingInfo waitingInfo = ticketService.getWaitingOrder(token);
        return ApiResponse.ok(waitingInfo);
    }

    @PostMapping("/event/{eventId}")
    public ApiResponse<Ticket> book(@RequestParam String token, @PathVariable Long eventId, @AuthenticationPrincipal ApiUser apiUser) {
        Ticket ticket = ticketService.bookTicket(Token.builder().token(UUID.fromString(token)).build(), eventId, apiUser.id());
        return ApiResponse.ok(ticket);
    }

}
