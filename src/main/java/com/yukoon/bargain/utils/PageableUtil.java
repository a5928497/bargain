package com.yukoon.bargain.utils;

import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.entities.User;

import java.lang.reflect.Array;
import java.util.*;

public class PageableUtil {

    //对User的Set进行排序处理
    public static ArrayList<User> convert2TreeSet(Set<User> set) {
        TreeSet<User> tree = new TreeSet(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                User u1 = (User) o1;
                User u2 = (User) o2;
                return u1.getId().compareTo(u2.getId());
            }
        });
        tree.addAll(set);
        ArrayList<User> list = new ArrayList<>();
        list.addAll(tree);
        return list;
    }

    //进行分页
    public static Page page(Integer pageNo,Integer pageSize,List list) {
        Page page  = new Page();
        //总页数
        int total = (list.size()%pageSize) ==0?(list.size()/pageSize):((list.size()/pageSize)+1);
        page.setPageTotal(total);
        page.setPageNo(pageNo).setPageSize(pageSize);
        //元素下标起点
        int start = (pageNo -1)*pageSize;
        int end = (pageNo * pageSize) -1;
        ArrayList list_new = new ArrayList();
        for (int i = start;i<= end ;i++) {
            list_new.add(list.get(i));
        }
        page.setList(list_new);
        return page;
    }

}
