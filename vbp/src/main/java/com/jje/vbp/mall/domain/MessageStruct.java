package com.jje.vbp.mall.domain;

/**
 * Created by IntelliJ IDEA.
 * User: yapon
 * Date: 3/13/12
 * Time: 5:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MessageStruct<H, B> {

    H getHead();

    B getBody();
}
