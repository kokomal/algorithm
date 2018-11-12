/**
 * 
 */
package yuanjun.chen.advanced.ods;

import java.util.Comparator;

class DefaultComparator<T> implements Comparator<T> {
    @SuppressWarnings("unchecked")
    public int compare(T a, T b) {
        return ((Comparable<T>) a).compareTo(b);
    }
}