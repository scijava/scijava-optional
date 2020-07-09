[![](https://travis-ci.com/scijava/scijava-optional.svg?branch=master)](https://travis-ci.com/scijava/scijava-optional)

# scijava-optional

Helpers for emulating named and default arguments in builder-like classes.

The scenario is that certain methods take optional parameters, subsets of which can overlap.
For example, `FactoryA` should take `FactoryAOptions` and
`FactoryB` should take `FactoryBOptions`, where both `FactoryAOptions` and `FactoryBOptions`
expose an optional parameter "`int a`" with the same meaning and default values.

To maintain convenience and type-safety, both `FactoryAOptions` and `FactoryBOptions` should expose
a method `a(int)` to set the optional parameter. But `FactoryAOptions::a` should return a `FactoryAOptions`,
and `FactoryBOptions::a` should return a `FactoryBOptions` to allow chaining more parameters of
`FactoryAOptions` and `FactoryBOptions` respectively, while retaining the type of the builder.

Using scijava-optional, this can be achieved as follows (see [full example](src/test/java/org/scijava/optional/examples/Playground.java)):

Each subset of optional parameters ("`int a`" in the above example) is implemented as two interfaces,
one exposing methods to set the parameters, one exposing methods to retrieve parameter values.

For setting:
```java
interface OptionA<T> extends Options<T> {
    default T a(int a) {
        return setValue("a", a);
    }
}
```
where the `a()` method records the parameter value (with key `"a"`) via the
`setValue()` method of the `Options` super-interface.

For getting:
```java
interface ValueA extends Values {
    ...
    default int a() {
        return getValueOrDefault( "a", 0 );
    }
}
```
where the `a()` method returns the parameter value (with key `"a"` and default value `0`)
via the `getValueOrDefault()` method of the `Values` super-interface.

Finally, the implementation of `FactoryAOptions` derives from `AbstractOptions` and all desired
subsets of options
```java
public class FactoryAOptions
         extends AbstractOptions< FactoryAOptions >
         implements OptionA< FactoryAOptions >, ...
{
    public class FactoryAValues
            extends AbstractValues
            implements ValueA, ...
    {}

    public final FactoryAValues values = new FactoryAValues();

    // =======================================================================
 
    // If in-place modification of the options builder is desired,
    // the following methods should be left out. 

    public FactoryAOptions() {}

    private FactoryAOptions(FactoryAOptions that) {
        super(that);
    }

    @Override
    protected FactoryAOptions copyOrThis() {
        return new FactoryAOptions(this);
    }
}
```
The parameter values are exposed through inner class `FactoryAValues` that derives from `AbstractValues`
and all desired subsets of option values.

The only thing that has been omitted from the above example is the parts that provide a nice `toString` implementation
for the values. This is be achieved by overriding the `forEach()` methods in the `Values` interfaces and
implementation
```java
interface ValueA extends Values {
    default void forEach(BiConsumer<String, Object> action) {
        action.accept("a", a());
        // and so on, for other parameters defined in this Values interface
    }

    default int a() {
        return getValueOrDefault("a", 0);
    }
}
```
and
```java
public class FactoryAValues
        extends AbstractValues
        implements ValueA, ...
{
    @Override
    public void forEach(BiConsumer<String, Object> action) {
        ValueA.super.forEach( action );
        // and so on, for other implemented Values interfaces
    }
}
```
