[![](https://travis-ci.com/scijava/scijava-optional.svg?branch=master)](https://travis-ci.com/scijava/scijava-optional)

# scijava-optional

Helpers for emulating named and default arguments in builder-like classes.

The scenario is that certain methods take optional parameters, subsets of which can overlap.
For example, `FactoryA` should take `FactoryAOptions` and
`FactoryB` should take `FactoryBOptions`, where both `FactoryAOptions` and `FactoryBOptions`
expose an optional parameter "`int a`" with the same meaning and default values.

To maintain convenience and type-safety, both `FactoryAOptions` and `FactoryBOptions` should expose
a method `a(int)` to set the optional parameter. But `FactoryAOptions::a` should return an `FactoryAOptions`,
and `FactoryBOptions::a` should return an `FactoryBOptions` to allow chaining more parameters of
`FactoryAOptions` and `FactoryBOptions` respectively, while retaining the type of the builder.

Using scijava-optional, this can be achieved as follows:

Each subset of optional parameters ("`int a`" in the above example) is implemented as two interfaces,
one exposing methods to set the parameters, one exposing methods to retrieve parameter values.

For setting:
```java
interface OptionA<T extends OptionA<T>> extends Options<T> {
    default T a(int a) {
        return add("a", a);
    }
}
```
where the `a()` method records the parameter value (with key `"a"`) via the
`add()` method of the `Options` super-interface.

For getting:
```java
interface ValueA extends Values {
    ...
    default int a() {}
        return value( "a", 0 );
    }
}
```
where the `a()` method returns the parameter value (with key `"a"` and default value `0`)
via the `value()` method of the `Values` super-interface.

Finally, the implementation of `FactoryAOptions` derives from `AbstractOptions` and all desired
subsets of options
```java
public class FactoryAOptions
         extends AbstractOptions< FactoryAOptions >
         implements OptionA< FactoryAOptions >, ...
{
    static class FactoryAValues
            extends AbstractValues
            implements ValueA, ...
    {
          ...
          public FactoryAValues(FactoryAOptions options) {
              super( options );
          }
    }

    public final FactoryAValues values = new FactoryAValues(this);;

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

    // =======================================================================
 
    // The following is not necessary, but can be overridden like this
    // to make it show up more nicely in IDE auto-complete
 
    @Override
    public FactoryAOptions a(int a) {
        return OptionA.super.a(a);
    }
}
```
The parameter values are exposed through inner class `FactoryAValues` that derives from `AbstractValues`
and all desired subsets of option values.

The only thing that has been omitted from the above example is the parts that provide a nice `toString` implementation
for the values. This is be achieved by
```java
interface ValueA extends Values {
    default void buildToString(AbstractValues.ValuesToString sb) {
        sb.append("a", a());
    }

    default int a() {
        return value("a", 0);
    }
}
```
and
```java
static class FactoryAValues
        extends AbstractValues
        implements ValueA, ...
{
    @Override
    public String toString() {
        final ValuesToString sb = new ValuesToString();
        ValueA.super.buildToString(sb);
        return sb.toString();
    }

    public FactoryAValues(FactoryAOptions options) {
        super(options);
    }
}
```
