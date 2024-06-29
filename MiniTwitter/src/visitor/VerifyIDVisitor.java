package MiniTwitter.src.visitor;

import java.util.HashSet;
import java.util.Set;

import MiniTwitter.src.app.GroupUser;
import MiniTwitter.src.app.SingleUser;
import MiniTwitter.src.app.User;

public class VerifyIDVisitor implements Visitor {
    private boolean noneInvalid;
    private String invalidID;
    private Set<String> allUserID;

    public VerifyIDVisitor() {
        noneInvalid = true;
        invalidID = "";
        allUserID = new HashSet<>();
    }

    @Override
    public int visitUser(User user) {
        if (user.getClass() == SingleUser.class) {
            visitSingleUser(user);
        } else if (user.getClass() == GroupUser.class) {
            visitGroupUser(user);
        }
        return 0;
    }

    @Override
    public int visitSingleUser(User user) {
        String userID = user.getID();;
        if ((userID.contains(" ")) || !allUserID.add(userID)) {
            noneInvalid = false;
            if (invalidID.indexOf("'" + userID + "'") == -1) { // Check if already added
                if (invalidID.length() > 0) {
                    invalidID += ", ";
                }
                invalidID += "'" + userID + "'";
            }
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

    public boolean verifyID() {
        return noneInvalid;
    }

    public String getInvalidID() {
        return invalidID;
    }
}
