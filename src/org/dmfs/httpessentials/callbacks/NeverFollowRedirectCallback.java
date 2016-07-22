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

package org.dmfs.httpessentials.callbacks;

import java.net.URI;

import org.dmfs.httpessentials.HttpStatus;
import org.dmfs.httpessentials.client.OnRedirectCallback;


/**
 * A simple {@link OnRedirectCallback} implementation that doesn't follow any redirects. To get an instance use {@link #getInstance()}.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class NeverFollowRedirectCallback implements OnRedirectCallback
{
	private final static class SingletonHolder
	{
		/**
		 * A static instance of the {@link NeverFollowRedirectCallback}. It will be initialized when {@link NeverFollowRedirectCallback#getInstance()} is called
		 * the first time.
		 */
		final static NeverFollowRedirectCallback INSTANCE = new NeverFollowRedirectCallback();
	}


	/**
	 * Returns an instance of {@link NeverFollowRedirectCallback}.
	 * 
	 * @return A static instance of {@link NeverFollowRedirectCallback}.
	 */
	public static NeverFollowRedirectCallback getInstance()
	{
		return SingletonHolder.INSTANCE;
	}


	/**
	 * Don't instantiate this, use {@link #getInstance()}. Instead.
	 */
	private NeverFollowRedirectCallback()
	{
	}


	@Override
	public boolean followRedirect(final HttpStatus status, final URI redirectingLocation, final URI newLocation)
	{
		return false;
	}
}
