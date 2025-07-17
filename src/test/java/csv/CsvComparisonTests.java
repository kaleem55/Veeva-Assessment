package csv;

import org.example.csv.CsvComparisonApp;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.example.csv.LogUtil;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CsvComparisonTests {



    private Path getResourcePath(String fileName) {
        try {
            URL resource = getClass().getClassLoader().getResource("test_csv_files/" + fileName);
            if (resource == null) {
                throw new RuntimeException("File not found: " + fileName);
            }
            return Paths.get(resource.toURI());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load file: " + fileName, e);
        }
    }
    @Test
    public void TC001_testIdenticalFiles() throws IOException {
        Path file1 = getResourcePath("TC001_IdenticalFile1.csv");
        Path file2 = getResourcePath("TC001_IdenticalFile2.csv");
        LogUtil.log("Executing TC001 - Test for Identical files");
        String result = CsvComparisonApp.compareCsvFiles(file1.toString(), file2.toString());
        Assert.assertEquals(result, "IDENTICAL", "Files should be identical");
        LogUtil.log("TC001 passed: Identical files comparison correctly identified as 'IDENTICAL'%n%n");
    }

    @Test
    public void TC002_testDifferentValues() throws IOException {
        Path file1 = getResourcePath("TC002_MismatchFile1.csv");
        Path file2 = getResourcePath("TC002_MismatchFile2.csv");
        LogUtil.log("Executing TC002 - Test for Mismatching files");
        String result = CsvComparisonApp.compareCsvFiles(file1.toString(), file2.toString());
        Assert.assertEquals(result, "DIFFERENT");
        LogUtil.log("TC002 passed: Data mismatch files comparison correctly identified as 'DIFFERENT'%n%n");
    }

    @Test
    public void TC003_testDifferentHeaders() throws IOException {
        Path file1 = getResourcePath("TC003_HeadersMismatchFile1.csv");
        Path file2 = getResourcePath("TC003_HeadersMismatchFile2.csv");
        LogUtil.log("Executing TC003 - Test for Mismatching headers");
        String result = CsvComparisonApp.compareCsvFiles(file1.toString(), file2.toString());
        Assert.assertEquals(result, "HEADER_MISMATCH");
        LogUtil.log("TC003 passed: Header mismatch file comparison correctly identified as 'HEADER_MISMATCH'%n%n");
    }

    @Test
    public void TC004_testOneFileEmpty() throws IOException {
        Path file1 = getResourcePath("TC004_OneFileEmptyFile1.csv");
        Path file2 = getResourcePath("TC004_OneFileEmptyFile2.csv");
        LogUtil.log("Executing TC004 - Test for one or more empty files");
        String result = CsvComparisonApp.compareCsvFiles(file1.toString(), file2.toString());
        Assert.assertEquals(result, "EMPTY");
        LogUtil.log("TC004 passed: Empty file comparison correctly identified as 'EMPTY'%n%n");
    }
}
