/*
 * Copyright (C) 2022 QASSA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.SystemProperties;

import javax.inject.Inject;
import android.service.quicksettings.Tile;

import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile.BooleanState;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;


/** Quick settings tile: Spectrum kernel profiles **/
public class SpectrumTile extends QSTileImpl<BooleanState> {

    private static final String SPECTRUM_PROFILE_PROP = "persist.spectrum.profile";
    private static final String SPECTRUM_KERNEL_PROP = "persist.spectrum.kernel";

    private static final int PROFILE_BALANCE = 0;
    private static final int PROFILE_PERFORMANCE = 1;
    private static final int PROFILE_BATTERY = 2;
    private static final int PROFILE_GAMING = 3;
    private static final int PROFILE_COUNT = 4;

    @Inject
    public SpectrumTile(QSHost host) {
        super(host);
    }

    @Override
    public BooleanState newTileState() {
        BooleanState state = new BooleanState();
        state.handlesLongClick = false;
        return state;
    }

    private int getCurrentProfile() {
        return SystemProperties.getInt(SPECTRUM_PROFILE_PROP, PROFILE_BALANCE);
    }

    private boolean isSpectrumEnabled() {
        return SystemProperties.getInt(SPECTRUM_KERNEL_PROP, 0) == 1;
    }

    @Override
    protected void handleClick() {
        if (!isSpectrumEnabled()) return;
        int current = getCurrentProfile();
        int next = (current + 1) % PROFILE_COUNT;
        SystemProperties.set(SPECTRUM_PROFILE_PROP, String.valueOf(next));
        refreshState();
    }

    @Override
    public Intent getLongClickIntent() {
        return null;
    }

    @Override
    protected void handleUpdateState(BooleanState state, Object arg) {
        int profile = getCurrentProfile();
        boolean enabled = isSpectrumEnabled();

        state.value = enabled && profile != PROFILE_BALANCE;

        switch (profile) {
            case PROFILE_BALANCE:
                state.label = mContext.getString(R.string.quick_settings_spectrum_balance);
                state.icon = ResourceIcon.get(R.drawable.ic_qs_spectrum_balance);
                state.state = enabled ? Tile.STATE_INACTIVE : Tile.STATE_UNAVAILABLE;
                break;
            case PROFILE_PERFORMANCE:
                state.label = mContext.getString(R.string.quick_settings_spectrum_performance);
                state.icon = ResourceIcon.get(R.drawable.ic_qs_spectrum_performance);
                state.state = Tile.STATE_ACTIVE;
                break;
            case PROFILE_BATTERY:
                state.label = mContext.getString(R.string.quick_settings_spectrum_battery);
                state.icon = ResourceIcon.get(R.drawable.ic_qs_spectrum_battery);
                state.state = Tile.STATE_ACTIVE;
                break;
            case PROFILE_GAMING:
                state.label = mContext.getString(R.string.quick_settings_spectrum_gaming);
                state.icon = ResourceIcon.get(R.drawable.ic_qs_spectrum_gaming);
                state.state = Tile.STATE_ACTIVE;
                break;
            default:
                state.label = mContext.getString(R.string.quick_settings_spectrum_balance);
                state.icon = ResourceIcon.get(R.drawable.ic_qs_spectrum_balance);
                state.state = Tile.STATE_INACTIVE;
                break;
        }

        state.contentDescription = state.label;
    }

    @Override
    public CharSequence getTileLabel() {
        return mContext.getString(R.string.quick_settings_spectrum_label);
    }

    @Override
    protected String composeChangeAnnouncement() {
        return getTileLabel().toString();
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.QASSA;
    }

    @Override
    public void handleSetListening(boolean listening) {
        // Do nothing
    }
}
