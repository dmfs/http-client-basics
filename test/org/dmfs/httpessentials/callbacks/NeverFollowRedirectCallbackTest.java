package org.dmfs.httpessentials.callbacks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.dmfs.httpessentials.HttpStatus;
import org.junit.Test;


public class NeverFollowRedirectCallbackTest
{

	@Test
	public void testGetInstance()
	{
		assertTrue(NeverFollowRedirectCallback.getInstance() instanceof NeverFollowRedirectCallback);
	}


	@Test
	public void testFollowRedirect()
	{
		assertFalse(NeverFollowRedirectCallback.getInstance().followRedirect(HttpStatus.PERMANENT_REDIRECT, URI.create("https://www.example.com"),
			URI.create("https://www.example.net")));
		assertFalse(NeverFollowRedirectCallback.getInstance().followRedirect(HttpStatus.TEMPORARY_REDIRECT, URI.create("https://www.example.com"),
			URI.create("https://www.example.net")));

		assertFalse(NeverFollowRedirectCallback.getInstance().followRedirect(HttpStatus.PERMANENT_REDIRECT, URI.create("http://www.example.com"),
			URI.create("https://www.example.net")));
		assertFalse(NeverFollowRedirectCallback.getInstance().followRedirect(HttpStatus.TEMPORARY_REDIRECT, URI.create("http://www.example.com"),
			URI.create("https://www.example.net")));

		assertFalse(NeverFollowRedirectCallback.getInstance().followRedirect(HttpStatus.PERMANENT_REDIRECT, URI.create("http://www.example.com"),
			URI.create("http://www.example.net")));
		assertFalse(NeverFollowRedirectCallback.getInstance().followRedirect(HttpStatus.TEMPORARY_REDIRECT, URI.create("http://www.example.com"),
			URI.create("http://www.example.net")));

		assertFalse(NeverFollowRedirectCallback.getInstance().followRedirect(HttpStatus.PERMANENT_REDIRECT, URI.create("https://www.example.com"),
			URI.create("http://www.example.net")));
		assertFalse(NeverFollowRedirectCallback.getInstance().followRedirect(HttpStatus.TEMPORARY_REDIRECT, URI.create("https://www.example.com"),
			URI.create("http://www.example.net")));

	}

}
