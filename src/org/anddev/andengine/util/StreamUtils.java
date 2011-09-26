package org.anddev.andengine.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 15:48:56 - 03.09.2009
 */
public class StreamUtils {
	// ===========================================================
	// Constants
	// ===========================================================

	public static final int IO_BUFFER_SIZE = 8 * 1024;

	private static final int END_OF_STREAM = -1;

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public static final String readFully(final InputStream pInputStream) throws IOException {
		final StringWriter writer = new StringWriter();
		final char[] buf = new char[StreamUtils.IO_BUFFER_SIZE];
		try {
			final Reader reader = new BufferedReader(new InputStreamReader(pInputStream, "UTF-8"));
			int read;
			while((read = reader.read(buf)) != StreamUtils.END_OF_STREAM) {
				writer.write(buf, 0, read);
			}
		} finally {
			StreamUtils.close(pInputStream);
		}
		return writer.toString();
	}

	public static byte[] streamToBytes(final InputStream pInputStream) throws IOException {
		return StreamUtils.streamToBytes(pInputStream, StreamUtils.END_OF_STREAM);
	}

	public static byte[] streamToBytes(final InputStream pInputStream, final int pReadLimit) throws IOException {
		final ByteArrayOutputStream os = new ByteArrayOutputStream((pReadLimit == StreamUtils.END_OF_STREAM) ? StreamUtils.IO_BUFFER_SIZE : pReadLimit);
		StreamUtils.copy(pInputStream, os, pReadLimit);
		return os.toByteArray();
	}

	public static void streamToBytes(final InputStream pInputStream, final int pReadLimit, final byte[] pData, final int pOffset) throws IOException {
		final int read = pInputStream.read(pData, pOffset, pReadLimit);
		if(read != pReadLimit) {
			throw new IOException("ReadLimit: '" + pReadLimit + "', read: '" + read + "'.");
		}
	}

	public static void copy(final InputStream pInputStream, final OutputStream pOutputStream) throws IOException {
		StreamUtils.copy(pInputStream, pOutputStream, StreamUtils.END_OF_STREAM);
	}

	public static void copy(final InputStream pInputStream, final byte[] pData) throws IOException {
		int dataOffset = 0;
		final byte[] buf = new byte[StreamUtils.IO_BUFFER_SIZE];
		int read;
		while((read = pInputStream.read(buf)) != StreamUtils.END_OF_STREAM) {
			System.arraycopy(buf, 0, pData, dataOffset, read);
			dataOffset += read;
		}
	}

	public static void copy(final InputStream pInputStream, final ByteBuffer pByteBuffer) throws IOException {
		final byte[] buf = new byte[StreamUtils.IO_BUFFER_SIZE];
		int read;
		while((read = pInputStream.read(buf)) != StreamUtils.END_OF_STREAM) {
			pByteBuffer.put(buf, 0, read);
		}
	}

	/**
	 * Copy the content of the input stream into the output stream, using a temporary
	 * byte array buffer whose size is defined by {@link #IO_BUFFER_SIZE}.
	 *
	 * @param pInputStream The input stream to copy from.
	 * @param pOutputStream The output stream to copy to.
	 * @param pByteLimit not more than so much bytes to read, or unlimited if smaller than 0.
	 *
	 * @throws IOException If any error occurs during the copy.
	 */
	public static void copy(final InputStream pInputStream, final OutputStream pOutputStream, final long pByteLimit) throws IOException {
		if(pByteLimit == StreamUtils.END_OF_STREAM) {
			final byte[] buf = new byte[StreamUtils.IO_BUFFER_SIZE];
			int read;
			while((read = pInputStream.read(buf)) != StreamUtils.END_OF_STREAM) {
				pOutputStream.write(buf, 0, read);
			}
		} else {
			final byte[] buf = new byte[StreamUtils.IO_BUFFER_SIZE];
			final int bufferReadLimit = Math.min((int)pByteLimit, StreamUtils.IO_BUFFER_SIZE);
			long pBytesLeftToRead = pByteLimit;

			int read;
			while((read = pInputStream.read(buf, 0, bufferReadLimit)) != StreamUtils.END_OF_STREAM) {
				if(pBytesLeftToRead > read) {
					pOutputStream.write(buf, 0, read);
					pBytesLeftToRead -= read;
				} else {
					pOutputStream.write(buf, 0, (int) pBytesLeftToRead);
					break;
				}
			}
		}
		pOutputStream.flush();
	}

	public static boolean copyAndClose(final InputStream pInputStream, final OutputStream pOutputStream) {
		try {
			StreamUtils.copy(pInputStream, pOutputStream, StreamUtils.END_OF_STREAM);
			return true;
		} catch (final IOException e) {
			return false;
		} finally {
			StreamUtils.close(pInputStream);
			StreamUtils.close(pOutputStream);
		}
	}

	/**
	 * Closes the specified stream.
	 *
	 * @param pCloseable The stream to close.
	 */
	public static void close(final Closeable pCloseable) {
		if(pCloseable != null) {
			try {
				pCloseable.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Flushes and closes the specified stream.
	 *
	 * @param pOutputStream The stream to close.
	 */
	public static void flushCloseStream(final OutputStream pOutputStream) {
		if(pOutputStream != null) {
			try {
				pOutputStream.flush();
			} catch (final IOException e) {
				e.printStackTrace();
			} finally {
				StreamUtils.close(pOutputStream);
			}
		}
	}

	/**
	 * Flushes and closes the specified stream.
	 *
	 * @param pWriter The Writer to close.
	 */
	public static void flushCloseWriter(final Writer pWriter) {
		if(pWriter != null) {
			try {
				pWriter.flush();
			} catch (final IOException e) {
				e.printStackTrace();
			} finally {
				StreamUtils.close(pWriter);
			}
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
