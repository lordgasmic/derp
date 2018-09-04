package lordgasmic.common.listener;

import java.util.ArrayList;
import java.util.List;

public class Listener {
	private List<Listenable> listeners;
	
	public Listener() {
		listeners = new ArrayList<>();
	}

	public void notifyListeners() {
        for(Listenable listener : listeners) {
        	//listener.notify(ballPosition);
        }
    }
	
}


/*
private static class Position {
}

private static interface BallObserver {
    void notify(Position ballPosition);
}

private static class Ball {
    private ArrayList<FootballObserver> observers = new ArrayList<FootballObserver>();
    private Position ballPosition = new Position();

    public void registerObserver(FootballObserver observer) {
        observers.add(observer);
    }

    public void notifyListeners() {
        for(FootballObserver observer : observers) {
            observer.notify(ballPosition);
        }
    }

    public void doSomethingWithFootballPosition() {
        //bounce etc
        notifyListeners();
    }
}

private static interface FootballObserver {
    void notify(Position ball);
}

private static class Player implements FootballObserver {
    public void notify(Position ball) {
        System.out.println("received new ball position");
    }
}

public static void main(String... args) {
    FootballObserver player1 = new Player();
    Ball football = new Ball();
    football.registerObserver(player1);
    football.doSomethingWithFootballPosition();
}

*/