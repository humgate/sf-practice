package sorters;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Класс содержит в методе main() сравнительный тест скорости работы алгоритмов сортировки, а так же вспомогательные
 * методы и параметры для его выполнения
 */
public class Benchmarks {
    //в этот файл будем писать результаты сравнительного теста скорости работы алгоритмов
    public static final String FILE_PATH = "sortersResults.txt";

    //Тесты будем начинать со значения n
    public static final int START_N = 4000;

    //Тесты будем заканчивать со значения n ;
    public static final int STOP_N = 8000;

    //Тесты будем выполнять с инкрементом n;
    public static final int INCREMENT_N = 10000;

    /**
     * Cортирует переданный массив переданным сортером и логирует время своей работы в файл при
     * помощи System.nanoTime().
     */
    public static void sortAndLog(int[] arr, String sorterName, Consumer<int[]> consumer, BufferedWriter writer) {
        long startTime = System.nanoTime();
        consumer.accept(arr);
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        writeStringToFile(writer, sorterName + "," + arr.length + "," + timeElapsed);
    }

    //инициализация писателя в текстовый файл
    private static BufferedWriter initWriter() {
        FileWriter fileWriter;
        BufferedWriter bufFileWriter;
        try {
            //писатель в файл в режиме добавления
            fileWriter = new FileWriter(Benchmarks.FILE_PATH, true);
            bufFileWriter = new BufferedWriter(fileWriter);
            return bufFileWriter;
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * писатель результатов в текстовый файл. Необходимо предварительно инициализировать BufferedWriter
     */
    public static void writeStringToFile(BufferedWriter bufFileWriter, String str) {
        try {
            bufFileWriter.write(str + "\n");
            bufFileWriter.flush();
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл");
        }
    }

    //генератор массива рандомных чисел для сравнительного теста
    public static int[] generateArray(int length) {
        int [] result = new int[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result[i] = random.nextInt();
        }
        return result;
    }

    /*
     * Сам тест
     */
    public static void main(String[] args) {
        Sorters sorter = new Sorters();
        BufferedWriter writer = initWriter();
        writeStringToFile(Objects.requireNonNull(writer),"Sort algorithm,n,elapsed time");

        for (int i = START_N; i < STOP_N + 1; i += INCREMENT_N) {
            int[] arr = generateArray(i);
            int[] arrCopy = Arrays.copyOf(arr, arr.length);

            sortAndLog(arr, "selectionSort", sorter::selectionSort, writer);
            arr = Arrays.copyOf(arrCopy, arrCopy.length);

            sortAndLog(arr, "insertionSort", sorter::insertionSort, writer);
            arr = Arrays.copyOf(arrCopy, arrCopy.length);

            sortAndLog(arr, "bubbleSort", sorter::bubbleSort, writer);
            arr = Arrays.copyOf(arrCopy, arrCopy.length);

            sortAndLog(arr, "mergeSort", sorter::mergeSort, writer);
            arr = Arrays.copyOf(arrCopy, arrCopy.length);

            sortAndLog(arr, "quickSort", sorter::quickSort, writer);
            arr = Arrays.copyOf(arrCopy, arrCopy.length);

            sortAndLog(arr, "quickSortImprooved", sorter::quickSortImprooved, writer);
        }
    }
}
