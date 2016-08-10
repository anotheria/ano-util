package net.anotheria.util.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mapper annotation
 *
 * @author vitaliy
 * @version 1.0
 *          Date: Jan 10, 2010
 *          Time: 9:50:45 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DontPopulate {		
}
