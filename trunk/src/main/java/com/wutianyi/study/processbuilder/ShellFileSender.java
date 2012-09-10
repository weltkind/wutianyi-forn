package com.wutianyi.study.processbuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class ShellFileSender {

    private static Logger logger = Logger.getLogger(ShellFileSender.class);

    /**
     * ���õ���������
     */
    private String        shellCommand;

    /**
     * ������Ҫ�Ĳ���
     */
    private List<String>  parameters;

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public void setShellCommand(String shellCommand) {
        this.shellCommand = shellCommand;
    }

    public boolean send() throws IOException, InterruptedException {
        if (null == shellCommand || null == parameters) {
            logger.error("[error] shellCommand or parameters must not be null! ");
            return false;
        }

        Process process = null;

        ProcessBuilder pb = new ProcessBuilder(initCommand());

        try {
            process = pb.start();

            while (!isProcessFinisned(process)) {
                logger.info("[running] waiting for sending files ");
                process.waitFor();
            }
            int exitValue = process.exitValue();
            if (0 == exitValue) {
                logger.info("[running] finish sending files ");

                return true;
            } else {
                logger.error("[error] " + IOUtils.toString(process.getErrorStream()));
            }
        } finally {
            if (null != process) {
                process.destroy();
            }
        }
        return false;
    }

    private boolean isProcessFinisned(Process process) {
        try {
            process.exitValue();
            return true;
        } catch (IllegalThreadStateException e) {
            return false;
        }
    }

    private List<String> initCommand() {
        List<String> command = new ArrayList<String>();
        command.add(shellCommand);
        command.addAll(parameters);
        return command;
    }

    public static void main(String[] args) {
        ShellFileSender sender = new ShellFileSender();
        sender.setShellCommand("deploy/bin/automation_dispatch.pl");
        List<String> parameters = new ArrayList<String>();
        parameters.add("product");
        // parameters.add("/home/wutianyi/dump/*");
        // parameters.add("/home/wutianyi/dumpTran");

        sender.setParameters(parameters);
        System.out.println("==================start====================");
        // sender.send();

        System.out.println("==================end====================");
    }
}
