package com.mouseeffects;

import com.mouseeffects.effects.*;

import java.awt.*;

import javax.swing.*;

import com.sun.awt.AWTUtilities;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;

public class Overlay {
	public static void main(String[] args) {
		Window w = new Window(null);
		JComponent trail = new trail();
		w.add(trail);
		w.pack();
		w.setLocationRelativeTo(null);
		w.setVisible(true);
		w.setAlwaysOnTop(true);
		/*
		 * Sets the background of the window to be transparent.
		 */
		AWTUtilities.setWindowOpaque(w, false);
		setTransparent(w);
	}
	
	private static void setTransparent(Component w) {
		HWND hwnd = getHWnd(w);
		int wl = User32.INSTANCE.GetWindowLong(hwnd, WinUser.GWL_EXSTYLE);
		wl = wl | WinUser.WS_EX_LAYERED | WinUser.WS_EX_TRANSPARENT;
		User32.INSTANCE.SetWindowLong(hwnd, WinUser.GWL_EXSTYLE, wl);
	}
	
	/**
	 * Get the window handle from the OS
	 */
	private static HWND getHWnd(Component w) {
		HWND hwnd = new HWND();
		hwnd.setPointer(Native.getComponentPointer(w));
		return hwnd;
	}
}