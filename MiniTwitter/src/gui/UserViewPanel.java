package MiniTwitter.src.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import MiniTwitter.src.app.GroupUser;
import MiniTwitter.src.app.Observer;
import MiniTwitter.src.app.SingleUser;
import MiniTwitter.src.app.Subject;
import MiniTwitter.src.app.User;

public class UserViewPanel extends ControlPanel {
    private static JFrame frame;
    private GridBagConstraints gBagConstraints;
    private JTextField toFollowTextField;
    private JTextArea tweetMsgTextArea;
    private JTextArea currentFollowingTextArea;
    private JTextArea newsTextArea;
    private JScrollPane tweetMsgScrollPane;
    private JScrollPane currentFollowingScrollPane;
    private JScrollPane newsScrollPane;
    private JButton followUserButton;
    private JButton postTweetButton;
    private JLabel createTimeLabel;
    private JLabel lastUpdatedTimeLabel;
    private Subject user;
    private Map<String, Observer> allUsers;
    private Map<String, JPanel> openPanels;

    public UserViewPanel(Map<String, Observer> allUsers, Map<String, JPanel> allPanels, DefaultMutableTreeNode user) {
        super();
        this.user = (Subject) user;
        this.allUsers = allUsers;
        this.openPanels = allPanels;
        initializeComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(frame, toFollowTextField, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, followUserButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, currentFollowingTextArea, 0, 1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, tweetMsgScrollPane, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, postTweetButton, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, newsScrollPane, 0, 3, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, createTimeLabel, 0, 4, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, lastUpdatedTimeLabel, 0, 5, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        frame = new JFrame("User View");
        formatFrame();

        gBagConstraints = new GridBagConstraints();
        gBagConstraints.ipady = 100;

        toFollowTextField = new JTextField("User ID");
        followUserButton = new JButton("Follow User");
        initializeFollowUserButtonActionListener();

        currentFollowingTextArea = new JTextArea("Current Following: ");
        formatTextArea(currentFollowingTextArea);
        currentFollowingScrollPane = new JScrollPane(currentFollowingTextArea);
        currentFollowingScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        tweetMsgTextArea = new JTextArea("Tweet Message");
        tweetMsgScrollPane = new JScrollPane(tweetMsgTextArea);
        tweetMsgScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        postTweetButton = new JButton("Post Tweet");
        initializePostTweetButtonActionListener();

        newsTextArea = new JTextArea("News Feed: ");
        formatTextArea(newsTextArea);
        newsScrollPane = new JScrollPane(newsTextArea);
        newsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        createTimeLabel = new JLabel("Creation Time: " + ((SingleUser) user).getCreationTime());
        lastUpdatedTimeLabel = new JLabel("Last Updated at: " + ((SingleUser) user).getLastUpdatedTime());

        updateCurrentFollowingTextArea();
        updateNewsFeedTextArea();
    }

    private void formatTextArea(JTextArea textArea) {
        textArea.setLineWrap(true);
        textArea.setRows(8);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
    }

    private void formatFrame() {
        frame.setLayout(new GridBagLayout());
        frame.setSize(800, 400);
        frame.setTitle(((User) user).getID());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                openPanels.remove(((User) user).getID());
            }
        });
    }

    private void updateNewsFeedTextArea() {
        String list = "News Feed: \n";

        for (String news : ((SingleUser) user).getNewsFeed()) {
            list += " - " + news + "\n";
        }

        newsTextArea.setText(list);
        newsTextArea.setCaretPosition(0);
    }

    private void updateCurrentFollowingTextArea() {
        String list = "Currently Following: \n";
        for (String followings : ((SingleUser) user).getFollowings().keySet()) {
            list += " - " + followings + "\n";
        }
        currentFollowingTextArea.setText(list);
        currentFollowingTextArea.setCaretPosition(0);
    }

    private void initializePostTweetButtonActionListener() {
        postTweetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ((SingleUser) user).sendMessage(tweetMsgTextArea.getText());

                for (JPanel panel : openPanels.values()) {
                    ((UserViewPanel) panel).updateNewsFeedTextArea();
                }
            }
        });
    }

    private void initializeFollowUserButtonActionListener() {
        followUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                User toFollow = (User) allUsers.get(toFollowTextField.getText());

                if (!allUsers.containsKey(toFollowTextField.getText())) {
                    InfoDialogBox dialogBox = new InfoDialogBox("Error!",
                            "User does not exist!",
                            JOptionPane.ERROR_MESSAGE);

                } else if (toFollow.getClass() == GroupUser.class) {
                    InfoDialogBox dialogBox = new InfoDialogBox("Error!",
                            "Cannot follow a group!",
                            JOptionPane.ERROR_MESSAGE);
                } else if (allUsers.containsKey(toFollowTextField.getText())) {
                    ((Subject) toFollow).attach((Observer) user);
                }

                // show current following as list
                updateCurrentFollowingTextArea();
            }
        });
    }
}
