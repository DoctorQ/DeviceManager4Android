/*
 * Copyright (C) 2010 The Android Open Source Project
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;

/**
 * Container for for device selection criteria.
 */
public class DeviceSelectionOptions implements IDeviceSelection {
	private static Logger LOG = Logger.getLogger("DeviceSelectionOptions");

	private static final String LOG_TAG = "DeviceSelectionOptions";

	private Collection<String> mSerials = new ArrayList<String>();

	private Collection<String> mExcludeSerials = new ArrayList<String>();

	private Collection<String> mProductTypes = new ArrayList<String>();

	private Collection<String> mPropertyStrings = new ArrayList<String>();

	private boolean mEmulatorRequested = false;

	private boolean mDeviceRequested = false;

	private boolean mStubEmulatorRequested = false;

	private boolean mNullDeviceRequested = false;

	private Integer mMinBattery = null;

	private Integer mMaxBattery = null;

	private boolean mRequireBatteryCheck = false;

	// If we have tried to fetch the environment variable ANDROID_SERIAL before.
	private boolean mFetchedEnvVariable = false;

	private static final String VARIANT_SEPARATOR = ":";

	public static final String DEVICE_PRODUCT_PROPERTY = "ro.hardware";
	public static final String DEVICE_VARIANT_PROPERTY = "ro.product.device";

	/**
	 * Add a serial number to the device selection options.
	 *
	 * @param serialNumber
	 */
	public void addSerial(String serialNumber) {
		mSerials.add(serialNumber);
	}

	/**
	 * {@inheritDoc}
	 */

	public void setSerial(String... serialNumber) {
		mSerials.clear();
		mSerials.addAll(Arrays.asList(serialNumber));
	}

	/**
	 * Add a serial number to exclusion list.
	 *
	 * @param serialNumber
	 */
	public void addExcludeSerial(String serialNumber) {
		mExcludeSerials.add(serialNumber);
	}

	/**
	 * Add a product type to the device selection options.
	 *
	 * @param productType
	 */
	public void addProductType(String productType) {
		mProductTypes.add(productType);
	}

	/**
	 * Add a property criteria to the device selection options
	 *
	 * @param propertyKeyValue
	 *            a property to match. Expected format propertykey=propertyvalue
	 */
	public void addProperty(String propertyKeyValue) {
		mPropertyStrings.add(propertyKeyValue);
	}

	/**
	 * {@inheritDoc}
	 */

	public Collection<String> getSerials() {
		// If no serial was explicitly set, use the environment variable
		// ANDROID_SERIAL.
		if (mSerials.isEmpty() && !mFetchedEnvVariable) {
			String env_serial = fetchEnvironmentVariable("ANDROID_SERIAL");
			if (env_serial != null) {
				mSerials.add(env_serial);
			}
			mFetchedEnvVariable = true;
		}
		return copyCollection(mSerials);
	}

	/**
	 * {@inheritDoc}
	 */

	public Collection<String> getExcludeSerials() {
		return copyCollection(mExcludeSerials);
	}

	/**
	 * {@inheritDoc}
	 */

	public Collection<String> getProductTypes() {
		return copyCollection(mProductTypes);
	}

	/**
	 * {@inheritDoc}
	 */

	public boolean deviceRequested() {
		return mDeviceRequested;
	}

	/**
	 * {@inheritDoc}
	 */

	public boolean emulatorRequested() {
		return mEmulatorRequested;
	}

	/**
	 * {@inheritDoc}
	 */

	public boolean stubEmulatorRequested() {
		return mStubEmulatorRequested;
	}

	/**
	 * {@inheritDoc}
	 */

	public boolean nullDeviceRequested() {
		return mNullDeviceRequested;
	}

	/**
	 * Sets the emulator requested flag
	 */
	public void setEmulatorRequested(boolean emulatorRequested) {
		mEmulatorRequested = emulatorRequested;
	}

	/**
	 * Sets the stub emulator requested flag
	 */
	public void setStubEmulatorRequested(boolean stubEmulatorRequested) {
		mStubEmulatorRequested = stubEmulatorRequested;
	}

	/**
	 * Sets the emulator requested flag
	 */
	public void setDeviceRequested(boolean deviceRequested) {
		mDeviceRequested = deviceRequested;
	}

	/**
	 * Sets the null device requested flag
	 */
	public void setNullDeviceRequested(boolean nullDeviceRequested) {
		mNullDeviceRequested = nullDeviceRequested;
	}

	/**
	 * Sets the minimum battery level
	 */
	public void setMinBatteryLevel(Integer minBattery) {
		mMinBattery = minBattery;
	}

	/**
	 * Gets the requested minimum battery level
	 */
	public Integer getMinBatteryLevel() {
		return mMinBattery;
	}

	/**
	 * Sets the maximum battery level
	 */
	public void setMaxBatteryLevel(Integer maxBattery) {
		mMaxBattery = maxBattery;
	}

	/**
	 * Gets the requested maximum battery level
	 */
	public Integer getMaxBatteryLevel() {
		return mMaxBattery;
	}

	/**
	 * Sets whether battery check is required for devices with unknown battery
	 * level
	 */
	public void setRequireBatteryCheck(boolean requireCheck) {
		mRequireBatteryCheck = requireCheck;
	}

	/**
	 * Gets whether battery check is required for devices with unknown battery
	 * level
	 */
	public boolean getRequireBatteryCheck() {
		return mRequireBatteryCheck;
	}

	/**
	 * {@inheritDoc}
	 */

	public Map<String, String> getProperties() {
		Map<String, String> propertyMap = new HashMap<String, String>(
				mPropertyStrings.size());
		for (String propertyKeyValue : mPropertyStrings) {
			String[] keyValuePair = propertyKeyValue.split("=");
			if (keyValuePair.length == 2) {
				propertyMap.put(keyValuePair[0], keyValuePair[1]);
			} else {
				Log.e(LOG_TAG, String.format(
						"Unrecognized property key value pair: '%s'",
						propertyKeyValue));
			}
		}
		return propertyMap;
	}

	private Collection<String> copyCollection(Collection<String> original) {
		Collection<String> listCopy = new ArrayList<String>(original.size());
		listCopy.addAll(original);
		return listCopy;
	}

	/**
	 * Helper function used to fetch environment variable. It is essentially a
	 * wrapper around {@link System#getenv(String)} This is done for unit
	 * testing purposes.
	 *
	 * @param name
	 *            the environment variable to fetch.
	 * @return a {@link String} value of the environment variable or null if not
	 *         available.
	 */
	String fetchEnvironmentVariable(String name) {
		return System.getenv(name);
	}

	/**
	 * @return <code>true</code> if the given {@link IDevice} is a match for the
	 *         provided options. <code>false</code> otherwise
	 */

	public boolean matches(IDevice device) {
		Collection<String> serials = getSerials();
		Collection<String> excludeSerials = getExcludeSerials();
		Map<String, Collection<String>> productVariants = splitOnVariant(getProductTypes());
		Collection<String> productTypes = productVariants.keySet();
		Map<String, String> properties = getProperties();

		if (!serials.isEmpty() && !serials.contains(device.getSerialNumber())) {
			return false;
		}
		if (excludeSerials.contains(device.getSerialNumber())) {
			return false;
		}
		if (!productTypes.isEmpty()) {
			String productType = getDeviceProductType(device);
			if (productTypes.contains(productType)) {
				// check variant
				String productVariant = getDeviceProductVariant(device);
				Collection<String> variants = productVariants.get(productType);
				if (variants != null && !variants.contains(productVariant)) {
					return false;
				}
			} else {
				// no product type matches; bye-bye
				return false;
			}
		}
		for (Map.Entry<String, String> propEntry : properties.entrySet()) {
			if (!propEntry.getValue().equals(
					device.getProperty(propEntry.getKey()))) {
				return false;
			}
		}
		if ((emulatorRequested() || stubEmulatorRequested())
				&& !device.isEmulator()) {
			return false;
		}
		if (deviceRequested() && device.isEmulator()) {
			return false;
		}
		if (device.isEmulator() && (device instanceof StubDevice)
				&& !stubEmulatorRequested()) {
			// only allocate the stub emulator if requested
			return false;
		}
		if (nullDeviceRequested() != (device instanceof NullDevice)) {
			return false;
		}
		if ((mMinBattery != null) || (mMaxBattery != null)) {
			Integer deviceBattery = getBatteryLevel(device);
			if (mRequireBatteryCheck && (deviceBattery == null)) {
				// Couldn't determine battery level when that check is required;
				// reject device
				return false;
			}
			if (isLessAndNotNull(deviceBattery, mMinBattery)) {
				// deviceBattery < mMinBattery
				return false;
			}
			if (isLessEqAndNotNull(mMaxBattery, deviceBattery)) {
				// mMaxBattery <= deviceBattery
				return false;
			}
		}

		return true;
	}

	/** Determine if x is less-than y, given that both are non-Null */
	private static boolean isLessAndNotNull(Integer x, Integer y) {
		if ((x == null) || (y == null)) {
			return false;
		}
		return x < y;
	}

	/** Determine if x is less-than y, given that both are non-Null */
	private static boolean isLessEqAndNotNull(Integer x, Integer y) {
		if ((x == null) || (y == null)) {
			return false;
		}
		return x <= y;
	}

	private Map<String, Collection<String>> splitOnVariant(
			Collection<String> products) {
		// FIXME: we should validate all provided device selection options once,
		// on the first
		// FIXME: call to #matches
		Map<String, Collection<String>> splitProducts = new HashMap<String, Collection<String>>(
				products.size());
		// FIXME: cache this
		for (String prod : products) {
			String[] parts = prod.split(VARIANT_SEPARATOR);
			if (parts.length == 1) {
				splitProducts.put(parts[0], null);
			} else if (parts.length == 2) {
				// A variant was specified as product:variant
				Collection<String> variants = splitProducts.get(parts[0]);
				if (variants == null) {
					variants = new HashSet<String>();
					splitProducts.put(parts[0], variants);
				}
				variants.add(parts[1]);
			} else {
				throw new IllegalArgumentException(
						String.format(
								"The product type filter \"%s\" "
										+ "is invalid.  It must contain 0 or 1 '%s' characters, not %d.",
								prod, VARIANT_SEPARATOR, parts.length));
			}
		}

		return splitProducts;
	}

	public String getDeviceProductType(IDevice device) {
		return getProperty(device, DEVICE_PRODUCT_PROPERTY);
	}

	private String getProperty(IDevice device, String propName) {
		try {
			return device.getPropertyCacheOrSync(propName);
		} catch (TimeoutException e) {
			handlePropException(device, e);
		} catch (AdbCommandRejectedException e) {
			handlePropException(device, e);
		} catch (IOException e) {
			handlePropException(device, e);
		} catch (ShellCommandUnresponsiveException e) {
			handlePropException(device, e);
		}
		return null;
	}

	private void handlePropException(IDevice device, Exception e) {
		LOG.warn(String.format("Failed to query device property for %s: %s",
				device.getSerialNumber(), e.toString()));
	}

	public String getDeviceProductVariant(IDevice device) {
		return getProperty(device, DEVICE_VARIANT_PROPERTY);
	}

	public Integer getBatteryLevel(IDevice device) {
		try {
			return device.getBatteryLevel();
		} catch (TimeoutException e) {
			handleBatteryException(device, e);
		} catch (AdbCommandRejectedException e) {
			handleBatteryException(device, e);
		} catch (IOException e) {
			handleBatteryException(device, e);
		} catch (ShellCommandUnresponsiveException e) {
			handleBatteryException(device, e);
		}
		return null;
	}

	private void handleBatteryException(IDevice device, Exception e) {
		LOG.warn(String.format("Failed to query battery level for %s: %s",
				device.getSerialNumber(), e.toString()));
	}
}
