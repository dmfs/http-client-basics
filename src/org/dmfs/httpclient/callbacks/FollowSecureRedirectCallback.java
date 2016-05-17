/*
 * Copyright (C) 2016 Marten Gajda <marten@dmfs.org>
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmfs.httpclient.callbacks;

import java.net.URI;

import org.dmfs.httpclient.HttpStatus;
import org.dmfs.httpclient.OnRedirectCallback;


/**
 * An {@link OnRedirectCallback} that follows only secure redirects. A redirect is considered to be secure if origin and target are secure (i.e. if their URL
 * scheme equals "https"). To get a static instance call {@link #getInstance()}.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class FollowSecureRedirectCallback implements OnRedirectCallback
{
	private final static class SingletonHolder
	{
		/**
		 * A static instance of a {@link FollowSecureRedirectCallback}. It will be initialized when {@link FollowSecureRedirectCallback#getInstance()} is called
		 * the first time.
		 */
		final static FollowSecureRedirectCallback INSTANCE = new FollowSecureRedirectCallback();
	}

	private final static String SECURE_SCHEME = "https";


	/**
	 * Returns an instance of {@link FollowSecureRedirectCallback}.
	 * 
	 * @return A static instance of {@link FollowSecureRedirectCallback}.
	 */
	public static FollowSecureRedirectCallback getInstance()
	{
		return SingletonHolder.INSTANCE;
	}


	/**
	 * Don't instantiate this, use {@link #getInstance()}. Instead.
	 */
	private FollowSecureRedirectCallback()
	{
	}


	@Override
	public boolean followRedirect(final HttpStatus status, final URI redirectingLocation, final URI newLocation)
	{
		return SECURE_SCHEME.equals(newLocation.getScheme()) && SECURE_SCHEME.equals(redirectingLocation.getScheme());
	}
}
