package com.dpeter99.ArcaneRituals.arcaneFuel.capability;

import com.dpeter99.ArcaneRituals.arcaneFuel.ArcaneFuelIngredient;

public interface IArcaneFuelProvider {

    int canFulfill(ArcaneFuelIngredient<?> type);



}
