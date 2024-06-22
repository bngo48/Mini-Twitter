package MiniTwitter.src.gui;

import javax.swing.JOptionPane;

public class InfoDialogBox {

    public InfoDialogBox(String title, String msg, int msgType) {
        JOptionPane.showMessageDialog(null, msg, title, msgType);
    }
}
