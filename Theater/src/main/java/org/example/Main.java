package org.example;

public class Main {
    public static void main(String[] args) {

        final Long FEE = 10000L;

        Bag bag1 = new Bag(10000);
        Audience a1 = new Audience(bag1);

        Invitation i = new Invitation();
        Bag bag2 = new Bag(i, 20000);
        Audience a2 = new Audience(bag2);

        TicketOffice ticketOffice = new TicketOffice(500000L, new Ticket(FEE), new Ticket(FEE), new Ticket(FEE));
        TicketSeller ticketSeller = new TicketSeller(ticketOffice);
        Theater theater = new Theater(ticketSeller);

        theater.enter(a1);
        theater.enter(a2);

        System.out.println(a1.getBag().getAmount());
        System.out.println(a2.getBag().getAmount());

    }
}