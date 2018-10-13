package com.guillen.santiago.findmeapp.view;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Utils {

    public static void createToastMessage (Toast toast, Context context, String message ) {
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void setLoadingView(ProgressBar progressBar, boolean isLoading){
        assert progressBar != null;
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
