package com.goodforgoodbusiness.webapp.stream;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.streams.WriteStream;

/**
 * Wrap a Vert.x {@link WriteStream} in to an {@link OutputStream}.
 * XXX THIS NEEDS OPTIMISING!
 */
public class BufferInputStream extends ByteArrayInputStream {
    public BufferInputStream(final Buffer _input) {
    	super(_input.getBytes());
    }
}