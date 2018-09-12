package com.yukoon.bargain.controllers;

import com.yukoon.bargain.services.DownloadService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DownloadController {
	@Autowired
	private DownloadService downloadService;

	//批量导出整个活动的参与者数据
	@RequiresRoles("admin")
	@ResponseBody
	@GetMapping("/exportallgameinfo/{act_id}")
	public void exportAllGameInfo(@PathVariable("act_id")Integer act_id, HttpServletRequest request, HttpServletResponse response) {
		response.reset(); //清除buffer缓存
		Map<String,Object> map = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		response.setHeader("Content-Disposition", "attachment;filename="+sdf.format(new Date())+".xlsx");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		XSSFWorkbook workbook=null;
		try {
			workbook = downloadService.exportAllParticipantByActid(act_id);
			OutputStream out = response.getOutputStream();
			BufferedOutputStream bout = new BufferedOutputStream(out);
			bout.flush();
			workbook.write(bout);
			bout.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("失败");
		}
		System.out.println("成功");
	}

	//批量导出某一具体活动的帮砍者数据
	@RequiresRoles("admin")
	@ResponseBody
	@GetMapping("/exportallhelper/{gameinfo_id}")
	public void exportAllHelperInfo(@PathVariable("gameinfo_id")Integer gameinfo_id, HttpServletRequest request, HttpServletResponse response) {
		response.reset(); //清除buffer缓存
		Map<String,Object> map = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		response.setHeader("Content-Disposition", "attachment;filename="+sdf.format(new Date())+".xlsx");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		XSSFWorkbook workbook=null;
		try {
			workbook = downloadService.exportAllHelperByGameInfoId(gameinfo_id);
			OutputStream out = response.getOutputStream();
			BufferedOutputStream bout = new BufferedOutputStream(out);
			bout.flush();
			workbook.write(bout);
			bout.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("失败");
		}
		System.out.println("成功");
	}

	//批量导出某一活动的所有帮砍者数据
	@RequiresRoles("admin")
	@ResponseBody
	@GetMapping("/exportallhelperinact/{act_id}")
	public void exportAllHelperInfoByActId(@PathVariable("act_id")Integer act_id, HttpServletRequest request, HttpServletResponse response) {
		response.reset(); //清除buffer缓存
		Map<String,Object> map = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		response.setHeader("Content-Disposition", "attachment;filename="+sdf.format(new Date())+".xlsx");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		XSSFWorkbook workbook=null;
		try {
			workbook = downloadService.exportAllHelperByActId(act_id);
			OutputStream out = response.getOutputStream();
			BufferedOutputStream bout = new BufferedOutputStream(out);
			bout.flush();
			workbook.write(bout);
			bout.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("失败");
		}
		System.out.println("成功");
	}

	//批量导出某一活动的得奖者数据
	@RequiresRoles("admin")
	@ResponseBody
	@GetMapping("/exportallwinner/{act_id}")
	public void exportAllWinnerInfo(@PathVariable("act_id")Integer act_id, HttpServletRequest request, HttpServletResponse response) {
		response.reset(); //清除buffer缓存
		Map<String,Object> map = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		response.setHeader("Content-Disposition", "attachment;filename="+sdf.format(new Date())+".xlsx");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		XSSFWorkbook workbook=null;
		try {
			workbook = downloadService.exportAllWinnersByActid(act_id);
			OutputStream out = response.getOutputStream();
			BufferedOutputStream bout = new BufferedOutputStream(out);
			bout.flush();
			workbook.write(bout);
			bout.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("失败");
		}
		System.out.println("成功");
	}

	//批量导出某一活动的已兑奖得奖者数据
	@RequiresRoles("admin")
	@ResponseBody
	@GetMapping("/exportcwinner/{act_id}")
	public void exportCashedWinnerInfo(@PathVariable("act_id")Integer act_id, HttpServletRequest request, HttpServletResponse response) {
		response.reset(); //清除buffer缓存
		Map<String,Object> map = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		response.setHeader("Content-Disposition", "attachment;filename="+sdf.format(new Date())+".xlsx");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		XSSFWorkbook workbook=null;
		try {
			workbook = downloadService.exportCashedWinnersByActid(act_id);
			OutputStream out = response.getOutputStream();
			BufferedOutputStream bout = new BufferedOutputStream(out);
			bout.flush();
			workbook.write(bout);
			bout.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("失败");
		}
		System.out.println("成功");
	}

	//批量导出某一活动的未兑奖得奖者数据
	@RequiresRoles("admin")
	@ResponseBody
	@GetMapping("/exportucwinner/{act_id}")
	public void exportUncashedWinnerInfo(@PathVariable("act_id")Integer act_id, HttpServletRequest request, HttpServletResponse response) {
		response.reset(); //清除buffer缓存
		Map<String,Object> map = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		response.setHeader("Content-Disposition", "attachment;filename="+sdf.format(new Date())+".xlsx");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		XSSFWorkbook workbook=null;
		try {
			workbook = downloadService.exportUncashedWinnersByActid(act_id);
			OutputStream out = response.getOutputStream();
			BufferedOutputStream bout = new BufferedOutputStream(out);
			bout.flush();
			workbook.write(bout);
			bout.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("失败");
		}
		System.out.println("成功");
	}

}
