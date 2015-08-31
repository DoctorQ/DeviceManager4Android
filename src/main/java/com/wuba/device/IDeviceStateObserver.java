/**
 * 
 */
package com.wuba.device;

import com.wuba.device.IDeviceMonitor.DeviceLister;

/**
 * @author hui.qian qianhui@58.com
 * @date 2015年8月27日 下午5:45:20
 */
public interface IDeviceStateObserver {

	public void updateDeviceDevice(DeviceLister lister);

}
