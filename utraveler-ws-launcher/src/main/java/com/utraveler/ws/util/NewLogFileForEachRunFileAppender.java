package com.utraveler.ws.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorCode;

public class NewLogFileForEachRunFileAppender extends FileAppender {

    private static final String DOT = ".";
    private static final String DASH = "_";


    public NewLogFileForEachRunFileAppender() {
    }


    public NewLogFileForEachRunFileAppender(Layout layout, String filename, boolean append,
                                            boolean bufferedIO, int bufferSize)
            throws IOException {
        super(layout, filename, append, bufferedIO, bufferSize);
    }


    public NewLogFileForEachRunFileAppender(Layout layout, String filename, boolean append)
            throws IOException {
        super(layout, filename, append);
    }


    public NewLogFileForEachRunFileAppender(Layout layout, String filename)
            throws IOException {
        super(layout, filename);
    }


    @Override
    public void activateOptions() {
        if (fileName != null) {
            try {
                fileName = getNewLogFileName();
                setFile(fileName, fileAppend, bufferedIO, bufferSize);
            } catch (Exception e) {
                errorHandler.error("Error while activating log options", e, ErrorCode.FILE_OPEN_FAILURE);
            }
        }
    }


    private String getNewLogFileName() {
        if (fileName != null) {
            final File logFile = new File(fileName);
            final String logFileName = logFile.getName();
            String newFileName;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String time = sdf.format(new Date(System.currentTimeMillis()));

            final int dotIndex = logFileName.indexOf(DOT);
            if (dotIndex != -1) {
                // the file name has an extension. so, insert the time between the file name and the
                // extension
                newFileName = logFileName.substring(0, dotIndex) + DASH + time + DOT + logFileName.substring(dotIndex + 1);
            } else {
                // the file name has no extension. So, just append the time at the end.
                newFileName = logFileName + DASH + time;
            }
            return logFile.getParent() + File.separator + newFileName;
        }
        return null;
    }

}
