package com.homedepot.renowalk.ReportManager;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.homedepot.renowalk.TestDriver;
import com.homedepot.renowalk.ConfigManager.FileLocSetter;
import com.homedepot.renowalk.ConfigManager.SQLConfig;

public class CopyOfReporter extends TestDriver
{
	
	public static ArrayList<ReportGen> reportList;
	public static ArrayList<IndexGen> masterList;
	
	public static String strFinal=null;
	public static int slNo;
	static int totalCnt; // total step count
	static int passCnt; // step pass
	static int failCnt; // step fail
	static int incompleteCnt=0; // step incomplete
	
	
	//static int warnCnt = 0;
	static int diff = 0;
	
	static int testCaseCnt = 0;
	
	public static int totalExecuted = 0;
	public static int totalPass = 0;
	public static int totalFail = 0;
	public static int totalIncomplete = 0;
	public static int totalPlanned = 0;
	
	static int testPassCnt= 0;
	static int testFailCnt = 0;
	//static int testWarnCnt = 0;
	static int testIncompleteCnt = 0;
	static int ttlPassCnt = 0;
	static int ttlFailCnt = 0;
	static int ttlIncompleteCnt = 0;
	static int testCnt = 0;
	
	
	@SuppressWarnings("rawtypes")
	static ArrayList tc;
	static String strDiff = "";
	static String startTime = "";
	static Date startDate;
	static Date endDate;
	static Date testStartDate;
	static Date testEndDate;
	public int i;
	public String rowid;
	public static File file;
	
	//static final String PATH = FileLoc.sProjPath+"Results\\";
    //static final String REFPATH = FileLoc.sProjPath+"ReportRef\\";	
	
	static final String PATH = FileLocSetter.sReportsPath+"Results\\";
    static final String REFPATH = FileLocSetter.sReportsPath+"ReportRef\\";	
	
	public static String filePath; // local report testcase folder
	public static String shareFolderPath; // track shared folder path
	
	SQLConfig objSQLConfig = new SQLConfig();
	/* @@@ Generate Header for the index HTML @@@ */
	

	//public static String repGenerateIndexHeader(String testEnv, String appName, String productionType, String testPhase) throws Exception
	public static String repGenerateIndexHeader(String testEnv, String appName, String testPhase) throws Exception
	{
		String indexHeaderHtmlContent = "";
		String miniPath = null;
		String sSharedServerIP = null;
		ttlFailCnt = 0;
		testCnt = 0;
		testStartDate = null;
		File dir = null;
		testStartDate = new Date();
		
		sSharedServerIP =  TestDriver.prop.getProperty("sharedServerIP");
		/*
		miniPath = testEnv+"_"
				+ appName+"_"
				+ productionType+"_"
				+ testPhase+"_"
				+ new SimpleDateFormat("MM_dd_yyyy_hh_mm_ss_a")
						.format(new Date());*/
		
		//Folder name Compatible with QTP Scripts
		miniPath = testPhase+"_"
				+ new SimpleDateFormat("MM_dd_yyyy_hh_mm_ss_a")
						.format(new Date());
		
		filePath = FileLocSetter.sReportsPath+"\\Results\\"+ miniPath;
		sFolderPath = filePath; // save path of local report folder

		shareFolderPath = "\\\\"+sSharedServerIP+"\\"+testEnv+"\\"+mEnvSheetData.get(iMasterRowId).get("Release")+"\\"+appName+"\\"+mEnvSheetData.get(iMasterRowId).get("VCP_Type")+"\\"+"Results"+"\\";
		shareFolderPath = shareFolderPath + miniPath;
		
		dir = new File(filePath);
		dir.mkdir();
		
		masterList = new ArrayList<IndexGen>();
		indexHeaderHtmlContent = "<html><head><link rel = \"stylesheet\" href = \""
				+ "../../ReportRef/css/styles.css\" /></head><body><hr class = \"divline\"><table class = \"reportheader\" width = 100%><tr><td height = 3px><img src  = '"
				+ "../../ReportRef/Images/logo_en.gif'></td><BR><td height = 5px>"
				//+ appName
				+ mEnvSheetData.get(iMasterRowId).get("ReportName")
				+ "<BR>"
				+ "<td height = 3px><img src  = '../../ReportRef/Images/logo_en_nex.gif'></td>"
				+"<td height = 3px align = right></td></tr></table><hr class = \"divline\"><BR><table class = \"subheader\" width = 900px><tr><td width = 400px class = \"subheader\" align = left>Test sPhase</td><td width = 300px class = \"subheader\">Execution Date</td><td width = 350px class = \"subheader\" align = right>sEnvironment</td></tr><tr><td width = 400px class = \"subcontents\" align = left>"
				+ testPhase
				+ "</td><td width = 300px class = \"subcontents\">"
				+ new SimpleDateFormat("MM-dd-yyyy").format(testStartDate)
				+ "</td><td width = 400px class = \"subcontents\" align = right>"
				+ testEnv + "</td></tr></table><hr class = \"divline\"><BR>";
		return indexHeaderHtmlContent;
	}

	/* @@@ Generate Header for the HTML @@@ */

	public static String repGenerateHeader(String testCaseHeader,
			String testCase, String sApplication, String testCaseDescription)
			throws IOException, ParseException {

		String headerHtmlContent = "";
		passCnt = 0;
		totalCnt = 0;
		failCnt = 0;
		incompleteCnt = 0;
		startTime = "";
		reportList = new ArrayList<ReportGen>();

		startDate = null;
		endDate = null;
		
		startDate = new Date();

		headerHtmlContent = "<html><head><link rel = \"stylesheet\" href = \""
				+ "../../ReportRef/css/styles.css\" /></head><hr class = \"divline\"><table class = \"reportheader\" width = 900px><tr><td height = 3px><img src = '"
				+ "../../ReportRef/Images/logo_en.gif'></td><BR><td height = 3px>"
				+ testCaseHeader
				+ "<BR><td height = 3px align = right></td></tr></table><hr class = \"divline\"><BR><table class = \"subheader\" width = 900px><tr><td width = 400px class = \"subheader\">Test Case</td><td width = 300px class = \"subheader\">Execution Date</td><td width = 200px align = right class = \"subheader\">sApplication</td></tr><tr><td width = 400px class = \"subcontents\">"
				+ testCase
				+ "</td><td width = 300px class = \"subcontents\">"
				+ new SimpleDateFormat("MM-dd-yyyy").format(startDate)
				+ "</td><td width = 200px align = right class = \"subcontents\">"
				+ sApplication
				+ "</td></tr></table><hr class = \"divline\"> <BR><table class = \"subheader\" width = 900px><tr><tr><td width = 900px class = \"subheader\">Test Case Description</td></tr><tr><td width = 900px class = \"subcontents\">"
				+ testCaseDescription
				+ "</td></tr></tr></table><hr class = \"divline\"><BR>";
		return headerHtmlContent;
	}

	/* @@@ Generate Footer for the HTML @@@ */

	public String repGenerateFooter() {
		String footer = "";
		endDate = new Date();
		
		diff = (int) ((endDate.getTime() / 1000) - (startDate.getTime() / 1000));
		if ((diff / 60) != 0) {
			strDiff = ((diff / 60) + "Min" + " " + (diff % 60) + "Secs");
		} else {
			strDiff = ((diff % 60) + " " + "Secs");
		}
		
		// System.out.println("@@ Time difference @@" + diff);
		//footer = "<table class = \"tblinks\" width = 250px align = left><tr><td class = \"tsheader\">Links</td></tr><tr><td class = \"pfind\"><a href = \"index.html\">Index Page</a></td></tr></table><table width = 250px class = \"tbtime\"><tr><td colspan = 2 class = \"tsheader\">Execution Time</td></tr><tr><td class = \"pfhead\" width = 120px>Start Time</td><td class = \"pfind\" width = 130px>"
		footer = "<table class = \"tblinks\" width = 250px align = left><tr><td class = \"tsheader\">Links</td></tr><tr><td class = \"pfind\"><a href = "+TestDriver.sIndexHTMLFileName+">Index Page</a></td></tr></table><table width = 250px class = \"tbtime\"><tr><td colspan = 2 class = \"tsheader\">Execution Time</td></tr><tr><td class = \"pfhead\" width = 120px>Start Time</td><td class = \"pfind\" width = 130px>"
				+ new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a")
						.format(startDate)
				+ "</td></tr><tr><td class = \"pfhead\" width = 120px>End Time</td><td class = \"pfind\" width = 130px>"
				+ new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(endDate)
				+ "</td></tr><tr><td class = \"pfhead\" width = 600px>Duration</td><td class = \"pfind\" width = 600px>"
				+ strDiff
				+ "  </td></tr></table><!-- Pass Fail count--><table width = 250px class = \"pfsummary\"><tr><td colspan = 2 class = \"tsheader\">Test Case Summary</td></tr><tr><td class = \"pfhead\" width = 200px>Total Steps</td><td class = \"pfind\" width = 50px>"
				+ totalCnt
				+ "</td>	</tr><tr><td class = \"pfhead\" width = 200px>Steps Passed</td><td class = \"pfind\" width = 50px>"
				+ passCnt
				+ "</td></tr><tr><td class = \"pfhead\" width = 200px>Incomplete</td><td class = \"pfind\" width = 50px>"+incompleteCnt+"</td></tr><tr><td class = \"pfhead\" width = 200px>Steps Failed</td><td class = \"pfind\" width = 50px>"
				+ failCnt + "</td></tr></table><BR><BR>";
		return footer;
	}

	/* @@@ Generate Footer for the index HTML @@@ */

	public String repGenerateIndexFooter() 
	{
//		System.out.println(" Inside generate index footer");
		String footer = "";
		endDate = new Date();
		diff = (int) ((endDate.getTime() / 1000) - (testStartDate.getTime() / 1000));
		if ((diff / 60) != 0) {
			strDiff = ((diff / 60) + "Min" + " " + (diff % 60) + " " + "Secs");
		} else {
			strDiff = ((diff % 60) + " Secs");
		}
		
		footer = "<table width = 250px class = \"tbtime\"><tr><td colspan = 2 class = \"tsheader\">Execution Time</td></tr><tr><td class = \"pfhead\" width = 120px>Start Time</td><td class = \"pfind\" width = 130px>"
				+ new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a")
						.format(testStartDate)
				+ "</td></tr><tr><td class = \"pfhead\" width = 120px>End Time</td><td class = \"pfind\" width = 130px>"
				+ new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(endDate)
				+ "</td></tr><tr><td class = \"pfhead\" width = 120px>Duration</td><td class = \"pfind\" width = 130px>"
				+ strDiff
				+ " </td></tr></table><table width = 250 class = \"pfsummary\"><tr><td colspan = 2 class = \"tsheader\">Test Execution Summary</td></tr><tr><td class = \"pfhead\" width = 200px>Total Test Cases</td> <td class = \"pfind\" width = 50px>"
				+ totalExecuted
				+ "</td></tr><tr><td class = \"pfhead\" width = 200px>Test Cases Passed</td><td class = \"pfind\" width = 50px> "
				+ totalPass
				+ "</td></tr><tr><td class = \"pfhead\" width = 200px>Incomplete Test Cases</td><td class = \"pfind\" width = 50px>"+totalIncomplete+"</td></tr><tr><td class = \"pfhead\" width = 200px>Test Cases Failed</td><td class = \"pfind\" width = 50px>"
				+ totalFail + "</td></tr></table></body></html>";


		return footer;
	}

	/* @@@ Generate Body content for the HTML @@@ */

	//public void repAddData(String step, String stepDescription,String expectedResult, String actualResult, String status) throws Exception 
	public void repAddData(String stepDescription,String expectedResult, String actualResult, String status) throws Exception
	{
		try {
			String step = "";
		if(expectedResult.equalsIgnoreCase("") && actualResult.equalsIgnoreCase("") && status.equalsIgnoreCase(""))
			{
				mainStep++;
				step=String.valueOf(mainStep);
				subStep=0;  //If we start a new tc header like 2 and 3
			}
			else
			{
				subStep++;
				step=String.valueOf(mainStep)+"."+String.valueOf(subStep);
			}
			
		//	if(status.equals("")) subStep=0;
			
			if(status.equalsIgnoreCase("Fail"))
			{
				String fileName = "";
				testCaseStatus = false;
				
				ReportGen re = new ReportGen();
				if (step.indexOf(".") != -1) {
					re.setStep(step);
					re.setStepDescription(stepDescription);
					re.setExpectedResult(expectedResult);
					re.setActualResult(actualResult);
					re.setStatus(status.toUpperCase());
					fileName = ScreenShotGetter.screenShotCapture(filePath);
					re.setScreenShotFileName(fileName);
					re.setTimeStamp();
				} else 
				{
					re.setStep(step);
					re.setStepDescription(stepDescription);
				}
				reportList.add(re);
			} 
			else if(status.equalsIgnoreCase("Incomplete"))
			{
				//String fileName = "";
				testCaseStatus = false;
				
				ReportGen re = new ReportGen();
				if (step.indexOf(".") != -1) {
					re.setStep(step);
					re.setStepDescription(stepDescription);
					re.setExpectedResult(expectedResult);
					re.setActualResult(actualResult);
					re.setStatus(status.toUpperCase());
					//fileName = ScreenCapture.screenShotCapture(filePath);   //Shameem - Comment screenshot for Incomplete status
					//re.setScreenShotFileName(fileName);
					re.setTimeStamp();
				} else 
				{
					re.setStep(step);
					re.setStepDescription(stepDescription);
				}
				reportList.add(re);
			}
			
			else 
			{
				//String fileName = "";

				ReportGen re = new ReportGen();
				if (step.indexOf(".") != -1) {
					re.setStep(step);
					re.setStepDescription(stepDescription);
					re.setExpectedResult(expectedResult);
					re.setActualResult(actualResult);
					re.setStatus(status.toUpperCase());
					//fileName = ScreenCapture.screenShotCapture(filePath); //Shameem - Comment screenshot for Pass status
					//re.setScreenShotFileName(fileName);
					re.setTimeStamp();
				} else 
				{
					re.setStep(step);
					re.setStepDescription(stepDescription);
				}
				reportList.add(re);
			}
			
			System.out.println("["+step+"]"+"["+stepDescription+"]"+"["+expectedResult+"]"+"["+actualResult+"]"+"["+status+"]");

		} catch (Exception e) {
			System.out.println(e);
		}
		
	}	

		// System.out.println("@@@ Report List Size @@@" + reportList.size());
	

	/* @@@ Generate the child test report HTML @@@ */

	public void repGenerateResult(String testName, String header)	throws Exception 
	{
		
		String str = "";
		String footer = "";
		IndexGen me = new IndexGen();

		slNo++;
		for (int i = 0; i < reportList.size(); i++) 
		{
			if (reportList.get(i).getStep().indexOf(".") == -1) 
			{
				str = str
						+ "<tr><td class = \"tsindlevel1\" width = 75px>"
						+ reportList.get(i).getStep()
						+ "</td><td class = \"tsgenlevel1\" width = 155px>"
						+ reportList.get(i).getStepDescription()
						+ "</td><td class = \"tsgenlevel1\" width = 285px></td><td class = \"tsgenlevel1\" width = 285px></td><td class = \"tsindlevel1\" width = 50px>&nbsp</td>"
						+"<td class = \"tsgenlevel1\" width = 50px></td>"  //Shameem we need screenshot column just to show on the screen (but of course without screenshot)
						+"</tr>";
			} else 
			{
				totalCnt++; //total test step count
				if ("Pass".equalsIgnoreCase(reportList.get(i).getStatus())) 
				{
					passCnt++; //total testcase passed
					str = str
							+ "<tr><td class = \"tsindlevel2\" width = 75px>"
							+ reportList.get(i).getStep()
							+ "</td><td class = \"tsgen\" width = 155px>"
							+ reportList.get(i).getStepDescription()
							+ " </td><td class = \"tsgen\" width = 285px>"
							+ reportList.get(i).getExpectedResult()
							+ "</td><td class = \"tsgen\" width = 285px>"
							+ reportList.get(i).getActualResult()
							+ "</td><td class = \"tsind\" width = 50px><font size  =  2 color  =  green><b>"
							+ reportList.get(i).getStatus()
							//+ "</b></td><td class = \"tsind\" width = 50px>&nbsp</td></tr>";
							+ "</b></td>"
				/*			+"<td class = \"tsind\" width = 50px><a target = \"_blank\" class = \"anibutton\" href =  '"
							+ reportList.get(i).getScreenShotFileName()
							+ "'\"><img class = \"screen\" src  =  \""
							+ "../../ReportRef/Images/screenshot.gif\"></a></td>" */ //Commented by Shameem
							+"<td class = \"tsind\" width = 50px></td>"  //Shameem we don't want screenshot for Pass step
							+"</tr>";
					
					
				} 
				else if ("Incomplete".equalsIgnoreCase(reportList.get(i).getStatus())) 
				{
					incompleteCnt++; //total testcase incompleted
					str = str
							+ "<tr><td class = \"tsindlevel2\" width = 75px>"
							+ reportList.get(i).getStep()
							+ "</td><td class = \"tsgen\" width = 155px>"
							+ reportList.get(i).getStepDescription()
							+ " </td><td class = \"tsgen\" width = 285px>"
							+ reportList.get(i).getExpectedResult()
							+ "</td><td class = \"tsgen\" width = 285px>"
							+ reportList.get(i).getActualResult()
							+ "</td><td class = \"tsind\" width = 50px><font size  =  1 color  =  black><img src  =  '"
							+ "../../ReportRef/Images/Incomplete.gif'/>"
							+ reportList.get(i).getStatus()
							//+ "</b></td><td class = \"tsind\" width = 50px>&nbsp</td></tr>";
							+ "</td>"
							/*+"<td class = \"tsind\" width = 50px><a target = \"_blank\" class = \"anibutton\" href =  '"
							+ reportList.get(i).getScreenShotFileName()
							+ "'\"><img class = \"screen\" src  =  \""
							+ "../../ReportRef/Images/screenshot.gif\"></a></td>"*/ //Commented by Shameem
							+"<td class = \"tsind\" width = 50px></td>"  //Shameem we don't want screenshot for Pass step
							+"</tr>";
					
					
				}
				
				else {
					failCnt++; //total testcase failed
					str = str
							+ "<tr><td class = \"tsindlevel2\" width = 75px>"
							+ reportList.get(i).getStep()
							+ "</td><td class = \"tsgen\" width = 155px>"
							+ reportList.get(i).getStepDescription()
							+ " </td><td class = \"tsgen\" width = 285px>"
							+ reportList.get(i).getExpectedResult()
							+ "</td><td class = \"tsgen\" width = 285px>"
							+ reportList.get(i).getActualResult()
							+ "</td><td class = \"tsind\" width = 50px><font size  =  2 color  =  red><b><img src  =  '"
							+ "../../ReportRef/Images/failed.gif'/>"
							+ reportList.get(i).getStatus()
							+ "</b></td><td class = \"tsind\" width = 50px><a target = \"_blank\" class = \"anibutton\" href =  '"
							+ reportList.get(i).getScreenShotFileName()
							+ "'\"><img class = \"screen\" src  =  \""
							+ "../../ReportRef/Images/screenshot.gif\"></a></td></tr>";
				}
			}
		}

		String bTblHdr = "<BR><table width = 900px class = \"teststeps\"><tr><td class = \"tsheader\" width = 75px>Step #</td><td class = \"tsheader\" width = 155px>Step Description</td><td class = \"tsheader\" width = 285px>Expected Result</td><td class = \"tsheader\" width = 285px>Actual Result</td><td class = \"tsheader\" width = 50px>Status</td><td class = \"tsheader\" width = 50px>Screen Shot</td></tr>";
		footer = repGenerateFooter();
		file = new File(filePath + "\\" + testName + ".html");
		System.out.println("HTML test case report: " + file);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		String output = header
				+ "<body><table BORDER = 1 CELLPADDING = 3 CELLSPACING = 1 WIDTH = 100%>"
				+ bTblHdr + str + "</table><BR><BR>" + footer
				+ "</body></html>";
		bw.write(output);
		bw.close();

		/* @@@ Prepare Data for index.html @@@ */

		me.setSlNo(slNo);
		me.setTestCase(sTest_Case_Name);
		me.setTestDesc(sTest_Case_Description);
		me.setSteps(totalCnt); // total count of steps in Index
		me.setPass(passCnt); // total count of pass steps in Index
		me.setIncomplete(incompleteCnt);
		me.setFail(failCnt); // total count of fail steps in Index
		
		if (incompleteCnt!=0) {
			me.setStatus("INCOMPLETE");
			System.out.println("Not Completed");

		}
		else if (failCnt == 0 && incompleteCnt==0) {
			me.setStatus("PASS");
			System.out.println("Passed");

		} 
		else {
			me.setStatus("FAIL");
			System.out.println("Failed");
		}
		
	/*	try {
			fnALMUpdate(me, testName); 
		} catch (Exception e) {
			System.out.println("ALM Update Failed. Check credentials and ensure proper connectivity");
			DriverScript.log.error("ALM Update Failed. Check credentials and ensure proper connectivity",e);
		}*/
		
		me.setDuration(strDiff);
		me.setHistory(strFinal);
		masterList.add(me);
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//me.setDuration(strDiff);
		///masterList.add(me);

	}
	
	
	//@SuppressWarnings("static-access")
	/*public void fnALMUpdate(MasterEntity me, String testName) throws Exception
	{
		try {
			ALMUtility almUtility ;
			//almUtility = new ALMUtility("Root\\SA", "SA_TS");
			almUtility = new ALMUtility(DriverScript.prop.getProperty("almTestSetPath"), DriverScript.prop.getProperty("almTestSet"));
			String almTestCase=testName;
			strFinal="";
			
			almUtility.openConnection(DriverScript.prop.getProperty("almUrl"), DriverScript.prop.getProperty("almDomain"), DriverScript.prop.getProperty("almProject"), DriverScript.prop.getProperty("almUser"), DriverScript.prop.getProperty("almPassword"));
			
			almTestCase="[1]" +almTestCase;
			if (incompleteCnt!=0) {
				almUtility.updateTestCaseStatusToALM(almTestCase, "Not Completed");
				System.out.println("ALM Not Completed");

			}
			else if (failCnt == 0 && incompleteCnt==0) {
				almUtility.updateTestCaseStatusToALM(almTestCase, "Passed");
				System.out.println("ALM Passed");

			} 
			else {
				almUtility.updateTestCaseStatusToALM(almTestCase, "Failed");
				System.out.println("ALM Failed");
			}
			
			//************
					try
					{
						//Collections.reverse(almUtility.PreviousResult);   //Now not needed, we are already getting Run Id in descending order
					} catch(Exception e) 
					{
						
					}
					
					int PreviousResultCount = almUtility.PreviousResult.size();
					
					if(PreviousResultCount>5){
						PreviousResultCount=5;
					}
						
					
				//**************
					
					System.out.println("Size "+PreviousResultCount);
					
					if(PreviousResultCount!=0)
					{
						for(int i=0;i<PreviousResultCount;i++)
						{
							String sALMStatus = almUtility.PreviousResult.get(i).toString().trim();
							if(sALMStatus.contains("P"))
							{
								strFinal = strFinal + ","+ "<font color=\"green\"><b>" + sALMStatus + "</b></font>";
							}
							else if(sALMStatus.contains("F"))
							{
								strFinal = strFinal + ","+ "<font color=\"red\"><b>" + sALMStatus + "</b></font>";
							}
							else if(sALMStatus.contains("I"))
							{
								strFinal = strFinal +  ","+ "<font color=\"orange\"><b>" + sALMStatus + "</b></font>";
							}
								
						}
						strFinal = strFinal.substring(1);
						System.out.println(strFinal);
					}
					
					//*****************************************************************************************
					almUtility.releaseALMConnection();
					
					almUtility.PreviousResult.clear();
		
			
		} catch (Exception e) {
			System.out.println("fnALMUpdate--------------Failed");
			DriverScript.log.error("fnALMUpdate--------------Failed",e);
		}	
	}
*/
	// @@@ Generate the index report HTML @@@ 

	public void repGenerateIndexReport(String indexHeaderHtmlContent) throws Exception 
	{
		String tblHdr = "";
		String content = "";
		String footerCntnt = "";
		String imgSrc = "";
//		System.out.println(" Inside generate index report");
//		System.out.println("***************************" + filePath);
		

		//tblHdr = "<BR><table width = 1030 class = \"teststeps\"><tr><td class = \"tsheader\" width = 20px>SNo</td><td class = \"tsheader\" width = 250px>Test Case</td><td class = \"tsheader\" width = 250 px>Description</td><td class = \"tsheader\" width = 25px>Steps</td><td class = \"tsheader\" width = 40px>Passed</td><td class = \"tsheader\" width = 40px>Incomplete</td><td class = \"tsheader\" width = 40px>Failed</td><td class = \"tsheader\" width = 50px>Status</td><td class = \"tsheader\" width = 25px>Duration</td><td class = \"tsheader\" width = 60px>History</td></tr>";
		tblHdr = "<BR><table width = 900 class = \"teststeps\"><tr><td class = \"tsheader\" width = 20px>SNo</td><td class = \"tsheader\" width = 185px>Test Case</td><td class = \"tsheader\" width = 185 px>Description</td><td class = \"tsheader\" width = 25px>Steps</td><td class = \"tsheader\" width = 40px>Passed</td><td class = \"tsheader\" width = 40px>Incomplete</td><td class = \"tsheader\" width = 40px>Failed</td><td class = \"tsheader\" width = 50px>Status</td><td class = \"tsheader\" width = 25px>Duration</td><td class = \"tsheader\" width = 60px>History</td></tr>";
		for (int i = 0; i < masterList.size(); i++) 
		{
			testCnt++;
			if (masterList.get(i).getStatus().toString().trim()
					.equalsIgnoreCase("Pass")) {
				ttlPassCnt++;
				imgSrc = "../../ReportRef/Images/pass.gif";
			} 
			
			else if (masterList.get(i).getStatus().toString().trim()
					.equalsIgnoreCase("Incomplete")) {
				ttlIncompleteCnt++;
				imgSrc = "../../ReportRef/Images/Incomplete.gif";
			} 
			
			else {
				ttlFailCnt++;
				imgSrc = "../../ReportRef/Images/failed.gif";
			}
			
			
			content = content
					+ "\n\n<tr>\n<td class = \"tsind\" width = 20px>"
					+ masterList.get(i).getSlNo()
					+ "</td>\n<td class = \"tsgen\" width = 200px><a class = \"tcindex\" href = \""
					+ masterList.get(i).getTestCase() + ".html\">"
					+ masterList.get(i).getTestCase()
					+ "</a></td>\n<td class = \"tsgen\" width = 200px>"
					+ masterList.get(i).getTestDesc()
					+ "</td>\n<td class = \"tsind\" width = 25px>"
					+ masterList.get(i).getSteps()
					+ "</td>\n<td class = \"tsind\" width = 40px>"
					+ masterList.get(i).getPass()
					+ "</td>\n<td class = \"tsind\" width = 40px>"
					+ masterList.get(i).getIncomplete()
					+ "</td>\n<td class = \"tsind\" width = 40px>"
					+ masterList.get(i).getFail()
					+ "</td>\n<td class = \"tsind\" width = 50px><img src  =  '"
					+ imgSrc + "' width = \"20\" height = \"20\">"
					+ masterList.get(i).getStatus()
					+ "</td>\n<td class = \"tsind\" width = 40px>"
					+ masterList.get(i).getDuration() + "</td>"
					//+"\n<td  class=\"pfind\" width=40px>"+ masterList.get(i).getHistory() +"</td>\n</tr>\n\n";  
					+"\n<td  class=\"pfind\" width=40px>"+ "" +"</td>\n</tr>\n\n";
					//+ "<td class = \"tsind\" width = 60px>" + strFinal
					//+ "</td></tr>";
			
		}
		
		footerCntnt = repGenerateIndexFooter();
		

		//file = new File(filePath + "\\index.html");
		file = new File(filePath + "\\"+TestDriver.sIndexHTMLFileName);
//		System.out.println("file " + file);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		String output = indexHeaderHtmlContent
				+ "<body><table BORDER = 1 CELLPADDING = 4 CELLSPACING = 1 WIDTH = 100%>"
				+ tblHdr + content + "</table><BR><BR>" + footerCntnt
				+ "<BR><BR><BR><BR><BR><BR><BR><BR>"; // + resultCntnt;
		bw.write(output);
		bw.close();
		
	}
	

	
	public static void openHTMLreport() throws Exception
	{
		try {
			String url1 = "" + filePath + "\\"+TestDriver.sIndexHTMLFileName;
			File htmlFile = new File(url1);
			Desktop.getDesktop().browse(htmlFile.toURI());
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}

	public static String getCurrentTimeStamp(String format) {
	    SimpleDateFormat sdfDate = new SimpleDateFormat(format);  // "MM/dd/yyyy"
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
	
	// copy report folder from Local to share machine
	public void createTestResultsWithTimestamp(String filePath) 
	{
		System.out.println("Creating another copy of test results with timestamp...");
		try {

			String xCopyCommand = "xcopy /i  \"" + filePath + "\"" + " " + "\""	+ shareFolderPath + "\"";
			System.out.println(xCopyCommand);
			@SuppressWarnings("unused")
			Process process = Runtime.getRuntime().exec(xCopyCommand); 
			
		} catch (IOException ioe) {
			System.out.println("Error occurred while making a copy of test results with timestamp::: "+ ioe.getMessage());
		} 
		System.out.println("END - Creating another copy of test results with timestamp...");
		sHtmlLink = shareFolderPath + "\\" + TestDriver.sIndexHTMLFileName + ";"; // link to index.html in share machine
	}
	
	
	
} // end of class