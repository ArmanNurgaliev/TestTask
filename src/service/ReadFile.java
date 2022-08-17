package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс для чтения данных из входных файлов
 */
public class ReadFile {
    /**
     * Вычитывает все Integer из файла и записывает их в List
     * @param file - входной файл
     * @return возвращает List с входными данными
     */
    public static List<Integer> readInts(File file) {
        List<Integer> list = new ArrayList<>();
        try(Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line == null || line.length() == 0) {
                    System.err.printf("Empty line in file %s\n", file.getName());
                    continue;
                }
                if (line.contains("/+s")) {
                    System.err.printf("Value %s in file %s contains white spaces\n", line, file.getName());
                    line = line.replaceAll("/+s", "");
                }
                try {
                    int n = Integer.parseInt(line);
                    list.add(n);
                } catch (NumberFormatException e) {
                    System.err.printf("Value %s in file %s is not Integer\n", line, file.getName());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    /**
     * Вычитывает все String из файла и записывает их в List
     * @param file - входной файл
     * @return возвращает List с входными данными
     */
    public static List<String> readStrings(File file) {
        List<String> list = new ArrayList<>();
        try(Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line == null || line.length() == 0) {
                    System.err.printf("Empty line in file %s\n", file.getName());
                    continue;
                }
                if (line.contains("/+s")) {
                    System.err.printf("Value %s in file %s contains white spaces\n", line, file.getName());
                }
                list.add(line.replaceAll("/+s", ""));
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }
}
