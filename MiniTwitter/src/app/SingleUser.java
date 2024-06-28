package MiniTwitter.src.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MiniTwitter.src.visitor.Visitor;

public class SingleUser extends User implements Subject {
    private static final List<String> POSITIVE_WORDS = Arrays.asList("good", "great", "excellent");
    private Map<String, Observer> followers;
    private Map<String, Subject> followings;
    private List<String> newsFeed;
    private String latestMsg;
    private int positiveMsgs;
    private long createTime;

    public SingleUser(String id) {
        super(id);
        followers = new HashMap<String, Observer>();
        followers.put(this.getID(), this);
        followings = new HashMap<String, Subject>();
        newsFeed = new ArrayList<String>();
        createTime = System.currentTimeMillis();
    }

    public Map<String, Observer> getFollowers() {
        return followers;
    }

    public Map<String, Subject> getFollowings() {
        return followings;
    }

    public List<String> getNewsFeed() {
        return newsFeed;
    }

    public String getLatestMessage() {
        return this.latestMsg;
    }

    public int getPositiveMessageCount() {
        return positiveMsgs;
    }

    public long getCreationTime() {
        return createTime;
    }

    public void sendMessage(String msg) {
        this.latestMsg = msg;
        this.setMsgCount(this.getMsgCount() + 1);

        if (isPositiveMessage(msg)) {
            positiveMsgs++;
        }

        notifyObservers();
    }

    public boolean contains(String id) {
        return this.getID().equals(id);
    }

    public int getGroupUserCount() {
        return 0;
    }

    public int getSingleUserCount() {
        return 1;
    }

    public void update(Subject subject) {
        newsFeed.add(0, (((SingleUser) subject).getID() + ": " + ((SingleUser) subject).getLatestMessage()));
    }

    public void attach(Observer observer) {
        addFollower(observer);
    }

    public void notifyObservers() {
        for (Observer observer : followers.values()) {
            observer.update(this);
        }
    }

    public void accept(Visitor visitor) {
        visitor.visitSingleUser(this);
    }

    private void addFollower(Observer user) {
        this.getFollowers().put(((User) user).getID(), user);
        ((SingleUser) user).addUserToFollow(this);
    }

    private void addUserToFollow(Subject toFollow) {
        if (toFollow.getClass() == SingleUser.class) {
            getFollowings().put(((User) toFollow).getID(), toFollow);
        }
    }

    private boolean isPositiveMessage(String msg) {
        boolean positive = false;
        msg = msg.toLowerCase();
        for (String word : POSITIVE_WORDS) {
            if (msg.contains(word)) {
                positive = true;
            }
        }
        return positive;
    }
}
