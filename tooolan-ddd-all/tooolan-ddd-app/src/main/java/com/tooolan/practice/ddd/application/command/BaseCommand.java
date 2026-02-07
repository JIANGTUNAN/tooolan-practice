package com.tooolan.practice.ddd.application.command;

import java.io.Serializable;

/**
 * 命令对象基类
 * 所有写操作的命令对象应继承此类
 *
 * @author tooolan
 */
public abstract class BaseCommand implements Serializable {

    private static final long serialVersionUID = 1L;

}
