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

package org.dmfs.httpclient.entities;

import java.io.IOException;
import java.io.OutputStream;

import org.dmfs.httpclient.HttpRequestEntity;
import org.dmfs.httpclient.types.MediaType;


/**
 * An empty {@link HttpRequestEntity}. This entity doesn't have any {@link MediaType}. This is typically used for requests that don't send a message body.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class EmptyHttpRequestEntity implements HttpRequestEntity
{
	public final static HttpRequestEntity INSTANCE = new EmptyHttpRequestEntity();


	/**
	 * Private constructor, there is no need to instantiate this class another time.
	 */
	private EmptyHttpRequestEntity()
	{
	}


	@Override
	public MediaType contentType()
	{
		return null;
	}


	@Override
	public long contentLength() throws IOException
	{
		return -1;
	}


	@Override
	public void writeContent(OutputStream out) throws IOException
	{
	}

}
