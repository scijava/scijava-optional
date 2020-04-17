package org.scijava.optional;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public abstract class AbstractOptions< T > implements Options< T >
{
	final private Map< String, Object > theOptions;

	public AbstractOptions()
	{
		this.theOptions = new LinkedHashMap<>();
	}

	public T copy() {
		try {
			AbstractOptions<T> copy = this.getClass().getConstructor().newInstance();
			copy.append(this);
			@SuppressWarnings("unchecked")
			T t = (T) copy;
			return t;
		}
		catch (InstantiationException | IllegalAccessException |
				NoSuchMethodException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	protected T append( final AbstractOptions< T > additionalOptions )
	{
		theOptions.putAll(additionalOptions.theOptions);
		@SuppressWarnings("unchecked")
		T t = (T) this;
		return t;
	}

	@Override
	public T setValue( final String key, final Object value )
	{
		theOptions.put(key, value);
		@SuppressWarnings("unchecked")
		T t = (T) this;
		return t;
	}

	@Override
	public < V > V getValue(String key, V defaultValue) {
		@SuppressWarnings("unchecked")
		V value = (V) theOptions.get(key);
		return value != null ? value : defaultValue;
	}

	@Override
	public String toString()
	{
		StringJoiner result = new StringJoiner(", ","{ ",  " }");
		theOptions.forEach((key, value) -> result.add(key + "=" + value));
		return result.toString();
	}

}
