/*
NumpadView.java
Copyright (C) 2010  Belledonne Communications, Grenoble, France

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/
package org.linphone.ui;

import java.util.ArrayList;
import java.util.Collection;

import org.linphone.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * @author Guillaume Beraudo
 *
 */
public class Numpad extends LinearLayout implements AddressAware {

	private boolean mPlayDtmf;
	public void setPlayDtmf(boolean sendDtmf) {
		this.mPlayDtmf = sendDtmf;
	}

	public Numpad(Context context, boolean playDtmf) {
		super(context);
		mPlayDtmf = playDtmf;
		LayoutInflater.from(context).inflate(R.layout.numpad, this);
		setLongClickable(true);
		onFinishInflate();
	}

	public Numpad(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Numpad);
        mPlayDtmf = 1 == a.getInt(org.linphone.R.styleable.Numpad_play_dtmf, 1);
        a.recycle();
		LayoutInflater.from(context).inflate(R.layout.numpad, this);
		setLongClickable(true);
	}

	@Override
	protected final void onFinishInflate() {
		for (Digit v : retrieveChildren(this, Digit.class)) {
			v.setPlayDtmf(mPlayDtmf);
		}
		super.onFinishInflate();
	}
	public void setAddressWidget(AddressText address) {
		for (AddressAware v : retrieveChildren(this, AddressAware.class)) {
			v.setAddressWidget(address);
		}
	}


	private final <T> Collection<T> retrieveChildren(ViewGroup viewGroup, Class<T> clazz) {
		final Collection<T> views = new ArrayList<T>();

		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View v = viewGroup.getChildAt(i);
			if (v instanceof ViewGroup) {
				views.addAll(retrieveChildren((ViewGroup) v, clazz));
			} else {
				if (clazz.isInstance(v))
					views.add(clazz.cast(v));
			}
		}

		return views;
	}

	public Digit hardkey(KeyEvent e) {
		Digit v = null;
		int id = -1;

		switch (e.getKeyCode()) {
			case KeyEvent.KEYCODE_1:
				id = R.id.Digit1;
				break;
			case KeyEvent.KEYCODE_2:
				id = R.id.Digit2;
				break;
			case KeyEvent.KEYCODE_3:
				id = R.id.Digit3;
				break;
			case KeyEvent.KEYCODE_4:
				id = R.id.Digit4;
				break;
			case KeyEvent.KEYCODE_5:
				id = R.id.Digit5;
				break;
			case KeyEvent.KEYCODE_6:
				id = R.id.Digit6;
				break;
			case KeyEvent.KEYCODE_7:
				id = R.id.Digit7;
				break;
			case KeyEvent.KEYCODE_8:
				id = R.id.Digit8;
				break;
			case KeyEvent.KEYCODE_9:
				id = R.id.Digit9;
				break;
			case KeyEvent.KEYCODE_0:
				id = R.id.Digit00;
				break;
			case KeyEvent.KEYCODE_STAR:
				id = R.id.DigitStar;
				break;
			case KeyEvent.KEYCODE_POUND:
				id = R.id.DigitHash;
				break;
		}

		if (id > 0) {
			v = (Digit)findViewById(id);
			v.requestFocusFromTouch();
		}
		return v;
	}
}
