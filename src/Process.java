import javafx.util.Pair;

import java.util.ArrayList;

public class Process {
    private int pid; // same as index in PCB
    private int state; // 1 stands for running, 0 stands for stopped, -1 stands for blocked
    private int parent = 0;
    private ArrayList<Integer> children;
    private int priority;
    private ArrayList<Pair<Integer, Integer>> resources;

    Process(int parent, int priority) {
        this.state = 0;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.priority = priority;
        this.resources = new ArrayList<>();
    }

    public void destroySingleProcess() {
        state = 0;
        parent = 0;
        priority = 0;
        resources.clear();
        children = null;
    }

    void addResource(Pair<Integer, Integer> rPair) {
        this.resources.add(rPair);
    }

    void removeResource(Pair<Integer, Integer> rPair) {
        this.resources.remove(rPair);
    }

    int getPid() {
        return pid;
    }

    int getState() {
        return state;
    }

    int getParent() {
        return parent;
    }

    ArrayList<Integer> getChildren() {
        return children;
    }

    int getPriority() {
        return priority;
    }

    ArrayList<Pair<Integer, Integer>> getResources() {
        return resources;
    }

    void setPid(int id) {
        this.pid = id;
    }

    void setState(int state) {
        this.state = state;
    }

    void setChildren(int pid) {
        if (pid == -1) {
            this.children.clear();
            return;
        }
        this.children.add(pid);
    }

    void setResources(ArrayList resources) {
        if (resources == null) {
            resources.clear();
        }
        this.resources = resources;
    }

    void setPriority(int p) {
        this.priority = p;
    }

    void addChild(int pid) {
        this.children.add(pid);
    }

    void deleteChild(int pid) {
        this.children.remove(Integer.valueOf(pid));
    }
}
