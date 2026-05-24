package io.swagger.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Stub for Springfox @ApiModelProperty — Phase 2 replaces with Springdoc @Schema.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ApiModelProperty {
    String value() default "";
    String notes() default "";
    boolean required() default false;
    String allowableValues() default "";
    boolean hidden() default false;
}
