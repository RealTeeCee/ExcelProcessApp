package com.excelprocessapp.excelprocessapp.services;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.io.buffer.DataBuffer;

import com.excelprocessapp.excelprocessapp.model.BaseDto;
import com.excelprocessapp.excelprocessapp.model.MyExcelFile;

import reactor.core.publisher.Flux;

public interface ExcelProcessService {
    public BaseDto uploadExcel();

    void getInputStreamFromFluxDataBuffer(Flux<DataBuffer> data) throws IOException;

    public List<MyExcelFile> readExcelFile(String excelFilePath);

    
}
