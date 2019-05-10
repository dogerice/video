package com.hisense.video.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @Auther:liaohongbing@hisense.com
 * @date:2019/4/25
 * @des
 */
public class ShellExecutor {
    private static Logger logger = LoggerFactory.getLogger(ShellExecutor.class);

    public static void runShell(String cmd){
        Process process ;
        logger.info("执行linux命令:"+cmd);

        String sh [] = {"sh","-c",cmd};
        try{
            process = Runtime.getRuntime().exec(sh);
        }catch (Exception e){
            logger.error("shell执行异常");
            e.printStackTrace();
        }
    }

    public static void runShell(List<String> cmd){
        Process process ;
        logger.info("执行linux命令:"+cmd);

        String command = String.join(" ",cmd);
        String[] sh = {"sh","-c",command};
//        System.out.println(sh[2]);
        try{
            System.out.println(command);
            process = Runtime.getRuntime().exec(sh);
//            process = Runtime.getRuntime().exec(command);
            OutHandler errorGobbler = new OutHandler(process.getErrorStream(),"error");
            OutHandler outputGobbler = new OutHandler(process.getInputStream(),"info");
            errorGobbler.start();
            outputGobbler.start();
//            int wf = process.waitFor();
//            System.out.println(wf);
//            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        }catch (Exception e){
            logger.error("shell执行异常");
            e.printStackTrace();
        }
    }


}
