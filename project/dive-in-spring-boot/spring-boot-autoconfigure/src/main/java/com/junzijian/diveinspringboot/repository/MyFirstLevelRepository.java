package com.junzijian.diveinspringboot.repository;

import com.junzijian.diveinspringboot.annotation.FirstLevelRepository;
import com.junzijian.diveinspringboot.annotation.SecondLevelRepository;

/**
 * 我的 {@link FirstLevelRepository}
 *
 * @author junzijian
 * @since 2018/5/14
 */
@SecondLevelRepository(value = "myFirstLevelRepository") // Bean 名称
public class MyFirstLevelRepository {
}
