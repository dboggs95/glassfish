/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
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

package team;

import java.util.Collection;
import java.rmi.RemoteException;
import javax.ejb.FinderException;

public interface Player extends  javax.ejb.EJBObject   {

    public String getPlayerId() throws RemoteException;
    public String getName() throws RemoteException;
    public String getPosition() throws RemoteException;
    public double getSalary() throws RemoteException;
//    public Collection getTeams() throws RemoteException;
//    public Collection getLeagues() throws FinderException, RemoteException;
//    public Collection getSports() throws FinderException, RemoteException;

}