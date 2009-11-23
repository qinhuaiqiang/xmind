/*
 * Copyright (c) 2006-2008 XMind Ltd. and others.
 * 
 * This file is a part of XMind 3. XMind releases 3 and above are dual-licensed
 * under the Eclipse Public License (EPL), which is available at
 * http://www.eclipse.org/legal/epl-v10.html and the GNU Lesser General Public
 * License (LGPL), which is available at http://www.gnu.org/licenses/lgpl.html
 * See http://www.xmind.net/license.html for details.
 * 
 * Contributors: XMind Ltd. - initial API and implementation
 */
package org.xmind.cathy.internal;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class Startup implements IStartup {

    public void earlyStartup() {
        final IWorkbench workbench = PlatformUI.getWorkbench();
        final Display display = workbench.getDisplay();
        display.asyncExec(new Runnable() {
            public void run() {
                IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
                if (window != null) {
                    hookWindow(window);
                }
            }
        });
    }

    private void hookWindow(IWorkbenchWindow window) {
        Shell shell = window.getShell();
        if (shell != null && !shell.isDisposed()) {
            int hWnd = shell.handle;
            logPrimaryWindow(hWnd);
            shell.addDisposeListener(new DisposeListener() {
                public void widgetDisposed(DisposeEvent e) {
                    Log.get(Log.SINGLETON).delete();
                    e.display.asyncExec(new Runnable() {
                        public void run() {
                            checkRemainingWindow();
                        }
                    });
                }
            });
        }
    }

    private void checkRemainingWindow() {
        if (!PlatformUI.isWorkbenchRunning())
            return;
        IWorkbenchWindow window = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow();
        if (window == null) {
            IWorkbenchWindow[] windows = PlatformUI.getWorkbench()
                    .getWorkbenchWindows();
            if (windows == null || windows.length <= 0)
                return;
            window = windows[0];
        }
        hookWindow(window);
    }

    private void logPrimaryWindow(int hWnd) {
        Log log = Log.get(Log.SINGLETON);
        if (log.exists()) {
            log.delete();
        }
        log.getProperties().setProperty(Log.K_PRIMARY_WINDOW,
                String.valueOf(hWnd));
        log.saveProperties();
    }

}