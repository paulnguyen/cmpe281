/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;

public class Driver
{
   public static void main(String[] args) throws Exception
   {
      // open file
      BufferedReader reader = new BufferedReader(new InputStreamReader(
            new FileInputStream("input.txt")));
      // create bank
      Bank bank = new Bank();
      
      // parse file
      System.out.println();
      System.out.println("SETUP");
      System.out.println("=====");
      Parser parser = new Parser();
      parser.parseAccountSetup(bank, reader);
      Collection<Transaction> transactions = parser.parseTransactions(bank, reader);
      
      
      // create transaction threads
      Collection<Thread> threads = new ArrayList<Thread>();
      for (Transaction transaction: transactions)
      {
         threads.add(new Thread(transaction, transaction.toString()));
      }
      
      // execute transaction threads
      System.out.println();
      System.out.println("TRANSACTIONS");
      System.out.println("============");
      for (Thread thread: threads)
      {
         thread.start();
      }
      
      // join transaction threads
      for (Thread thread: threads)
      {
         thread.join();
      }
      
      // print final balance
      System.out.println();
      System.out.println("FINAL BALANCE");
      System.out.println("===== =======");
      bank.printAccounts();
   }
}