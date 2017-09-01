# Apache poi说明

​	Apache POI - the Java API for Microsoft Documents，Apache POI 是用Java编写的免费开源的跨平台的 Java API，它可以创建和维护操作各种符合Office Open XML（OOXML）标准和微软的OLE 2复合文档格式（OLE2）的Java API。用它可以使用Java读取和创建,修改MS Excel文件.而且,还可以使用Java读取和创建MS Word和MSPowerPoint文件。Apache POI 提供Java操作Excel解决方案（适用于Excel97-2008）。

​	官网：http://poi.apache.org/

# 使用HSSF和XSSF导出Excel

## HSSF导出Excel

HSSF只操作.xls文件（97-03版本excel），一个sheet中行有限制，最大65536行。

### 步骤：

#### 第一步：创建workbook工作簿（Excel文档）

```java
Workbook wb = new HSSFWorkbook();
FileOutputStream fileOut = new FileOutputStream("workbook.xls");
wb.write(fileOut);
fileOut.close();
```

#### 第二步：创建一个sheet文档

```java
Sheet sheet = wb.createSheet("sheetName");
```

#### 第三步：在sheet中创建Row行

```java
Row row = sheet.createRow((sheet)0);
```

#### 第四步：在Row中创建cell单元格

```java
Cell cell = row.createCell(0);
```

#### 第五步：向cell中写数据

```java
cell.setValue(1);
```

#### 第六步：输出Excel文件（写文件）

```java
wb.write(fileOut);
fileOut.close();
```

#### 演示代码：

```java
// 创建文件输出流
FileOutputStream out = new FileOutputStream("d:/workbook.xls");
// 创建一个工作簿
Workbook wb = new HSSFWorkbook();

for (int j = 0; j < 1; j++) {
	Sheet s = wb.createSheet();//创建1个sheet
	wb.setSheetName(j, "sheet" + j);//指定sheet的名称
	//xls文件最大支持65536行
	for (int rownum = 0; rownum < 65536; rownum++) {//创建行,.xls一个sheet中的行数最大65535
		// 创建一行
		Row r = s.createRow(rownum);

		for (int cellnum = 0; cellnum < 10; cellnum ++) {//一行创建10个单元格
		// 在行里边创建单元格
		Cell c = r.createCell(cellnum);
		//向单元格写入数据
		c.setCellValue(cellnum);

		}

	}

}
System.out.println("int..............");
wb.write(out);//输出文件内容

try {
	Thread.sleep(2000);
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
out.close();
```

 使用上边的测试代码，在一个工作簿中导出多个sheet，出现内存溢出

![](http://upload-images.jianshu.io/upload_images/1540531-56c1c2aa682e3582.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

出现内存溢出原因，HSSF工作原理，将excel中所有数据填充到java对象中，进行文件写操作。

#### 总结：

**缺点：**如果数据大，引起内存溢问题。

**优点：**编程方便，如果数据量小，速度很快的。

## XSSF导出Excel

XSSF操作03以上版本（07版本）excel，扩展名.xlsx，工作表行数没有限制

### 步骤：

#### 第一步：创建一个工作簿

```java
SXSSFWorkbook wb = new SXSSFWorkbook(-1);
//-1：关闭自动刷新
//SXSSFWorkbook wb = new SXSSFWorkbook(XXXX);（自动刷新）
//XXXX：保持内存中有XXXX条记录，超过部分写入磁盘
```

#### 第二步：创建一个工作表

```java
//创建一个sheet
Sheet sh = wb.createSheet();
```

#### 第三步：在sheet中创建行

```java
Row row = sh.createRow(rownum);
```

#### 第四步：创建单元格

```java
Cell cell = row.createCell(cellnum);
```

#### 第五步：向单元格中写入数据

```java
cell.setCellValue(address);
```

#### 第六步：将内容写入到磁盘

由于-1设置关闭自动刷新

需要人工主动刷新

调用：

```java
((SXSSFSheet)sh).flushRows(100);
```

#### 第七步：输出文件

```java
wb.write(out);//将临时写的文件合并，输出整个文件
```

跟踪代码：

向磁盘刷新数据，生成临时文件：

![](http://upload-images.jianshu.io/upload_images/1540531-4cad979f2fc111b0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

临时文件内容就是向excel中写的内容，

最后执行文件合并，将文件输出。

#### 总结：

**缺点：**写数据时速度慢

**优点：**写大数据量时不会发生内存溢出

##工具类ExcelExportSXXSSF 

```java
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
/**
 * excel导出的封装类
 */
public class ExcelExportSXXSSF {

	// 定义工作表
	private SXSSFWorkbook wb;

	/**
	 * 定义工作表中的sheet
	 */
	private Sheet sh;


	/**
	 * 定义保存在内存中的数量,-1表示手动控制
	 */
	private int flushRows;
	/** 导出文件行数 */
	private int rownum;
	/** 导出文件列数 */
	private int colnum;

	/** 导出文件的存放路径 */
	private String filePath;
	/** 下载导出文件的路径 */
	private String fileWebPath;
	/**文件名称前缀*/
	private String filePrefix;
	/**导出文件全路径*/
	private String fileAllPath;
	/** 导出文件列标题 */
	private List<String> fieldNames;
	/**导出文件每列代码，用于反射获取对象属性值*/
	private List<String> fieldCodes;

	private ExcelExportSXXSSF() {

	}

	/**
	 * 开始导出方法
	 * 
	 * @param filePath
	 *            导出文件存放物理路径
	 * @param fileWebPath
	 *            导出文件web下载路径
	 * @param filePrefix
	 *            导出文件名的前缀          
	 * @param flushRows
	 *            存放在内存的数据量
	 * @param fieldNames
	 *            导出文件列标题
	 * @param fieldCodes
	 * 			  导出数据对象的字段名称     
	 * @param flushRows
	 * 			写磁盘控制参数
	 * @return
	 */
	public static ExcelExportSXXSSF start(String filePath, String fileWebPath,String filePrefix,
			List<String> fieldNames,List<String> fieldCodes, int flushRows) throws Exception {
		ExcelExportSXXSSF excelExportSXXSSF = new ExcelExportSXXSSF();
		excelExportSXXSSF.setFilePath(filePath);
		excelExportSXXSSF.setFileWebPath(fileWebPath);
		excelExportSXXSSF.setFilePrefix(filePrefix);
		excelExportSXXSSF.setFieldNames(fieldNames);
		excelExportSXXSSF.setFieldCodes(fieldCodes);
		excelExportSXXSSF.setWb(new SXSSFWorkbook(flushRows));//创建workbook
		excelExportSXXSSF.setSh(excelExportSXXSSF.getWb().createSheet());//创建sheet
		excelExportSXXSSF.writeTitles();
		return excelExportSXXSSF;
	}

	/**
	 * 设置导入文件的标题
	 * 开始生成导出excel的标题
	 * @throws Exception
	 */
	private void writeTitles() throws Exception {
		rownum = 0;//第0行
		colnum = fieldNames.size();//根据列标题得出列数
		Row row = sh.createRow(rownum);
		for (int cellnum = 0; cellnum < colnum; cellnum++) {
			Cell cell = row.createCell(cellnum);
			cell.setCellValue(fieldNames.get(cellnum));
		}
	}

	/**
	 * 向导出文件写数据
	 * 
	 * @param datalist
	 *            存放Object对象，仅支持单个自定义对象，不支持对象中嵌套自定义对象
	 * @return
	 */
	public void writeDatasByObject(List datalist) throws Exception {

		for (int j = 0; j < datalist.size(); j++) {
			rownum = rownum + 1;
			Row row = sh.createRow(rownum);
			for (int cellnum = 0; cellnum < fieldCodes.size(); cellnum++) {
				Object owner = datalist.get(j);
				Object value = invokeMethod(owner, fieldCodes.get(cellnum),
						new Object[] {});
				Cell cell = row.createCell(cellnum);
				cell.setCellValue(value!=null?value.toString():"");
			}

		}

	}
	/**
	 * 向导出文件写数据
	 * 
	 * @param datalist
	 *            存放字符串数组
	 * @return
	 */
	public void writeDatasByString(List<String> datalist) throws Exception {
			rownum = rownum + 1;
			Row row = sh.createRow(rownum);
			int datalist_size = datalist.size();
			for (int cellnum = 0; cellnum < colnum; cellnum++) {
				Cell cell = row.createCell(cellnum);
				if(datalist_size>cellnum){
					cell.setCellValue(datalist.get(cellnum));
				}else{
					cell.setCellValue("");
				}
				
			}
	}

	/**
	 * 手动刷新方法,如果flushRows为-1则需要使用此方法手动刷新内存
	 * 
	 * @param flushNum
	 * @throws Exception
	 */
	public void flush(int flushNum) throws Exception {
		((SXSSFSheet) sh).flushRows(flushNum);
	}

	/**
	 * 导出文件
	 * 
	 * @throws Exception
	 */
	public String exportFile() throws Exception {
		String filename = filePrefix+"_"+MyUtil.getCurrentTimeStr() + ".xlsx";
		FileOutputStream out = new FileOutputStream(filePath + filename);
		wb.write(out);
		out.flush();
		out.close();
		setFileAllPath(fileWebPath + filename);
		return fileWebPath + filename;
	}

	/**
	 * 反射方法，通过get方法获取对象属性
	 * 
	 * @param owner
	 * @param fieldname
	 * @param args
	 * @return
	 * @throws Exception
	 */
	private Object invokeMethod(Object owner, String fieldname, Object[] args)
			throws Exception {

		String methodName = "get" + fieldname.substring(0, 1).toUpperCase()
				+ fieldname.substring(1);
		Class ownerClass = owner.getClass();

		Class[] argsClass = new Class[args.length];

		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}

		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(owner, args);
	}

	public SXSSFWorkbook getWb() {
		return wb;
	}

	public void setWb(SXSSFWorkbook wb) {
		this.wb = wb;
	}

	public Sheet getSh() {
		return sh;
	}

	public void setSh(Sheet sh) {
		this.sh = sh;
	}


	public int getFlushRows() {
		return flushRows;
	}

	public void setFlushRows(int flushRows) {
		this.flushRows = flushRows;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileWebPath() {
		return fileWebPath;
	}

	public void setFileWebPath(String fileWebPath) {
		this.fileWebPath = fileWebPath;
	}

	public List<String> getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(List<String> fieldNames) {
		this.fieldNames = fieldNames;
	}

	public List<String> getFieldCodes() {
		return fieldCodes;
	}

	public void setFieldCodes(List<String> fieldCodes) {
		this.fieldCodes = fieldCodes;
	}

	public int getRownum() {
		return rownum;
	}

	public String getFilePrefix() {
		return filePrefix;
	}

	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	public int getColnum() {
		return colnum;
	}

	public String getFileAllPath() {
		return fileAllPath;
	}

	public void setFileAllPath(String fileAllPath) {
		this.fileAllPath = fileAllPath;
	}
	
	public static void main(String[] args) throws Exception {
		/**            导出文件存放物理路径
		 * @param fileWebPath
		 *            导出文件web下载路径
		 * @param filePrefix
		 *            导出文件名的前缀          
		 * @param flushRows
		 *            存放在内存的数据量
		 * @param fieldNames
		 *            导出文件列标题
		 * @param fieldCodes
		 * 			  导出数据对象的字段名称     
		 * @param flushRows*/
		//导出文件存放的路径，并且是虚拟目录指向的路径
		String filePath = "d:/upload/linshi/";
		//导出文件的前缀
		String filePrefix="ypxx";
		//-1表示关闭自动刷新，手动控制写磁盘的时机，其它数据表示多少数据在内存保存，超过的则写入磁盘
		int flushRows=100;
		
		//指导导出数据的title
		List<String> fieldNames=new ArrayList<String>();
		fieldNames.add("流水号");
		fieldNames.add("通用名");
		fieldNames.add("价格");
		
		//告诉导出类数据list中对象的属性，让ExcelExportSXXSSF通过反射获取对象的值
		List<String> fieldCodes=new ArrayList<String>();
		fieldCodes.add("bm");//药品流水号
		fieldCodes.add("mc");//通用名
		fieldCodes.add("price");//价格
		
		
		
		//注意：fieldCodes和fieldNames个数必须相同且属性和title顺序一一对应，这样title和内容才一一对应
		
		
		//开始导出，执行一些workbook及sheet等对象的初始创建
		ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, flushRows);
		
		//准备导出的数据，将数据存入list，且list中对象的字段名称必须是刚才传入ExcelExportSXXSSF的名称
		List<Ypxx> list = new ArrayList<Ypxx>();
		
		Ypxx ypxx1 = new Ypxx("001", "青霉素", 5);
		Ypxx ypxx2 = new Ypxx("002", "感冒胶囊", 2.5f);
		list.add(ypxx1);
		list.add(ypxx2);
		
		//执行导出
		excelExportSXXSSF.writeDatasByObject(list);
		//输出文件，返回下载文件的http地址
		String webpath = excelExportSXXSSF.exportFile();
		
		System.out.println(webpath);
		
		
	}

}
```



# 使用HSSF导入Excel

## HSSF用户驱动模式导入

### 步骤：

#### 第一步：创建一个workbook文档

```java
//文件输入流
InputStream is = new FileInputStream("d:/test11.xls");
//创建hssf的workbook，将文件流传入workbook
HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
```

#### 第二步：读sheet

```java
//得到workbook某个 的sheet，numSheet是sheet 的序号，序号从0开始
HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
```

#### 第三步：读sheet中的每一行

```java
//读取每一行数据 ,rowNum指定行下标 从0开始
HSSFRow hssfRow = hssfSheet.getRow(rowNum);
```

#### 第四步：读取单元格的数据

```java
//读取一行中某个单元格内容，cellNum指定单元格的下标，从0开始
HSSFCell cell = hssfRow.getCell(cellNum);
//调用单元格的get方法
cell.getStringCellValue();
```

通过读大数据文件，导致内存溢出

![](http://upload-images.jianshu.io/upload_images/1540531-3d572356606bca30.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 总结：

**优点：**读取少数据，读取速度很快

**缺点：**读取大数据量，读取速度很慢，可能导致内存溢出

内存溢出原因，将流中的数据全部加载到内存，进行输出

## HSSF事件驱动模式导入

事件驱动模型，类似xml的sax解析

需要实现HSSFListener接口。

原理：根据excel底层存储（07以版本采用xml存储，以下版本采用二进制）标签决定事件出发点。

目标：在解析完一行(row)数据时进行触发。

### 总结：

**优点：**读取大数据时，不会导致内存溢出

**缺点**：用户在解析数据时比较困难。读取数据时速度不快的，因为读取数据的同时根据每个标签进行事件触发。

### HSSF事件驱动模型读取文件封装类

![](http://upload-images.jianshu.io/upload_images/1540531-40cd12e8b1db93d3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 第一步：实现HxlsOptRowsInterface（service）

根据具体业务需求，实现此接口

```java
public class HxlsOptRowsInterfaceImpl implements HxlsOptRowsInterface {

	@Override
	public String optRows(int sheetIndex, int curRow, List<String> rowlist)
			throws Exception {
		//插入数据库
		System.out.println("sheetIndex="+sheetIndex+"curRow="+curRow+rowlist);
		return null;
	}
}
```

#### 第二步：文件上传的action

#### 第三步：在action调用工具类

使用HxlsRead工具类执行数据导入，需要上传文件

### HSSF事件驱动模型详解

#### HxlsAbstract抽象类

```java
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public abstract class HxlsAbstract implements HSSFListener {
	private int minColumns;
	private POIFSFileSystem fs;
	private PrintStream output;

	private int lastRowNumber;
	private int lastColumnNumber;

	/** Should we output the formula, or the value it has? */
	private boolean outputFormulaValues = true;

	/** For parsing Formulas */
	private SheetRecordCollectingListener workbookBuildingListener;
	private HSSFWorkbook stubWorkbook;

	// Records we pick up as we process
	private SSTRecord sstRecord;
	private FormatTrackingHSSFListener formatListener;

	/** So we known which sheet we're on */
	private int sheetIndex = -1;
	private BoundSheetRecord[] orderedBSRs;
	@SuppressWarnings("unchecked")
	private ArrayList boundSheetRecords = new ArrayList();

	// For handling formulas with string results
	private int nextRow;
	private int nextColumn;
	private boolean outputNextStringRecord;

	private int curRow;
	private List<String> rowlist;
	@SuppressWarnings( "unused")
	private String sheetName;

	public HxlsAbstract(POIFSFileSystem fs)
			throws SQLException {
		this.fs = fs;
		this.output = System.out;
		this.minColumns = -1;
		this.curRow = 0;
		this.rowlist = new ArrayList<String>();
	}

	public HxlsAbstract(String filename) throws IOException,
			FileNotFoundException, SQLException {
		this(new POIFSFileSystem(new FileInputStream(filename)));
	}
	
	//excel记录行操作方法，以行索引和行元素列表为参数，对一行元素进行操作，元素为String类型
//	public abstract void optRows(int curRow, List<String> rowlist) throws SQLException ;
	
	//excel记录行操作方法，以sheet索引，行索引和行元素列表为参数，对sheet的一行元素进行操作，元素为String类型,rowlist存储了行数据
	public abstract void optRows(int sheetIndex,int curRow, List<String> rowlist) throws Exception;
	
	/**
	 * 遍历 excel 文件
	 */
	public void process() throws IOException {
		MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(
				this);
		formatListener = new FormatTrackingHSSFListener(listener);

		HSSFEventFactory factory = new HSSFEventFactory();
		HSSFRequest request = new HSSFRequest();

		if (outputFormulaValues) {
			request.addListenerForAllRecords(formatListener);
		} else {
			workbookBuildingListener = new SheetRecordCollectingListener(
					formatListener);
			request.addListenerForAllRecords(workbookBuildingListener);
		}

		factory.processWorkbookEvents(request, fs);
	}
	
	/**
	 * HSSFListener 监听方法，处理 Record
	 */
	@SuppressWarnings("unchecked")
	public void processRecord(Record record) {
		int thisRow = -1;
		int thisColumn = -1;
		String thisStr = null;
		String value = null;
		
		switch (record.getSid()) {
		case BoundSheetRecord.sid:
			boundSheetRecords.add(record);
			break;
		case BOFRecord.sid:
			BOFRecord br = (BOFRecord) record;
			if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
				// Create sub workbook if required
				if (workbookBuildingListener != null && stubWorkbook == null) {
					stubWorkbook = workbookBuildingListener
							.getStubHSSFWorkbook();
				}

				// Works by ordering the BSRs by the location of
				// their BOFRecords, and then knowing that we
				// process BOFRecords in byte offset order
				sheetIndex++;
				if (orderedBSRs == null) {
					orderedBSRs = BoundSheetRecord
							.orderByBofPosition(boundSheetRecords);
				}
				sheetName = orderedBSRs[sheetIndex].getSheetname();
			}
			break;

		case SSTRecord.sid:
			sstRecord = (SSTRecord) record;
			break;

		case BlankRecord.sid:
			BlankRecord brec = (BlankRecord) record;

			thisRow = brec.getRow();
			thisColumn = brec.getColumn();
			thisStr = "";
			break;
		case BoolErrRecord.sid:
			BoolErrRecord berec = (BoolErrRecord) record;

			thisRow = berec.getRow();
			thisColumn = berec.getColumn();
			thisStr = "";
			break;

		case FormulaRecord.sid:
			FormulaRecord frec = (FormulaRecord) record;

			thisRow = frec.getRow();
			thisColumn = frec.getColumn();

			if (outputFormulaValues) {
				if (Double.isNaN(frec.getValue())) {
					// Formula result is a string
					// This is stored in the next record
					outputNextStringRecord = true;
					nextRow = frec.getRow();
					nextColumn = frec.getColumn();
				} else {
					thisStr = formatListener.formatNumberDateCell(frec);
				}
			} else {
				thisStr = '"' + HSSFFormulaParser.toFormulaString(stubWorkbook,
						frec.getParsedExpression()) + '"';
			}
			break;
		case StringRecord.sid:
			if (outputNextStringRecord) {
				// String for formula
				StringRecord srec = (StringRecord) record;
				thisStr = srec.getString();
				thisRow = nextRow;
				thisColumn = nextColumn;
				outputNextStringRecord = false;
			}
			break;

		case LabelRecord.sid:
			LabelRecord lrec = (LabelRecord) record;

			curRow = thisRow = lrec.getRow();
			thisColumn = lrec.getColumn();
			value = lrec.getValue().trim();
			value = value.equals("")?" ":value;
			this.rowlist.add(thisColumn, value);
			break;
		case LabelSSTRecord.sid:
			LabelSSTRecord lsrec = (LabelSSTRecord) record;

			curRow = thisRow = lsrec.getRow();
			thisColumn = lsrec.getColumn();
			if (sstRecord == null) {
				rowlist.add(thisColumn, " ");
			} else {
				value =  sstRecord
				.getString(lsrec.getSSTIndex()).toString().trim();
				value = value.equals("")?" ":value;
				rowlist.add(thisColumn,value);
			}
			break;
		case NoteRecord.sid:
			NoteRecord nrec = (NoteRecord) record;

			thisRow = nrec.getRow();
			thisColumn = nrec.getColumn();
			// TODO: Find object to match nrec.getShapeId()
			thisStr = '"' + "(TODO)" + '"';
			break;
		case NumberRecord.sid:
			NumberRecord numrec = (NumberRecord) record;

			curRow = thisRow = numrec.getRow();
			thisColumn = numrec.getColumn();
			value = formatListener.formatNumberDateCell(numrec).trim();
			value = value.equals("")?" ":value;
			// Format
			rowlist.add(thisColumn, value);
			break;
		case RKRecord.sid:
			RKRecord rkrec = (RKRecord) record;

			thisRow = rkrec.getRow();
			thisColumn = rkrec.getColumn();
			thisStr = '"' + "(TODO)" + '"';
			break;
		default:
			break;
		}

		// 遇到新行的操作
		if (thisRow != -1 && thisRow != lastRowNumber) {
			lastColumnNumber = -1;
		}

		// 空值的操作
		if (record instanceof MissingCellDummyRecord) {
			MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
			curRow = thisRow = mc.getRow();
			thisColumn = mc.getColumn();
			rowlist.add(thisColumn," ");
		}

		// 如果遇到能打印的东西，在这里打印
		if (thisStr != null) {
			if (thisColumn > 0) {
				output.print(',');
			}
			output.print(thisStr);
		}

		// 更新行和列的值
		if (thisRow > -1)
			lastRowNumber = thisRow;
		if (thisColumn > -1)
			lastColumnNumber = thisColumn;

		// 行结束时的操作
		if (record instanceof LastCellOfRowDummyRecord) {
			if (minColumns > 0) {
				// 列值重新置空
				if (lastColumnNumber == -1) {
					lastColumnNumber = 0;
				}
			}
			// 行结束时， 调用 optRows() 方法
			lastColumnNumber = -1;
			try {
				optRows(sheetIndex,curRow, rowlist);
			} catch (Exception e) {
				e.printStackTrace();
			}
			rowlist.clear();
		}
	}
}
```

#### HxlsOptRowsInterface接口

```java
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
```

####  HxlsOptRowsInterfaceImpl接口实现类

```java
import java.util.List;

/**
 * 测试导入数据接口
 * @author Thinkpad
 *
 */
public class HxlsptRowsInterfaceImpl implements HxlsOptRowsInterface {

	@Override
	public String optRows(int sheetIndex, int curRow, List<String> rowlist)
			throws Exception {
		//插入数据库
		System.out.println("sheetIndex="+sheetIndex+"curRow="+curRow+rowlist);
		return null;
	}
	
}
```

#### HxlsRead工具类

```java
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
```

# 总结

## 使用HSSF进行excel文件写操作：

HSSF只支持97-03版本excel，扩展名是.xls，每个sheet行数最大65536行。

**缺点：**如果写数据量大文件，可能会导致内存溢出，原因，将所有数据对象放在内存中，最后执行文件内容写。

**优点：**对于小数据量文件写操作，速度很快的。

## 使用XSSF对excel写操作：

XSSF只支持07以上版本excel，扩展名是.xlsx（不向下兼容）, 每个sheet对行数据没 限制。

**优点：**对大数据量文件执行写操作，不会导致内存溢出，原因，在写的过程中生成很多临时文件，一边写一边生成文件（内存中不会保留很多数据），最后将临时文件合并输出。

**缺点：**对大数据量写操作，速度不快。

工具类掌握**ExcelExportSXXSSF**。

## 使用HSSF和XSSF对excel进行读操作：

HSSF只支持97-03版本excel，扩展名是.xls，每个sheet行数最大65536行。

### 使用HSSF用户驱动模式:

**优点：**方便编程，对于小数据量文件写操作，速度很快

**缺点：**如果写大数据量文件，可能导致内存溢出，原因，将文件内容全部加载到内存，进行读操作。

### 使用HSSF事件驱动模式：

​     大概原理：通过事件驱动模式，需要实现一个接口HSSFListener，接口方法中定义事件驱动执行内容，在解析Excel文件每一行、每一个单元格直到解析出数据，这个过程进行不同的事件驱动。

​     目标：在解析完每一行数据后事件驱动，将每一行数据获取到。

### 使用XSSF事件驱动模式。

XSSF只支持07以上版本excel，扩展名是.xlsx（不向下兼容）, 每个sheet对行数据没 限制。

事件驱动的方法同HSSF事件驱动。

使用HSSF或XSSF读取文件，实现数据导入，一般情况下03版本的65536行数据量够用。

如果本机安装07版本的excel软件，使用HSSF导入，可以将.xlsx另存为03版本的文件。

HSSF事件驱动读文件封装类：