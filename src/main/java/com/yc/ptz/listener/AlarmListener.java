package com.yc.ptz.listener;

import com.yc.ptz.constant.PtzInstruction;
import com.yc.ptz.message.AlarmDto;
import com.yc.ptz.service.SerialPortService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created By ChengHao On 2020/6/17
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "consumer-group", topic = "alarmToCamera")
public class AlarmListener implements RocketMQListener<AlarmDto> {
    @Resource
    SerialPortService serialPortService;

    @Override
    public void onMessage(AlarmDto alarmDto) {
        log.info("消费到消息 => " + alarmDto.toString());
        Double angle = alarmDto.getAngle();
        //水平方向速度20H,每次下发指令旋转大约为0.4度
        int count = 0;
        count = (int) (Math.abs(angle) / 0.4);
        log.info("旋转次数 => " + count);

        if (count > 0) { //顺时针
            if (angle > 0) {
                for (int i = 0; i < count; i++) {
                    serialPortService.sendData(PtzInstruction.RIGHT.getValue());
                }
            } else {   //逆时针
                for (int i = 0; i < count; i++) {
                    serialPortService.sendData(PtzInstruction.LEFT.getValue());
                }
            }

        }
    }
}
