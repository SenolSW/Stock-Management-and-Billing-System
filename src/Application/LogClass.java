/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

/**
 *
 * @author Anonymous
 */
public class LogClass {

    public String[] EmployeeUserID = new String[1000];
    public String[] EmployeeUserName = new String[1000];
    public String[] EmployeeLoginDateTime = new String[1000];
    public String[] EmployeeLogoutDateTime = new String[1000];
    public String[] AdminUserID = new String[1000];
    public String[] AdminUserName = new String[1000];
    public String[] AdminLoginDateTime = new String[1000];
    public String[] AdminLogoutDateTime = new String[1000];
    public int[] arrayProfit = new int[1000];
    public int[] arrayProfile = new int[1000];
    public int elements = 0;

//Insert Profit
    public void profitInsert(int AP) {
        arrayProfit[elements] = AP;
        elements++;
    }

//Insert Profile
    public void profileInsert(int AE) {
        arrayProfile[elements] = AE;
        elements++;
    }

//Insert Admin
    public void AdminInsert(String AID, String AN, String ALi, String ALo) {
        AdminUserID[elements] = AID;
        AdminUserName[elements] = AN;
        AdminLoginDateTime[elements] = ALi;
        AdminLogoutDateTime[elements] = ALo;
        elements++;
    }

//Insert Employee
    public void EmployeeInsert(String EID, String EN, String ELi, String ELo) {
        EmployeeUserID[elements] = EID;
        EmployeeUserName[elements] = EN;
        EmployeeLoginDateTime[elements] = ELi;
        EmployeeLogoutDateTime[elements] = ELo;
        elements++;
    }

//Search Admin
    public String SearchAdmin(String AID, String AN, String ALi) {
        int x;
        String Aresult = "";
        for (x = 0; x < elements; x++) {
            if (AdminUserID[x].equalsIgnoreCase(AID) || AdminUserName[x].equalsIgnoreCase(AN) || AdminLoginDateTime[x].equalsIgnoreCase(ALi)) {
                Aresult += "Admin ID: " + AdminUserID[x] + "; Name: ";
                Aresult += AdminUserName[x] + "; Login Date and Time: ";
                Aresult += AdminLoginDateTime[x] + "; Logout Date and Time: ";
                Aresult += AdminLogoutDateTime[x] + "\n";
                break;
            }
        }
        return Aresult;
    }

//Search Employee
    public String SearchEmployee(String EID, String EN, String ELi) {
        int x;
        String Eresult = "";
        for (x = 0; x < elements; x++) {
            if (EmployeeUserID[x].equalsIgnoreCase(EID) || EmployeeUserName[x].equalsIgnoreCase(EN) || EmployeeLoginDateTime[x].equalsIgnoreCase(ELi)) {
                Eresult += "Employee ID: " + EmployeeUserID[x] + "; Name: ";
                Eresult += EmployeeUserName[x] + "; Login Date and Time: ";
                Eresult += EmployeeLoginDateTime[x] + "; Logout Date and Time: ";
                Eresult += EmployeeLogoutDateTime[x] + "\n";
                break;
            }
        }
        return Eresult;
    }

//Display Admin
    public String AdminDisplay() {
        String result = "";
        for (int x = 0; x < elements; x++) {
            result += "Admin ID: " + AdminUserID[x] + "; Name: ";
            result += AdminUserName[x] + "; Login Date and Time: ";
            result += AdminLoginDateTime[x] + "; Logout Date and Time: ";
            result += AdminLogoutDateTime[x] + "\n";
        }
        return result;
    }

//Display Employee
    public String EmployeeDisplay() {
        String result = "";
        for (int x = 0; x < elements; x++) {
            result += "Employee ID: " + EmployeeUserID[x] + "; Name: ";
            result += EmployeeUserName[x] + "; Login Date and Time: ";
            result += EmployeeLoginDateTime[x] + "; Logout Date and Time: ";
            result += EmployeeLogoutDateTime[x] + "\n";
        }
        return result;
    }

}
