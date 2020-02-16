package com.lxgzhw.lxgSSMUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LxgSpider {
    public static void main(String[] args) {
        String from = "http://pic.netbian.com/index_5.html";
        String saveDir = "2020/2/16";
        ArrayList<String> allHref = getAllHref(from);
        allHref.forEach(System.out::println);
    }

    /**
     * 获取网页中所有的超链接
     *
     * @param url 网页地址
     * @return 超链接集合
     */
    public static ArrayList<String> getAllHref(String url) {
        String html = getHtml(url);
        ArrayList<String> hrefList = new ArrayList<>();
        //使用正则表达式提取
        Pattern compile = Pattern.compile("<a href=\"(.*?)\".*?>.*?</a>");
        Matcher matcher = compile.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group(1);
            if (!temp.contains("javascript:")) {
                hrefList.add(temp);
            }
        }
        return hrefList;
    }

    /**
     * 下载网页中所有的图片
     *
     * @param url     网页路径
     * @param baseUrl 网站域名
     * @param saveDir 要保存的文件夹
     */
    public static void downloadAllImg(String url, String baseUrl, String saveDir) {
        String html = getHtml(url);
        List<String> imgList = getAllImg(html);
        for (String img : imgList) {
            downloadImg(img, saveDir, baseUrl, ".jpg");
        }
    }

    /**
     * 下载图片
     *
     * @param from    从哪里下载
     * @param saveDir 要保存的文件夹
     * @param prefix  图片url前缀(http://)
     * @param suffix  后缀(保存的文件格式)
     */
    public static void downloadImg(String from, String saveDir, String prefix, String suffix) {
        //对img路径做处理
        if (!hasImgPrefix(from)) {
            if (isStartWithFlash(from)) {
                from = prefix + from;
            } else {
                from = prefix + "/" + from;
            }
        }
        //生成图片的唯一路径
        UUID randomUUID = UUID.randomUUID();
        long now = System.currentTimeMillis();
        long random = (long) (Math.random() * 99999999 + 100000000);
        String imgName = "lxgzhw" + randomUUID + now + random;
        imgName = imgName + suffix;
        //创建要保存的文件夹
        File file = new File(saveDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        //拼接要保存的文件路径
        String saveName = "";
        if (saveDir.endsWith("/")) {
            saveName = saveDir + imgName;
        } else {
            saveName = saveDir + "/" + imgName;
        }
        //保存图片
        copyUrlMedia(from, saveName);
    }

    /**
     * 下载图片
     *
     * @param from    从哪里下载
     * @param saveDir 要保存的文件夹
     */
    public static void downloadImg(String from, String saveDir) {
        //生成图片的唯一路径
        UUID randomUUID = UUID.randomUUID();
        long now = System.currentTimeMillis();
        long random = (long) (Math.random() * 99999999 + 100000000);
        String imgName = "lxgzhw" + randomUUID + now + random + ".jpg";
        //创建要保存的文件夹
        File file = new File(saveDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        //拼接要保存的文件路径
        String saveName = "";
        if (saveDir.endsWith("/")) {
            saveName = saveDir + imgName;
        } else {
            saveName = saveDir + "/" + imgName;
        }
        //保存图片
        copyUrlMedia(from, saveName);
    }

    /**
     * 判断路径是否以 / 开头
     *
     * @param path 路径
     * @return 判断结果
     */
    public static boolean isStartWithFlash(String path) {
        return path.startsWith("/");
    }

    /**
     * 复制网络图片到本地
     *
     * @param urlString 图片地址
     * @param filename  保存的文件名
     */
    public static void copyUrlMedia(String urlString, String filename) {
        OutputStream os = null;
        InputStream is = null;
        try {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            // 输入流
            is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024 * 1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            os = new FileOutputStream(filename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            if (os != null) {

                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断图片是否有http前缀
     *
     * @param imgPath 图片路径
     * @return 判断结果
     */
    public static boolean hasImgPrefix(String imgPath) {
        return imgPath.startsWith("http");
    }

    /**
     * 获取网页源码中所有的图片src路径
     *
     * @param html 网页源码
     * @return 图片路径List集合
     */
    public static List<String> getAllImg(String html) {
        ArrayList<String> imgList = new ArrayList<>();
        //使用正则表达式提取
        Pattern compile = Pattern.compile("<img src=\"(.*?)\".*?/>");
        Matcher matcher = compile.matcher(html);
        while (matcher.find()) {
            imgList.add(matcher.group(1));
        }
        return imgList;
    }

    /**
     * 获取网页源码
     *
     * @param urlPath 网页路径
     * @return 网页源码字符串
     */
    public static String getHtml(String urlPath) {
        StringBuffer sb = null;
        try {
            URL url = new URL(urlPath);//网址链接
            URLConnection conn = url.openConnection(); //打开链接
            //获取网页的源代码
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
