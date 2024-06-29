package MiniTwitter.src.visitor;

import MiniTwitter.src.app.GroupUser;
import MiniTwitter.src.app.SingleUser;
import MiniTwitter.src.app.User;

public class LastUpdatedUserVisitor implements Visitor {
    private SingleUser lastUpdatedUser;
    private long latestUpdatedTime;

    public LastUpdatedUserVisitor() {
        lastUpdatedUser = null;
        latestUpdatedTime = Long.MIN_VALUE;
    }

    public int visitUser(User user) {
        if (user.getClass() == SingleUser.class) {
            visitSingleUser(user);
        } else if (user.getClass() == GroupUser.class) {
            visitGroupUser(user);
        }
        return 0;
    }

    public int visitSingleUser(User user) {
        SingleUser singleUser = (SingleUser) user;
        if (singleUser.getLastUpdatedTime() > latestUpdatedTime) {
            latestUpdatedTime = singleUser.getLastUpdatedTime();
            lastUpdatedUser = singleUser;
        }
        return 0;
    }

    @Override
    public int visitGroupUser(User user) {
        for (User u : ((GroupUser) user).getGroupUsers().values()) {
            visitUser(u);
        }
        return 0;
    }

    public SingleUser getLastUpdatedUser() {
        return lastUpdatedUser;
    }
}
