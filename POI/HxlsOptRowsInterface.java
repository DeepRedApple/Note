package cn.zzuli.yycg.utils;

import java.util.List;

public interface HxlsOptRowsInterface {
	
	public static final String SUCCESS="success";
	/**
	 * 处理excel文件每行数据方法
	 * @param sheetIndex 为sheet的序号
	 * @param curRow	为行号
	 * @param rowlist   行数据
	 * @return success：成功，否则为失败原因
	 * @throws Exception
	 */
	public String optRows(int sheetIndex, int curRow, List<String> rowlist) throws Exception;

}
