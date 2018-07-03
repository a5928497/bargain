package com.yukoon.bargain.utils;

import com.yukoon.bargain.entities.User;

import java.util.*;

public class PageableUtil {

    //对User的Set进行排序处理
    public static ArrayList<User> UserSetconvert2TreeSet(Set<User> set) {
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
    public static ArrayList<User> page(Integer pageNo,Integer pageSize) {
        ArrayList<User> list = new ArrayList<>();
        return list;
    }
}
