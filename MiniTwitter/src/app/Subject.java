package MiniTwitter.src.app;

public interface Subject {
    
    public void attach(Observer observer);

    public void notifyObservers();
}
