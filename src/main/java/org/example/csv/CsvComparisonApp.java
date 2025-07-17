package org.example.csv;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;




public class CsvComparisonApp {

    /**
     * Compares two CSV files and logs differences in cell values, headers, or record count.
     *
     * @param file1_path the full path to the first CSV file
     * @param file2_path the full path to the second CSV file
     * @return "IDENTICAL" if files match, "MISMATCH" if differences are found, or "EMPTY" if either file is empty, or "HEADER_MISMATCH" if headers in files do not match
     * @throws IOException if an error occurs during file reading
     */
    public static String compareCsvFiles(String file1_path, String file2_path) throws IOException {

        //Compare row count and return EMPTY if at least one of the files is empty
        List<String[]> rows_file1 = readCsvRows(file1_path);
        List<String[]> rows_file2 = readCsvRows(file2_path);

        if(rows_file1.isEmpty() || rows_file2.isEmpty()){
            LogUtil.log("One or both files are empty. Please provide files with same number of rows and same property (same headers) for effective comparison");
            return "EMPTY";
        }

        //Compare properties(headers) and return HEADER_MISMATCH if headers do not match
        String[] headers1 = readHeaders(file1_path);
        String[] headers2 = readHeaders(file2_path);

        Set<String> headerSet1 = new HashSet<>();
        Set<String> headerSet2 = new HashSet<>();
        for (String h : headers1) headerSet1.add(h.trim().toLowerCase());
        for (String h : headers2) headerSet2.add(h.trim().toLowerCase());

        if (!headerSet1.equals(headerSet2)) {
            LogUtil.log("Files have different headers. Please provide files with same headers");
            return "HEADER_MISMATCH";
        }

        //Continue comparing data when files are not empty and both have same headers
        Map<String, String> mappedDataFile1 = readCsvData(file1_path);
        Map<String, String> mappedDataFile2 = readCsvData(file2_path);

        Set<String> allKeys = new HashSet<>();
        allKeys.addAll(mappedDataFile1.keySet());
        allKeys.addAll(mappedDataFile2.keySet());

        boolean identical = true;
        for (String key : allKeys) {
            String val1 = mappedDataFile1.getOrDefault(key, "<no value>");
            String val2 = mappedDataFile2.getOrDefault(key, "<no value>");
            if (!val1.equals(val2)) {
                LogUtil.log("Mismatch in %s: File1 data='%s', File2 data='%s'%n", key, val1, val2);
                identical = false;
            }
        }

        if (identical) {
            LogUtil.log("The CSV files are identical");
            return "IDENTICAL";

        } else {
            LogUtil.log("The CSV files have the above mismatches.");
            return "DIFFERENT";
        }
    }

    private static List<String[]> readCsvRows(String path) throws IOException {
        List<String[]> rows = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = reader.readLine()) != null) {
            rows.add(line.split(","));
        }
        reader.close();
        return rows;
    }


    /**
     * Extracts data from csv file in provided path and stores them as key value pairs
     *
     * @param path the full path to the first CSV file
     * @return Map with key value pairs mapping cell address and the value it carries in csv
     */
    private static Map<String, String> readCsvData(String path) throws IOException {
        Map<String, String> cellMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String headerLine = reader.readLine();
        if (headerLine == null) return cellMap;

        String[] headers = headerLine.split(",");
        String row;
        int rowNum = 1;
        while ((row = reader.readLine()) != null) {
            rowNum++;
            String[] values = row.split(",");
            for (int col = 0; col < headers.length; col++) {
                String key = String.format("Record %d, Header '%s'", rowNum, headers[col].trim().toLowerCase());
                String value = col < values.length ? values[col].trim() : "";
                cellMap.put(key, value);
            }
        }
        reader.close();
        return cellMap;
    }

    private static String[] readHeaders(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String headerLine = reader.readLine();
        reader.close();
        return headerLine != null ? headerLine.split(",") : new String[0];
    }

}
