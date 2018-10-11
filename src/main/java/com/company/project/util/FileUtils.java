package com.company.project.util;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;

/**
 * 文件拷贝，删除共用方法
 */
public class FileUtils implements Closeable {
    private InputStream in;
    private OutputStream out;

    public FileUtils(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    /**
     * @param in          输入流
     * @param fileOutPath 文件输出路径
     */
    public FileUtils(InputStream in, String fileOutPath) throws FileNotFoundException {
        this.in = in;

        File outFile = new File(fileOutPath);
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        if (!outFile.exists()) {
            try {
                outFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
        this.out = fileOutputStream;
    }

    /**
     * @param in      输入流
     * @param outFile 输出文件File对象
     * @throws FileNotFoundException
     */
    public FileUtils(InputStream in, File outFile) throws FileNotFoundException {
        this.in = in;

        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
        this.out = fileOutputStream;
    }

    /**
     * 文件拷贝，每次读取一个字节，然后写入
     *
     * @return 输入流
     * @throws IOException
     */
    public long copyByOne() throws IOException {
        long start = System.currentTimeMillis();
        int temp = 0;//保存每一个读取的字节数据
        while ((temp = this.in.read()) != -1) {
            this.out.write(temp);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    /**
     * 文件拷贝，读取数据到字节数组，然后一次写入
     *
     * @return
     * @throws IOException
     */
    public long copyByArray() throws IOException {
        long start = System.currentTimeMillis();
        byte[] data = new byte[1024];

        int len = 0;//保存读取到的字节个数
        //将每一次读取的数据保存到data数组里面，返回读取到的字节个数
        while ((len = this.in.read(data)) != -1) {
            this.out.write(data, 0, len);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    /**
     * 删除空目录
     *
     * @param dir 将要删除的目录路径
     */
    public static void delEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean
     */
    public static boolean delDirRecursive(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = delDirRecursive(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    @Override
    public void close() throws IOException {
        this.in.close();
        this.out.close();
    }

    public static void main(String[] args) {
        Instant now = Instant.now();
        Instant later = now.plusSeconds(3);
        Instant earlier = now.minusSeconds(3);

        System.err.println(now.toString());
        System.err.println(later);
        System.err.println(earlier);

//        boolean b = delDirRecursive(new File("/usr/local/image/test"));
//        new File("/usr/local/image/test").mkdir();
        System.err.println(BigDecimal.ZERO.getClass());


        String format = new DecimalFormat("#.0000").format(3.14);
        System.err.println(format);
        String format1 = String.format("%.2f", 3.1415926f);
        String format2 = String.format("%.2f", new BigDecimal(3.1453523523));
        System.err.println(format2);

        //四舍五入
        double   f   =   111231.5585;
        BigDecimal   b   =   new   BigDecimal(f);
//保留2位小数
        double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        System.err.println(f1);

    //非四舍五入
            BigDecimal   b1   =   new   BigDecimal(f);
//保留2位小数
            b1   =   b1.setScale(2,   BigDecimal.ROUND_DOWN); //主要是修改一下BigDecimal的枚举类型即可。
        System.err.println(b1);


    }
}
