package com.hisense.video.entity;

import lombok.Data;

/**
 * @Auther:liaohongbing@hisense.com
 * @date:2019/4/25
 * @des
 */

@Data
public class MediaInfo {

    public MediaInfo(){};

    public MediaInfo(String uuid, String fromRtspUrl, String toRtmpUrl, String pid) {
        this.uuid = uuid;
        this.fromRtspUrl = fromRtspUrl;
        this.toRtmpUrl = toRtmpUrl;
        this.pid = pid;
    }

    private String uuid;
    private String fromRtspUrl;
    private String toRtmpUrl;
    private String  pid;

}
