/**
 * Helpers for implementing optional parameters in a builder-like class.
 * <p>
 * The scenario is that certain methods take optional parameters, subsets of which can overlap.
 * For example, {@code FactoryA} should take {@code FactoryAOptions} and
 * {@code FactoryB} should take {@code FactoryBOptions}, where both {@code FactoryAOptions} and {@code FactoryBOptions}
 * expose an optional parameter "{@code int a}" with the same meaning and default values.
 * <p>
 * To maintain convenience and type-safety, both {@code FactoryAOptions} and {@code FactoryBOptions} should expose
 * a method {@code a(int)} to set the optional parameter. But {@code FactoryAOptions::a} should return an {@code FactoryAOptions},
 * and {@code FactoryBOptions::a} should return an {@code FactoryBOptions} to allow chaining more parameters of
 * {@code FactoryAOptions} and {@code FactoryBOptions} respectively, while retaining the type of the builder.
 * <p>
 * Using this package, this can be achieved as follows:
 * <p>
 * Each subset of optional parameters ("{@code int a}" in the above example) is implemented as two interfaces,
 * one exposing methods to set the parameters, one exposing methods to retrieve parameter values.
 * <p>
 * For setting:
 * <pre>{@code
 * interface OptionA< T extends OptionA< T > > extends Options< T > {
 *     default T a( int a ) {
 *         return add( "a", a );
 *     }
 * }}</pre> where the {@code a()} method records the parameter value (with key {@code "a"}) via the
 * {@code add()} method of the {@code Options} super-interface.
 * <p>
 * For getting:
 * <pre>{@code
 * interface ValueA extends Values {
 *     ...
 *     default int a() {}
 *         return value( "a", 0 );
 *     }
 * }}</pre> where the {@code a()} method returns the parameter value (with key {@code "a"} and default value {@code 0})
 * via the {@code value()} method of the {@code Values} super-interface.
 * <p>
 * Finally, the implementation of {@code FactoryAOptions} derives from {@code AbstractOptions} and all desired
 * subsets of options
 * <pre>{@code public class FactoryAOptions
 *          extends AbstractOptions< FactoryAOptions >
 *          implements OptionA< FactoryAOptions >, ...
 * {
 *     static class FactoryAValues
 *             extends AbstractValues
 *             implements ValueA, ...
 *     {
 *           ...
 *           public FactoryAValues( final FactoryAOptions options ) {
 *               super( options );
 *           }
 *     }
 *
 *     public final FactoryAValues values = new FactoryAValues( this );
 *
 *     // =======================================================================
 *
 *     // If in-place modification of the options builder is desired,
 *     // the following methods should be left out.
 *
 *     public FactoryAOptions() {
 *         super();
 *     }
 *
 *     private FactoryAOptions(FactoryAOptions that) {
 *         super(that);
 *     }
 *
 *     \@Override
 *     public FactoryAOptions copyOrThis() {
 *         return new FactoryAOptions(this);
 *     }
 *
 *     // =======================================================================
 *
 *     // The following is not necessary, but can be overridden like this
 *     // to make it show up more nicely in IDE auto-complete
 *
 *     \@Override
 *     public FactoryAOptions a(int a) {
 *         return OptionA.super.a(a);
 *     }
 * }}</pre> The parameter values are exposed through inner class {@code FactoryAValues} that derives from {@code AbstractValues}
 * and all desired subsets of option values.
 * <p>
 * The only thing that has been omitted from the above example is the parts that provide a nice {@code toString} implementation
 * for the values. This is be achieved by
 * <pre>{@code
 * interface ValueA extends Values {
 *     default void buildToString( AbstractValues.ValuesToString sb ) {
 *         sb.append( "a", a() );
 *     }
 *
 *     default int a() {
 *         return value( "a", 0 );
 *     }
 * }}</pre> and <pre>{@code
 * static class FactoryAValues
 *         extends AbstractValues
 *         implements ValueA, ...
 * {
 *     \@Override
 *     public String toString() {
 *         final ValuesToString sb = new ValuesToString();
 *         ValueA.super.buildToString( sb );
 *         return sb.toString();
 *     }
 *
 *     public FactoryAValues( final FactoryAOptions options ) {
 *         super( options );
 *     }
 * }}</pre>
 */
package org.scijava.optional;
