package MiniTwitter.src.visitor;

import MiniTwitter.src.app.GroupUser;
import MiniTwitter.src.app.SingleUser;
import MiniTwitter.src.app.User;

public class PositiveTotalVisitor implements Visitor {

    @Override
    public int visitUser(User user) {
        int count = 0;

        if (user.getClass() == SingleUser.class) {
            count += visitSingleUser(user);
        } else if (user.getClass() == GroupUser.class) {
            count += visitGroupUser(user);
        }
        return count;
    }

    @Override
    public int visitSingleUser(User user) {
        return ((SingleUser) user).getPositiveMessageCount();
    }

    @Override
    public int visitGroupUser(User user) {
        int count = 0;

        for (User u : ((GroupUser) user).getGroupUsers().values()) {
            count += visitUser(u);
        }
        return count;
    }
    
}
