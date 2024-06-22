package MiniTwitter.src.app;

import javax.swing.tree.DefaultMutableTreeNode;

import MiniTwitter.src.visitor.Visitor;

public abstract class User extends DefaultMutableTreeNode implements Observer {
    private String id;
    private int msgCount;
    public abstract boolean contains(String id);
    public abstract int getSingleUserCount();
    public abstract int getGroupUserCount();

    public User(String id) {
        super(id);
        this.id = id;
        this.setMsgCount(0);
    }

    public String getID() {
        return id;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public abstract void accept(Visitor visitor);
}

