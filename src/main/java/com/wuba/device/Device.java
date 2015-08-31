package com.wuba.device;

import java.awt.image.BufferedImage;

/**
 * 测试设备的接口，抽象公共部分
 * @author vigoss
 *
 */
public interface Device {
	
	String getPlatform();

	String getDeviceId();

	String getDeviceName();

	String getOsVersion();

	String getAppId();
	
	void insertLogStartFlag();
	
	void insertLogStopFlag();
	
	void instertImgCheckPoint(String imageFullName);
	
	int[] getScreenSize();

//	void clickOnScreen(int x, int y);

//	void dragFromToScreen(int startX, int startY, int endX, int endY);

	BufferedImage takeScreenShot();

	public abstract void unlock();

}
