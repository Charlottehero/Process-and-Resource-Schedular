import javafx.util.Pair;

import java.util.ArrayList;

class Resource {
    int rid;
    private int state; // how many resource units left
    private ArrayList<Pair<Integer, Integer>> waitlist;
    private int total; // total units it have

    Resource(int r) {
        switch (r) {
            case 0:
                this.rid = r;
                this.state = 1;
                this.total = 1;
                break;
            case 1:
                this.rid = r;
                this.state = 1;
                this.total = 1;
                break;
            case 2:
                this.rid = r;
                this.state = 2;
                this.total = 2;
                break;
            case 3:
                this.rid = r;
                this.state = 3;
                this.total = 3;
                break;
        }
        waitlist = new ArrayList<>();
    }

    public ArrayList<Pair<Integer, Integer>> getWaitlist() {
        return waitlist;
    }

    public void addToWaitlist(Pair<Integer, Integer> pair) {
        this.waitlist.add(pair);
    }

    public void removeFromWaitlist(Pair<Integer, Integer> pair) {
        this.waitlist.remove(pair);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
//        int he = this.state - state;
//        System.out.println("Allocating Resource " + rid + " for " + he + " unites");
        this.state = state;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
