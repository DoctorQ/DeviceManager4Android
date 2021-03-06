/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache
 * License, Version 2.0 (the "License");
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
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.android.ddmlib.IShellOutputReceiver;
import com.wuba.utils.ByteArrayInputStreamSource;
import com.wuba.utils.FixedByteArrayOutputStream;
import com.wuba.utils.InputStreamSource;
import com.wuba.utils.SizeLimitedOutputStream;
import com.wuba.utils.SnapshotInputStreamSource;
import com.wuba.utils.StreamUtil;

/**
 * A class designed to help run long running commands collect output.
 * <p>
 * The maximum size of the tmp file is limited to approximately {@code maxFileSize}.
 * To prevent data loss when the limit has been reached, this file keeps set of tmp host
 * files.
 * </p>
 */
public class LargeOutputReceiver implements IShellOutputReceiver {
	private static Logger LOG = Logger.getLogger("LargeOutputReceiver.class");

    private String mSerialNumber;
    private String mDescriptor;

    private boolean mIsCancelled = false;
    private SizeLimitedOutputStream mOutStream;
    private long mMaxDataSize;

    /**
     * Creates a {@link LargeOutputReceiver}.
     *
     * @param descriptor the descriptor of the command to run. For logging only.
     * @param serialNumber the serial number of the device. For logging only.
     * @param maxDataSize the approximate max amount of data to keep.
     */
    public LargeOutputReceiver(String descriptor, String serialNumber, long maxDataSize) {
        mDescriptor = descriptor;
        mSerialNumber = serialNumber;
        mMaxDataSize = maxDataSize;
        mOutStream = createOutputStream();
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void addOutput(byte[] data, int offset, int length) {
        if (mIsCancelled || mOutStream == null) {
            return;
        }
        try {
            mOutStream.write(data, offset, length);
        } catch (IOException e) {
        	LOG.warn(String.format("failed to write %s data for %s.", mDescriptor, mSerialNumber));
        }
    }

    /**
     * Gets the collected output as a {@link InputStreamSource}.
     *
     * @return The collected output from the command.
     */
    public synchronized InputStreamSource getData() {
        if (mOutStream != null) {
            try {
                return new SnapshotInputStreamSource(mOutStream.getData());
            } catch (IOException e) {
            }
        }

        // return an empty InputStreamSource
        return new ByteArrayInputStreamSource(new byte[0]);
    }

    /**
     * Gets the last <var>maxBytes</var> of collected output as a {@link InputStreamSource}.
     *
     * @param maxBytes the maximum amount of data to return. Should be an amount that can
     *            comfortably fit in memory
     * @return The collected output from the command, stored in memory
     */
    public synchronized InputStreamSource getData(final int maxBytes) {
        if (mOutStream != null) {
            InputStream fullStream = null;
            try {
                fullStream = mOutStream.getData();
                final FixedByteArrayOutputStream os = new FixedByteArrayOutputStream(maxBytes);
                StreamUtil.copyStreams(fullStream, os);
                return new InputStreamSource() {

                    public InputStream createInputStream()  {
                        return os.getData();
                    }

                    public void cancel() {
                        // ignore, nothing to do
                    }

                    public long size() {
                        return os.size();
                    }
                };
            } catch (IOException e) {
            	LOG.error(String.format("failed to get %s data for %s.", mDescriptor, mSerialNumber));
            	LOG.warn(e);
            } finally {
                StreamUtil.close(fullStream);
            }
        }

        // return an empty InputStreamSource
        return new ByteArrayInputStreamSource(new byte[0]);
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void flush() {
        if (mOutStream == null) {
            return;
        }
        mOutStream.flush();
    }

    /**
     * Delete currently accumulated data, and then re-create a new file.
     */
    public synchronized void clear() {
        delete();
        mOutStream = createOutputStream();
    }

    private SizeLimitedOutputStream createOutputStream() {
        return new SizeLimitedOutputStream(mMaxDataSize, String.format("%s_%s",
                getDescriptor(), mSerialNumber), ".txt");
    }

    /**
     * Cancels the command.
     */
    public synchronized void cancel() {
        mIsCancelled = true;
    }

    /**
     * Delete all accumulated data.
     */
    public void delete() {
        mOutStream.delete();
        mOutStream = null;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized boolean isCancelled() {
        return mIsCancelled;
    }

    /**
     * Get the descriptor.
     * <p>
     * Exposed for unit testing.
     * </p>
     */
    String getDescriptor() {
        return mDescriptor;
    }
}