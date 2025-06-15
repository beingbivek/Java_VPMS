package vpms.controller;

import java.awt.event.ActionEvent;
import vpms.view.UsersTestView;

public class UserTestController {
    private final UsersTestView view;

    public UserTestController(UsersTestView view) {
        this.view = view;
        initializeController();
    }

    private void initializeController() {
        view.buttonListener(this::handleButtonAction);
        view.setDefaultPosition();
    }

    private void handleButtonAction(ActionEvent e) {
        String name = view.getTextField().getText().trim();
        if (!name.isEmpty()) {
            view.setLabelName(name);
            view.getTextField().setText("");
        }
    }
}
