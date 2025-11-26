package com.fucar.util;

import com.fucar.entity.Account;

public class CurrentUser {
    
    private static Account currentAccount;
    
    public static void setCurrentAccount(Account account) {
        currentAccount = account;
    }
    
    public static Account getCurrentAccount() {
        return currentAccount;
    }
    
    public static void logout() {
        currentAccount = null;
    }
    
    public static boolean isLoggedIn() {
        return currentAccount != null;
    }
    
    public static boolean isAdmin() {
        return currentAccount != null && 
               currentAccount.getRole() == Account.Role.Admin;
    }
    
    public static boolean isCustomer() {
        return currentAccount != null && 
               currentAccount.getRole() == Account.Role.Customer;
    }
}