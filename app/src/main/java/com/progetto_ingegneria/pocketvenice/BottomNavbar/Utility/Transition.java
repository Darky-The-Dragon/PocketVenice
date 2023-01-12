package com.progetto_ingegneria.pocketvenice.BottomNavbar.Utility;

import android.content.Context;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.util.AttributeSet;

/**
 * La classe permette di creare delle transizioni per effettuare correttamente le animazioni.
 */
public class Transition extends TransitionSet {
    public Transition() {
        init();
    }

    public Transition(Context context, AttributeSet atts) {
        super(context, atts);
        init();
    }

    private void init() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds())
                .addTransition(new ChangeTransform())
                .addTransition(new ChangeImageTransform());
    }
}
