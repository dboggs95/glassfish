/*
 * Copyright (c) 2009, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package test.admincli.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;

/**
 *
 * @author Administrator
 */
public class StreamGobbler extends Thread
{
    InputStream is;
    String type;
    private final StringBuffer stringBuffer = new StringBuffer();

    public StreamGobbler(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }

    public void run()
    {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null) {
                stringBuffer.append(type + ">" + line + "\n");
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
         }
    }

    public String getOutput() {
        return stringBuffer.toString();
    }
}