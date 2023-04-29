package org.example;

public class Audience {
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    public Bag getBag() {
        return bag;
    }

    public Long buy(Ticket ticket) {
        if (getBag().hasInvitation()) {
            getBag().setTicket(ticket);
            return 0L;
        } else {
            getBag().minusAmount(ticket.getFee());
            getBag().setTicket(ticket);
            return ticket.getFee();
        }
    }
}