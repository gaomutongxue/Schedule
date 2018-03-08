package memberClass;

import org.litepal.crud.DataSupport;

import java.util.Date;
import java.text.*;
import java.io.IOException;
import java.io.PrintWriter;


public class Affair extends DataSupport {
	private String _affairName;
	private String _username;
	private int _type;
	private int _level;
	private String _note;
	private Date _startTime;
	private Date _endTime;
	
	public String getName() { return _affairName; }
	
	public String getUserName() { return _username; }
	
	public int getType() { return _type; }
	
	public int getLevel() { return _level; }
	
	public String getNote() { return _note; }
	
	public Date getStartTime() { return _startTime; }
	
	public Date getEndTime() { return _endTime; }
	
	public void set_affair(String affairName, String username, int type, int level,
			String note, String startTime, String endTime) {
		
		_affairName = affairName;
		_username = username;
		_type = type;
		_level = level;
		_note = note;
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			_startTime = sf.parse(startTime);
			_endTime = sf.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void output(PrintWriter out) throws IOException {
		String startTime = new String();
		String endTime = new String();
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			startTime = sf.format(_startTime).toString();
			endTime = sf.format(_endTime).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		out.print(_affairName + ",");
		out.print(Integer.toString(_type) + ",");
		out.print(Integer.toString(_level) + ",");
		out.print(_note + ",");
		out.print(startTime + ",");
		out.println(endTime);
	}
	
//	public static void main(String[] args) throws IOException {
//		PrintWriter out = new PrintWriter(System.out, true);
//		Affair test = new Affair();
//		test.set_affair("test", "root", 1, 1, "This is a test", "2017-1-4 34:44:00", "2018-4-6 19:44:19");
//		test.output(out);
//	} 
}
