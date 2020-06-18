package com.yc.ptz.service;

import com.yc.ptz.manager.SerialPortManager;
import com.yc.ptz.utils.ByteUtils;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created By ChengHao On 2020/6/17
 */
@Service
@Slf4j
public class SerialPortService {
    private SerialPort serialPort;

    /**
     * 打开串口
     *
     * @param commName
     */
    public void openSerialPort(String commName) {
        // 设置
        int baudrate = 2400;
        // 检查串口名称是否获取正确
        if (commName == null || commName.equals("")) {
            log.warn("没有搜索到有效串口！");
        } else {
            try {
                serialPort = SerialPortManager.openPort(commName, baudrate);
                if (serialPort != null) {
                    log.info("串口已打开");
                }
            } catch (PortInUseException e) {
                log.warn("串口已被占用！");
            }
        }

        // 添加串口监听
        SerialPortManager.addListener(serialPort, new SerialPortManager.DataAvailableListener() {

            @Override
            public void dataAvailable() {
                byte[] data = null;
                try {
                    if (serialPort == null) {
                        log.error("串口对象为空，监听失败！");
                    } else {
                        // 读取串口数据
                        data = SerialPortManager.readFromPort(serialPort);

                        // 以十六进制的形式接收数据
                        String receiveData = ByteUtils.byteArrayToHexString(data) + "\r\n";
                        System.out.println("receiveData = " + receiveData);
                    }
                } catch (Exception e) {
                    log.error(e.toString());
                    // 发生读取错误时显示错误信息后退出系统
                    System.exit(0);
                }
            }
        });
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort() {
        SerialPortManager.closePort(serialPort);
        serialPort = null;
    }

    /**
     * 发送数据
     *
     * @param data
     */
    public void sendData(String data) {
        if (serialPort == null) {
            log.warn("请先打开串口！");
            return;
        }

        if ("".equals(data) || data == null) {
            log.warn("请输入要发送的数据！");
            return;
        }

        // 以十六进制的形式发送数据
        SerialPortManager.sendToPort(serialPort, ByteUtils.hexStr2Byte(data));

    }
}
