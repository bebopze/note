package com.bebopze.jdk.frame.darkrule;

/**
 * @author bebopze
 * @date 2020/8/17
 */
public interface IDarkFeature {

    boolean enabled();

    boolean dark(long darkTarget);

    boolean dark(String darkTarget);
}
