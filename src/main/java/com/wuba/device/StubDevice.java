/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wuba.device;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.Client;
import com.android.ddmlib.FileListingService;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.InstallException;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.ScreenRecorderOptions;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.SyncService;
import com.android.ddmlib.TimeoutException;
import com.android.ddmlib.log.LogReceiver;

/**
 * Stub placeholder implementation of a {@link IDevice}.
 */
class StubDevice implements IDevice {

    private final String mSerial;
    private final boolean mIsEmulator;

    StubDevice(String serial) {
        this(serial, false);
    }

    StubDevice(String serial, boolean isEmulator) {
        mSerial = serial;
        mIsEmulator = isEmulator;
    }

    /**
     * {@inheritDoc}
     */
    
    public void createForward(int localPort, int remotePort) throws TimeoutException,
            AdbCommandRejectedException, IOException {
        throw new IOException("stub");
    }

    /**
     * {@inheritDoc}
     */
    
    public void executeShellCommand(String command, IShellOutputReceiver receiver)
            throws TimeoutException, AdbCommandRejectedException,
            ShellCommandUnresponsiveException, IOException {
        throw new IOException("stub");
    }

    /**
     * {@inheritDoc}
     */
    
    public void executeShellCommand(String command, IShellOutputReceiver receiver,
            int maxTimeToOutputResponse) throws TimeoutException, AdbCommandRejectedException,
            ShellCommandUnresponsiveException, IOException {
        throw new IOException("stub");
    }

    /**
     * {@inheritDoc}
     */
    
    public String getAvdName() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    
    public Client getClient(String applicationName) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    
    public String getClientName(int pid) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    
    public Client[] getClients() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    
    public FileListingService getFileListingService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    
    public String getMountPoint(String name) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    
    public Map<String, String> getProperties() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    
    public String getProperty(String name) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    
    public int getPropertyCount() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    
    public RawImage getScreenshot() throws TimeoutException, AdbCommandRejectedException,
            IOException {
        throw new IOException("stub");
    }

    /**
     * {@inheritDoc}
     */
    
    public String getSerialNumber() {
        return mSerial;
    }

    /**
     * {@inheritDoc}
     */
    
    public DeviceState getState() {
        return DeviceState.OFFLINE;
    }

    /**
     * {@inheritDoc}
     */
    
    public SyncService getSyncService() throws TimeoutException, AdbCommandRejectedException,
            IOException {
        throw new IOException("stub");
    }

    /**
     * {@inheritDoc}
     */
    
    public boolean hasClients() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    
    public String installPackage(String packageFilePath, boolean reinstall, String... extraArgs)
            throws InstallException {
        throw new InstallException(new IOException("stub"));
    }

    /**
     * {@inheritDoc}
     */
    
    public String installRemotePackage(String remoteFilePath, boolean reinstall,
            String... extraArgs) throws InstallException {
        throw new InstallException(new IOException("stub"));
    }

    /**
     * {@inheritDoc}
     */
    
    public boolean isBootLoader() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    
    public boolean isEmulator() {
        return mIsEmulator;
    }

    /**
     * {@inheritDoc}
     */
    
    public boolean isOffline() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    
    public boolean isOnline() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    
    public void reboot(String into) throws TimeoutException, AdbCommandRejectedException,
            IOException {
        throw new IOException("stub");
    }

    /**
     * {@inheritDoc}
     */
    
    public void removeForward(int localPort, int remotePort) throws TimeoutException,
            AdbCommandRejectedException, IOException {
        throw new IOException("stub");
    }

    /**
     * {@inheritDoc}
     */
    
    public void removeRemotePackage(String remoteFilePath) throws InstallException {
        throw new InstallException(new IOException("stub"));
    }

    /**
     * {@inheritDoc}
     */
    
    public void runEventLogService(LogReceiver receiver) throws TimeoutException,
            AdbCommandRejectedException, IOException {
        throw new IOException("stub");
    }

    /**
     * {@inheritDoc}
     */
    
    public void runLogService(String logname, LogReceiver receiver) throws TimeoutException,
            AdbCommandRejectedException, IOException {
        throw new IOException("stub");
    }

    /**
     * {@inheritDoc}
     */
    
    public String syncPackageToDevice(String localFilePath) throws TimeoutException,
            AdbCommandRejectedException, IOException, SyncException {
        throw new IOException("stub");
    }

    /**
     * {@inheritDoc}
     */
    
    public String uninstallPackage(String packageName) throws InstallException {
        throw new InstallException(new IOException("stub"));
    }

    /**
     * {@inheritDoc}
     */
    
    public void pushFile(String local, String remote) throws IOException,
            AdbCommandRejectedException, TimeoutException, SyncException {
        // ignore
    }

    /**
     * {@inheritDoc}
     */
    
    public void pullFile(String remote, String local) throws IOException,
            AdbCommandRejectedException, TimeoutException, SyncException {
        // ignore
    }

    /**
     * {@inheritDoc}
     */
    
    public String getPropertySync(String name) throws TimeoutException,
            AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    
    public boolean arePropertiesSet() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    
    public String getPropertyCacheOrSync(String name) throws TimeoutException,
            AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    
    public Integer getBatteryLevel() throws TimeoutException, AdbCommandRejectedException,
            IOException, ShellCommandUnresponsiveException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    
    public Integer getBatteryLevel(long freshnessMs) throws TimeoutException,
            AdbCommandRejectedException, IOException, ShellCommandUnresponsiveException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    
    public void createForward(int localPort, String remoteSocketName,
            DeviceUnixSocketNamespace namespace) throws TimeoutException,
            AdbCommandRejectedException, IOException {
        // ignore
    }

    /**
     * {@inheritDoc}
     */
    
    public void removeForward(int localPort, String remoteSocketName,
            DeviceUnixSocketNamespace namespace) throws TimeoutException,
            AdbCommandRejectedException, IOException {
        // ignore
    }

    /**
     * {@inheritDoc}
     */
    
    public String getName() {
        return null;
    }

	/* (non-Javadoc)
	 * @see com.android.ddmlib.IShellEnabledDevice#executeShellCommand(java.lang.String, com.android.ddmlib.IShellOutputReceiver, long, java.util.concurrent.TimeUnit)
	 */
	public void executeShellCommand(String command,
			IShellOutputReceiver receiver, long maxTimeToOutputResponse,
			TimeUnit maxTimeUnits) throws TimeoutException,
			AdbCommandRejectedException, ShellCommandUnresponsiveException,
			IOException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.android.ddmlib.IShellEnabledDevice#getSystemProperty(java.lang.String)
	 */
	public Future<String> getSystemProperty(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.android.ddmlib.IDevice#supportsFeature(com.android.ddmlib.IDevice.Feature)
	 */
	public boolean supportsFeature(Feature feature) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.android.ddmlib.IDevice#supportsFeature(com.android.ddmlib.IDevice.HardwareFeature)
	 */
	public boolean supportsFeature(HardwareFeature feature) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.android.ddmlib.IDevice#getScreenshot(long, java.util.concurrent.TimeUnit)
	 */
	public RawImage getScreenshot(long timeout, TimeUnit unit)
			throws TimeoutException, AdbCommandRejectedException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.android.ddmlib.IDevice#startScreenRecorder(java.lang.String, com.android.ddmlib.ScreenRecorderOptions, com.android.ddmlib.IShellOutputReceiver)
	 */
	public void startScreenRecorder(String remoteFilePath,
			ScreenRecorderOptions options, IShellOutputReceiver receiver)
			throws TimeoutException, AdbCommandRejectedException, IOException,
			ShellCommandUnresponsiveException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.android.ddmlib.IDevice#installPackages(java.util.List, int, boolean, java.lang.String[])
	 */
	public void installPackages(List<String> apkFilePaths, int timeOutInMs,
			boolean reinstall, String... extraArgs) throws InstallException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.android.ddmlib.IDevice#getBattery()
	 */
	public Future<Integer> getBattery() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.android.ddmlib.IDevice#getBattery(long, java.util.concurrent.TimeUnit)
	 */
	public Future<Integer> getBattery(long freshnessTime, TimeUnit timeUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.android.ddmlib.IDevice#getAbis()
	 */
	public List<String> getAbis() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.android.ddmlib.IDevice#getDensity()
	 */
	public int getDensity() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.android.ddmlib.IDevice#getLanguage()
	 */
	public String getLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.android.ddmlib.IDevice#getRegion()
	 */
	public String getRegion() {
		// TODO Auto-generated method stub
		return null;
	}
}
