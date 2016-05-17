package org.dmfs.httpclient.responsehandlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Random;

import org.dmfs.httpclient.HttpResponse;
import org.dmfs.httpclient.HttpResponseEntity;
import org.dmfs.httpclient.HttpStatus;
import org.dmfs.httpclient.exceptions.ProtocolError;
import org.dmfs.httpclient.exceptions.ProtocolException;
import org.dmfs.httpclient.headers.EmptyHeaders;
import org.dmfs.httpclient.headers.Headers;
import org.dmfs.httpclient.headers.HttpHeaders;
import org.dmfs.httpclient.headers.SingletonHeaders;
import org.dmfs.httpclient.types.MediaType;
import org.dmfs.httpclient.types.StructuredMediaType;
import org.junit.Test;


/**
 * Test {@link StringResponseHandler}.
 * <p />
 * TODO: test some error conditions.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class StringResponseHandlerTest
{

	@Test
	public void testHandleResponse() throws IOException, ProtocolError, ProtocolException
	{
		// test various empty responses

		assertEquals("", new StringResponseHandler().handleResponse(new TestHttpResponse(new byte[0], -1, null)));
		assertEquals("", new StringResponseHandler().handleResponse(new TestHttpResponse(new byte[0], 0, null)));
		assertEquals("", new StringResponseHandler().handleResponse(new TestHttpResponse(new byte[0], -1, new StructuredMediaType("Application", "test"))));
		assertEquals("", new StringResponseHandler().handleResponse(new TestHttpResponse(new byte[0], 0, new StructuredMediaType("Application", "test"))));
		assertEquals("",
			new StringResponseHandler().handleResponse(new TestHttpResponse(new byte[0], -1, new StructuredMediaType("Application", "test", "utf-8"))));
		assertEquals("",
			new StringResponseHandler().handleResponse(new TestHttpResponse(new byte[0], 0, new StructuredMediaType("Application", "test", "utf-8"))));

		assertEquals("", new StringResponseHandler("latin1").handleResponse(new TestHttpResponse(new byte[0], -1, null)));
		assertEquals("", new StringResponseHandler("latin1").handleResponse(new TestHttpResponse(new byte[0], 0, null)));
		assertEquals("",
			new StringResponseHandler("latin1").handleResponse(new TestHttpResponse(new byte[0], -1, new StructuredMediaType("Application", "test"))));
		assertEquals("",
			new StringResponseHandler("latin1").handleResponse(new TestHttpResponse(new byte[0], 0, new StructuredMediaType("Application", "test"))));
		assertEquals("",
			new StringResponseHandler("latin1").handleResponse(new TestHttpResponse(new byte[0], -1, new StructuredMediaType("Application", "test", "utf-8"))));
		assertEquals("",
			new StringResponseHandler("latin1").handleResponse(new TestHttpResponse(new byte[0], 0, new StructuredMediaType("Application", "test", "utf-8"))));

		// test reading a short String in various encodings

		final String smallTestContent = "öä@ł€¶ŧ←↓→øĸŋđðſæ»«¢„“”µ·";
		final byte[] smallTestContentUtf8Bytes = smallTestContent.getBytes("utf-8");
		final byte[] smallTestContentLatin1Bytes = smallTestContent.getBytes("latin1");

		assertEquals(smallTestContent, new StringResponseHandler().handleResponse(new TestHttpResponse(smallTestContentUtf8Bytes, -1, null)));
		assertEquals(smallTestContent,
			new StringResponseHandler().handleResponse(new TestHttpResponse(smallTestContentUtf8Bytes, smallTestContentUtf8Bytes.length, null)));
		assertEquals(smallTestContent,
			new StringResponseHandler().handleResponse(new TestHttpResponse(smallTestContentUtf8Bytes, -1, new StructuredMediaType("Application", "test"))));
		assertEquals(smallTestContent, new StringResponseHandler().handleResponse(new TestHttpResponse(smallTestContentUtf8Bytes,
			smallTestContentUtf8Bytes.length, new StructuredMediaType("Application", "test"))));
		assertEquals(smallTestContent, new StringResponseHandler().handleResponse(new TestHttpResponse(smallTestContentUtf8Bytes, -1, new StructuredMediaType(
			"Application", "test", "utf-8"))));
		assertEquals(smallTestContent, new StringResponseHandler().handleResponse(new TestHttpResponse(smallTestContentUtf8Bytes,
			smallTestContentUtf8Bytes.length, new StructuredMediaType("Application", "test", "utf-8"))));

		assertEquals(new String(smallTestContentLatin1Bytes, "latin1"),
			new StringResponseHandler("latin1").handleResponse(new TestHttpResponse(smallTestContentLatin1Bytes, -1, null)));
		assertEquals(new String(smallTestContentLatin1Bytes, "latin1"),
			new StringResponseHandler("latin1").handleResponse(new TestHttpResponse(smallTestContentLatin1Bytes, smallTestContentLatin1Bytes.length, null)));
		assertEquals(new String(smallTestContentLatin1Bytes, "latin1"), new StringResponseHandler("latin1").handleResponse(new TestHttpResponse(
			smallTestContentLatin1Bytes, -1, new StructuredMediaType("Application", "test"))));
		assertEquals(new String(smallTestContentLatin1Bytes, "latin1"), new StringResponseHandler("latin1").handleResponse(new TestHttpResponse(
			smallTestContentLatin1Bytes, smallTestContentLatin1Bytes.length, new StructuredMediaType("Application", "test"))));
		assertEquals(new String(smallTestContentLatin1Bytes, "latin1"), new StringResponseHandler("utf-8").handleResponse(new TestHttpResponse(
			smallTestContentLatin1Bytes, -1, new StructuredMediaType("Application", "test", "latin1"))));
		assertEquals(new String(smallTestContentLatin1Bytes, "latin1"), new StringResponseHandler("utf-8").handleResponse(new TestHttpResponse(
			smallTestContentLatin1Bytes, smallTestContentLatin1Bytes.length, new StructuredMediaType("Application", "test", "latin1"))));

		// test reading a long String in various encodings
		// generate a long String first
		final int stringLength = 1000000;
		final StringBuilder builder = new StringBuilder(stringLength);
		final String testChars = "abcdefghijklmnopqrtsuvwxyzÖÄÜßöäü1234567890@ł€¶ŧ←↓→øþæſðđŋĸł«¢„“”µ·\n\t\r";
		final Random testRandom = new Random();
		for (int i = 0; i < stringLength; ++i)
		{
			builder.append(testChars.charAt(testRandom.nextInt(testChars.length())));
		}

		final String longTestContent = builder.toString();
		final byte[] longTestContentUtf8Bytes = longTestContent.getBytes("utf-8");
		final byte[] longTestContentLatin1Bytes = longTestContent.getBytes("latin1");

		assertEquals(longTestContent, new StringResponseHandler().handleResponse(new TestHttpResponse(longTestContentUtf8Bytes, -1, null)));
		assertEquals(longTestContent,
			new StringResponseHandler().handleResponse(new TestHttpResponse(longTestContentUtf8Bytes, longTestContentUtf8Bytes.length, null)));
		assertEquals(longTestContent,
			new StringResponseHandler().handleResponse(new TestHttpResponse(longTestContentUtf8Bytes, -1, new StructuredMediaType("Application", "test"))));
		assertEquals(longTestContent, new StringResponseHandler().handleResponse(new TestHttpResponse(longTestContentUtf8Bytes,
			longTestContentUtf8Bytes.length, new StructuredMediaType("Application", "test"))));
		assertEquals(longTestContent, new StringResponseHandler().handleResponse(new TestHttpResponse(longTestContentUtf8Bytes, -1, new StructuredMediaType(
			"Application", "test", "utf-8"))));
		assertEquals(longTestContent, new StringResponseHandler().handleResponse(new TestHttpResponse(longTestContentUtf8Bytes,
			longTestContentUtf8Bytes.length, new StructuredMediaType("Application", "test", "utf-8"))));

		assertEquals(new String(longTestContentLatin1Bytes, "latin1"),
			new StringResponseHandler("latin1").handleResponse(new TestHttpResponse(longTestContentLatin1Bytes, -1, null)));
		assertEquals(new String(longTestContentLatin1Bytes, "latin1"),
			new StringResponseHandler("latin1").handleResponse(new TestHttpResponse(longTestContentLatin1Bytes, longTestContentLatin1Bytes.length, null)));
		assertEquals(new String(longTestContentLatin1Bytes, "latin1"), new StringResponseHandler("latin1").handleResponse(new TestHttpResponse(
			longTestContentLatin1Bytes, -1, new StructuredMediaType("Application", "test"))));
		assertEquals(new String(longTestContentLatin1Bytes, "latin1"), new StringResponseHandler("latin1").handleResponse(new TestHttpResponse(
			longTestContentLatin1Bytes, longTestContentLatin1Bytes.length, new StructuredMediaType("Application", "test"))));
		assertEquals(new String(longTestContentLatin1Bytes, "latin1"), new StringResponseHandler("utf-8").handleResponse(new TestHttpResponse(
			longTestContentLatin1Bytes, -1, new StructuredMediaType("Application", "test", "latin1"))));
		assertEquals(new String(longTestContentLatin1Bytes, "latin1"), new StringResponseHandler("utf-8").handleResponse(new TestHttpResponse(
			longTestContentLatin1Bytes, longTestContentLatin1Bytes.length, new StructuredMediaType("Application", "test", "latin1"))));

	}

	private final static class TestHttpResponse implements HttpResponse
	{

		private final MediaType mResponseMediaType;
		private final int mContentLength;
		private final byte[] mResponse;


		public TestHttpResponse(byte[] response, int contentLength, MediaType responseMediaType)
		{
			mResponse = response;
			mContentLength = contentLength;
			mResponseMediaType = responseMediaType;
		}


		@Override
		public HttpStatus status()
		{
			fail("StringResponseHandler must not depend on status");
			return null;
		}


		@Override
		public Headers headers()
		{
			if (mResponseMediaType != null)
			{
				return new SingletonHeaders(HttpHeaders.CONTENT_TYPE.entity(mResponseMediaType));
			}
			else
			{
				return EmptyHeaders.INSTANCE;
			}
		}


		@Override
		public HttpResponseEntity responseEntity()
		{
			return new HttpResponseEntity()
			{

				@Override
				public MediaType contentType() throws IOException
				{
					return mResponseMediaType;
				}


				@Override
				public InputStream contentStream() throws IOException
				{
					return new ByteArrayInputStream(mResponse);
				}


				@Override
				public long contentLength() throws IOException
				{
					return mContentLength;
				}
			};
		}


		@Override
		public URI requestUri()
		{
			fail("StringResponseHandler must not depend on request uri");
			return null;
		}


		@Override
		public URI responseUri()
		{
			fail("StringResponseHandler must not depend on response uri");
			return null;
		}

	}

}
