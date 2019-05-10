package com.hisense.video.util;

import org.apache.tomcat.jni.Proc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:liaohongbing@hisense.com
 * @date:2019/4/23
 * @des FFMpeg工具类
 */


public class FFMpegUtil {

    private static String ffmpegExe = "D:\\ffmpeg\\bin\\ffmpeg.exe";

    /*
        ffmpeg -i "rtsp://10.16.4.80:554/pag://10.16.4.80:7302:37021200001310015420:0:MAIN:TCP?cnid=100001&pnid=1&auth=50" -vcodec
        copy -acodec copy -f flv "rtmp://10.16.3.232:1935/live/111"
     */

    public static void convertor(String videoInputPath , String videoOutputPath) throws Exception{
        List<String> command = new ArrayList<String>();
        command.add(ffmpegExe);
        command.add("-i");
        command.add(videoInputPath);
        command.add(videoOutputPath);

        ProcessBuilder builder = new ProcessBuilder(command);
        System.out.println(command);
        Process process = null;
        process = builder.start();

        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = br.readLine()) != null) {

        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }


    }

    public static void relayRtspToRtmp(String rtspUrl,String rtmpUrl){
        List<String> cmd = new ArrayList<String>();

//        cmd.add("nohup");
        cmd.add("ffmpeg");
        cmd.add("-i");
        cmd.add("\""+rtspUrl+"\"");
        cmd.add("-vcodec");
        cmd.add("copy");
        cmd.add("-acodec");
        cmd.add("copy");
        cmd.add("-f");
        cmd.add("flv");
        cmd.add("\""+rtmpUrl+"\"");
        cmd.add(" 1>/dev/null 2>&1 ");
//        cmd.add("&");


/*        cmd.add(ffmpegExe);
        cmd.add("-i");
        cmd.add("\""+rtspUrl+"\"");
        cmd.add("-vcodec");
        cmd.add("copy");
        cmd.add("-acodec");
        cmd.add("copy");
        cmd.add("-f");
        cmd.add("flv");
        cmd.add("\""+rtmpUrl+"\"");*/

        ShellExecutor.runShell(cmd);
    }

}
