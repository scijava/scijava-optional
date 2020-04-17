package org.scijava.optional;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public abstract class AbstractOptions< T > implements Options< T >
{
	final private Map< String, Object > theOptions;

	protected AbstractOptions( final AbstractOptions< T > that )
	{
		this.theOptions = new LinkedHashMap<>(that.theOptions);
	}

	public AbstractOptions()
	{
		this.theOptions = new LinkedHashMap<>();
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
