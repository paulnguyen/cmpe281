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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class Bank
{
   public static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance();
   
   private Map<String,Account> accounts = new HashMap<String,Account>();
   
   public void createAccount(String name, double amount)
   {
      System.out.println("Creating account \'" + name + "\' with initial balance of $" + amount);
      accounts.put(name, new Account(name, amount));
   }
   
   public void printAccounts()
   {
      for (Account account: accounts.values())
      {
         System.out.println(account);
      }
   }
   
   public Transaction getDepositTransaction(String accountName)
      throws NoSuchAccountException
   {
      return new Deposit(getAccount(accountName));
   }
   
   public Transaction getWithdrawalTransaction(String accountName)
      throws NoSuchAccountException
   {
      return new Withdrawal(getAccount(accountName));
   }
   
   public Transaction getWireTransferTransaction(String fromAccountName,
         String toAccountName) throws NoSuchAccountException
   {
      return new WireTransfer(getAccount(fromAccountName), getAccount(toAccountName));
   }
   
   private Account getAccount(String name) throws NoSuchAccountException
   {
      if (!accounts.containsKey(name))
      {
         throw new NoSuchAccountException("Account named '" + name + "' does not exist.");
      }
      return accounts.get(name);
   }
}