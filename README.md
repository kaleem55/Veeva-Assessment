# Automation Framework to test CSV comparison application

This is a test automation framework to run tests for csv comparison application covering functional tests and negative scenarios

## Test Scenarios
TC001: When two files are Identical (matching records and headers) then csv comparison should result `IDENTICAL`

TC002: When either of the files have mismatch in data then csv comparison should result `DIFFERENT`
        
(Comparison results found in logs here `target/test-logs/test-output.txt`)

TC003: When either of the files have mismatching HEADER then csv comparison should result `HEADER_MISMATCH`

TC004: When either of the files have empty data then csv comparison should result `EMPTY`

## Setup instructions

### 1. Prerequisites

- Java 8 
- Maven 3.8.1 

### 2. Clone project and Build the Project from Compare_CSV_Files_Maven directory

```
cd Compare_CSV_Files_Maven
mvn clean test
```

### 3. File Structure for Test Files

Place your CSV test files under:
```
src/test/resources/test_csv_files/
```

Ensure the filenames match the ones used in `CsvComparisonTests.java`.

## Test Reports & Logs

- Log contains the actual csv comparison and records that are mismatched be saved at: `target/test-logs/test-output.txt`
- Reports will be available at: `target/surefire-reports/`

---


