package com.excelprocessapp.excelprocessapp.delegates;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import com.excelprocessapp.excelprocessapp.api.V1ApiDelegate;
import com.excelprocessapp.excelprocessapp.model.BaseDto;
import com.excelprocessapp.excelprocessapp.services.ExcelProcessService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class V1Delegate implements V1ApiDelegate {
    private final ExcelProcessService excelProcessService;

    @Override
    public Mono<ResponseEntity<BaseDto>> uploadExcel(List<Flux<Part>> files, ServerWebExchange exchange) {
        for (Flux<Part> flux : files) {
            flux.flatMap(file -> Mono.fromRunnable(() -> {
                try {
                    excelProcessService.getInputStreamFromFluxDataBuffer(file.content());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }));
        }
        // TODO Auto-generated method stub
        return V1ApiDelegate.super.uploadExcel(files, exchange);
    }

}
