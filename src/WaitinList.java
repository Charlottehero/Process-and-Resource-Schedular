import java.util.ArrayList;

class WaitinList {
    private ArrayList<Process> level0;
    private ArrayList<Process> level1;
    private ArrayList<Process> level2;

    WaitinList() {
        level0 = new ArrayList<>();
        level1 = new ArrayList<>();
        level2 = new ArrayList<>();
    }

    void insertProcess(Process process, int priority) {
        switch (priority) {
            case 0:
                level0.add(process);
                break;
            case 1:
                level1.add(process);
                break;
            case 2:
                level2.add(process);
//                System.out.println("-------This is waitlist 2------");
//                for (Process p: level2) {
//                    System.out.println(p.getPid());
//                }
                break;
        }
    }

    void deleteProcess(Process process, int priority) {
        switch (priority) {
            case 0:
                level0.remove(process);
                break;
            case 1:
                level1.remove(process);
                break;
            case 2:
                level2.remove(process);
                break;
        }
    }
}
