package MiniTwitter.src.visitor;

import MiniTwitter.src.app.User;

public interface Visitor {
    public int visitUser(User user);
    public int visitSingleUser(User user);
    public int visitGroupUser(User user);
}
