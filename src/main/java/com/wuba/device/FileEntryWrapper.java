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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.ddmlib.FileListingService;
import com.android.ddmlib.FileListingService.FileEntry;
import com.wuba.utils.IFileEntry;

/**
 * Implementation of a {@link IFileEntry}.
 */
class FileEntryWrapper implements IFileEntry {

    private final TestDevice mTestDevice;
    private final FileListingService.FileEntry mFileEntry;
    private Map<String, IFileEntry> mChildMap = null;

    /**
     * Creates a {@link FileEntryWrapper}.
     *
     * @param testDevice the {@link TestDevice} to use
     * @param entry the corresponding {@link FileEntry} to wrap
     */
    FileEntryWrapper(TestDevice testDevice, FileEntry entry) {
        mTestDevice = testDevice;
        mFileEntry = entry;
    }

    /**
     * {@inheritDoc}
     */
   
    public String getFullEscapedPath() {
        return mFileEntry.getFullEscapedPath();
    }

    /**
     * {@inheritDoc}
     */
   
    public String getFullPath() {
        return mFileEntry.getFullPath();
    }

    /**
     * {@inheritDoc}
     */
   
    public IFileEntry findChild(String name) throws DeviceNotAvailableException {
        if (mChildMap == null || !mChildMap.containsKey(name)) {
            mChildMap = buildChildrenMap();
        }
        return mChildMap.get(name);
    }

    /**
     * {@inheritDoc}
     */
   
    public boolean isDirectory() {
        return mFileEntry.isDirectory();
    }

    /**
     * {@inheritDoc}
     */
   
    public boolean isAppFileName() {
        return mFileEntry.isAppFileName();
    }

    /**
     * {@inheritDoc}
     */
   
    public String getName() {
        return mFileEntry.getName();
    }

    /**
     * {@inheritDoc}
     */
   
    public Collection<IFileEntry> getChildren(boolean useCache) throws DeviceNotAvailableException {
        if (!useCache || mChildMap == null) {
            mChildMap = buildChildrenMap();
        }
        return mChildMap.values();
    }

    private Map<String, IFileEntry> buildChildrenMap() throws DeviceNotAvailableException {
        FileEntry[] childEntrys = mTestDevice.getFileChildren(mFileEntry);
        Map<String, IFileEntry> childMap = new HashMap<String, IFileEntry>(childEntrys.length);
        for (FileEntry entry : childEntrys) {
            childMap.put(entry.getName(), new FileEntryWrapper(mTestDevice, entry));
        }
        return childMap;
    }

    /**
     * Helper method that tries to a find the descendant of a {@link IFileEntry}
     *
     * @param fileEntry the starting point
     * @param childSegments the relative path of the {@link IFileEntry} to find
     * @return the {@link IFileEntry}, or <code>null</code> if it cannot be found
     * @throws DeviceNotAvailableException
     */
    static IFileEntry getDescendant(IFileEntry fileEntry, List<String> childSegments)
            throws DeviceNotAvailableException {
        IFileEntry child = fileEntry;
        for (String childName: childSegments) {
            if (childName.length() > 0) {
                child = child.findChild(childName);
                if (child == null) {
                    return null;
                }
            }
        }
        return child;
    }

    /**
     * {@inheritDoc}
     */
   
    public FileEntry getFileEntry() {
        return mFileEntry;
    }

    /**
     * {@inheritDoc}
     */
   
    public String getTime() {
        return mFileEntry.getTime();
    }

    /**
     * {@inheritDoc}
     */
   
    public String getDate() {
        return mFileEntry.getDate();
    }

    /**
     * {@inheritDoc}
     */
   
    public String getPermissions() {
        return mFileEntry.getPermissions();
    }
}
