package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.Excel;
import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.entities.GameInfoExport;
import com.yukoon.bargain.repository.GameInfoRepo;
import com.yukoon.bargain.utils.ExcelUtil;
import org.apache.el.parser.ParseException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DownloadService {
    @Autowired
    private GameInfoRepo gameInfoRepo;

    public XSSFWorkbook exportGameInfoExcel(List list,List<Excel> excels,String excelName,Class clazz) throws InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException, IllegalAccessException {
        Map<Integer,List<Excel>> map = new LinkedHashMap<>();
        XSSFWorkbook xssfWorkbook = null;
        map.put(0,excels);
        xssfWorkbook = ExcelUtil.createExcelFile(clazz,list,map,excelName);
        return xssfWorkbook;
    }

    //根据活动导出参与者名单
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
        return exportGameInfoExcel(list,excels,excelName,GameInfoExport.class);
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
            if (gie.getRedeemCode() != null) {
                gie.setRedeemCode(gi.getRedeemCode().getCode());
            }
            list.add(gie);
        }
        return list;
    }

}
