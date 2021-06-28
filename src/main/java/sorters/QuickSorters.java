package sorters;

import java.util.ArrayList;
import java.util.Random;

/**
 * Класс содержит оптимизированный (дополнительный) вариант реализации  алгоритма quickSort
 */
public class QuickSorters {
    /**
     * Реализует работу с параметром сортировки для quickSort сортировки.
     * Принимает на вход массив в виде массива-коллекции и границы сортируемого полуинтервала (l, r),
     * где l - начальный индекс под-интервала, r - длинна под-интервала.
     * Генерирует рандомное число и перекидывает элементы в переданном массиве так, что слева от рандомно выбранного
     * на предыдущем шаге элемента остаются меньшие или равные ему элементы, а справа - большие.
     * Возвращает индекс этого пограничного элемента в обновленном массиве
     */
    public static int partition(ArrayList<Integer> a, int l, int r) {
        int pivot = a.get(l + new Random().nextInt(r - l)); // выбрали опорный элемент
        int i = l, j = r - 1;

        while (true) {
            while (i != a.size() && a.get(i) < pivot) { // пока текущий элемент соответствует левой части и индекс ок
                i++; // двигаем указатель влево
            }

            while (j > 0 && a.get(j) > pivot) { // пока текущий элемент соответствует правой части
                j--; // двигаем указатель вправо
            }

            if (i < j) { // если столкнулись с инверсией
                if (a.get(i).equals(a.get(j))) { //если два одинаковых элемента, ставим их рядом на 1 индекс больше
                    i++;
                }
                int temp = a.get(i);
                a.set(i, a.get(j)); // решаем ее
                a.set(j, temp);
            } else {
                break; // иначе выходим из цикла
            }
        }
        return j;
    }

    /**
    * Выполняет quickSort сортировку.  Принимает на вход массив в виде массива-коллекции
    * и границы сортируемого полуинтервала (l, r), где l - начальный индекс под-интервала, r - длинна под-интервала.
    */
    public static void quickSort(ArrayList<Integer> a, int l, int r) {
        if (l >= r - 1) { // пустой отрезок или состоящий из одного элемента
            return;
        }
        int mid = partition(a, l, r); // опорный элемент
        quickSort(a, l, mid);
        quickSort(a, mid + 1 , r);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{5, 2, 2, 4, 70,80, 80, 6, 90, 1};
        ArrayList<Integer> list = new ArrayList<>();
        for (int el : arr) {
            list.add(el);
        }
        for (Integer el : list) {
            System.out.print(el + " ");
        }
        System.out.println();
        quickSort(list,0, list.size());
        for (Integer el : list) {
            System.out.print(el + " ");
        }
    }
}
