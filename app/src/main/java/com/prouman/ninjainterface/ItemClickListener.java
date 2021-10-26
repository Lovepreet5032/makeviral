package com.prouman.ninjainterface;

import android.view.View;

public interface ItemClickListener {
    void onItemClick(int i, View view);

    void onLongClick(View view, int i);
}
