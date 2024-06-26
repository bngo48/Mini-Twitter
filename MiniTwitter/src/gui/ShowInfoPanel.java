package MiniTwitter.src.gui;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.midi.MidiDevice.Info;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import MiniTwitter.src.app.User;
import MiniTwitter.src.app.SingleUser;
import MiniTwitter.src.visitor.GroupTotalVisitor;
import MiniTwitter.src.visitor.LastUpdatedUserVisitor;
import MiniTwitter.src.visitor.MsgTotalVisitor;
import MiniTwitter.src.visitor.PositiveTotalVisitor;
import MiniTwitter.src.visitor.UserTotalVisitor;
import MiniTwitter.src.visitor.VerifyIDVisitor;

public class ShowInfoPanel extends ControlPanel {
    private JButton userTotalButton;
    private JButton groupTotalButton;
    private JButton msgTotalButton;
    private JButton positivePercentButton;
    private JButton lastUpdatedUserButton;
    private JButton verifyIDButton;
    private JPanel treePanel;

    public ShowInfoPanel(JPanel treePanel) {
        super();
        this.treePanel = treePanel;
        initializeComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(this, userTotalButton, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, groupTotalButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, msgTotalButton, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, positivePercentButton, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, verifyIDButton, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, lastUpdatedUserButton, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        userTotalButton = new JButton("Show User Total");
        initializeUserTotalButtonActionListener();

        groupTotalButton = new JButton("Show Group Total");
        initializeGroupTotalButtonActionListener();

        msgTotalButton = new JButton("Show Messages Total");
        initializeMessagesTotalButtonActionListener();

        positivePercentButton = new JButton("Show Positive Percentage");
        initializePositivePercentageButtonActionListener();

        verifyIDButton = new JButton("Verify User IDs");
        initializeVerifyIDButtonActionListener();

        lastUpdatedUserButton = new JButton("Find Last Updated User");
        initializeLastUpdatedUserButtonActionListener();
    }

    private DefaultMutableTreeNode getSelectedNode() {
        JTree tree = ((TreePanel) treePanel).getTree();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
        if (!((TreePanel) treePanel).getRoot().equals(selectedNode)) {
            selectedNode = (DefaultMutableTreeNode) selectedNode.getUserObject();
        }

        return selectedNode;
    }

    private void initializeUserTotalButtonActionListener() {
        userTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                UserTotalVisitor visitor = new UserTotalVisitor();
                ((User) selectedNode).accept(visitor);
                String message = "Total number of inidividual users within "
                        + ((User) selectedNode).getID() + ": "
                        + Integer.toString(visitor.visitUser(((User) selectedNode)));

                InfoDialogBox popUp = new InfoDialogBox(((User) selectedNode).getID() + " information",
                        message, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void initializeGroupTotalButtonActionListener() {
        groupTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                GroupTotalVisitor visitor = new GroupTotalVisitor();
                ((User) selectedNode).accept(visitor);
                String message = "Total number of groups within "
                        + ((User) selectedNode).getID() + ": "
                        + Integer.toString(visitor.visitUser(((User) selectedNode)));

                InfoDialogBox popUp = new InfoDialogBox(((User) selectedNode).getID() + " information",
                        message, JOptionPane.INFORMATION_MESSAGE);

            }
        });
    }

    private void initializeMessagesTotalButtonActionListener() {
        msgTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                MsgTotalVisitor visitor = new MsgTotalVisitor();
                ((User) selectedNode).accept(visitor);
                String message = "Total number of messages sent by "
                        + ((User) selectedNode).getID() + ": "
                        + Integer.toString(visitor.visitUser(((User) selectedNode)));

                InfoDialogBox popUp = new InfoDialogBox(((User) selectedNode).getID() + " information",
                        message, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void initializePositivePercentageButtonActionListener() {
        positivePercentButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                PositiveTotalVisitor positiveCountVisitor = new PositiveTotalVisitor();
                ((User) selectedNode).accept(positiveCountVisitor);
                int positiveCount = positiveCountVisitor.visitUser(((User) selectedNode));

                MsgTotalVisitor messageCountVisitor = new MsgTotalVisitor();
                ((User) selectedNode).accept(messageCountVisitor);
                int messageCount = messageCountVisitor.visitUser(((User) selectedNode));

                // calculate percentage, default 0.00
                double percentage = 0;
                if (messageCount > 0) {
                    percentage = ((double) positiveCount / messageCount) * 100;
                }
                String percentageString = String.format("%.2f", percentage);

                String message = "Percentage of positive messages sent by "
                        + ((User) selectedNode).getID() + ": "
                        + percentageString + "%";

                InfoDialogBox popUp = new InfoDialogBox(((User) selectedNode).getID() + " information",
                        message, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    
    private void initializeLastUpdatedUserButtonActionListener() {
        lastUpdatedUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // start from the root
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) ((TreePanel) treePanel).getRoot();

                LastUpdatedUserVisitor visitor = new LastUpdatedUserVisitor();
                ((User) root).accept(visitor);
                SingleUser lastUpdatedUser = visitor.getLastUpdatedUser();

                String message = "Last Updated User: " + lastUpdatedUser.getID();

                InfoDialogBox popUp = new InfoDialogBox("Last Updated User", message, 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void initializeVerifyIDButtonActionListener() {
        verifyIDButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // start from root
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) ((TreePanel) treePanel).getRoot();

                VerifyIDVisitor visitor = new VerifyIDVisitor();
                ((User) root).accept(visitor);

                if (visitor.verifyID()) {
                    InfoDialogBox popUp = new InfoDialogBox("ID Validation", "All User/Group IDs are valid.",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String message = "The following IDs are invalid or duplicate: " + visitor.getInvalidID();

                    InfoDialogBox popUp = new InfoDialogBox("ID Validation", message, 
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
