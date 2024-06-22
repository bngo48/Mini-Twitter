package MiniTwitter.src.gui;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import MiniTwitter.src.app.GroupUser;
import MiniTwitter.src.app.Observer;
import MiniTwitter.src.app.SingleUser;
import MiniTwitter.src.app.User;

public class OpenUserViewPanel extends ControlPanel {
    private JButton openUserViewButton;
    private JPanel spacerPanel;
    private JPanel treePanel;
    private Map<String, Observer> allUsers;
    private Map<String, JPanel> openPanels;

    public OpenUserViewPanel(JPanel treePanel, Map<String, Observer> allUsers) {
        super();
        this.treePanel = treePanel;
        this.allUsers = allUsers;
        initializeComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(this, openUserViewButton, 1, 2, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, spacerPanel, 1, 3, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        openPanels = new HashMap<String, JPanel>();

        openUserViewButton = new JButton("Open User View");
        initializeOpenUserViewActionListener();

        // empty spacer
        spacerPanel = new JPanel();
    }

    // return selected user
    private DefaultMutableTreeNode getSelectedNode() {
        JTree tree = ((TreePanel) treePanel).getTree();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
        if (!((TreePanel) treePanel).getRoot().equals(selectedNode)) {
            selectedNode = (DefaultMutableTreeNode) selectedNode.getUserObject();
        }
        return selectedNode;
    }

    private void initializeOpenUserViewActionListener() {
        openUserViewButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get selected user in treePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                // open user view UI on click, only open one window per User
                if (!allUsers.containsKey(((User) selectedNode).getID())) {
                    InfoDialogBox dialogBox = new InfoDialogBox("Error!",
                            "No such user exists!",
                            JOptionPane.ERROR_MESSAGE);
                } else if (selectedNode.getClass() == GroupUser.class) {
                    InfoDialogBox dialogBox = new InfoDialogBox("Error!",
                            "Cannot open user view for a group!",
                            JOptionPane.ERROR_MESSAGE);
                } else if (openPanels.containsKey(((User) selectedNode).getID())) {
                    InfoDialogBox dialogBox = new InfoDialogBox("Error!",
                            "User view already open for " + ((User) selectedNode).getID() + "!",
                            JOptionPane.ERROR_MESSAGE);
                } else if (selectedNode.getClass() == SingleUser.class) {
                    UserViewPanel userView = new UserViewPanel(allUsers, openPanels, selectedNode);
                    openPanels.put(((User) selectedNode).getID(), userView);
                }
            }
        });
    }
}
