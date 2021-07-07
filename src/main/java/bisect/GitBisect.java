package bisect;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;


public class GitBisect {
    static String firstCommit = "7f777ed95a19224294949e1b4ce56bbffcb1fe9f";
    static String lastCommit = "dd104400dc551dd4098f35e12072e12c45822adc";

    /**
     * Реализация бинарного поиска по списку коммитов с использованием для передаваемого "проверятеля" условия
     * @param commitList - список коммитов
     * @param a - левая граница бинарного поиска
     * @param b - правая граница бинарного поиска
     * @param check - BooleanSupplier
     * @return - возвращает значение int при котором предикат начинает давать true
     */
    static int binarySearchInCommitList(ArrayList<String> commitList, int a, int b, BooleanSupplier check) {
        int l = a;
        int r = b;

        while (l != r - 1) {
            int mid = (l + r) / 2;
            Utils.runExtCommand("git", "checkout", commitList.get(mid));
            if (check.getAsBoolean()) {
                l = mid;
            } else {
                r = mid;
            }
        }

        Utils.runExtCommand("git", "checkout", "main");
        return l;
    }

    public static void main(String [] args) {
        firstCommit = args[0];
        lastCommit = args[1];

        //Заполнили список коммитов
        ArrayList<String> list = new ArrayList<>(
                Utils.runExtCommand("git", "rev-list", "--ancestry-path", firstCommit + ".." + lastCommit));

        //Если он пуст - выходим
        if (list.size() == 0) {
            System.out.println("No commits read from repository, exiting now");
            System.exit(0);
        }

        System.out.println(list.size() +" commits read from git repository. Starting bisect...");
        int num = binarySearchInCommitList(list,0,list.size()-1, () -> Utils.runSimpleExtCommand(args[2]) == 0);

        System.out.println("Commit found. Hash = " + list.get(num) + ", number counting from recent= " + num);
     }
}
