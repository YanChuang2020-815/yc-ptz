package com.yc.ptz.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created By ChengHao On 2020/6/17
 */
@Data
@Accessors(chain = true)
public class AlarmDto implements Serializable {

    private static final long serialVersionUID = 1758711587941568833L;
    private Long deviceId;
    private Long sceneId;
    private double angle;
}
