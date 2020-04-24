package org.scijava.optional;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractOptions< T extends AbstractOptions< T > > implements Options< T >, Map<String, Object>
{
	final LinkedHashMap< String, Object > theOptions = new LinkedHashMap<>();

	protected AbstractOptions( T that )
	{
		that.theOptions.forEach( theOptions::put );
	}

	public AbstractOptions()
	{
	}

	protected T copyOrThis()
	{
		return ( T ) this;
	}

	protected T append( final T additionalOptions )
	{
		if ( additionalOptions == null )
			return ( T ) this;

		T concat = copyOrThis();
		additionalOptions.theOptions.forEach( concat.theOptions::put );
		return concat;
	}

	@Override
	public T add( final String key, final Object value )
	{
		final T copy = copyOrThis();
		copy.theOptions.remove( key );
		copy.theOptions.put( key, value );
		return copy;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append( "{" );
		int numLeft = theOptions.size();
		for ( Map.Entry< String, Object > option : theOptions.entrySet() )
		{
			sb.append( option.getKey() );
			sb.append( " = " );
			sb.append( option.getValue() );
			if ( --numLeft > 0 )
				sb.append( ", " );
		}
		sb.append( "}" );
		return sb.toString();
	}

	@Override
	public int size() {
		return theOptions.size();
	}

	@Override
	public boolean isEmpty() {
		return theOptions.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return theOptions.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return theOptions.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return theOptions.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return theOptions.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return theOptions.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ?> m) {
		theOptions.putAll(m);
	}

	@Override
	public void clear() {
		theOptions.clear();
	}

	@Override
	public Set<String> keySet() {
		return theOptions.keySet();
	}

	@Override
	public Collection<Object> values() {
		return theOptions.values();
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return theOptions.entrySet();
	}
}
