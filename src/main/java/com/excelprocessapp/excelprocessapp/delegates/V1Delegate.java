package com.excelprocessapp.excelprocessapp.delegates;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;

import com.excelprocessapp.excelprocessapp.api.V1ApiDelegate;
import com.excelprocessapp.excelprocessapp.model.BaseDto;
import com.excelprocessapp.excelprocessapp.services.ExcelProcessService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class V1Delegate implements V1ApiDelegate {
    private final ExcelProcessService excelProcessService;
    // @Override
    // public Mono<ResponseEntity<BaseDto>> uploadExcel(Flux<Part> files, ServerWebExchange exchange) {

    //     files.flatMap(file -> Mono.fromRunnable(() -> {
    //         try {
    //             excelProcessService.getInputStreamFromFluxDataBuffer(file.content());
    //         } catch (IOException e) {
    //             // TODO Auto-generated catch block
    //             e.printStackTrace();
    //         }
    //     }));

    //     // TODO Auto-generated method stub
    //     return V1ApiDelegate.super.uploadExcel(files, exchange);
    // }

    @Override
    public Mono<ResponseEntity<BaseDto>> uploadExcel(@RequestPart("files") MultipartFile files, ServerWebExchange exchange) {
        // TODO Auto-generated method stub
        return V1ApiDelegate.super.uploadExcel(files, exchange);
    }

    

    // @Override
    // public Mono<ResponseEntity<BaseDto>> uploadExcel(List<Flux<Part>> files,
    // ServerWebExchange exchange) {
    // for (Flux<Part> flux : files) {
    // flux.flatMap(file -> Mono.fromRunnable(() -> {
    // try {
    // excelProcessService.getInputStreamFromFluxDataBuffer(file.content());
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }));
    // }
    // // TODO Auto-generated method stub
    // return V1ApiDelegate.super.uploadExcel(files, exchange);
    // }

}
