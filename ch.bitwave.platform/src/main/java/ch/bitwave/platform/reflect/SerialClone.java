package ch.bitwave.platform.reflect;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.codec.binary.Base64;

/**
 * Helper class for reflection-based deep-copying of object graphs via Java's serialization mechanism.
 * 
 */
public class SerialClone
{

	private final Queue<Class<?>> queue = new LinkedList<Class<?>>();

	/**
	 * Returns a deep copy of the given object.
	 * 
	 * @param x
	 *            the object to copy.
	 * @return the copy of x.
	 */
	public Object clone(final Object x)
	{
		try
		{
			return cloneX(x);
		}
		catch (IOException e)
		{
			throw new IllegalArgumentException(e);
		}
		catch (ClassNotFoundException e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	private Object cloneX(final Object x) throws IOException, ClassNotFoundException
	{
		Queue<Class<?>> classQueue = new LinkedList<Class<?>>();
		byte[] bytes = save(x, classQueue);
		Object clone = load(classQueue, bytes);
		return clone;
	}

	private <T> T load(final Queue<Class<?>> classQueue, final byte[] bytes) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		CloneInput cin = new CloneInput(bin, classQueue);

		@SuppressWarnings("unchecked")
		// thanks to Bas de Bakker for the tip!
		T clone = (T) cin.readObject();

		cin.close();

		return clone;
	}

	private byte[] save(final Object x, final Queue<Class<?>> classQueue) throws IOException
	{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		CloneOutput cout = new CloneOutput(bout, classQueue);
		cout.writeObject(x);
		byte[] bytes = bout.toByteArray();
		cout.close();
		return bytes;
	}

	/**
	 * Loads the object from the given base64-encoded data.
	 * 
	 * @param base64Data
	 *            the serialized object data to read.
	 * @return the resulting object.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object load(final String base64Data) throws IOException, ClassNotFoundException
	{
		byte[] bytes = Base64.decodeBase64(base64Data.getBytes());
		return load(this.queue, bytes);
	}

	/**
	 * Saves the given object as a base64-encoded string.
	 * 
	 * @param x
	 *            the object to serialize.
	 * @return the corresponding serialized object data.
	 * @throws IOException
	 */
	public String save(final Object x) throws IOException
	{
		this.queue.clear();
		byte[] bytes = save(x, this.queue);
		return new String(Base64.encodeBase64(bytes));
	}

	private class CloneOutput extends ObjectOutputStream
	{
		private final Queue<Class<?>> classQueue;

		CloneOutput(final OutputStream out, final Queue<Class<?>> classQueue) throws IOException
		{
			super(out);
			this.classQueue = classQueue;
		}

		@Override
		protected void annotateClass(final Class<?> c)
		{
			this.classQueue.add(c);
		}

		@Override
		protected void annotateProxyClass(final Class<?> c)
		{
			this.classQueue.add(c);
		}
	}

	private class CloneInput extends ObjectInputStream
	{
		private final Queue<Class<?>> classQueue;

		CloneInput(final InputStream in, final Queue<Class<?>> classQueue) throws IOException
		{
			super(in);
			this.classQueue = classQueue;
		}

		@Override
		protected Class<?> resolveClass(final ObjectStreamClass osc) throws IOException, ClassNotFoundException
		{
			Class<?> c = this.classQueue.poll();
			String expected = osc.getName();
			String found = (c == null) ? null : c.getName();
			if (!expected.equals(found))
			{
				throw new InvalidClassException(String.format("Classes desynchronized: found %s when expecting %s.", found, expected)); //$NON-NLS-1$
			}
			return c;
		}

		@Override
		protected Class<?> resolveProxyClass(final String[] interfaceNames) throws IOException, ClassNotFoundException
		{
			return this.classQueue.poll();
		}
	}
}