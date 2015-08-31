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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A File-backed InputStreamSource. Creates a snapshot of original
 * {@link InputStream} contents to ensure that {@link #createInputStream()} will
 * return identically-behaving {@link InputStream}s as required.
 */
public class SnapshotInputStreamSource implements InputStreamSource {
	private File mBackingFile;
	private boolean mIsCancelled = false;

	/**
	 * FIXME
	 */
	public SnapshotInputStreamSource(InputStream stream) {
		if (stream == null) {
			throw new NullPointerException();
		}

		try {
			mBackingFile = createBackingFile(stream);
		} catch (IOException e) {
			// Log an error and invalidate ourself
			cancel();
		}
	}

	/**
	 * Create the backing file and fill it with the contents of {@code stream}.
	 * <p />
	 * Exposed for unit testing
	 */
	File createBackingFile(InputStream stream) throws IOException {
		File backingFile = FileUtil.createTempFile(this.getClass()
				.getSimpleName() + "_", ".txt");
		FileUtil.writeToFile(stream, backingFile);
		return backingFile;
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized InputStream createInputStream() {
		if (mIsCancelled) {
			return null;
		}

		try {
			return new FileInputStream(mBackingFile);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized void cancel() {
		mIsCancelled = true;
		if (mBackingFile != null) {
			mBackingFile.delete();
			mBackingFile = null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public long size() {
		return mBackingFile.length();
	}
}