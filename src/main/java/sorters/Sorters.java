package sorters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/**
 * Класс содержит "стандартные" реализации нескольких методов сортировки, согласно заданию.
 * Стандартные - то есть такие, которые будучи вызванными сами досортировывают массив до конца (не вызывают других
 * методов сортировки
 */
public class Sorters {

    /**
     * Сортировка выбором
     * 1. Находим минимальный элемент в массиве.
     * 2. Меняем его местами с первым элементом.
     * 3. Сортируем оставшиеся  элементов таким же подходом.
     */
    public void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = arr[i];
            int minIndex = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < min) {
                    min = arr[j];
                    minIndex = j;
                }
            }
            if (min <= arr[i]) {
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
        }
    }

    /**
    * Сортировка вставкой
    * 1. Находим минимальный элемент в массиве.
    * 2. Меняем его местами с первым элементом.
    * 3. Сортируем оставшиеся  элементов таким же подходом.
    */
    public void insertionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j - 1] > arr[j]) {
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    /**
    * Сортировка пузырьком
    * 1. Проходим по массиву от начала до конца и сравниваем каждую пару соседних элементов,
    * если левый больше - меняем местами с правым.
    * 2. Повторяем эту операцию пока в массиве есть пара где левый больше правого (инверсия) соседнего.
    * Меняем его в таком случае местами с первым элементом.
    */
    public void bubbleSort(int[] arr) {
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 1; i < arr.length; i++) {
                if (arr[i - 1] > arr[i]) {
                    int temp = arr[i - 1];
                    arr[i - 1] = arr[i];
                    arr[i] = temp;
                    sorted = false;
                }
            }
        }
    }

    /**
     * Сортировка слиянием mergeSort
     * алгоритм принимает массив, разбивает его на две половины,
     * рекурсивно вызывается от обеих, сортируя таким образом две половины массива,
     * а затем линейным алгоритмом объединяет отсортированные половины в единый массив.
     */
    public void mergeSort(int[] arr) {
        if (arr.length < 2) {
            return;
        }

        if (arr.length <= 21 || (arr.length > 145 && arr.length <= 450)) {
            selectionSort(arr);
            return;
        }

        int pos = arr.length/2;
        int[] left = Arrays.copyOfRange(arr, 0, pos);
        int[] right = Arrays.copyOfRange(arr, pos, arr.length);

        mergeSort(left);
        mergeSort(right);

        //слияние отсортированных left и right
        int i = 0, j = 0;
        while (i < left.length || j < right.length) {
            if (i < left.length && (j == right.length || (left[i] < right[j]))) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }
    }

    /**
     * Быстрая сортировка Хоера
     * 1. алгоритм принимает массив, рандомно генерирует номер элемента в пределах длинны массива,
     * разбивает массив на три части: левую, среднюю и правую. В левой - элементы меньше, в центре равные
     * а справа больше выбранного на предыдущем шаге.
     * 2. рекурсивно вызывается от левой и правой частей, сортируя таким образом две половины массива.
     * 3. после рекурсивного вызова складываем левый + центр + правый  (аналогично конкатенации конкатенации)
     */
    public void quickSort(int[] arr) {
        if (arr.length < 2) {
            return;
        }
        int pos = new Random().nextInt(arr.length);

        int[] left = new int[arr.length];
        int[] center = new int[arr.length];
        int[] right = new int[arr.length];

        int idx = 0, i =0, j = 0, k = 0;

        while (idx < arr.length) {
            if (arr[idx] < arr[pos]) {
                left[i] = arr[idx];
                i++;
            }
            if (arr[idx] == arr[pos]) {
                center[j] = arr[idx];
                j++;
            }
            if (arr[idx] > arr[pos]) {
                right[k] = arr[idx];
                k++;
            }
            idx++;
        }

        left = Arrays.copyOf(left,i);
        center = Arrays.copyOf(center,j);
        right = Arrays.copyOf(right,k);

        quickSort(left);
        quickSort(right);

        System.arraycopy(left, 0, arr, 0, i);
        System.arraycopy(center, 0, arr, i, j);
        System.arraycopy(right, 0, arr, i + j, k);
    }

    /**
     * Написанная по другому версия алгоритма быстрой сортировки Хоера, в которой убрано большое количество
     * перекопирований массивов в процессе работы за счет использования ArrayList.
     * Фактически quickSortImprooved- это просто "пускач" метода quickSort реализованного в классе
     * QuickSorters. Как показали тесты - не так уж и велика разница в скорости этих двух версий quickSort
     */
    public void quickSortImprooved(int[] arr) {
        ArrayList<Integer> a = new ArrayList<>();
        int l = 0;
        int r = arr.length;

        for (int element : arr) {
            a.add(element);
        }
        QuickSorters.quickSort(a, l, r);
    }
}
