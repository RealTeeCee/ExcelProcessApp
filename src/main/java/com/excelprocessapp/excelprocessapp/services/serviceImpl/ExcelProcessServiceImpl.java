package com.excelprocessapp.excelprocessapp.services.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;

import com.excelprocessapp.excelprocessapp.exceptions.BadRequestException;
import com.excelprocessapp.excelprocessapp.model.BaseDto;
import com.excelprocessapp.excelprocessapp.model.MyExcelFile;
import com.excelprocessapp.excelprocessapp.services.ExcelProcessService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExcelProcessServiceImpl implements ExcelProcessService {
    private final int COL_BANK_DATE = 0;
    private final int COL_BANK_REF = 1;
    private final int COL_DESCRIPTION = 2;
    private final int COL_CREDIT = 3;

    public static final String excelFilePath = "C:/Users/nngongu/Desktop/MyExcel.xlsx";

    @Override
    public BaseDto uploadExcel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uploadExcel'");
    }

    @Override
    public void getInputStreamFromFluxDataBuffer(Flux<DataBuffer> data) throws IOException {

        Mono<InputStream> mono = data.map(dataBuffer -> dataBuffer.asInputStream(true))
                .reduce(SequenceInputStream::new);
        mono.flatMap(this::readExcel);

    }

    private Mono<Void> readExcel(InputStream inputStream) {
        return Mono.fromRunnable(() -> {
            try (Workbook workbook = new XSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheet("SHEET");
                Iterator<Row> rows = sheet.iterator();

                while (rows.hasNext()) {
                    Row currentRow = rows.next();

                    Iterator<Cell> cellsInRow = currentRow.iterator();

                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();

                        // each cell case
                        // id = (long) currentCell.getNumericCellValue();
                        // title = currentCell.getStringCellValue();
                        // published = currentCell.getBooleanCellValue();
                    }

                    workbook.close();
                    // Mono.empty().then(;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    public List<MyExcelFile> readExcelFile(String excelFilePath) {
        List<MyExcelFile> myExcelFiles = new ArrayList<>();
        try {
            // Get file
            InputStream inputStream = new FileInputStream(new File(excelFilePath));
            // Get workbook
            Workbook workbook = getWorkbook(inputStream, excelFilePath);
            Sheet sheet = workbook.getSheet("Sheet1");
            Iterator<Row> rows = sheet.iterator();
            while (rows.hasNext()) {
                Row nextRow = rows.next();
                if (nextRow.getRowNum() == 0) {
                    // Ignore header
                    continue;
                }
                // Get all cells
                Iterator<Cell> cellsInRow = nextRow.iterator();
                // Read cells and set value for MyExcelFile object
                MyExcelFile myExcelFile = new MyExcelFile();
                while (cellsInRow.hasNext()) {
                    // Read cell
                    Cell cell = cellsInRow.next();
                    Object cellValue = getCellValue(cell);

                    if (cellValue == null || cellValue.toString().isEmpty()) {
                        continue;
                    }

                    // Set value for MyExcelFile object
                    int columnIndex = cell.getColumnIndex();

                    switch (columnIndex) {
                        case COL_BANK_DATE:
                            // myExcelFile.setBankDate(new BigDecimal((double) cellValue).intValue());
                            myExcelFile.setBankDate((String) getCellValue(cell));
                            break;
                        case COL_BANK_REF:
                            myExcelFile.setBankRef((String) getCellValue(cell));
                            break;
                        case COL_DESCRIPTION:
                            myExcelFile.setDescription((String) getCellValue(cell));
                            break;
                        case COL_CREDIT:
                            myExcelFile.setCredit(BigDecimal.valueOf((double) getCellValue(cell)));
                            break;
                        default:
                            break;
                    }
                }
                myExcelFiles.add(myExcelFile);
            }
            workbook.close();
            inputStream.close();
            return myExcelFiles;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    // Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellType();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }

}
