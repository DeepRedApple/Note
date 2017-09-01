package cn.zzuli.yycg.utils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class HxlsRead extends HxlsAbstract{

	
	//数据处理解析数据的接口
	private  HxlsOptRowsInterface hxlsOptRowsInterface;
	//处理数据总数
	private int optRows_sum = 0;
	//处理数据成功数量
	private int optRows_success = 0;
	//处理数据失败数量
	private int optRows_failure = 0;
	//excel表格每列标题
	private List<String> rowtitle ;
	//失败数据
	private List<List<String>> failrows;
	//失败原因
	private List<String> failmsgs ;
	
	//要处理数据所在的sheet索引,sheet索引从0开始
	private int sheetIndex;
	/**
	 * 导入文件的名称
	 * @param filename 导入文件的物理路径 
	 * @param sheetIndex 要读取数据所在sheet序号
	 * @param hxlsOptRowsInterface 处理读取每一行数据的接口
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 */
	public HxlsRead(String filename,int sheetIndex,HxlsOptRowsInterface hxlsOptRowsInterface) throws IOException,
			FileNotFoundException, SQLException {
		super(filename);
		this.sheetIndex = sheetIndex;
		this.hxlsOptRowsInterface = hxlsOptRowsInterface;
		this.rowtitle = new ArrayList<String>();
		this.failrows = new ArrayList<List<String>>();
		this.failmsgs = new ArrayList<String>();
	}

	/**
	 * 对读取到一行数据进行解析
	 */
	@Override
	public void optRows(int sheetIndex,int curRow, List<String> rowlist) throws Exception {
		/*for (int i = 0 ;i< rowlist.size();i++){
			System.out.print("'"+rowlist.get(i)+"',");
		}
		System.out.println();*/
		//将rowlist的长度补齐和标题一致
		int k=rowtitle.size()-rowlist.size();
		for(int i=0;i<k;i++){
			rowlist.add(null);
		}
		if(sheetIndex == this.sheetIndex){
			optRows_sum++;
			
			if(curRow == 0){//记录标题
				rowtitle.addAll(rowlist);
			}else{
				//接口返回的结果是导入数据的结果，有成功，有失败
				String result = hxlsOptRowsInterface.optRows(sheetIndex, curRow, rowlist);
				if(result!=null && !result.equals(hxlsOptRowsInterface.SUCCESS)){
					optRows_failure++;//失败统计数加1
					//失败数据列表
					failrows.add(new ArrayList<String>(rowlist));
					failmsgs.add(result);
				}else{
					optRows_success++;
				}
			}

		}
		
		
	}
	
	public long getOptRows_sum() {
		return optRows_sum;
	}

	public void setOptRows_sum(int optRows_sum) {
		this.optRows_sum = optRows_sum;
	}

	public long getOptRows_success() {
		return optRows_success;
	}

	public void setOptRows_success(int optRows_success) {
		this.optRows_success = optRows_success;
	}

	public long getOptRows_failure() {
		return optRows_failure;
	}

	public void setOptRows_failure(int optRows_failure) {
		this.optRows_failure = optRows_failure;
	}

	
	public List<String> getRowtitle() {
		return rowtitle;
	}

	public List<List<String>> getFailrows() {
		return failrows;
	}

	public List<String> getFailmsgs() {
		return failmsgs;
	}

	public void setFailmsgs(List<String> failmsgs) {
		this.failmsgs = failmsgs;
	}

	public static void main(String[] args){
		HxlsRead xls2csv;
		try {
			//第一个参数就是导入的文件
			//第二个参数就是导入文件中哪个sheet
			//第三个参数导入接口的实现类对象
			xls2csv = new HxlsRead("d:/test11.xls",0,new HxlsOptRowsInterfaceImpl());
			xls2csv.process();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
