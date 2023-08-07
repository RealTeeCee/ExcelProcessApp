package com.excelprocessapp.excelprocessapp.services.serviceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;

import com.excelprocessapp.excelprocessapp.model.BaseDto;
import com.excelprocessapp.excelprocessapp.services.ExcelProcessService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExcelProcessServiceImpl implements ExcelProcessService {

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

    @Override
    public void getInputStream(InputStream data) {
       
    }
}
