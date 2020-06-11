/**
 * Helpers for implementing optional parameters in a builder-like class.
 * <p>
 * The scenario is that certain methods take optional parameters, subsets of which can overlap.
 * For example, {@code FactoryA} should take {@code FactoryAOptions} and
 * {@code FactoryB} should take {@code FactoryBOptions}, where both {@code FactoryAOptions} and {@code FactoryBOptions}
 * expose an optional parameter "{@code int a}" with the same meaning and default values.
 * <p>
 * To maintain convenience and type-safety, both {@code FactoryAOptions} and {@code FactoryBOptions} should expose
 * a method {@code a(int)} to set the optional parameter. But {@code FactoryAOptions::a} should return a {@code FactoryAOptions},
 * and {@code FactoryBOptions::a} should return a {@code FactoryBOptions} to allow chaining more parameters of
 * {@code FactoryAOptions} and {@code FactoryBOptions} respectively, while retaining the type of the builder.
 * <p>
 * Using this package, this can be achieved as follows:
 * <p>
 * Each subset of optional parameters ("{@code int a}" in the above example) is implemented as two interfaces,
 * one exposing methods to set the parameters, the other exposing methods to retrieve parameter values.
 * <p>
 * For setting:
 * <pre>{@code
 * interface OptionA<T> extends Options<T> {
 *     default T a( int a ) {
 *         return setValue( "a", a );
 *     }
 * }}</pre> where the {@code a()} method records the parameter value (with key {@code "a"}) via the
 * {@code setValue()} method of the {@code Options} super-interface.
 * <p>
 * For getting:
 * <pre>{@code
 * interface ValueA extends Values {
 *     ...
 *     default int a() {}
 *         return getValueOrDefault( "a", 0 );
 *     }
 * }}</pre> where the {@code a()} method returns the parameter value (with key {@code "a"} and default value {@code 0})
 * via the {@code getValueOrDefault()} method of the {@code Values} super-interface.
 * <p>
 * Finally, the implementation of {@code FactoryAOptions} derives from {@code AbstractOptions} and all desired
 * subsets of options
 * <pre>{@code public class FactoryAOptions
 *          extends AbstractOptions<FactoryAOptions>
 *          implements OptionA<FactoryAOptions>, ...
 * {
 *     public class FactoryAValues
 *             extends AbstractValues
 *             implements ValueA, ...
 *     {}
 *
 *     public final FactoryAValues values = new FactoryAValues();
 *
 *     // =======================================================================
 *
 *     // If in-place modification of the options builder is desired,
 *     // the following methods should be left out.
 *
 *     public FactoryAOptions() {}
 *
 *     private FactoryAOptions( FactoryAOptions that ) {
 *         super( that );
 *     }
 *
 *     \@Override
 *     protected FactoryAOptions copyOrThis() {
 *         return new FactoryAOptions( this );
 *     }
 * }}</pre> The parameter values are exposed through inner class {@code FactoryAValues} that derives from {@code AbstractValues}
 * and all desired subsets of option values.
 * <p>
 * The only thing that has been omitted from the above example is the parts that provide a nice {@code toString} implementation
 * for the values. This is be achieved by overriding the {@code forEach()} methods in the {@code Values} interfaces and
 * implementation
 * <pre>{@code
 * interface ValueA extends Values {
 *     default void forEach( BiConsumer<String, Object> action ) {
 *         action.accept( "a", a() );
 *         // and so on, for other parameters defined in this Values interface
 *     }
 *
 *     default int a() {
 *         return getValueOrDefault( "a", 0 );
 *     }
 * }}</pre> and <pre>{@code
 * public class FactoryAValues
 *         extends AbstractValues
 *         implements ValueA, ...
 * {
 *     \@Override
 *     public void forEach( BiConsumer<String, Object> action )
 *         ValueA.super.forEach( action );
 *         // and so on, for other implemented Values interfaces
 *     }
 * }}</pre>
 */
package org.scijava.optional;
