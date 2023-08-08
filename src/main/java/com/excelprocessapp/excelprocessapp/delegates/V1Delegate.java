package com.excelprocessapp.excelprocessapp.delegates;

import static com.excelprocessapp.excelprocessapp.contants.GlobalStorage.GLOBAL_EXCEPTION;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.excelprocessapp.excelprocessapp.api.V1ApiDelegate;
import com.excelprocessapp.excelprocessapp.exceptions.BadRequestException;
import com.excelprocessapp.excelprocessapp.model.BaseDto;
import com.excelprocessapp.excelprocessapp.model.BaseDto.AntTypeEnum;
import com.excelprocessapp.excelprocessapp.services.ExcelProcessService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class V1Delegate implements V1ApiDelegate {
    @Override
    public Mono<ResponseEntity<BaseDto>> uploadExcel(String filename, Mono<Resource> body, ServerWebExchange exchange) {
        try {

            return body.flatMap(file -> {
                try {
                    excelProcessService.getInputStream(filename, file.getInputStream());
                } catch (IOException e) {
                    throw new BadRequestException(e.getMessage());
                }
                return Mono.just(ResponseEntity
                        .ok(BaseDto.builder().message("Upload success.").antType(AntTypeEnum.SUCCESS).build()));
            });

        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());

        } catch (Exception e) {
            throw new BadRequestException(GLOBAL_EXCEPTION);
        }

    }

    private final ExcelProcessService excelProcessService;

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
