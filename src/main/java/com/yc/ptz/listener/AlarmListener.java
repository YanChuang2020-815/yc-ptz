package com.yc.ptz.listener;

import com.yc.ptz.constant.PtzInstruction;
import com.yc.ptz.service.SerialPortService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By ChengHao On 2020/6/17
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "consumer-group", topic = "alarmToCamera")
public class AlarmListener implements RocketMQListener<String> {
    @Resource
    SerialPortService serialPortService;

    Map<Double, String> map = new HashMap<>();
    Double tempAngle = 1.0;
    Double tempAngle2 = 0.1;
    int count = 0;

    @Override
    public void onMessage(String alarmDto) {
        log.info("消费到消息 => " + alarmDto);
//        Long deviceId = Long.parseLong(alarmDto.substring(alarmDto.indexOf("=")+1, alarmDto.indexOf(",")));
//        Long sceneId = Long.parseLong(alarmDto.substring(alarmDto.lastIndexOf("sceneId=")+8,alarmDto.lastIndexOf(",")));
        Double angle = Double.valueOf(alarmDto.substring(alarmDto.lastIndexOf("=") + 1, alarmDto.lastIndexOf(")")));
        log.info("tempAngle:"+tempAngle);
//        if (!map.containsKey(angle)) {
        if (angle.intValue() != tempAngle.intValue()) {
            tempAngle = angle;
            log.info("angle:" + angle);

            //水平方向速度20H,每次下发指令旋转大约为0.4度

            if (angle > 0 && tempAngle2 > 0) {
                count = (int) (Math.abs(angle - tempAngle2) / 0.4);
            } else if (angle < 0 && tempAngle2 < 0) {
                count = (int) (Math.abs(angle - tempAngle2) / 0.4);
            } else {
                count = (int) ((Math.abs(angle) + Math.abs(tempAngle2)) / 0.4);
            }
            log.info("旋转次数 => " + count);

            if (count > 0) {
                if (angle > 0 && tempAngle2 > 0) {  //顺时针，同方向
                    if (angle > tempAngle2) {   //顺时针旋转
                        for (int i = 0; i < count; i++) {
                            serialPortService.sendData(PtzInstruction.RIGHT.getValue());
                        }
                    } else {    //逆时针旋转
                        for (int i = 0; i < count; i++) {
                            serialPortService.sendData(PtzInstruction.LEFT.getValue());
                        }
                    }

                } else if (angle < 0 && tempAngle2 < 0) {   //逆时针，同方向
                    if (angle < tempAngle2) {   //顺时针旋转
                        for (int i = 0; i < count; i++) {
                            serialPortService.sendData(PtzInstruction.RIGHT.getValue());
                        }
                    } else {    //逆时针旋转
                        for (int i = 0; i < count; i++) {
                            serialPortService.sendData(PtzInstruction.LEFT.getValue());
                        }
                    }
                }else if(angle>0){
                    for (int i = 0; i < count; i++) {
                        serialPortService.sendData(PtzInstruction.RIGHT.getValue());
                    }
                }else{
                    for (int i = 0; i < count; i++) {
                        serialPortService.sendData(PtzInstruction.LEFT.getValue());
                    }
                }
                tempAngle2 = angle;
                log.info("tempAngle2:"+ tempAngle2);

            }
        }

    }
}
