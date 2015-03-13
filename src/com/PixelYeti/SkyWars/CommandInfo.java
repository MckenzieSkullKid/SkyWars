package com.PixelYeti.SkyWars;

/**
 * Created by Callum on 13/03/2015.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandInfo {

    String description();
    String[] aliases();
    String usage();
    boolean op(); // If true only an op can run the command
}