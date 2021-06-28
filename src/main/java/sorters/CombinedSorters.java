package sorters;

import java.util.Arrays;
/*
 * Итоговая реализация сортировки, комбинирующая в себе наиболее эффективные реализации сортировочных алгоритмов
 * на разных значениях длинны массива n.
 * Согласно результатам проведенных тестов:
 *
 * при 1 < n <= 21 - selection sort
 * при 21 < n <= 145 - merge sort
 * при 145 < n <= 450 - опять selection sort как это не странно, но несколько раз повторял тесты и именно так выходит
 * при 450 < n < 200 000 (при более высоких значениях не тестировалось)
 */

public class CombinedSorters {
    /*
     * Для реализации комбинированного метода сортировки потребуется внутри каждого используемого в нем метода
     * сортировки анализировать n и вызывать (или не вызывать) другой метод сортировки. Написаны новые реализации для
     * каждого из методов сортировки, который будет использоваться внутри комбинированного метода. Эти методы имеют в
     * имени суффикс Ex.
     * Первым делом проверяется условие применимости данного частного метода в зависимости от n и затем либо выполняется
     * далее этот метод, либо или вызывается другой метод.
     *
     */

    //Спец. версия мердж сортировки с проверкой, не нужно ли вызвать другой метод
    public void mergeSortEx(int[] arr) {
        if (arr.length <= 21 || (arr.length > 145 && arr.length <= 450)) {
            //если true, значит этот массива мердж сортировкой не нужно сортировать. Запускаем selectionSortEx
            selectionSortEx(arr);
            return;
        }
        System.out.println("n = " + arr.length + ": mergeSort");
        int pos = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, pos);
        int[] right = Arrays.copyOfRange(arr, pos, arr.length);

        //рекурсивно вызываем методы Ex, чтобы на какой то итерации мог сработать другой нужный метод
        mergeSortEx(left);
        mergeSortEx(right);

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

    //Спец. версия сортировки вставкой с проверкой, не нужно ли вызвать другой метод
    public void selectionSortEx(int[] arr) {
        //если true, значит этот массив сортировкой вставкой не нужно сортировать. Запускаем mergeSortEx
        if ((arr.length > 21 && arr.length <= 145) || arr.length > 450) {
            mergeSortEx(arr);
            return;
        }
        System.out.println("n = " + arr.length + ": selectionSort");

        //поскольку сортировка выбором не использует рекурсию - то просто вызовем обычный selectionSort()
        Sorters sorters = new Sorters();
        sorters.selectionSort(arr);
    }

    /* Финальный (комбинированный) метод сортировки
     * Просто запускаем любой один из Ex методов сортировки. Он сам уже внутри себя выполнит проверку длинны
     * массива и либо сам досортирует массив, либо вызовет другой метод сортировки. Тот в свою очередь или
     * сам досортирует массив до конца или вызовет другой метод. Таким образом они досортируют массив до конца
     */
    public void combinedSort (int[] arr) {
        mergeSortEx(arr);
    }

    //протестируем
    public static void main(String[] args) {
        int[] array = Benchmarks.generateArray(451);
        CombinedSorters cSorters = new CombinedSorters();
        cSorters.combinedSort(array);
    }
}
