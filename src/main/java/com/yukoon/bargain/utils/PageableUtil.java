package com.yukoon.bargain.utils;

import com.sun.tools.javadoc.Start;
import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.entities.User;

import java.lang.reflect.Array;
import java.util.*;

public class PageableUtil {

    //进行分页
    public static Page page(Integer pageNo,Integer pageSize,List list) {
        Page page  = new Page();
        //总页数
        int total = 1;
        int start = 0;
        int end = list.size()-1;
        if (list.size()>=pageSize) {
            total = (list.size()%pageSize) ==0?(list.size()/pageSize):((list.size()/pageSize)+1);
            //元素下标起点
            start = (pageNo -1)*pageSize;
            end = pageNo == total?list.size()-1 : start+pageSize-1;
        }
        page.setPageTotal(total);
        page.setPageNo(pageNo).setPageSize(pageSize).setRecordTotal(list.size());
        ArrayList list_new = new ArrayList();
        for (int i = start;i<= end ;i++) {
            list_new.add(list.get(i));
        }
        page.setList(list_new);
        return page;
    }

    public static void main(String[] args) {
        System.out.println(2%10);
    }
}
