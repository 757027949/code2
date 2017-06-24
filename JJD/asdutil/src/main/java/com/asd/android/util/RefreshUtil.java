package com.asd.android.util;

/**
 * Created by Administrator on 2016/6/21.
 */
public class RefreshUtil {
    /**
     * 获取总页数
     *
     * @param totalCount 总条数
     * @param pageSize   每页条数
     * @return 总页数
     */
    public static int getTotalPages(int totalCount, int pageSize) {
        if (0 == pageSize) {
            throw new RuntimeException("pageSize不能为0...");
        }
        return totalCount / pageSize + (0 != totalCount % pageSize ? 1 : 0);
    }
}
