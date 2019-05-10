package com.hisense.video.controller;

import com.alibaba.fastjson.JSONObject;
import com.hisense.video.entity.MediaInfo;
import com.hisense.video.util.FFMpegUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;


/**
 * @Auther:liaohongbing@hisense.com
 * @date:2019/4/23
 * @des
 */


@RestController
@RequestMapping("/video")
public class VideoController {

    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);
    private static Map<String, MediaInfo> mediaInfoMap = new HashMap<String, MediaInfo>();


    @RequestMapping("/view")
    public void viewVideo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        File file = new File("E:/temp/2.mp4");
        long fileLen = Files.size(file.toPath());
        logger.info("getVideo " + fileLen);
        try {
            InputStream in = new FileInputStream(file);
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("video/mp4");
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Range",
                    String.format("bytes %s-%s/%s", 0, fileLen - 1, fileLen));
            response.setHeader("Content-Length", String.format("%s", fileLen));
            int length;
            byte[] buffer = new byte[4 * 1024];
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/convert")
    public Object convertVideo() throws Exception {
        FFMpegUtil.convertor("E:/temp/2.flv", "E:/temp/" + UUID.randomUUID() + ".mp4");
        return "success";
    }

    @PostMapping("/relay")
    public Object relayVideo(@RequestBody JSONObject params) {


        String uuid = UUID.randomUUID() + "";
        String rtspUrl = params.getString("url");
        String rtmpUrl = "rtmp://10.16.3.232:1935/live/" + uuid;

        logger.info("relay params:" + params.toString());
        MediaInfo media = new MediaInfo(uuid, rtspUrl, rtmpUrl, "0");

        if (mediaInfoMap.containsKey(rtspUrl)) {
            return mediaInfoMap.get(rtspUrl);
        } else {
            FFMpegUtil.relayRtspToRtmp(params.getString("url"), rtmpUrl);
            mediaInfoMap.put(params.getString("url"), media);
        }


        return media;
    }

    @GetMapping("/getMediaMap")
    public Object getMediaMap() {
        return mediaInfoMap;
    }

    @GetMapping("/getMediaList")
    public Object getMediaList() {
        Collection collectionValue = mediaInfoMap.values();
        List<MediaInfo> mediaList = new ArrayList<MediaInfo>(collectionValue);
        return mediaList;
    }

}
