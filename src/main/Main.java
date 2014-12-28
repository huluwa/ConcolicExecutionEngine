package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import staticFamily.StaticApp;
import staticFamily.StaticClass;
import staticFamily.StaticMethod;
import tools.Adb;
import analysis.StaticInfo;
import concolic.Condition;
import concolic.Execution;
import concolic.ExecutionEngine;
import concolic.Operation;
import concolic.PathSummary;

public class Main {

	static StaticApp testApp;
	static Adb adb = new Adb();
	
	public static void main(String[] args) {
		
		String[] apps = {
/* 0 */		"C:/Users/Wenhao/Documents/juno_workspace/AndroidTest/bin/TheApp.apk",
				"C:/Users/Wenhao/Documents/juno_workspace/AndroidTest/bin/AndroidTest.apk",
				"/home/wenhaoc/AppStorage/Fast.apk",
				"/home/wenhaoc/AppStorage/APAC_engagement/backupHelper/backupHelper.apk",
				"/home/wenhaoc/AppStorage/APAC_engagement/Butane/Butane.apk",
/* 5 */		"/home/wenhaoc/AppStorage/APAC_engagement/CalcA/CalcA.apk",
				"/home/wenhaoc/AppStorage/APAC_engagement/KitteyKittey/KitteyKittey.apk",
				"/home/wenhaoc/AppStorage/net.mandaria.tippytipper.apk",
				"/home/wenhaoc/adt_workspace/TheApp/bin/TheApp.apk",
		
		};
		
		String apkPath = apps[0];
		
		testApp = StaticInfo.initAnalysis(apkPath, false);
		
		ExecutionEngine ee = new ExecutionEngine(testApp);
		
		testApp = ee.buildPathSummaries();
		
		testPathSummaries();

		
	}
	
	static void testPathSummaries() {
		System.out.println("-------------------- PS in StaticMethod");
		for (StaticClass c : testApp.getClasses())
			for (StaticMethod m : c.getMethods())
				if (m.getPathSummaries().size() > 0) {
					System.out.println("[Method]" + m.getSmaliSignature());
					System.out.println("[PS count]" + m.getPathSummaries().size());
				}
		System.out.println("\n------------------PS in StaticApp (count: " + testApp.getAllPathSummaries().size() + ")");
		ArrayList<String> mList = new ArrayList<String>();
		for (PathSummary ps : testApp.getAllPathSummaries())
			if (!mList.contains(ps.getMethodSignature())) {
				mList.add(ps.getMethodSignature());
				System.out.println("  " + ps.getMethodSignature());
			}
	}
	
	private void printOutPathSummary(PathSummary pS) {
		System.out.println("\n Execution Log: ");
		for (String s : pS.getExecutionLog())
			System.out.println("  " + s);
		System.out.println("\n Symbolic States: ");
		for (Operation o : pS.getSymbolicStates())
			System.out.println("  " + o.toString());
		System.out.println("\n PathCondition: ");
		for (Condition cond : pS.getPathCondition())
			System.out.println("  " + cond.toString());
		System.out.println("\n PathChoices: ");
		for (String pC : pS.getPathChoices())
			System.out.println("  " + pC);
		System.out.println("========================");
	}
	

}
