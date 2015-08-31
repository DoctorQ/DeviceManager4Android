/**
 * 
 */
package com.wuba.device;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import org.apache.log4j.Logger;

/**
 * @author hui.qian qianhui@58.com
 * @date 2015年8月27日 下午3:42:19
 */
public class DeviceMonitor implements IDeviceMonitor {

	private List<IDeviceStateObserver> observers = new ArrayList<IDeviceStateObserver>();
	private static Logger LOG = Logger.getLogger("DeviceMonitor.class");
	private DeviceLister lister;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wuba.device.IDeviceMonitor#run()
	 */
	public void run() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wuba.device.IDeviceMonitor#setDeviceLister(com.wuba.device.IDeviceMonitor
	 * .DeviceLister)
	 */
	public void setDeviceLister(DeviceLister lister) {
		this.lister = lister;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wuba.device.IDeviceMonitor#notifyDeviceStateChange()
	 */
	public void notifyDeviceStateChange() {
		// TODO Auto-generated method stub

		LOG.info("设备状态发生改变");
		for (IDeviceStateObserver observer : observers) {
			observer.updateDeviceDevice(lister);
		}
	}

	public void registerObserver(IDeviceStateObserver observer) {
		observers.add(observer);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wuba.device.IDeviceMonitor#removeObserver(com.wuba.device.
	 * IDeviceStateObserver)
	 */
	public void removeObserver(IDeviceStateObserver observer) {
		// TODO Auto-generated method stub
		int index = observers.indexOf(observer);
		if (index != -1) {
			observers.remove(observer);
		}

	}

}
