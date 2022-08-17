package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для слияния данных из двух файлов в один
 * @param <T> - тип сортируемых данных
 */
public class Merge<T extends Comparable<T>> {
    private final boolean ascendingOrder;
    private final String outputName;

    /**
     * Конструктор - создание нового объекта
     * @param ascendingOrder - порядок сортировки
     * @param outputName - название выходного файла
     */
    public Merge(boolean ascendingOrder, String outputName) {
        this.ascendingOrder = ascendingOrder;
        this.outputName = outputName;
    }

    /**
     * Сортировка слиянием данных из двух файлов
     * @param list1 - данные из первого файла
     * @param list2 - данные из второго файла
     * @return отсортированные данные из двух файлов
     */
    public List<T> merge(List<T> list1, List<T> list2) {
        // if both or some of the arrays are empty
        if (list1.isEmpty() && list2.isEmpty()) return new ArrayList<>();
        if (list1.isEmpty()) return list2;
        if (list2.isEmpty()) return list1;

        int len1 = list1.size();
        int len2 = list2.size();

        List<T> merge = new ArrayList<>();
        int first = 0, second = 0;
        T max1 = list1.get(0);
        T max2 = list2.get(0);
        if (ascendingOrder) {
            while (first < len1 || second < len2) {
                while (first < len1 && first > 0 && list1.get(first).compareTo(max1) < 0) {
                    System.err.println("Skipping value " + list1.get(first) + ", that is not in sorted order");
                    first++;
                }
                if (first < len1) max1 = list1.get(first);
                while (second < len2 && second > 0 && list2.get(second).compareTo(max2) < 0) {
                    System.err.println("Skipping value " + list2.get(second) + ", that is not in sorted order");
                    second++;
                }
                if (second < len2) max2 = list2.get(second);
                if (first == len1 && second == len2) break;

                if (first == len1)                                          merge.add(list2.get(second++));
                else if (second == len2)                                    merge.add(list1.get(first++));
                else if (list1.get(first).compareTo(list2.get(second)) < 0) merge.add(list1.get(first++));
                else                                                        merge.add(list2.get(second++));
            }
        }
        else {
            while (first < len1 || second < len2)  {
                while (first < len1 && first > 0 && list1.get(first).compareTo(max1) > 0) {
                    System.err.println("Skipping value " + list1.get(first) + ", that is not in sorted order");
                    first++;
                }
                if (first < len1) max1 = list1.get(first);
                while (second < len2 && second > 0 && list2.get(second).compareTo(max2) > 0) {
                    System.err.println("Skipping value " + list2.get(second) + ", that is not in sorted order");
                    second++;
                }
                if (second < len2) max2 = list2.get(second);

                if (first == len1 && second == len2) break;
                if (first == len1)                                          merge.add(list2.get(second++));
                else if (second == len2)                                    merge.add(list1.get(first++));
                else if (list1.get(first).compareTo(list2.get(second)) > 0) merge.add(list1.get(first++));
                else                                                        merge.add(list2.get(second++));
            }
        }
        return merge;
    }

    /**
     * Создание выходного файла и запись в него данных
     * @param list - данные для записи в файл
     */
    public void createOutputFile(List<T> list) {
        try {
            File out = new File(outputName);
            if (out.createNewFile()) {
                System.out.println("File created: " + out.getName());
            } else {
                System.out.println("File " + out.getName() + " already exists.");
            }
            writeInFile(out, list);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Запись данных в файл
     * @param out - файл для записи данных
     * @param list - данные для записи
     */
    public void writeInFile(File out, List<T> list) {
        try (PrintWriter pw = new PrintWriter(out)) {
            for (T i: list)
                pw.println(i);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
