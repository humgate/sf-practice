package bisect;

import java.util.Arrays;
import java.util.function.Predicate;


public class BinarySearch {
    static int f(int x) {
        return 2 * x;
    }

    static int search(int a, int b, int Y) {
        int l = a; // левая граница
        int r = b; // правая граница
        // пока диапазон не сойдется к одному числу
        while ((l != r) && (l != r - 1)) {
            int mid = (l + r) / 2; // смотрим x посредине
            if (f(mid) > Y) {
                r = mid; // двигаем правую границу, если f(x) > Y
            } else {
                l = mid; // двигаем левую границу, если f(x) <= Y
            }
        }
        // если найденное значение подходит
        if (f(l) == Y) {
            return l;
        } else {
            return -1; // если такого x не существует
        }
    }


    static void sqrt3(float x) {
        float l = 0;
        float r = x + 1;
        float delta = (float) 1.0E-04;
        while (Math.abs(l - r) > delta) {
            float mid = (l + r) / 2;
            if (mid * mid * mid > x) {
                r = mid;
            } else {
                l = mid;
            }
        }
        System.out.println(l);
    }

    //находит в переданном массиве длинной n, наибольшее число которое менmше или равно Y
    static int binsearch(int[] array, int n, int Y) {
        Arrays.sort(array);
        int l = 0; // левая граница включительно
        int r = n; // правая граница не включительно
        while (l != r - 1){
        int mid = (l + r) / 2;
            if (array[mid] > Y) {
                r = mid;
            } else {
                l = mid;
            }
        }
        return array[l];
    }

    //ищет в переданном массиве длинной n самое правое вхождение Y
    static int rightBin(int[] array, int n, int Y) {
        int l = 0;
        int r = n;
        while (l != r - 1) {
            int mid = (l + r) / 2;
            if (array[mid] > Y) {
                r = mid;
            } else {
                l = mid;
            }
        }
        if (array[l] == Y) { // если мы смогли найти элемент Y
            return l; // возвращаем индекс его самого правого вхождения
        } else {
            return -1; // если такого элемента нет, вернем -1
        }
    }

    //ищет в переданном массиве длинной n самое левое вхождение Y
    static int leftBin(int[] array, int n, int Y) {
        int l = 0;
        int r = n;
        while (l != r - 1) {
            int mid = (l + r) / 2;
            if (array[mid] >= Y) {
                r = mid;
            } else {
                l = mid;
            }
        }
        if (array[r] == Y) { // если мы смогли найти элемент Y
            return r; // возвращаем индекс его самого правого вхождения
        } else {
            return -1; // если такого элемента нет, вернем -1
        }
    }
    /*
     * имеются два ксерокса, один из которых копирует лист за  секунд, а другой — за  секунд. Разрешается использовать
     * оба одновременно. Можно копировать и с оригинала, и с копии. Какое минимальное время для этого потребуется?
     */

    /**
     * Функция считает сколько всего копий будет получено при одновременной работе двух ксероксов, имеющих разную
     * заданную скорость копирования за заданное время.
     *
     * @param a - кол-во секунд, необходимое для копирования одной странице первому ксероксу
     * @param b - кол-во секунд, необходимое для копирования одной странице второму ксероксу
     * @param t - отведенное вреия копирования в секундах
     * @return - количество полученных копий в штуках
     */

    static int copy(int a, int b, int t) {
        int min = Integer.min(a, b);
        return (t != 0) ? t / min + (t - min) / b + (t - min) / a : 0;
    }

    /**
     * Определяет, возможно ли построить забор заданной высоты и ширины из заданного набора досок разной длины.
     * @param a - содержит набор имеющихся длин досок
     * @param h - требуемая высота забора
     * @param m - требуемая ширина забора в коk-во досок
     * @param n - число имеющихся досок, то же самое что a.length()
     * @return - false если такой забор нельзя построить, true - если можно
     */
    static boolean isFenceFeasible (int[] a, int h, int m, int n) {
        /*
         * максимально возможная ширина забора есть сумма всех длин досок, поделенных целочисленно на требуемую высоту
         */
        int avM = 0;
        for (int i = 0; i < n; i++) {
            avM = avM + a[i]/h;
        }
        System.out.println("максимально возможная ширина забора = " + avM);
        return avM >= m;
    }

    /* Задача коровы и стойла
    * На прямой расположены n стойл (даны их координаты на прямой), в которые необходимо расставить k коров так,
    * чтобы минимальное расстояние между коровами было как можно больше. Гарантируется, что 1 < k < n
    *
    * Можно научиться проверять, можно ли поставить всех коров друг от друга на расстоянии не меньше, чем какое-то d.
    * Если мы сможем придумать такую проверку, то в таком случае можно решить задачу бинарным поиском.
    * То есть мы будем бинарным поиском искать такое d, для которого ответ будет «да», а для d + 1 ответ будет «нет».
    */

    /**
     * Проверка для задачи с коровами и стойлами. Проверяет можно ли разместить заданное количество коров по стойлам с
     * заданными в массиве расстояниями от начала отсчета для каждого стойла, так чтобы расстояние между каждыми
     * соседним коровами было не менее d
     * не менее d
     * @param arr - массив координат стойл
     * @param n - число стоил = arr.lenth()
     * @param k - число коров
     * @param dist - расстояние между коровами
     * @return - true если можно, false если нет
     */
    static boolean checkDistance(int[] arr, int n, int k, int dist) {
        int locatedCows = 1;
        int last_cow = 0;

        for (int i = 1; i < n; i++) {
            if (arr[i] - arr[last_cow] >= dist) {
                locatedCows++;
                last_cow = i;
            }
        }
        return locatedCows >= k;
    }

    /**
     * Реализация бинарного поиска для предиката от целого числа
     * @param a - левая граница бинарного поиска
     * @param b - правая граница бинарного поиска
     * @param check - предикат
     * @return - возвращает значение int при котором предикат начинает давать true
     */
    static int binarySearchBoolean(int a, int b, Predicate<Integer> check) {
        int l = a; // левая граница
        int r = b; // правая граница
        // пока диапазон не сойдется к одному числу
        while ((l != r) &&(l != r - 1)) {
            int mid = (l + r) / 2; // смотрим x посредине
            if (check.test(mid)) {
                l = mid;
            } else {
                r = mid;
            }
        }
         return l;
    }

    /**
     * Метод вычисляющий минимальное расстояние между коровами при максимально возможном удалении коров друг от друга
     * @param arr массив координат стоил
     * @param n число стоил
     * @param k число коров
     * @return минимальное расстояние между коровами
     */
    static int getCowDistance(int[] arr, int n, int k) {
        return binarySearchBoolean(0, arr[n - 1] - arr[0] + 1,
                d -> checkDistance(arr, n, k, d));
    }

    public static void main(String[] args) {
    int[] arr = new int[] {2, 5, 7, 11,15,20};
    System.out.println(getCowDistance(arr,6,4));

    }
}
