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
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;

public class Parser
{
   private int lineNumber = 0;
   private int index = 0;
   
   public void parseAccountSetup(Bank bank, BufferedReader reader) throws IOException
   {
      String line = reader.readLine().trim();
      
      while (line.length() > 0)
      {
         // read name and initial balance
         String accountName = readAccountName(line, "an amount");
         double balanceValue = readAmount(line);
         
         // create account
         bank.createAccount(accountName, balanceValue);

         lineNumber ++;
         line = reader.readLine().trim();
      }
   }
   
   public Collection<Transaction> parseTransactions(Bank bank, BufferedReader reader)
      throws IOException
   {
      Collection<Transaction> transactions = new ArrayList<Transaction>();
      
      lineNumber ++;
      String line = reader.readLine().trim();
      
      while (line.length() > 0)
      {
         // read first string
         String accountName = readAccountName(line, "an operation");
         Transaction transaction = null;
         try
         {
            // if first string contains a transfer (A->B, for example)
            int transferIndex = accountName.indexOf("->");
            if (transferIndex != -1)
            {
               // create wire transfer
               String fromAccountName = accountName.substring(0, transferIndex);
               String toAccountName = accountName.substring(transferIndex + 2);
               transaction = bank.getWireTransferTransaction(fromAccountName, toAccountName);
            }
            else
            {
               // create deposit or withdrawal
               switch(line.charAt(++index))
               {
                  case '+':
                     transaction = bank.getDepositTransaction(accountName);
                     break;
                  case '-':
                     transaction = bank.getWithdrawalTransaction(accountName);
                     break;
                  default:
                     System.err.println("Unexpected character at line " + lineNumber +
                     ". Should be \'+\' or \'-\', to indicate deposit and withdrawal transactions, respectively.");
                  System.exit(1);
               }
            }
            // read and set transaction amount
            double amount = readAmount(line);
            transaction.setAmount(amount);
            
            // add transaction to collection
            transactions.add(transaction);
         }
         catch(NoSuchAccountException e)
         {
            System.out.println("ERROR invalid transaction: " + e.getMessage());
         }
         
         
         
         lineNumber ++;
         line = reader.readLine().trim();
      }
      // return collection of created transactions
      return transactions;
   }
   
   private String readAccountName(String line, String afterAccount)
   {
      index = line.indexOf(' ');
      if (index == -1)
      {
         System.err.println("Line " + lineNumber +
         " should either contain an account name, followed by " + afterAccount +
            ", or be empty.");
         System.exit(1);
      }
      return line.substring(0, index);
   }
   
   private double readAmount(String line)
   {
      if (line.charAt(++ index) != '$')
      {
         System.err.println("Line " + lineNumber
               + " is missing \'$\' before amount value");
         System.exit(1);
      }
      
      try
      {
         return Double.parseDouble(line.substring(index + 1));
      }
      catch (NumberFormatException e)
      {
         System.err.println("Cannot read amount value in line " + lineNumber +
               ": " + e);
         System.exit(1);
      }
      return 0.0;
   }
}