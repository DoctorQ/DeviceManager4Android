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

import java.util.Map;

import com.android.ddmlib.Log.LogLevel;
import com.android.ddmlib.testrunner.ITestRunListener;
import com.android.ddmlib.testrunner.TestIdentifier;

/**
 * Stub implementation of {@link ITestRunListener}
 */
public class StubTestRunListener implements ITestRunListener {

    /**
     * {@inheritDoc}
     */
    
    public void testEnded(TestIdentifier test, Map<String, String> testMetrics) {
        // ignore
    }

    /**
     * {@inheritDoc}
     */
    

    /**
     * {@inheritDoc}
     */
    
    public void testRunEnded(long elapsedTime, Map<String, String> runMetrics) {
        // ignore
    }

    /**
     * {@inheritDoc}
     */
    
    public void testRunFailed(String errorMessage) {
        // ignore
    }

    /**
     * {@inheritDoc}
     */
    
    public void testRunStarted(String runName, int testCount) {
        // ignore
    }

    /**
     * {@inheritDoc}
     */
    
    public void testRunStopped(long elapsedTime) {
        // ignore
    }

    /**
     * {@inheritDoc}
     */
    
    public void testStarted(TestIdentifier test) {
        // ignore
    }

	/* (non-Javadoc)
	 * @see com.android.ddmlib.testrunner.ITestRunListener#testFailed(com.android.ddmlib.testrunner.TestIdentifier, java.lang.String)
	 */
	public void testFailed(TestIdentifier test, String trace) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.android.ddmlib.testrunner.ITestRunListener#testAssumptionFailure(com.android.ddmlib.testrunner.TestIdentifier, java.lang.String)
	 */
	public void testAssumptionFailure(TestIdentifier test, String trace) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.android.ddmlib.testrunner.ITestRunListener#testIgnored(com.android.ddmlib.testrunner.TestIdentifier)
	 */
	public void testIgnored(TestIdentifier test) {
		// TODO Auto-generated method stub
		
	}
}
