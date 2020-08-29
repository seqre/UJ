package uj.pwj2019.w9;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * This annotation is used to determine comparision priority of fields for comparator generated for classes
 * annotated with {@link uj.pwj2019.w9.MyComparable} annotation.
 * Comparision between fields is in descending order (field with value "1" will be compared before field with value "2";
 * only if field "1" is equal, then field "2" is being checked.
 * If more than one field has defined the same priority, then generator can use any ordering between them.
 * Not annotated fields are of the lowest priority.
 */
@Target(ElementType.FIELD)
public @interface ComparePriority {
    /**
     * Priority of filed in comparision.{br}
     * Lower value -> higher priority.
     * @return priority
     */
    int value();
}
