package com.excelprocessapp.excelprocessapp.services;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.buffer.DataBuffer;

import com.excelprocessapp.excelprocessapp.model.BaseDto;

import reactor.core.publisher.Flux;

public interface ExcelProcessService {
    public BaseDto uploadExcel();

    void getInputStreamFromFluxDataBuffer(Flux<DataBuffer> data) throws IOException;
}
