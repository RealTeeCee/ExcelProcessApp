package com.excelprocessapp.excelprocessapp.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.buffer.DataBuffer;

import com.excelprocessapp.excelprocessapp.model.BaseDto;
import com.excelprocessapp.excelprocessapp.model.MyExcelFile;

import reactor.core.publisher.Flux;

public interface ExcelProcessService {
    public BaseDto uploadExcel();

    void getInputStreamFromFluxDataBuffer(Flux<DataBuffer> data) throws IOException;

    void getInputStream(String filename, InputStream data);

    public List<MyExcelFile> readExcelFile(String excelFilePath);
}
