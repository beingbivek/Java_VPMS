package vpms.controller;

import vpms.view.ParkingTicketView;

public class ParkingTicketController {

    private final ParkingTicketView view;

    public ParkingTicketController(ParkingTicketView view,
                                   int ticketId,
                                   String slotCode,
                                   String entryTime) {
        this.view = view;
        view.setTicketId (String.valueOf(ticketId));
        view.setSlotCode (slotCode);
        view.setEntryTime(entryTime);
        view.setLocationRelativeTo(null);        // centre on screen
    }
    public void open() { view.setVisible(true); }
}
