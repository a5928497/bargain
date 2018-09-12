package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.*;
import com.yukoon.bargain.repository.GameInfoRepo;
import com.yukoon.bargain.repository.HelperInfoRepo;
import com.yukoon.bargain.utils.ExcelUtil;
import org.apache.el.parser.ParseException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class DownloadService {
    @Autowired
    private GameInfoRepo gameInfoRepo;
    @Autowired
    private HelperInfoRepo helperInfoRepo;

    public XSSFWorkbook exportExcel(List list,List<Excel> excels,String excelName,Class clazz) throws InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException, IllegalAccessException {
        Map<Integer,List<Excel>> map = new LinkedHashMap<>();
        XSSFWorkbook xssfWorkbook = null;
        map.put(0,excels);
        xssfWorkbook = ExcelUtil.createExcelFile(clazz,list,map,excelName);
        return xssfWorkbook;

    }

    //根据某条活动记录导出帮助者名单
    public XSSFWorkbook exportAllHelperByGameInfoId(Integer gameInfoId) throws ClassNotFoundException, IntrospectionException, IllegalAccessException, ParseException, InvocationTargetException {
        List<HelperExport> list = convert2HE(helperInfoRepo.findAllByGameInfo(gameInfoId));
        List<Excel> excels = new ArrayList<>();
        //设置标题栏
        excels.add(new Excel("参与者手机号","username",0));
        excels.add(new Excel("帮助者手机号","helperName",0));
        excels.add(new Excel("帮砍价值","bargainPrice",0));
        String excelName = list.get(0).getUsername()+"的帮助者统计";
        return exportExcel(list,excels,excelName,HelperExport.class);
    }

    //根据某条活动记录导出帮助者名单
    public XSSFWorkbook exportAllHelperByActId(Integer act_id) throws ClassNotFoundException, IntrospectionException, IllegalAccessException, ParseException, InvocationTargetException {
//        List<HelperExport> list = convert2HE(helperInfoRepo.findAllByGameInfo(gameInfoId));
        List<HelperExport> list = convert2HE(new ArrayList<>(repeatFliter(helperInfoRepo.findAllByActId(act_id))));
        List<Excel> excels = new ArrayList<>();
        //设置标题栏
        excels.add(new Excel("参与者手机号","username",0));
        excels.add(new Excel("帮助者手机号","helperName",0));
        excels.add(new Excel("帮砍价值","bargainPrice",0));
        String excelName = list.get(0).getUsername()+"的帮助者统计";
        return exportExcel(list,excels,excelName,HelperExport.class);
    }

    //根据活动导出所有参与者名单
    public XSSFWorkbook exportAllParticipantByActid(Integer act_id) throws ClassNotFoundException, IntrospectionException, IllegalAccessException, ParseException, InvocationTargetException {
        List<GameInfoExport> list = convert2GIE(gameInfoRepo.findGameInfoByActivity_Id(act_id));
        List<Excel> excels = new ArrayList<>();
        //设置标题栏
        excels.add(new Excel("手机号","username",0));
        excels.add(new Excel("活动名","actName",0));
        excels.add(new Excel("奖品名","rewardName",0));
        excels.add(new Excel("剩余价值","priceLeft",0));
        excels.add(new Excel("剩余次数","timesLeft",0));
        excels.add(new Excel("兑换码","redeemCode",0));
        String excelName = list.get(0).getActName()+"所有参与者统计";
        return exportExcel(list,excels,excelName,GameInfoExport.class);
    }

    //根据活动导出所有得奖者名单（包含未兑换和已兑换）
    public XSSFWorkbook exportAllWinnersByActid(Integer act_id) throws ClassNotFoundException, IntrospectionException, IllegalAccessException, ParseException, InvocationTargetException {
        List<GameInfoExport> list = convert2GIE(gameInfoRepo.whoswin(act_id));
        List<Excel> excels = new ArrayList<>();
        //设置标题栏
        excels.add(new Excel("手机号","username",0));
        excels.add(new Excel("活动名","actName",0));
        excels.add(new Excel("奖品名","rewardName",0));
        excels.add(new Excel("剩余价值","priceLeft",0));
        excels.add(new Excel("剩余次数","timesLeft",0));
        excels.add(new Excel("兑换码","redeemCode",0));
        String excelName = list.get(0).getActName()+"所有得奖者统计";
        return exportExcel(list,excels,excelName,GameInfoExport.class);
    }

    //根据活动导出所有已兑换得奖者名单
    public XSSFWorkbook exportCashedWinnersByActid(Integer act_id) throws ClassNotFoundException, IntrospectionException, IllegalAccessException, ParseException, InvocationTargetException {
        List<GameInfo> list = gameInfoRepo.whoswin(act_id);
        List<GameInfo> list_new = new ArrayList<>();
        for (GameInfo gi : list) {
            if (gi.getRedeemCode() != null) {
                list_new.add(gi);
            }
        }
        List<GameInfoExport> list_temp = convert2GIE(list_new);
        List<Excel> excels = new ArrayList<>();
        //设置标题栏
        excels.add(new Excel("手机号","username",0));
        excels.add(new Excel("活动名","actName",0));
        excels.add(new Excel("奖品名","rewardName",0));
        excels.add(new Excel("剩余价值","priceLeft",0));
        excels.add(new Excel("剩余次数","timesLeft",0));
        excels.add(new Excel("兑换码","redeemCode",0));
        String excelName = list_temp.get(0).getActName()+"所有已兑奖者统计";
        return exportExcel(list_temp,excels,excelName,GameInfoExport.class);
    }

    //根据活动导出所有未兑换得奖者名单
    public XSSFWorkbook exportUncashedWinnersByActid(Integer act_id) throws ClassNotFoundException, IntrospectionException, IllegalAccessException, ParseException, InvocationTargetException {
        List<GameInfo> list = gameInfoRepo.whoswin(act_id);
        List<GameInfo> list_new = new ArrayList<>();
        for (GameInfo gi : list) {
            if (gi.getRedeemCode() == null) {
                list_new.add(gi);
            }
        }
        List<GameInfoExport> list_temp = convert2GIE(list_new);
        List<Excel> excels = new ArrayList<>();
        //设置标题栏
        excels.add(new Excel("手机号","username",0));
        excels.add(new Excel("活动名","actName",0));
        excels.add(new Excel("奖品名","rewardName",0));
        excels.add(new Excel("剩余价值","priceLeft",0));
        excels.add(new Excel("剩余次数","timesLeft",0));
        excels.add(new Excel("兑换码","redeemCode",0));
        String excelName = list_temp.get(0).getActName()+"所有已兑奖者统计";
        return exportExcel(list_temp,excels,excelName,GameInfoExport.class);
    }

    //GameInfo对象转换
    public List<GameInfoExport> convert2GIE(List<GameInfo> gameInfos) {
        List<GameInfoExport> list = new ArrayList<>();
        for (GameInfo gi :gameInfos) {
            GameInfoExport gie = new GameInfoExport();
            gie.setActName(gi.getActivity().getAct_name());
            gie.setPriceLeft(gi.getPriceLeft());
            gie.setRewardName(gi.getReward().getRewardName());
            gie.setTimesLeft(gi.getTimesLeft());
            gie.setUsername(gi.getUser().getUsername());
            if (gi.getRedeemCode() != null) {
                gie.setRedeemCode(gi.getRedeemCode().getCode());
            }
            list.add(gie);
        }
        return list;
    }

    //HelperInfo对象转换
    public List<HelperExport> convert2HE(List<HelperInfo> helperInfos) {
        List<HelperExport> list = new ArrayList<>();
        for (HelperInfo hi :helperInfos) {
            HelperExport helperExport = new HelperExport();
            helperExport.setUsername(hi.getGameInfo().getUser().getUsername());
            helperExport.setHelperName(hi.getHelper().getUsername());
            helperExport.setBargainPrice(hi.getBarginPrice());
            list.add(helperExport);
        }
        return list;
    }
    //去重
    public Set<HelperInfo> repeatFliter(List<HelperInfo> helperInfos) {
        Set<HelperInfo> set = new HashSet<>();
        for (HelperInfo hi : helperInfos) {
            set.add(hi);
        }
        return set;
    }
}
