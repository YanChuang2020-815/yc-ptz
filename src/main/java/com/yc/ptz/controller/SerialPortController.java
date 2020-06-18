package com.yc.ptz.controller;

import com.yc.ptz.constant.PtzInstruction;
import com.yc.ptz.service.SerialPortService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created By ChengHao On 2020/6/17
 */
@Slf4j
@RestController
@RequestMapping("/ptz")
public class SerialPortController {
    @Resource
    SerialPortService serialPortService;


    @GetMapping("/open")
    public void openSerialPort() {
        serialPortService.openSerialPort("COM3");
    }

    @GetMapping("/close")
    public void closeSerialPort() {
        serialPortService.closeSerialPort();
    }


    @GetMapping("/left")
    public void leftTest() {
        for (int i = 0; i < 100; i++) {
            serialPortService.sendData(PtzInstruction.LEFT.getValue());
        }
        log.info("turn left end");
    }

    @GetMapping("/right")
    public void rightTest() {
        for (int i = 0; i < 100; i++) {
            serialPortService.sendData(PtzInstruction.RIGHT.getValue());
        }
        log.info("turn right end");
    }

    @GetMapping("/up")
    public void upTest() {
        for (int i = 0; i < 10; i++) {
            serialPortService.sendData(PtzInstruction.UP.getValue());
        }
        log.info("turn up end");
    }

    @GetMapping("/down")
    public void downTest() {
        for (int i = 0; i < 10; i++) {
            serialPortService.sendData(PtzInstruction.DOWN.getValue());
        }
        log.info("turn down end");
    }

    @GetMapping("/upLeft")
    public void upLeftTest() {
        for (int i = 0; i < 100; i++) {
            serialPortService.sendData(PtzInstruction.UPLEFT.getValue());
        }
        log.info("turn up-left end");
    }

}
