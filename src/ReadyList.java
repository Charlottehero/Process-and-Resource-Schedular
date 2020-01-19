import java.util.ArrayList;

class ReadyList {
    private ArrayList<Process> level0;
    private ArrayList<Process> level1;
    private ArrayList<Process> level2;

    ReadyList() {
        level0 = new ArrayList<Process>();
        level1 = new ArrayList<Process>();
        level2 = new ArrayList<Process>();
    }

    void insertProcess(Process process, int priority) {
        switch (priority) {
            case 0:
//                System.out.println("-------Setting Level 0------");
                level0.add(process);
                break;
            case 1:
//                System.out.println("-------Setting Level 1------");
                level1.add(process);
//                for (Process p: level1) {
//                    System.out.println(p.getPid());
//                }
                break;
            case 2:
                level2.add(process);
                break;
        }
    }

    void deleteProcess(Process p, int priority) {
        switch (priority) {
            case 0:
                //System.out.println("-------Removing Level 0------");
                level0.remove(p);
                break;
            case 1:
                //System.out.println("-------Removing Level 1------");
                level1.remove(p);
//                for (Process process: level1) {
//                    System.out.println(process.getPid());
//                }
                break;
            case 2:
                level2.remove(p);
                break;
        }
    }

    ArrayList<Process> getLevel(int priority) {
        switch (priority) {
            case 0:
//                System.out.println("-------This is Level 0------");
                return level0;
            case 1:
//                System.out.println("-------This is Level 1------");
//                for (Process p: level1) {
//                    System.out.println(p.getPid());
//                }
                return level1;
            case 2:
//                System.out.println("-------This is Level 2------");
//                for (Process p: level2) {
//                    System.out.println(p.getPid());
//                }
                return level2;
        }
        return null;
    }
}
