import java.util.List;

/**
 * 测试导入数据接口
 * @author Thinkpad
 *
 */
public class HxlsOptRowsInterfaceImpl implements HxlsOptRowsInterface {

	@Override
	public String optRows(int sheetIndex, int curRow, List<String> rowlist)
			throws Exception {
		//插入数据库
		System.out.println("sheetIndex="+sheetIndex+"curRow="+curRow+rowlist);
		return null;
	}
	
}
