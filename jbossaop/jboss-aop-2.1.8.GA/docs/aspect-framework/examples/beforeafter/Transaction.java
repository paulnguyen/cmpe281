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

public abstract class Transaction implements Runnable
{
   protected double amount;
   
   public void setAmount(double amount)
   {
      this.amount = amount;
   }
}

class Deposit extends Transaction 
{
   private Account account;
   
   public Deposit(Account account)
   {
      this.account = account;
   }
   
   public void run()
   {
      System.out.println("Depositing " + Bank.CURRENCY.format(amount) +
            " to account " + account.getName());
      account.value += amount;
   }
   
   public String toString()
   {
      return "DEPOSIT: " + account.getName() + " " + Bank.CURRENCY.format(amount);
   }
}

class Withdrawal extends Transaction
{
   private Account account;
   
   public Withdrawal(Account account)
   {
      this.account = account;
   }
   
   public void run()
   {
      System.out.println("Withdrawing " + Bank.CURRENCY.format(amount) +
            " from account " + account.getName());
      account.value -= amount;
   }
   
   public String toString()
   {
      return "WITHDRAWAL: " + account.getName() + " " + Bank.CURRENCY.format(amount);
   }
}

class WireTransfer extends Transaction
{
   private Account fromAccount;
   private Account toAccount;
   
   public WireTransfer(Account fromAccount, Account toAccount)
   {
      this.fromAccount = fromAccount;
      this.toAccount = toAccount;
   }
   
   public void run()
   {
      System.out.println("Transfering " + Bank.CURRENCY.format(amount) +
            " from account " + fromAccount.getName() +
            " to account " + toAccount.getName());
      fromAccount.value -= amount;
      toAccount.value += amount;
   }
   
   public String toString()
   {
      return "TRANSFER: " + fromAccount.getName() + "->" + toAccount.getName() +
      " " + Bank.CURRENCY.format(amount);
   }
}