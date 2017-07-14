package com.li.test.spring_boot;


import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.*;

/**
 * Created by lihn on 17-7-13.
 */
public class ModifyJar {
    /**
     * 解压并删除jar文件
     */
    public static synchronized void decompress(String fileName, String outputPath) {
        if (!outputPath.endsWith(File.separator)) {
            outputPath += File.separator;
        }
        File dir = new File(outputPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        JarFile jf = null;
        try {
            jf = new JarFile(fileName);
            for (Enumeration<JarEntry> e = jf.entries(); e.hasMoreElements(); ) {
                JarEntry je = e.nextElement();
                String outFileName = outputPath + je.getName();
                File f = new File(outFileName);
                if (je.isDirectory()) {
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                } else {
                    File pf = f.getParentFile();
                    if (!pf.exists()) {
                        pf.mkdirs();
                    }
                    InputStream in = jf.getInputStream(je);
                    OutputStream out = new BufferedOutputStream(new FileOutputStream(f));
                    if (je.getName().contains("/application.properties")) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        PrintWriter pw = new PrintWriter(out);
                        String line = "";
                        pw.write("dispatch.agent.token=11111\n");
                        while ((line = br.readLine()) != null) {
                            pw.write(line + "\n");
                        }
                        pw.close();
                        br.close();
                    } else {
                        byte[] buffer = new byte[2048];
                        int nBytes = 0;
                        while ((nBytes = in.read(buffer)) > 0) {
                            out.write(buffer, 0, nBytes);
                        }
                    }
                    out.flush();
                    out.close();
                    in.close();
                }
            }
        } catch (Exception e) {
            System.out.println("解压" + fileName + "出错---" + e.getMessage());
        } finally {
            if (jf != null) {
                try {
                    jf.close();
                    File jar = new File(jf.getName());
                    if (jar.exists()) {
                        //jar.delete();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //利用jarInputStream生成jar文件写入内容
    public static void writeJar(String fileName, String outFileName) throws Exception {

        byte[] buffer = new byte[2048];
        int read = 0;
        //读取jar文件流,这一步是读取需要更新的jar包.
        JarInputStream zin = new JarInputStream(new FileInputStream(fileName));

        JarEntry entry;
        Manifest manifest = zin.getManifest();
        if (manifest == null) {
            manifest = new Manifest();
        }
        //用JarOutputStream 建立输出文件
        FileOutputStream fout = new FileOutputStream(outFileName);
        JarOutputStream zout = new JarOutputStream(fout, manifest);
        //从原jar包中读取文件

        while ((entry = zin.getNextJarEntry()) != null) {


            if (entry.getName().contains("/application.properties")) {
                zout.putNextEntry(new JarEntry(entry.getName()));
                zout.write("dispatch.agent.token=11111\n".getBytes());
            }else{
                zout.putNextEntry(entry);
            }
            //写入输出流
            while ((read = zin.read(buffer)) != -1) {
                zout.write(buffer, 0, read);
            }
            zout.closeEntry();
        }

        //关闭流
        zout.closeEntry();

        zout.close();
        zin.close();
    }

    public static void main(String[] args) {
        try {
            writeJar("/home/lihn/spring_boot-0.0.1-SNAPSHOT (复件).jar", "/home/lihn/spring_boot-0.0.1-SNAPSHOT.jar");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //decompress("/home/lihn/spring_boot-0.0.1-SNAPSHOT.jar", "/home/lihn/spring_boot");
    }
}
