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

	@Override
	@SuppressWarnings("unchecked")
	public T setValue( final String key, final Object value )
	{
		theOptions.put(key, value);
		return (T) this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public < V > V getValueOrDefault(String key, V defaultValue) {
		V value = (V) theOptions.get(key);
		return value != null ? value : defaultValue;
	}

	@SuppressWarnings("unchecked")
	public T copy() {
		try {
			AbstractOptions<T> copy = this.getClass().getConstructor().newInstance();
			copy.append((T) this);
			return (T) copy;
		}
		catch (InstantiationException | IllegalAccessException |
				NoSuchMethodException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected T append( T additionalOptions )
	{
		theOptions.putAll(((AbstractOptions) additionalOptions).theOptions);
		return (T) this;
	}


	@Override
	public String toString()
	{
		StringJoiner result = new StringJoiner(", ","{ ",  " }");
		theOptions.forEach((key, value) -> result.add(key + "=" + value));
		return result.toString();
	}

}
