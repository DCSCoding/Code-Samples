import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MyAnimeListMemberRecorder {
	
	public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
	
			long day = 1000 * 60 * 60 * 24;
			
while(true) {
			
    		try {
    		long start = System.currentTimeMillis();
    		Path copy = Paths.get("C:\\Users\\...\\Desktop\\MAL_Recorder\\MAL_Members_BACKUP.xlsx");
    		Path originalPath = Paths.get("C:\\Users\\...\\Desktop\\MAL_Recorder\\MAL_Members.xlsx");
    			
    		try {
    			Files.copy(originalPath, copy, StandardCopyOption.REPLACE_EXISTING);
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
					report(e1);
				}
    		
			SimpleDateFormat date = new SimpleDateFormat("yyyy");
			SimpleDateFormat rundate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat runmonth = new SimpleDateFormat("MM");
			int currentmonth = Integer.parseInt(runmonth.format(System.currentTimeMillis()));
			int latestmonth = Integer.parseInt(runmonth.format(System.currentTimeMillis()+TimeUnit.DAYS.toMillis(180)));
			int nextyear = 0;
			String year = date.format(System.currentTimeMillis());
			String latestseason = "";
			if(latestmonth < 4) {
				latestseason = "winter";
				nextyear = 1;
			}else if(latestmonth < 7) {
				latestseason = "spring";
				nextyear = 1;
			}else if(latestmonth < 10) {
				latestseason = "summer";
				nextyear = 0;
			}else{
				latestseason = "fall";
				nextyear = 0;
			}
			
			nextyear = Integer.parseInt(year)+nextyear;
			
			//recordAnime takes arguments URL, title, and sheet. 
			try {
			recordAnime("https://myanimelist.net/anime/35503/Shoujo%E2%98%86Kageki_Revue_Starlight", "Revue Starlight", "Revue Starlight");
			recordAnime("https://myanimelist.net/anime/33573/BanG_Dream?q=bang%20dream", "BanG Dream!", "BanG Dream!");
			recordAnime("https://myanimelist.net/anime/37869/BanG_Dream_2nd_Season?q=BanG%20Dream%20", "BanG Dream! 2nd Season", "BanG Dream!");
			recordAnime("https://myanimelist.net/anime/37870/BanG_Dream_3rd_Season", "BanG Dream! 3rd Season", "BanG Dream!");
			recordAnime("https://myanimelist.net/anime/40129/Rebirth_for_You", "Rebirth for You,", "Rebirth for You");
			recordAnime("https://myanimelist.net/anime/40550/Assault_Lily__Bouquet", "Assault Lily: Bouquet", "Assault Lily");
			recordSeason("https://myanimelist.net/anime/season/"+year+"/fall");
			recordSeason("https://myanimelist.net/anime/season/"+year+"/spring");
			recordSeason("https://myanimelist.net/anime/season/"+year+"/summer");
			recordSeason("https://myanimelist.net/anime/season/later");
			
			recordSeason("https://myanimelist.net/anime/season/"+nextyear+"/"+latestseason);
			
			recordTop("https://myanimelist.net/topanime.php", 1, 50, "Top 100");
			recordTop("https://myanimelist.net/topanime.php?limit=50", 51, 101, "Top 100");
			recordTop("https://myanimelist.net/topanime.php?type=bypopularity", 1, 50, "Most Popular");
			recordTop("https://myanimelist.net/topanime.php?type=bypopularity&limit=100", 51, 100, "Most Popular");
			
			}catch(IOException io) {
				report(io.getMessage());
				io.printStackTrace();
			}
		
			report("Completed update cycle. Next update: " + rundate.format(System.currentTimeMillis()+TimeUnit.DAYS.toMillis(1)));
			
			long end = System.currentTimeMillis();
			double elapsed = (end-start)/1000;
			
			System.out.println("[Debug] Update finished in " + elapsed + " seconds.");
			
			//TimeUnit sleep seems to be unreliable when the system is logged out. 
			//A partial fix to this is, every loop, to have it check the system time against the last reported time, and subtract the difference from the 
			//wait time. This way, even if the loop only repeats irregularly, it will at least have a good chance of updating once per day. 
			//One predictable failure case is that the program won't report enough times in a day, and miss its interval to update.
			
			long currenttime = System.currentTimeMillis();
			long nextupdate = currenttime+day;
			long timeuntilnextupdate = nextupdate-currenttime;
			
			report("Next run in " + timeuntilnextupdate/1000/60 + " minutes.");
			
			while(timeuntilnextupdate > 0) {
				//After the first time, will only output time remaining to debug if 10 minutes or more have elapsed. 
				if(System.currentTimeMillis() - currenttime >= 1000 * 60 * 10) {
					report("Next run in " + timeuntilnextupdate/1000/60 + " minutes.");
				}
				currenttime = System.currentTimeMillis();
				timeuntilnextupdate = nextupdate-currenttime;
				TimeUnit.MINUTES.sleep(1);
			}
			
			/*for(int h = 0; h < 144; h++) {
				int i = 144-h;
				report("Next run in " + i*10 + " minutes.");
				try {
					TimeUnit.MINUTES.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					report(e.getMessage());
					e.printStackTrace();
				}
				
			}*/
			
    	}catch(Exception m) {
    		report(m);
    	}
			
		}
    }

    public static void recordSeason(String urlS) throws IOException {
	
    	ExcelSheetMaker ex = new ExcelSheetMaker();
    	BufferedReader br = null;
	
    	try {
		
    		URL url = new URL(urlS);
    		br = new BufferedReader(new InputStreamReader(url.openStream()));
		
    		String line;
		
    		StringBuilder sb = new StringBuilder();
		
    		while((line = br.readLine()) != null) {
			
    			sb.append(line);
    			sb.append(System.lineSeparator());
    		}
		
    		String temp = sb.toString();
    		String title = "";
    		String titles = "";
    		String members = "";
    		String season = "";
	
    		int sIndex = sb.indexOf("<title>")+7;
    		int eIndex = 0;
	
    		season = sb.substring(sIndex, sb.indexOf("-", sIndex)).trim();
    		//report("Season: " + season);
    		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	
    		//ArrayList to hold title and member information
    		ArrayList<String> memberStrings = new ArrayList<String>();
    		ArrayList<String> titleStrings = new ArrayList<String>();
	
    		sIndex = temp.indexOf("link-title");
    		eIndex = temp.indexOf("</a>", sIndex);
    		
    		while(sIndex != -1) {
    			
    			sIndex += 12;
    			title = temp.substring(sIndex, eIndex);
    			titleStrings.add(title);
    			sIndex = temp.indexOf("title=\"Members\"", eIndex)+16;
    			eIndex = temp.indexOf("</span>", sIndex);
    			members = temp.substring(sIndex, eIndex).trim();
    			memberStrings.add(members);
    			titles += title + " - " + members + System.lineSeparator();
    			sIndex = temp.indexOf("link-title", eIndex);
    			eIndex = temp.indexOf("</a>", sIndex);
		
    		}
    		
    		ExcelSheetMaker.addInformationArray(season, titleStrings, date.format(System.currentTimeMillis()), memberStrings);
    		//report(titles);
    		File file = new File("Mal_Members.txt");
    		boolean checked = false;
    		if(file.exists()) {
    			BufferedReader fr = new BufferedReader(new FileReader("Mal_Members.txt"));
    			String temp2 = "";
    			while((line = fr.readLine()) != null) {
    				temp2 += line;
    			}
	
    			if(temp2.contains(date.format(System.currentTimeMillis()))) {
    				checked = true;
    			}
    			fr.close();
    		}
    		if(!checked) {
    			BufferedWriter bw = new BufferedWriter(new FileWriter("Mal_Members.txt", true));
    			bw.write(date.format(System.currentTimeMillis()) + System.lineSeparator() + titles + System.lineSeparator());
    			bw.close();
    		}

    	}finally {
		
    		if(br != null) {
    			br.close();
    		}
    	}
    }

	public static void recordTop(String urlS, int start, int end, String sheetname) throws IOException{
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateformat.format(System.currentTimeMillis());
		
		ExcelSheetMaker ex = new ExcelSheetMaker();
		URL url = new URL(urlS);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<String> rankings = new ArrayList<String>();
		
    	try {
    	
    		String line;
    	
    		StringBuilder sb = new StringBuilder();
    	
    		while((line = br.readLine()) != null) {
    			sb.append(line);
    			sb.append(System.lineSeparator());
    		}
    	
    		String temp = sb.toString();
    		
    		int sIndex = temp.indexOf("Anime: ");
    		int eIndex = temp.indexOf("\"", sIndex);
    		
    		while(sIndex != -1) {
    			
    			sIndex += 7;
    			titles.add(temp.substring(sIndex, eIndex));
    			sIndex = temp.indexOf("Anime: ", eIndex);
    			eIndex = temp.indexOf("\"", sIndex);
    			
    		}
    		
    		
    		for(int i = start; i < end; i++) {
    			rankings.add("#" + i);
    		}
    		
    		ExcelSheetMaker.addInformationArray(sheetname, rankings, date, titles);
    		
    	}finally {
		
    		if(br != null) {
    			br.close();
		}
	}
		
	}
	
	public static void recordAnime(String urlS, String title, String sheet) throws IOException {
	
	BufferedReader br = null;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String date = sdf.format(System.currentTimeMillis());
		try {
		
			URL url = new URL(urlS);
			br = new BufferedReader(new InputStreamReader(url.openStream()));
		
			String line;
		
			StringBuilder sb = new StringBuilder();
		
			while((line = br.readLine()) != null) {
			
				sb.append(line);
				sb.append(System.lineSeparator());
		
			}
		
		String members = "";
		int index = 0;
		String membersKey = "<span class=\"dark_text\">Members:</span>";
		index = sb.indexOf(membersKey)+membersKey.length();
		members = sb.substring(index, sb.substring(index).indexOf("</div>")+index).trim();
		
		ExcelSheetMaker.addInformation(sheet, title, date, members);
		
		
		}finally {
			
			if(br != null) {
				br.close();
			}
		
		}
	}
	
	private static void report(Exception e) {
		try {
			
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	    	Date date = new Date();
			BufferedWriter debug = new BufferedWriter(new FileWriter("C:\\Users\\...\\Desktop\\MAL_Recorder\\debug.txt", true), 32768);
			PrintWriter errstream = new PrintWriter(debug);
			System.out.println();
			System.out.println("[DEBUG] " + dateFormat.format(date));
			e.printStackTrace();
			debug.append("[DEBUG]" + dateFormat.format(date) + " ");
			e.printStackTrace(errstream);
			debug.newLine();
			
			debug.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1);
		}
		
	}
	
	private static void report(String s) {
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	    	Date date = new Date();
			BufferedWriter debug = new BufferedWriter(new FileWriter("C:\\Users\\...\\Desktop\\MAL_Recorder\\debug.txt", true), 32768);
			System.out.println();
			System.out.println("[DEBUG] " + dateFormat.format(date) + " " + s);
			debug.append("[DEBUG]" + dateFormat.format(date) + " " + s);
			debug.newLine();
			debug.flush();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1);
		}
	}
}
