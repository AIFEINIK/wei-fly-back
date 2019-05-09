package com.wei.fly.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.UUID;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/3/26
 * @Version 1.0.0
 */
public class FileUtils {

    private static char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getName(String filename) {
        return FilenameUtils.getName(filename);
    }

    public static String dirCompletion(String dir) {
        if (null == dir) {
            return null;
        } else {
            return dir.endsWith(File.separator) ? dir : dir + File.separator;
        }
    }

    public static void moveFile(File srcFile, File destFile) throws IOException {
        org.apache.commons.io.FileUtils.moveFile(srcFile, destFile);
    }

    public static void moveAndDeleteFile(File srcFile, File destFile) throws IOException {
        if (destFile.exists() && !destFile.delete()) {
            throw new IOException("The file can't be deleted.");
        } else {
            org.apache.commons.io.FileUtils.moveFile(srcFile, destFile);
        }
    }

    public static boolean renameFileAndDeleteFile(File srcfile, File destFile) throws IOException {
        if (destFile.exists() && !destFile.delete()) {
            throw new IOException("The file can't be deleted.");
        } else {
            return srcfile.renameTo(destFile);
        }
    }

    public static void moveFileAndmkParent(File srcFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }

        org.apache.commons.io.FileUtils.moveFile(srcFile, destFile);
    }

    public static void moveToDirectory(File srcDir, File destDir) throws IOException {
        org.apache.commons.io.FileUtils.moveToDirectory(srcDir, destDir, false);
    }

    public static void copyFile(File srcFile, File destFile) throws IOException {
        org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
    }

    public static boolean copyFileResult(File srcFile, File destFile) {
        try {
            org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
            return true;
        } catch (IOException var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static void copyFileAndmkParent(File srcFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }

        org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
    }

    public static void copyFileToDirectory(File srcDir, File destDir) throws IOException {
        org.apache.commons.io.FileUtils.copyFileToDirectory(srcDir, destDir);
    }

    public static void copyDirectoryToDirectory(File srcDir, File destDir) throws IOException {
        org.apache.commons.io.FileUtils.copyDirectoryToDirectory(srcDir, destDir);
    }

    public static synchronized File createTempFile(String dir, String fileName) throws IOException {
        File tempFile = null;
        if (checkDirAndCreate(dir)) {
            dir = dirCompletion(dir);
            String name = UUID.randomUUID().toString() + getExtension(fileName);
            StringBuffer fileNames = new StringBuffer();
            fileNames.append(dir).append("upload").append(File.separator);
            File fileDir = new File(fileNames.toString());
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            fileNames.append(name);
            tempFile = new File(fileNames.toString());
        }

        return tempFile;
    }

    public static FileInputStream getFileInputStrean(File file) throws IOException {
        return org.apache.commons.io.FileUtils.openInputStream(file);
    }

    public static FileOutputStream getFileOutputStream(File file) throws IOException {
        return org.apache.commons.io.FileUtils.openOutputStream(file);
    }

    public static String getFileContent(File file) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToString(file, "UTF-8");
    }

    public static String getFileContent(File file, String encode) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToString(file, encode);
    }

    public static List getFileConntent(File file, String encode) throws IOException {
        return org.apache.commons.io.FileUtils.readLines(file, encode);
    }

    public static String getInputContent(InputStream input) throws IOException {
        return IOUtils.toString(input, "UTF-8");
    }

    public static String getInputContent(InputStream input, String encoding) throws IOException {
        return IOUtils.toString(input, encoding);
    }

    public static void WriteContentToFile(File file, String str) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }

        org.apache.commons.io.FileUtils.writeStringToFile(file, str);
    }

    public static void WriteContentToFile(File file, String str, String encodeing) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }

        org.apache.commons.io.FileUtils.writeStringToFile(file, str, encodeing);
    }

    public static void WriteContentToFileUTF8(File file, String str) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }

        org.apache.commons.io.FileUtils.writeStringToFile(file, str, "UTF-8");
    }

    public static boolean isDirExsit(String dirstr) {
        File file = new File(dirstr);
        if (!file.exists()) {
            return false;
        } else {
            return file.isDirectory();
        }
    }

    public static boolean isFileExsit(String dirstr) {
        File file = new File(dirstr);
        if (!file.exists()) {
            return false;
        } else {
            return file.isFile();
        }
    }

    public static boolean checkDirAndCreate(String dirstr) {
        File dirFile = new File(dirstr);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        return isDirExsit(dirstr);
    }

    public static File checkDirAndCreateFile(String dirstr) {
        File dirfile = new File(dirstr);
        if (!dirfile.exists()) {
            dirfile.mkdirs();
        }

        return isDirExsit(dirstr) ? dirfile : null;
    }

    public static String InsertStrToBaseName(String filename, String str) {
        String Extension = getExtension(filename);
        String newname = getFileBaseName(filename) + "_" + str + "." + Extension;
        return newname;
    }

    public static String InsertStrToBaseNames(String filename, String str) {
        String Extension = getExtension(filename);
        String newname = getFileBaseName(filename) + "" + str + "." + Extension;
        return newname;
    }

    public static File ReplaceExtension(File file, String newextension) {
        File newfile = new File(FilenameUtils.removeExtension(file.getPath()) + newextension);
        return newfile;
    }

    public static boolean CreateDir(String dirstr) {
        File dirfile = new File(dirstr);
        dirfile.mkdirs();
        return isDirExsit(dirstr);
    }

    public static boolean isFileExistAndCanRW(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            return false;
        } else if (!file.isFile()) {
            return false;
        } else if (!file.canRead()) {
            return false;
        } else {
            return file.canWrite();
        }
    }

    public static boolean isFileExistAndRead(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            return false;
        } else if (!file.isFile()) {
            return false;
        } else {
            return file.canRead();
        }
    }

    public static void deleteDirectory(File directory) throws IOException {
        org.apache.commons.io.FileUtils.deleteDirectory(directory);
    }

    public static boolean deleteQuietly(File file) {
        return org.apache.commons.io.FileUtils.deleteQuietly(file);
    }

    public static String getFileBaseName(String filename) {
        return FilenameUtils.getBaseName(filename);
    }

    public static boolean isExtension(String filename, String extension, boolean iscase) {
        if (iscase) {
            return FilenameUtils.isExtension(filename, extension);
        } else {
            return FilenameUtils.isExtension(filename, extension.toLowerCase()) || FilenameUtils.isExtension(filename, extension.toUpperCase());
        }
    }

    public static String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    public static File concatDirAndName(String dir, String filename) {
        return new File(FilenameUtils.concat(dir, filename));
    }

    public static String concatPath(String dir, String subdir) {
        return FilenameUtils.concat(dir, subdir);
    }

    public static String getFileMd5(byte[] file) {
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.update(file);
            return bufferToHex(messagedigest.digest());
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;

        for(int l = m; l < k; ++l) {
            appendHexPair(bytes[l], stringbuffer);
        }

        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 240) >> 4];
        char c1 = hexDigits[bt & 15];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

}
