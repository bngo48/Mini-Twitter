package MiniTwitter.src.gui;

import MiniTwitter.src.app.GroupUser;
import MiniTwitter.src.app.Observer;
import MiniTwitter.src.app.User;

import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;
import java.awt.GridBagConstraints;

public class AdminControlPanel extends ControlPanel {
    private static JFrame frame;
    private JPanel treePanel;
    private JPanel addUserPanel;
    private JPanel openUserViewPanel;
    private JPanel showInfoPanel;

    private DefaultMutableTreeNode root;
    private Map<String, Observer> allUsers;

    // singleton
    private static AdminControlPanel instance;
    
    // double lock to ensure singleton
    public static AdminControlPanel getInstance() {
        if (instance == null) {
            synchronized (Driver.class) {
                if (instance == null) {
                    instance = new AdminControlPanel();
                }
            }
        }
        return instance;
    }

    // private constructor
    private AdminControlPanel() {
        super();
        initializeComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(frame, treePanel, 0, 0, 1, 6, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, addUserPanel, 1, 0, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, openUserViewPanel, 1, 2, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, showInfoPanel, 1, 4, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents(){
        frame = new JFrame("Mini Twitter App");
        formatFrame();

        allUsers = new HashMap<String, Observer>();
        root = new GroupUser("root");
        allUsers.put(((User) root).getID(), (Observer) this.root);

        treePanel = new TreePanel(root);
        addUserPanel = new AddUserPanel(treePanel, allUsers);
        openUserViewPanel = new OpenUserViewPanel(treePanel, allUsers);
        showInfoPanel = new ShowInfoPanel(treePanel);

    }

    private void formatFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setSize(1280, 720);
        frame.setVisible(true);
    }
}
