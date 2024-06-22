package MiniTwitter.src.app;

import java.util.HashMap;
import java.util.Map;

import MiniTwitter.src.visitor.Visitor;

public class GroupUser extends User {
    private Map<String, User> groupUsers;

    public GroupUser(String id) {
        super(id);
        groupUsers = new HashMap<String, User>();
    }

    public Map<String, User> getGroupUsers() {
        return groupUsers;
    }

    public User addUserToGroup(User user) {
        if (!this.contains(user.getID())) {
            this.groupUsers.put(user.getID(), user);
        }
        return this;
    }

    public boolean contains(String id) {
        boolean contains = false;
        for (User user : groupUsers.values()) {
            if (user.contains(id)) {
                contains = true;
            }
        }
        return contains;
    }

    public int getSingleUserCount() {
        int count = 0;
        for (User user : this.groupUsers.values()) {
            count += user.getSingleUserCount();
        }
        return count;
    }

    public int getGroupUserCount() {
        int count = 0;
        for (User user : this.groupUsers.values()) {
            if (user.getClass() == GroupUser.class) {
                count++;
                count += user.getGroupUserCount();
            }
        }
        return count;
    }

    public int getMsgCount() {
        int msgCount = 0;
        for (User user : this.groupUsers.values()) {
            msgCount += user.getMsgCount();
        }
        return msgCount;
    }

    public void update(Subject subject) {
        for (User user : groupUsers.values()) {
            ((Observer) user).update(subject);
        }
    }

    public void accept(Visitor visitor) {
        for (User user : groupUsers.values()) {
            user.accept(visitor);
        }
        visitor.visitGroupUser(this);
    }

    @SuppressWarnings("unused")
    private boolean containsGroupUser() {
        boolean containsGroup = false;
        for (User user : this.groupUsers.values()) {
            if (user.getClass() == GroupUser.class) {
                containsGroup = true;
            }
        }
        return containsGroup;
    }
}
