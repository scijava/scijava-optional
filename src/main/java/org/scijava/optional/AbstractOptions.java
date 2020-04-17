package org.scijava.optional;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public abstract class AbstractOptions< T > implements Options< T >
{
	final private Map< String, Object > values;

	protected AbstractOptions()
	{
		this.values = new LinkedHashMap<>();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T setValue( final String key, final Object value )
	{
		values.put(key, value);
		return (T) this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public < V > V getValueOrDefault( String key, V defaultValue ) {
		V value = (V) values.get(key);
		return value != null ? value : defaultValue;
	}

	@SuppressWarnings("unchecked")
	protected T setOptions( T options )
	{
		values.putAll(((AbstractOptions) options).values);
		return (T) this;
	}

	@Override
	public String toString()
	{
		StringJoiner result = new StringJoiner(", ","{ ",  " }");
		values.forEach((key, value) -> result.add(key + "=" + value));
		return result.toString();
	}

}
