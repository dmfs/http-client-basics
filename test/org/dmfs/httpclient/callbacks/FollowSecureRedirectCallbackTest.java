package org.dmfs.httpclient.callbacks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.dmfs.httpclient.HttpStatus;
import org.junit.Test;


public class FollowSecureRedirectCallbackTest
{

	@Test
	public void testGetInstance()
	{
		assertTrue(FollowSecureRedirectCallback.getInstance() instanceof FollowSecureRedirectCallback);
	}


	@Test
	public void testFollowRedirect()
	{
		// only follow if source and target are secure
		assertTrue(FollowSecureRedirectCallback.getInstance().followRedirect(HttpStatus.PERMANENT_REDIRECT, URI.create("https://www.example.com"),
			URI.create("https://www.example.net")));
		assertTrue(FollowSecureRedirectCallback.getInstance().followRedirect(HttpStatus.TEMPORARY_REDIRECT, URI.create("https://www.example.com"),
			URI.create("https://www.example.net")));

		// don't follow otherwise
		assertFalse(FollowSecureRedirectCallback.getInstance().followRedirect(HttpStatus.PERMANENT_REDIRECT, URI.create("http://www.example.com"),
			URI.create("https://www.example.net")));
		assertFalse(FollowSecureRedirectCallback.getInstance().followRedirect(HttpStatus.TEMPORARY_REDIRECT, URI.create("http://www.example.com"),
			URI.create("https://www.example.net")));

		assertFalse(FollowSecureRedirectCallback.getInstance().followRedirect(HttpStatus.PERMANENT_REDIRECT, URI.create("http://www.example.com"),
			URI.create("http://www.example.net")));
		assertFalse(FollowSecureRedirectCallback.getInstance().followRedirect(HttpStatus.TEMPORARY_REDIRECT, URI.create("http://www.example.com"),
			URI.create("http://www.example.net")));

		assertFalse(FollowSecureRedirectCallback.getInstance().followRedirect(HttpStatus.PERMANENT_REDIRECT, URI.create("https://www.example.com"),
			URI.create("http://www.example.net")));
		assertFalse(FollowSecureRedirectCallback.getInstance().followRedirect(HttpStatus.TEMPORARY_REDIRECT, URI.create("https://www.example.com"),
			URI.create("http://www.example.net")));

	}

}
