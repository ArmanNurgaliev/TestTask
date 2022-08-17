import service.ReadFile;
import service.Merge;

import java.io.File;
import java.util.List;

public class Main {
    private static boolean ascendingOrder = true;
    private static String dataType;
    private static String outputName;
    private static File[] files;

    public static void main(String[] args) throws Exception {
        readParameters(args);

        if (dataType.equals("Integer")) {
            Merge<Integer> sample = null;
            if (files.length > 1) {
                sample = new Merge<>(ascendingOrder, outputName);
                List<Integer> result = sample.merge(ReadFile.readInts(files[0]), ReadFile.readInts(files[1]));
                for (int i = 2; i < files.length; i++)
                    result = sample.merge(result, ReadFile.readInts(files[i]));
                sample.createOutputFile(result);
            } else
                sample.createOutputFile(ReadFile.readInts(files[0]));
        }
        else {
            Merge<String> sample = null;
            if (files.length > 1) {
                sample = new Merge<>(ascendingOrder, outputName);
                List<String> result= sample.merge(ReadFile.readStrings(files[0]), ReadFile.readStrings(files[1]));
                for (int i = 2; i < files.length; i++)
                    result = sample.merge(result, ReadFile.readStrings(files[i]));
                sample.createOutputFile(result);
            } else
                sample.createOutputFile(ReadFile.readStrings(files[0]));
        }
    }

    /**
        Метод читает входные параметры
        @param args - command-line arguments
     */
    private static void readParameters(String[] args) throws Exception {
        // Reading sorting order
        int startInput = 0;
        if (args[startInput].equalsIgnoreCase("-a"))
            startInput++;
        else if (args[startInput].equalsIgnoreCase("-d")) {
            startInput++;
            ascendingOrder = false;
        }

        // Reading type of data
        if (args[startInput] != null) {
            if (args[startInput].equals("-s"))
                dataType = "String";
            else if (args[startInput].equals("-i"))
                dataType = "Integer";
            else
                throw new Exception("No data type parameter");
            startInput++;
        }
        else
            throw new Exception("No data type parameter");

        // Reading name of the output file
        if (args[startInput] != null)
            outputName = args[startInput++];
        else
            throw new Exception("No output file name in parameters");

        // Reading name of input files
        if (args.length - startInput == 0)
            throw new Exception("No input files");

        files = new File[args.length - startInput];
        int i = 0;
        while (startInput < args.length)
            files[i++] = new File(args[startInput++]);

       /* System.out.println("Order: " + ascendingOrder);
        System.out.println("Data type: " + dataType);
        System.out.println("Output name: " + outputName);
        for (File f: files)
            System.out.println("File: " + f.getName());*/
    }
}
