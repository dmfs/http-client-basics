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

package org.dmfs.httpessentials.responsehandlers;

import java.io.IOException;
import java.io.InputStream;

import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.client.HttpResponseEntity;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;


/**
 * A simple {@link HttpResponseHandler} that always return the same response. It ensures that the response is fully read and properly closed.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 * 
 * @param <T>
 *            The type of the result.
 */
public final class TrivialResponseHandler<T> implements HttpResponseHandler<T>
{
	private final static int BUFFER_SIZE = 16 * 1024;

	private final T mResult;


	/**
	 * Creates a {@link TrivialResponseHandler} that returns the given result.
	 */
	public TrivialResponseHandler(T result)
	{
		mResult = result;
	}


	@Override
	public T handleResponse(HttpResponse response) throws IOException, ProtocolError, ProtocolException
	{
		HttpResponseEntity entity = response.responseEntity();
		InputStream in = entity.contentStream();
		try
		{
			// first try to skip as much content as possible, this might be more efficient than reading each byte
			in.skip(Long.MAX_VALUE);
		}
		catch (IOException e)
		{
			/*
			 * According to Javadoc, skip might throw if the underlying stream doesn't support seek. Since we just want to consume the content, no matter how,
			 * we ignore this and move on to reading each byte into a buffer.
			 */
		}
		try
		{
			final byte[] buffer = new byte[BUFFER_SIZE];
			while (in.read(buffer) >= 0)
			{
				// discard the content
			}

			return mResult;
		}
		finally
		{
			in.close();
		}
	}
}
