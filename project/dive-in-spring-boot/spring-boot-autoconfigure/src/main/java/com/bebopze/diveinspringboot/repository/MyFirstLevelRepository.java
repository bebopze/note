package com.bebopze.diveinspringboot.repository;

import com.bebopze.diveinspringboot.annotation.FirstLevelRepository;
import com.bebopze.diveinspringboot.annotation.SecondLevelRepository;

/**
 * 我的 {@link FirstLevelRepository}
 *
 * @author bebopze
 * @since 2018/5/14
 */
@SecondLevelRepository(value = "myFirstLevelRepository") // Bean 名称
public class MyFirstLevelRepository {
}
