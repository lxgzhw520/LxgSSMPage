package com.lxgzhw.lxgSSMUtils;

import com.lxgzhw.lxgSSMUtils.bean.Page;

import java.util.List;

public class LxgPage {
    /**
     * 封装分页对象
     *
     * @param currentPage 当前页
     * @param rows        每页数量
     * @param users       数据集合
     * @param count       数据总量
     * @param <T>         泛型
     * @return 分页对象
     */
    public static <T> Page<T> page(int currentPage, int rows, List<T> users, long count) {
        Page<T> userPage = new Page<>();
        // List<User> users = userDao.findAll();
        //对参数做处理
        //这里要记住分页查询公式   limit (currentPage-1)*rows,rows
        //将数据修改为根据分页查询
        userPage.setList(users);

        //接下来,计算数据总量和总页数
        long totalPage = count % rows == 0 ? count / rows : count / rows + 1;//总页数
        userPage.setCurrentPage(currentPage);
        userPage.setRows(rows);
        userPage.setTotalCount(count);
        userPage.setTotalPage(totalPage);

        //最后一步,封装中间页码
        int index = 0;//计数器,记录数组的索引
        long[] midPages = new long[9];//中间页码数组
        for (long i = currentPage - 4; i < currentPage + 5; i++) {
            long value = i;
            //6: 1 2 3 4 5 6 7 8 9
            //如果i小于1,让i = 总页码 + 1
            //比如说 0 就是总页码  1就是总页码-1
            if (i < 1) {
                value = totalPage + i;
            }
            //如果i大于总页码 让i = i - 总页码
            //比如说总页数100   101 = 101 - 100 就等于1了
            if (i > totalPage) {
                value = i - totalPage;
            }
            //将每一个i都装进中间页码数组中
            midPages[index] = value;
            index++;
        }
        //将中间页码设置进Page对象
        userPage.setMidPages(midPages);

        return userPage;
    }
}
