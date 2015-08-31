/**
 * 
 */
package com.wuba.device;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.wuba.device.IDeviceManager.FreeDeviceState;

/**
 * @author hui.qian qianhui@58.com
 * @date 2015年8月27日 下午3:51:48
 */
public class DeviceManagerTest {
	private static Logger LOG = Logger.getLogger("DeviceManager.class");
	private IDeviceManager deviceManager;

	@BeforeClass
	public void setUp() throws InterruptedException {
		deviceManager = DeviceManager.getInstance();
		deviceManager.init();
		Thread.sleep(3 * 1000);
	}

	@Test
	public void deviceStateTest() throws InterruptedException {

	}

	@Test
	public void allocateDeviceBySn() throws InterruptedException {
		ITestDevice device = deviceManager.forceAllocateDevice("ZX1G42C39V");

		if (device != null) {
			LOG.info(device.getDeviceState());
		}
		Thread.sleep(200 * 1000);
		deviceManager.freeDevice(device, FreeDeviceState.AVAILABLE);
	}

	@AfterClass
	public void tearDown() throws InterruptedException {
		deviceManager.terminate();
	}

}
