import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Manager {
    static Process[] PCB; // process control block with each process inside
    static Resource[] RCB;
    static ReadyList RL; // ready list
    static WaitinList WL;

    Process currentP; // currentP process id

    private int totalProcess = 0;

    Manager() {
        PCB = new Process[16];

        RCB = new Resource[4];
        for (int rid = 0; rid < 4; rid++) {
            RCB[rid] = new Resource(rid);
        }

        RL = new ReadyList();

        WL = new WaitinList();

        Process newP = new Process(-1, 0);
        newP.setState(1);
        newP.setPid(0);
        PCB[0] = newP;

        RL.insertProcess(newP, 0);
        totalProcess++;
        currentP = newP;

        System.out.println("Initialization Succeeded!!!");
        System.out.println("*Process 0 is running");
    }

    int create(int priority) {
        if (totalProcess != 16) {
            Process newP = new Process(currentP.getPid(), priority);
            int i;

            for (i = 0; i < PCB.length; i++) {
                if (PCB[i] == null) {
                    PCB[i] = newP;
                    break;
                }
            }

            newP.setPid(i);
            currentP.addChild(i);

            RL.insertProcess(newP, priority);
            totalProcess++;
            System.out.println("Process " + i + " created");

            schedular();
            return 0;
        }
        return -1;
    }

    int destroy(int pid) {
        if (PCB[pid] == null) {
            System.out.println("Error: process does not exist!");
            return -1;
        }

        ArrayList<Integer> children = currentP.getChildren(); // check if deleting process is currentP's child

        if (children.contains(pid)) {
            currentP.deleteChild(pid);

            destroyP(pid);

            schedular();
            System.out.println("Process " + pid + " destroyed");
            totalProcess--;

//            System.out.println("Resource state status: ");
            for (int i = 0; i < RCB.length; i++) {
                for (Pair<Integer, Integer> p: RCB[i].getWaitlist()) {
                    if (p.getKey() == currentP.getPid()) {
//                        System.out.println("Which resource: " + i);
//                        System.out.println("How many: " + p.getValue());
                        RCB[i].removeFromWaitlist(p);
                        RCB[i].setState(RCB[i].getState() - p.getValue());
                    }
                }
                System.out.println(RCB[i].getState());
            }
            return 0;
        }
        if (currentP.getPid() == pid) {
            destroyP(currentP.getPid());
            currentP.setPriority(-1);
            totalProcess--;
            schedular();
        }
        System.out.println("Not a child of current process");
        return -1;
    }

    int request(int r, int units) {
        if (currentP.getPid() == 0) {
            System.out.println("Error: Process 0 is not allowed to request Resource");
            return -1;
        }
        ArrayList<Pair<Integer, Integer>> held = currentP.getResources();
        for (Pair<Integer, Integer> pHolding: held) {
            if(pHolding.getKey() == r) {
                if (pHolding.getValue() + units > RCB[r].getTotal()) {
                    System.out.println("Error: Too much resource request for this process");
                    return -1;
                }
            }
        }
//        System.out.println("Resource state status: ");
//        for (Resource a: RCB) {
//            System.out.println(a.getState());
//        }
        Resource resource = RCB[r];

        if (resource.getState() >= units) {
            resource.setState(resource.getState() - units);
            Pair<Integer, Integer> rPair= new Pair<>(r, units);
            currentP.addResource(rPair);
            System.out.println("Resource " + r + " allocated");

            return 0;
        }
        else {
            Pair<Integer, Integer> wPair = new Pair<>(currentP.getPid(), units);
            RCB[r].addToWaitlist(wPair);
            currentP.setState(-1);
            RL.deleteProcess(currentP, currentP.getPriority());
            System.out.println("Process "+ currentP.getPid() + " blocked");

//            System.out.println("Waitlist status: ");
//            for (Resource a: RCB) {
//                for (Pair<Integer, Integer> p: a.getWaitlist()) {
//                    System.out.println(a.rid);
//                    System.out.println("WAITING PID: " + p.getKey() + "; UNITS: " + p.getValue());
//                }
//            }

            Process p = currentP;
            int pPriority = p.getPriority();
            currentP.setPriority(-1);

            schedular();
            p.setPriority(pPriority);
            WL.insertProcess(p, pPriority);

            return 0;
        }
    }

    int release(int r, int units) {
//        System.out.println("Resource state status: ");
//        for (Resource a: RCB) {
//            System.out.println(a.getState());
//        }

        Resource resource = RCB[r];
        Pair<Integer, Integer> rPair = new Pair<>(r, units);

        Map<Integer, Integer> calHolding = new HashMap<>();

        for (Pair<Integer, Integer> pair: currentP.getResources()) {
            int count = calHolding.getOrDefault(pair.getKey(), 0);
            calHolding.put(pair.getKey(), count + pair.getValue());
        }

        if (!calHolding.containsKey(rPair.getKey())) {
            System.out.println("Not holding this resource");
            return -1;
        }
        if (calHolding.get(rPair.getKey()) < rPair.getValue()) {
            System.out.println("Not enough resource to release");
            return -1;
        }

        currentP.getResources().remove(rPair);
        ArrayList<Pair<Integer, Integer>> waitList = resource.getWaitlist();
        if (waitList.isEmpty()) {
            if (resource.rid == 0) {
                resource.setState(resource.rid);
            }
            else {
                resource.setState(resource.rid);
            }
            return 0;
        }
        else {
            ArrayList<Process> temp = new ArrayList<>();
            ArrayList<Pair<Integer, Integer>> nPairs = new ArrayList<>();
            ArrayList<Pair<Integer, Integer>> rePairs = new ArrayList<>();
            int u = resource.getTotal();
            for (Pair<Integer, Integer> wPair: waitList) {
                if (u != 0) {
                    temp.add(PCB[wPair.getKey()]);
                    u = u - wPair.getValue();
                    nPairs.add(new Pair<>(r,wPair.getValue()));
                    rePairs.add(wPair);
                }
            }
            for (Pair<Integer, Integer> aPair: rePairs) {
                waitList.remove(aPair);
            }
            int i = 0;
            for (Process j: temp) {
                WL.deleteProcess(j, j.getPriority());
                RL.insertProcess(j, j.getPriority());
                j.setState(0);
                j.addResource(nPairs.get(i));
                i++;
            }

            System.out.println("Resource " + r + " released");

            schedular();
            return 0;
        }
    }

    void timeout() {
        RL.deleteProcess(currentP, currentP.getPriority());

        Process p = currentP;
        int pPriority = p.getPriority();
        if (!(RL.getLevel(currentP.getPriority()).isEmpty())){
            currentP.setPriority(-1);
        }

        schedular();
        RL.insertProcess(p, pPriority);
        p.setPriority(pPriority);
    }

    private void schedular() { // context switching
        ArrayList<Process> highest = RL.getLevel(2);
        int i = 2;
        while (highest.isEmpty() && i > 0) {
            i--;
            highest = RL.getLevel(i);
        }
//        System.out.println("wherhwehrwherhwerh: " + i);
        for (Process p: highest) {
//            System.out.println("sdfsdfsdfsdf: " + p.getPriority());
//            System.out.println("hwowhwowhohwohwowohw: " + currentP.getPriority());
            if (p.getPriority() > currentP.getPriority()) {
                currentP.setState(0);
                currentP = p;
                currentP.setState(1);
                break;
            }
        }
    }

    private void destroyP(int pid) {
        Process p = PCB[pid];

        int priority = p.getPriority();
        WLorRL(p, priority);               // delete it from readyList or waitList

        for (Pair<Integer, Integer> r: p.getResources()) {      // release its holding resources
            RCB[r.getKey()].setState(RCB[r.getKey()].getState() + r.getValue());
            RCB[r.getKey()].removeFromWaitlist(r);
        }

        ArrayList<Integer> children = p.getChildren();
        for (int c : children) {
            destroyP(c);
        }

        PCB[pid] = null;    // remove it from PCB

        p.destroySingleProcess();       // destroy this process
        totalProcess--;
    }

    private void WLorRL(Process p, int priority) {
        List level = RL.getLevel(priority);
        if (level.contains(p)) {
            RL.deleteProcess(p, priority);
        }
        else {
            WL.deleteProcess(p, priority);
        }
    }
}
