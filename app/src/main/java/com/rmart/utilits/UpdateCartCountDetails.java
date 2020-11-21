package com.rmart.utilits;

import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Satya Seshu on 21/11/20.
 */
public class UpdateCartCountDetails {

    //public static
    public static BehaviorSubject<Integer> updateCartCountDetails = BehaviorSubject.createDefault(0);
}
