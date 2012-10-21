package adsim;

public class Util {
	public static String getCodeInfo(){
		StackTraceElement codeInfo = Thread.currentThread().getStackTrace()[2]; // [1] is current frame, [2] is caller's frame
		return codeInfo.toString();
	}
}
