package com.rmart.baseclass;

public interface BaseListener {
    void showBadge(boolean b);
    void updateBadgeCount();

    void hideHamburgerIcon();
    void showHamburgerIcon();

    void showCartIcon();

    void hideCartIcon();
}
