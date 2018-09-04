package lordgasmic.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;
import java.util.regex.PatternSyntaxException;

import lordgasmic.common.atomic.Poison;

public class Jbom {
	
	
	
	//Find duplicate SQL statements in the insert file
	public static void FindSQLDuplicates(String gfsID) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("C:\\dev\\sqlInsert" + gfsID + ".txt"));
		StringBuilder sb = new StringBuilder();
		String insert;
		int count = 0;
		
		while ((insert = br.readLine()) != null){
			sb.append(insert + "::::::::");
		}
		
		String[] read = sb.toString().split("::::::::");
		
		sb = new StringBuilder();
		
		for (int i = 0; i < read.length; i++){
			for (int j = i+1; j < read.length; j++){
				if (read[i].equalsIgnoreCase(read[j])){
					read[j] = "";
					count++;
				}
			}
			if (read[i].length()>1)
				sb.append(read[i] + "\r\n");
			
		}
		
		br.close();
		System.out.println(count);
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\dev\\sqlInsert" + gfsID + ".txt"));
		bw.write(sb.toString());
		bw.close();
	}
	
	//Take the mm/dd/yyyy date and convert it to yyyy-mm-dd format
	public static String MakeDate(ResultSet rset) throws SQLException {
		rset.first();
		String date = rset.getString(1);
		String modDate;
		String tempDate;

		if (date == null)
			return "'2010-01-01'";
		
		date = date.substring(0,date.lastIndexOf('/')+5);
		modDate = date.substring(date.lastIndexOf('/')+1);
		modDate += "-";
		tempDate = date.substring(0,date.indexOf('/'));
		if (tempDate.length() == 1)
			tempDate = "0" + tempDate;
		modDate += tempDate;
		tempDate = date.substring(date.indexOf('/')+1, date.lastIndexOf('/'));
		if (tempDate.length() == 1)
			tempDate = "0" + tempDate;
		modDate = "'" + modDate + "-" + tempDate + "'";
		
		return modDate;
	}
	
	//Implementation of C# WriteLine
	public static String MakeString(String input, String... params){
		int len = params.length;
		
		for(int i = 0; i < len; i++){
			String s = "\\{" + i + "\\}";
			input = input.replaceFirst(s, params[i]);
		}
		
		return input;
	}
	
	//Get Oracle Class
	public static void OraClass(){
		try {
			Class.forName(LgcConstants.Ora);
		}
		catch (Exception e) {
			System.out.println("An error has occured:");
			e.printStackTrace();
		}
	}

	//Get MySql Class
	public static void MySqlClass(){
		try {
			Class.forName(LgcConstants.MySql);
		}
		catch (Exception e){
			System.out.println("An error has occured:");
			e.printStackTrace();
		}
	}
	
	//get a date for the starting and ending period
	public static String[] MakePeriodDate(ResultSet rset) throws SQLException{
		rset.first();
		String[] date = new String[2];
		String start = rset.getString(1);
		String end = rset.getString(2);
		
		start = MakeDate(start);
		end = MakeDate(end);		
		
		date[0] = start;
		date[1] = end;
		return date;
	}
	
	private static String MakeDate(String date){
		String modDate;
		String tempDate;
		
		date = date.substring(0,date.lastIndexOf('/')+5);
		modDate = date.substring(date.lastIndexOf('/')+1);
		modDate += "-";
		tempDate = date.substring(0,date.indexOf('/'));
		if (tempDate.length() == 1)
			tempDate = "0" + tempDate;
		modDate += tempDate;
		tempDate = date.substring(date.indexOf('/')+1, date.lastIndexOf('/'));
		if (tempDate.length() == 1)
			tempDate = "0" + tempDate;
		modDate = "'" + modDate + "-" + tempDate + "'";
		
		return modDate;
	}
	public static String WriteLine(String input, String... params){
		int len = params.length;

		for(int i = 0; i < len; i++){
			String s = "\\{" + i + "\\}";

			input = input.replaceFirst(s, params[i]);
		}

		System.out.println(input);
		return input;
	}
	public static String MakeSQLString(String input, String[] col, String[] val){
		int colLen = col.length;

		String colReg = "\\{\\{C\\}\\}";
		String valReg = "\\{\\{V\\}\\}";

		for(int i = 0; i < colLen; i++){			
			input = input.replaceFirst(colReg, col[i]);
			input = input.replaceFirst(valReg, val[i]);
		}

		return input;
	}
	public static void OutLine(Object o){
		System.out.println(o.toString());
	}
	public static void Out(Object o){
		System.out.print(o.toString());
	}
	public static String MakeSelect(String[] sel, String table, String[] column, String[] value){
		int selLen;
		int colLen;
		int valLen;

		if (sel == null){
			selLen = -1;
		}
		else {
			selLen = sel.length;
		}
		if (selLen == 0)
			selLen = -1;

		if (column == null){
			colLen = -1;
		}
		else {
			colLen = column.length;
		}
		if (colLen == 0)
			colLen = -1;

		if (value == null){
			valLen = -1;
		}
		else {
			valLen = value.length;
		}
		if (valLen == 0)
			valLen = -1;

		if (valLen != colLen){
			return null;
		}

		String base = "select * from " + table;;

		if (selLen > -1){
			String s = sel[0];
			if (selLen > 1){
				for(int i = 1; i < selLen; i++){
					s = s + "," + sel[i];
				}
			}
			base = base.replaceAll("\\*", s);
		}
		if (colLen == -1){
			return base;
		}
		else {
			base = base + "\r\nwhere {{C}} = {{V}}";
			if (colLen > 1) {
				for (int i = 0; i < colLen -1; i ++){
					base = base + " and {{C}} = {{V}}";
				}
			}
			base = MakeSQLString(base, column, value);
		}

		return base;
	}
	public static String Shuffle(String in, String mix){
		StringBuilder sb = new StringBuilder();
		Random r = new Random();

		char[] bArr = mix.toCharArray();

		sb.append(in);

		for(int i = 0; i < bArr.length; i++){
			int ri = r.nextInt(sb.length());
			sb.insert(ri,bArr[i]);
		}

		return sb.toString();
	}
	
	//Returns a 256 byte string
	public static String RandomString() throws InterruptedException{
		StringBuilder sb = new StringBuilder();
		String base = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()-_=+[]{};:,.<>`~";

		for (int i = 1; i <= 256; i++){
			long a = System.currentTimeMillis();
			sb.append(base.charAt((int)(a%88)));
			Thread.sleep((a%1000)+100);
		}

		return sb.toString();
	}
	public static String DecToBin(int dec){
		String binary = "";

		int ex = 1;

		while (dec > 0){
			System.out.println(dec);
			binary = dec%1 + binary;
			dec = dec - (int)Math.pow(2, ex);
			ex++;
		}

		return binary;
	}
	private static boolean errorCheck(String path, String fname, int len){
		File p = new File(path);
		p.mkdirs();
		
		if (len == -1)
			return false;
		
		File f = new File(path + fname);
		
		if (f.exists()){
			if (f.length() < len)
				return false;
			return true;
		}
		else
			return false;
	}
	public static String urlToFile(String url){
		String file = url.substring(url.lastIndexOf('/') + 1);
		return file;
	}
	public static void download(URL url, String loc){
		download(url,loc,null);
	}
	public static void download(String url, String loc){
		try {
			download(new URL(url),loc,null);
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	public static boolean download(URL url, String loc, String fname){
		try {
			URLConnection uc = url.openConnection();	
			int len = uc.getContentLength();

			if (fname == null || fname == ""){
				try {
					loc = loc.replaceAll("\\\\", "/");
				}
				catch (PatternSyntaxException e){
					
				}
				fname = loc.substring(loc.lastIndexOf('/') + 1);
				loc = loc.substring(0, loc.lastIndexOf('/') + 1);
			}
			
			if (errorCheck(loc, fname, len))
				return false;

			loc = loc + fname;

			System.out.println("Starting download for " + url.toString());
			
			ReadableByteChannel rbc = Channels.newChannel(uc.getInputStream());
			FileOutputStream fos = new FileOutputStream(loc);
			FileChannel fc = fos.getChannel();

			if (len == -1)
				fc.transferFrom(rbc, 0, 1 << 24);
			else
				fc.transferFrom(rbc, 0, len);

			fos.close();
			fc.close();
			rbc.close();
			
			return true;
		} 
		catch (FileNotFoundException e) {
			String path = System.getenv("LORDGASMIC");
			try {
				String sout = new Date(System.currentTimeMillis()).toString() + "  :  Failed to get " + url.toString() + " to " + loc + "\r\n";
				System.out.println(sout);
				BufferedWriter bw = new BufferedWriter(new FileWriter(path + "\\Logs\\download.log", true));
				bw.write(sout);
				bw.close();
			} catch (IOException e1) {
				System.out.println("Log file failed to write");
				System.exit(-1);
			}
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e){
			if (e.getMessage().contains("502")){
				System.out.println(e.getLocalizedMessage());
			}
			else 
				e.printStackTrace();
		}
		catch (Exception e) {
			Exit(e);
		}
		
		return false;
	}
	/**
	 * 
	 * @param url
	 * @param loc
	 * @param fname
	 * @return 
	 */
	public static boolean download(String url, String loc, String fname){
		try {
			return download(new URL(url), loc, fname);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	//Get GFS ID
	public static String GetGFSID(){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter GFS ID:");
			return br.readLine();
		}
		catch (Exception e) {
			System.out.println("Could not read line");
		}
		return null;
	}
	
	//Get Connection and return Statement
	public static Statement Stmt(String s, String u, String p) throws SQLException{
		Connection conn = DriverManager.getConnection(s,u,p);
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		return stmt;
	}
	
	public static void Exit(Exception e){
		e.printStackTrace();
		System.exit(-1);
	}
	
	public static Poison MakePoison(){
		return new Poison() {
		};
	}

}
