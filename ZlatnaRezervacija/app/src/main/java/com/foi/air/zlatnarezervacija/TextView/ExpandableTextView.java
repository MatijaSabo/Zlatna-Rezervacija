package com.foi.air.zlatnarezervacija.TextView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.foi.air.zlatnarezervacija.R;

/**
 * Created by Lovro on 28.1.2017..
 */

public class ExpandableTextView extends TextView implements View.OnClickListener{

    private static final int MAX_LINES = 5;
    private int currentMaxLines = Integer.MAX_VALUE;
    private boolean flag=false;


    /* konstruktori*/
    public ExpandableTextView(Context context)
    {
        super(context);
        setOnClickListener(this);
    }


    public ExpandableTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setOnClickListener(this);
    }


    public ExpandableTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setOnClickListener(this);
    }

    /*  provjerava broj linija, u slučaju da se unutar  ExpandableTextView nalaz više od 5 linija, postavlja ikonu */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter)
    {
        post(new Runnable()
        {
            public void run()
            {
                if (getLineCount()>MAX_LINES)
                {
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.mipmap.ic_keyboard_arrow_down_black_18dp);
                    flag=true;
                }
                else
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                setMaxLines(MAX_LINES);
            }
        });
    }

    /* postavlja maximalan broj linija*/
    @Override
    public void setMaxLines(int maxLines)
    {
        currentMaxLines = maxLines;
        super.setMaxLines(maxLines);
    }

    /* vraća max broj linija*/
    public int getMyMaxLines()
    {
        return currentMaxLines;
    }

    /* u slučaju klika na ExpandableTextView povećava se ili smanjuje broj linija unutar ExpandableTextView, varijabla flag služi samo za provjeru broja linija, bez te varijable
     * neovisno o broju linija ikone bi se izmjenjivale na klik */
    @Override
    public void onClick(View v) {
        if (flag == true) {
            if (getMyMaxLines() == Integer.MAX_VALUE) {
                setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.mipmap.ic_keyboard_arrow_down_black_18dp);
                setMaxLines(MAX_LINES);
            } else {
                setMaxLines(Integer.MAX_VALUE);
                setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.mipmap.ic_keyboard_arrow_up_black_18dp);
            }
        }
    }
}
