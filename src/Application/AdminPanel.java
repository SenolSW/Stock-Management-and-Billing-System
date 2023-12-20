/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import java.sql.Connection;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//Print import
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
//SQL imports
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//Date imports
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
//Table imports
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
//Report imports
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Anonymous
 */
public class AdminPanel extends javax.swing.JFrame {

    LogClass LC;
    private static Scanner x;
    File TextFile = new File("AdminLogInfo.txt");
    PreparedStatement pst;
    ResultSet rs;
    LocalDateTime localDate = LocalDateTime.now();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM-dd-yy h:mm a");
    String AdminLoginDateTime = dtf.format(localDate);

    /**
     * Creates new form EmployeePanel
     */
    public AdminPanel() {
        initComponents();
        LC = new LogClass();
        setPanel("card5");
        btnInventory.setForeground(Color.orange);
        SuppliersSidePanel.setVisible(false);
        EmployeesSidePanel.setVisible(false);
        SalesSidePanel.setVisible(false);
        ReportSidePanel.setVisible(false);
        ProfileSidePanel.setVisible(false);
        AuditLogSidePanel.setVisible(false);
        this.setLocationRelativeTo(null);
        DateTime();
        setSize(1030, 700);
        showItems();
        showSuppliers();
        showEmployees();
        showLowStock();
        showSales();
        showItemSupplier();
        maxStockID();
        maxEmployeeID();
        maxSupplierID();
        AdminReadData();
        EmployeeLoadData();
        times();

    }

    public AdminPanel(Locale lcl) { //internationlization
        Locale.setDefault(lcl);
        initComponents();
        LC = new LogClass();
        setPanel("card5");
        btnInventory.setForeground(Color.orange);
        SuppliersSidePanel.setVisible(false);
        EmployeesSidePanel.setVisible(false);
        SalesSidePanel.setVisible(false);
        ReportSidePanel.setVisible(false);
        ProfileSidePanel.setVisible(false);
        AuditLogSidePanel.setVisible(false);
        this.setLocationRelativeTo(null);
        DateTime();
        setSize(1030, 700);
        showItems();
        showSuppliers();
        showEmployees();
        showLowStock();
        showSales();
        showItemSupplier();
        maxStockID();
        maxEmployeeID();
        maxSupplierID();
        AdminReadData();
        EmployeeLoadData();
        times();

    }

    void setPanel(String cname) {
        CardLayout CL = (CardLayout) CardPanel.getLayout();
        CL.show(CardPanel, cname);
    }

    void DateTime() {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM-dd-yy h:mm a"); //setting the date time format
        lblAdminPanelDateTime.setText(dtf.format(localDate)); //Displating date and time through the label

    }
    Timer t;
    SimpleDateFormat st;
    
public void times(){

    t= new Timer(0, new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            //Date dt = new Date();
            LocalDateTime localDate = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM-dd-yyyy    h:mm a");
            //st = new SimpleDateFormat("hh:mm a");
            //String tt = st.format(dt);
            //lblDateTime.setText(tt);
            lblAdminPanelDateTime.setText(dtf.format(localDate));
}});
    t.start();
}
//ReadData
    private void AdminReadData() {
        try {
            if (TextFile.exists() == true) {
                AdminLoadData();
            } else {
                TextFile.createNewFile();
                AdminLoadData();
            }
        } catch (IOException ex) {
            Logger.getLogger(LogClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//Admin LoadData    
    private void AdminLoadData() {
        try {
            x = new Scanner(new File("AdminLogInfo.txt"));
            x.useDelimiter("[;\n]");
            while (x.hasNext()) {
                LC.AdminUserID[LC.elements] = x.next();
                LC.AdminUserName[LC.elements] = x.next();
                LC.AdminLoginDateTime[LC.elements] = x.next();
                LC.AdminLogoutDateTime[LC.elements] = x.next();
                LC.elements++;
            }
            x.close();
        } catch (IOException ex) {
            Logger.getLogger(LogClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//Employee LoadData    
    private void EmployeeLoadData() {
        try {
            x = new Scanner(new File("EmployeeLogInfo.txt"));
            x.useDelimiter("[;\n]");
            while (x.hasNext()) {
                LC.EmployeeUserID[LC.elements] = x.next();
                LC.EmployeeUserName[LC.elements] = x.next();
                LC.EmployeeLoginDateTime[LC.elements] = x.next();
                LC.EmployeeLogoutDateTime[LC.elements] = x.next();
                LC.elements++;
            }
            x.close();
        } catch (IOException ex) {
            Logger.getLogger(LogClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BorderPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnInventory = new javax.swing.JLabel();
        InventorySidePanel = new javax.swing.JPanel();
        lblLowStock = new javax.swing.JLabel();
        SalesSidePanel = new javax.swing.JPanel();
        btnSales = new javax.swing.JLabel();
        ReportSidePanel = new javax.swing.JPanel();
        btnReport = new javax.swing.JLabel();
        ProfileSidePanel = new javax.swing.JPanel();
        btnProfile = new javax.swing.JLabel();
        SuppliersSidePanel = new javax.swing.JPanel();
        btnSuppliers = new javax.swing.JLabel();
        EmployeesSidePanel = new javax.swing.JPanel();
        btnEmployees = new javax.swing.JLabel();
        AuditLogSidePanel = new javax.swing.JPanel();
        btnAuditLog = new javax.swing.JLabel();
        btnSignOut = new javax.swing.JLabel();
        lblAdminPanelDateTime = new javax.swing.JLabel();
        CardPanel = new javax.swing.JPanel();
        InventoryPanel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtItemIDInsert = new javax.swing.JTextField();
        txtItemNameInsert = new javax.swing.JTextField();
        txtItemQuantityInsert = new javax.swing.JTextField();
        txtItemPriceInsert = new javax.swing.JTextField();
        cbItemCategoryInsert = new javax.swing.JComboBox<>();
        btnItemClearInsert = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txtItemSupplierIDInsert = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtItemProfitInsert = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtItemSupplierView = new javax.swing.JTable();
        btnItemInsert = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtItemIDUpdate = new javax.swing.JTextField();
        txtItemNameUpdate = new javax.swing.JTextField();
        txtItemQuantityUpdate = new javax.swing.JTextField();
        txItemPriceUpdate = new javax.swing.JTextField();
        cbItemCategoryUpdate = new javax.swing.JComboBox<>();
        btnItemUpdate = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnItemClearUpdate = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtItemSupplierIDUpdate = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtItemProfitUpdate = new javax.swing.JTextField();
        btnItemDelete = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtItemStockView = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtItemView = new javax.swing.JTable();
        txtItemIDView = new javax.swing.JTextField();
        cbItemSort = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jdcAddedDateSort1 = new com.toedter.calendar.JDateChooser();
        jdcAddedDateSort2 = new com.toedter.calendar.JDateChooser();
        jLabel45 = new javax.swing.JLabel();
        btnItemFilterView = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jdcAddedDateSortReport1 = new com.toedter.calendar.JDateChooser();
        jLabel47 = new javax.swing.JLabel();
        jdcAddedDateSortReport2 = new com.toedter.calendar.JDateChooser();
        btnItemFilterReport = new javax.swing.JButton();
        cbItemSortReport = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        btnItemSortReport = new javax.swing.JButton();
        btnItemResetView = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        SupplierPanel = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtSupplierIDInsert = new javax.swing.JTextField();
        txtSupplierNameInsert = new javax.swing.JTextField();
        txtSupplierEmailInsert = new javax.swing.JTextField();
        txtSupplierContactInsert = new javax.swing.JTextField();
        btnInsertSupplier = new javax.swing.JButton();
        btnSupplierClearInsert = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txtSupplierContactUpdate = new javax.swing.JTextField();
        txtSupplierEmailUpdate = new javax.swing.JTextField();
        txtSupplierNameUpdate = new javax.swing.JTextField();
        txtSupplierIDUpdate = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        btnSupplierClearUpdate = new javax.swing.JButton();
        btnSupplierDelete = new javax.swing.JButton();
        btnSupplierUpdate = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtSupplierView = new javax.swing.JTable();
        txtSupplierIDView = new javax.swing.JTextField();
        cbSupplierSort = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        cbSupplierReportSort = new javax.swing.JComboBox<>();
        btnDisplaySupplierReport = new javax.swing.JButton();
        jLabel63 = new javax.swing.JLabel();
        SalesPanel = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        btnSalesAddAdmin = new javax.swing.JButton();
        txtSalesSubtotalAdmin = new javax.swing.JTextField();
        txtSalesPriceAdmin = new javax.swing.JTextField();
        txtSalesQuantityAdmin = new javax.swing.JTextField();
        txtSalesItemNameAdmin = new javax.swing.JTextField();
        txtSalesItemIDAdmin = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtSalesItemViewAdmin = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        btnSalesDisplayAdmin = new javax.swing.JButton();
        txtSalesTotalAdmin = new javax.swing.JTextField();
        txtSalesAmountAdmin = new javax.swing.JTextField();
        txtSalesBalanceAdmin = new javax.swing.JTextField();
        btnSalesPrintAdmin = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtSalesBillAdmin = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jtSaleItemView = new javax.swing.JTable();
        jdcSaleAddedDateSort1 = new com.toedter.calendar.JDateChooser();
        jdcSaleAddedDateSort2 = new com.toedter.calendar.JDateChooser();
        btnSaleItemFilterView = new javax.swing.JButton();
        cbSaleItemSort = new javax.swing.JComboBox<>();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        txtSaleItemIDView = new javax.swing.JTextField();
        btnSaleItemResetView = new javax.swing.JButton();
        jLabel57 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        btnSaleItemFilterReport = new javax.swing.JButton();
        btnSaleItemSortReport = new javax.swing.JButton();
        jdcSaleAddedDateSortReport4 = new com.toedter.calendar.JDateChooser();
        cbSaleItemSortReport = new javax.swing.JComboBox<>();
        jdcSaleAddedDateSortReport3 = new com.toedter.calendar.JDateChooser();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        ReportPanel = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        btnReportStockFilter = new javax.swing.JButton();
        btnReportItemSort = new javax.swing.JButton();
        cbReportItemSort = new javax.swing.JComboBox<>();
        jdcReportStockFilter1 = new com.toedter.calendar.JDateChooser();
        jdcReportStockFilter2 = new com.toedter.calendar.JDateChooser();
        jLabel49 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        cbReportSupplierSort = new javax.swing.JComboBox<>();
        btnReportSupplierSort = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jdcReportSalesFilter1 = new com.toedter.calendar.JDateChooser();
        jdcReportSalesFilter2 = new com.toedter.calendar.JDateChooser();
        jLabel52 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        cbReportSalesSort = new javax.swing.JComboBox<>();
        btnReportSalesFilter = new javax.swing.JButton();
        btnReportSalesSort = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        cbReportEmployeeSort = new javax.swing.JComboBox<>();
        btnReportEmployeeSort = new javax.swing.JButton();
        ProfilePanel = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        btnProfileUpdate = new javax.swing.JButton();
        txtProfileCPasswordUpdate = new javax.swing.JPasswordField();
        txtProfilePasswordUpdate = new javax.swing.JPasswordField();
        txtProfileContactUpdate = new javax.swing.JTextField();
        txtProfileEmailUpdate = new javax.swing.JTextField();
        txtProfileNameUpdate = new javax.swing.JTextField();
        txtProfileAdminIDUpdate = new javax.swing.JTextField();
        EmployeePanel = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel21 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        txtEmployeeIDUpdate = new javax.swing.JTextField();
        txtEmployeeNameUpdate = new javax.swing.JTextField();
        txtEmployeeEmailUpdate = new javax.swing.JTextField();
        txtEmployeeContactUpdate = new javax.swing.JTextField();
        txtEmployeeGenderUpdate = new javax.swing.JTextField();
        txtEmployeePasswordUpdate = new javax.swing.JPasswordField();
        txtEmployeeCPasswordUpdate = new javax.swing.JPasswordField();
        jLabel51 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        btnEmployeeClear = new javax.swing.JButton();
        btnEmployeeUpdate = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        txtEmployeeIDInsert = new javax.swing.JTextField();
        txtEmployeeNameInsert = new javax.swing.JTextField();
        txtEmployeeEmailInsert = new javax.swing.JTextField();
        txtEmployeeContactInsert = new javax.swing.JTextField();
        txtEmployeeGenderInsert = new javax.swing.JTextField();
        txtEmployeePasswordInsert = new javax.swing.JPasswordField();
        txtEmployeeCPasswordInsert = new javax.swing.JPasswordField();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        btnEmployeeClearInsert = new javax.swing.JButton();
        btnEmployeeInsert = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        cbEmployeeReportSort = new javax.swing.JComboBox<>();
        jLabel50 = new javax.swing.JLabel();
        btnEmployeeReportSort = new javax.swing.JButton();
        txtEmployeeSearch = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtEmployeeView = new javax.swing.JTable();
        jLabel69 = new javax.swing.JLabel();
        cbEmployeeSort = new javax.swing.JComboBox<>();
        LogAuditPanel = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        txtAdminLogSearch = new javax.swing.JTextField();
        btnAdminLogDisplay = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtAdminLogView = new javax.swing.JTextArea();
        jPanel25 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        txtEmployeeLogSearch = new javax.swing.JTextField();
        btnEmployeeLogDisplay = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        txtEmployeeLogView = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1030, 700));

        BorderPanel.setBackground(new java.awt.Color(0, 0, 0));
        BorderPanel.setPreferredSize(new java.awt.Dimension(195, 700));

        jLabel1.setFont(new java.awt.Font("Segoe Script", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/BlackLotus.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Application/Bundle"); // NOI18N
        jLabel1.setText(bundle.getString("AdminPanel.jLabel1.text_1")); // NOI18N

        btnInventory.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnInventory.setForeground(new java.awt.Color(255, 255, 255));
        btnInventory.setText(bundle.getString("AdminPanel.btnInventory.text_1")); // NOI18N
        btnInventory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInventoryMouseClicked(evt);
            }
        });

        InventorySidePanel.setBackground(new java.awt.Color(255, 153, 0));

        javax.swing.GroupLayout InventorySidePanelLayout = new javax.swing.GroupLayout(InventorySidePanel);
        InventorySidePanel.setLayout(InventorySidePanelLayout);
        InventorySidePanelLayout.setHorizontalGroup(
            InventorySidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        InventorySidePanelLayout.setVerticalGroup(
            InventorySidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblLowStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Notification.jpg"))); // NOI18N
        lblLowStock.setText(bundle.getString("AdminPanel.lblLowStock.text_1")); // NOI18N

        SalesSidePanel.setBackground(new java.awt.Color(255, 153, 0));

        javax.swing.GroupLayout SalesSidePanelLayout = new javax.swing.GroupLayout(SalesSidePanel);
        SalesSidePanel.setLayout(SalesSidePanelLayout);
        SalesSidePanelLayout.setHorizontalGroup(
            SalesSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        SalesSidePanelLayout.setVerticalGroup(
            SalesSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnSales.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnSales.setForeground(new java.awt.Color(255, 255, 255));
        btnSales.setText(bundle.getString("AdminPanel.btnSales.text_1")); // NOI18N
        btnSales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalesMouseClicked(evt);
            }
        });

        ReportSidePanel.setBackground(new java.awt.Color(255, 153, 0));

        javax.swing.GroupLayout ReportSidePanelLayout = new javax.swing.GroupLayout(ReportSidePanel);
        ReportSidePanel.setLayout(ReportSidePanelLayout);
        ReportSidePanelLayout.setHorizontalGroup(
            ReportSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        ReportSidePanelLayout.setVerticalGroup(
            ReportSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );

        btnReport.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnReport.setForeground(new java.awt.Color(255, 255, 255));
        btnReport.setText(bundle.getString("AdminPanel.btnReport.text_1")); // NOI18N
        btnReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReportMouseClicked(evt);
            }
        });

        ProfileSidePanel.setBackground(new java.awt.Color(255, 153, 0));

        javax.swing.GroupLayout ProfileSidePanelLayout = new javax.swing.GroupLayout(ProfileSidePanel);
        ProfileSidePanel.setLayout(ProfileSidePanelLayout);
        ProfileSidePanelLayout.setHorizontalGroup(
            ProfileSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        ProfileSidePanelLayout.setVerticalGroup(
            ProfileSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );

        btnProfile.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnProfile.setForeground(new java.awt.Color(255, 255, 255));
        btnProfile.setText(bundle.getString("AdminPanel.btnProfile.text_1")); // NOI18N
        btnProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProfileMouseClicked(evt);
            }
        });

        SuppliersSidePanel.setBackground(new java.awt.Color(255, 153, 0));

        javax.swing.GroupLayout SuppliersSidePanelLayout = new javax.swing.GroupLayout(SuppliersSidePanel);
        SuppliersSidePanel.setLayout(SuppliersSidePanelLayout);
        SuppliersSidePanelLayout.setHorizontalGroup(
            SuppliersSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        SuppliersSidePanelLayout.setVerticalGroup(
            SuppliersSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );

        btnSuppliers.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnSuppliers.setForeground(new java.awt.Color(255, 255, 255));
        btnSuppliers.setText(bundle.getString("AdminPanel.btnSuppliers.text_1")); // NOI18N
        btnSuppliers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuppliersMouseClicked(evt);
            }
        });

        EmployeesSidePanel.setBackground(new java.awt.Color(255, 153, 0));

        javax.swing.GroupLayout EmployeesSidePanelLayout = new javax.swing.GroupLayout(EmployeesSidePanel);
        EmployeesSidePanel.setLayout(EmployeesSidePanelLayout);
        EmployeesSidePanelLayout.setHorizontalGroup(
            EmployeesSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        EmployeesSidePanelLayout.setVerticalGroup(
            EmployeesSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );

        btnEmployees.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnEmployees.setForeground(new java.awt.Color(255, 255, 255));
        btnEmployees.setText(bundle.getString("AdminPanel.btnEmployees.text")); // NOI18N
        btnEmployees.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEmployeesMouseClicked(evt);
            }
        });

        AuditLogSidePanel.setBackground(new java.awt.Color(255, 153, 0));

        javax.swing.GroupLayout AuditLogSidePanelLayout = new javax.swing.GroupLayout(AuditLogSidePanel);
        AuditLogSidePanel.setLayout(AuditLogSidePanelLayout);
        AuditLogSidePanelLayout.setHorizontalGroup(
            AuditLogSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        AuditLogSidePanelLayout.setVerticalGroup(
            AuditLogSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );

        btnAuditLog.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnAuditLog.setForeground(new java.awt.Color(255, 255, 255));
        btnAuditLog.setText(bundle.getString("AdminPanel.btnAuditLog.text_1")); // NOI18N
        btnAuditLog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAuditLogMouseClicked(evt);
            }
        });

        btnSignOut.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnSignOut.setForeground(new java.awt.Color(255, 255, 255));
        btnSignOut.setText(bundle.getString("AdminPanel.btnSignOut.text_1")); // NOI18N
        btnSignOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSignOutMouseClicked(evt);
            }
        });

        lblAdminPanelDateTime.setForeground(new java.awt.Color(255, 255, 255));
        lblAdminPanelDateTime.setText(bundle.getString("AdminPanel.lblAdminPanelDateTime.text_1")); // NOI18N

        javax.swing.GroupLayout BorderPanelLayout = new javax.swing.GroupLayout(BorderPanel);
        BorderPanel.setLayout(BorderPanelLayout);
        BorderPanelLayout.setHorizontalGroup(
            BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BorderPanelLayout.createSequentialGroup()
                .addGroup(BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BorderPanelLayout.createSequentialGroup()
                        .addComponent(InventorySidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLowStock, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSignOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(BorderPanelLayout.createSequentialGroup()
                        .addComponent(EmployeesSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEmployees, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(BorderPanelLayout.createSequentialGroup()
                        .addComponent(SalesSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(BorderPanelLayout.createSequentialGroup()
                        .addComponent(ProfileSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(BorderPanelLayout.createSequentialGroup()
                        .addComponent(ReportSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(BorderPanelLayout.createSequentialGroup()
                        .addComponent(AuditLogSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAuditLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(BorderPanelLayout.createSequentialGroup()
                        .addComponent(SuppliersSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSuppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addGroup(BorderPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblAdminPanelDateTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        BorderPanelLayout.setVerticalGroup(
            BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BorderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(InventorySidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnInventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblLowStock)))
                .addGap(26, 26, 26)
                .addGroup(BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSuppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuppliersSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEmployees, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmployeesSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SalesSidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSales, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ReportSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReport, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ProfileSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AuditLogSidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAuditLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addComponent(btnSignOut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(lblAdminPanelDateTime))
        );

        getContentPane().add(BorderPanel, java.awt.BorderLayout.LINE_START);

        CardPanel.setBackground(new java.awt.Color(255, 153, 0));
        CardPanel.setPreferredSize(new java.awt.Dimension(835, 700));
        CardPanel.setLayout(new java.awt.CardLayout());

        InventoryPanel.setBackground(new java.awt.Color(255, 153, 0));
        InventoryPanel.setPreferredSize(new java.awt.Dimension(835, 700));

        jTabbedPane1.setBackground(new java.awt.Color(255, 153, 0));

        jPanel3.setBackground(new java.awt.Color(255, 153, 0));
        jPanel3.setPreferredSize(new java.awt.Dimension(696, 560));

        jLabel2.setBackground(new java.awt.Color(255, 153, 0));
        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText(bundle.getString("AdminPanel.jLabel2.text_1")); // NOI18N

        jLabel3.setBackground(new java.awt.Color(255, 153, 0));
        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText(bundle.getString("AdminPanel.jLabel3.text_1")); // NOI18N

        jLabel4.setBackground(new java.awt.Color(255, 153, 0));
        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText(bundle.getString("AdminPanel.jLabel4.text_1")); // NOI18N

        jLabel5.setBackground(new java.awt.Color(255, 153, 0));
        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText(bundle.getString("AdminPanel.jLabel5.text_1")); // NOI18N

        jLabel6.setBackground(new java.awt.Color(255, 153, 0));
        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText(bundle.getString("AdminPanel.jLabel6.text_1")); // NOI18N

        txtItemIDInsert.setEditable(false);

        cbItemCategoryInsert.setEditable(true);
        cbItemCategoryInsert.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Beverages", "Bread/Bakery", "Canned/Jarred Goods", "Dair", "Dry/Baking", "Frozen Foods", "Meat", "Produce", "Cleaners", "Paper Goods", "Personal Care", "Baby Care" }));

        btnItemClearInsert.setText(bundle.getString("AdminPanel.btnItemClearInsert.text_1")); // NOI18N
        btnItemClearInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemClearInsertActionPerformed(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(255, 153, 0));
        jLabel17.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText(bundle.getString("AdminPanel.jLabel17.text_1")); // NOI18N

        jLabel18.setBackground(new java.awt.Color(255, 153, 0));
        jLabel18.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText(bundle.getString("AdminPanel.jLabel18.text_1")); // NOI18N

        jtItemSupplierView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier ID", "Supplier Name"
            }
        ));
        jScrollPane4.setViewportView(jtItemSupplierView);
        if (jtItemSupplierView.getColumnModel().getColumnCount() > 0) {
            jtItemSupplierView.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("AdminPanel.jtItemSupplierView.columnModel.title0_1")); // NOI18N
            jtItemSupplierView.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("AdminPanel.jtItemSupplierView.columnModel.title1_1")); // NOI18N
        }

        btnItemInsert.setText(bundle.getString("AdminPanel.btnItemInsert.text_1")); // NOI18N
        btnItemInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemInsertActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtItemIDInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbItemCategoryInsert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtItemQuantityInsert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtItemPriceInsert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtItemProfitInsert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtItemSupplierIDInsert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnItemInsert)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnItemClearInsert))))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(txtItemNameInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(184, 184, 184))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtItemIDInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtItemNameInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbItemCategoryInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtItemQuantityInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtItemPriceInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtItemProfitInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtItemSupplierIDInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(104, 104, 104)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnItemClearInsert)
                    .addComponent(btnItemInsert))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(bundle.getString("AdminPanel.jPanel3.TabConstraints.tabTitle_1"), jPanel3); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 153, 0));

        jLabel7.setBackground(new java.awt.Color(255, 153, 0));
        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText(bundle.getString("AdminPanel.jLabel7.text_1")); // NOI18N

        jLabel8.setBackground(new java.awt.Color(255, 153, 0));
        jLabel8.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText(bundle.getString("AdminPanel.jLabel8.text_1")); // NOI18N

        jLabel9.setBackground(new java.awt.Color(255, 153, 0));
        jLabel9.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText(bundle.getString("AdminPanel.jLabel9.text_1")); // NOI18N

        txtItemIDUpdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtItemIDUpdateKeyReleased(evt);
            }
        });

        cbItemCategoryUpdate.setEditable(true);
        cbItemCategoryUpdate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Beverages", "Bread/Bakery", "Canned/Jarred Goods", "Dair", "Dry/Baking", "Frozen Foods", "Meat", "Produce", "Cleaners", "Paper Goods", "Personal Care", "Baby Care" }));

        btnItemUpdate.setText(bundle.getString("AdminPanel.btnItemUpdate.text_1")); // NOI18N
        btnItemUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemUpdateActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(255, 153, 0));
        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText(bundle.getString("AdminPanel.jLabel10.text_1")); // NOI18N

        btnItemClearUpdate.setText(bundle.getString("AdminPanel.btnItemClearUpdate.text_1")); // NOI18N
        btnItemClearUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemClearUpdateActionPerformed(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(255, 153, 0));
        jLabel11.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText(bundle.getString("AdminPanel.jLabel11.text_1")); // NOI18N

        jLabel13.setBackground(new java.awt.Color(255, 153, 0));
        jLabel13.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText(bundle.getString("AdminPanel.jLabel13.text_1")); // NOI18N

        jLabel19.setBackground(new java.awt.Color(255, 153, 0));
        jLabel19.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText(bundle.getString("AdminPanel.jLabel19.text_1")); // NOI18N

        btnItemDelete.setText(bundle.getString("AdminPanel.btnItemDelete.text_1")); // NOI18N
        btnItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemDeleteActionPerformed(evt);
            }
        });

        jtItemStockView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Item Name", "Quantity"
            }
        ));
        jScrollPane7.setViewportView(jtItemStockView);
        if (jtItemStockView.getColumnModel().getColumnCount() > 0) {
            jtItemStockView.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("AdminPanel.jtItemStockView.columnModel.title0_1")); // NOI18N
            jtItemStockView.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("AdminPanel.jtItemStockView.columnModel.title1_1")); // NOI18N
            jtItemStockView.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("AdminPanel.jtItemStockView.columnModel.title2_1")); // NOI18N
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel19)
                    .addComponent(jLabel13)
                    .addComponent(jLabel10))
                .addGap(49, 49, 49)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnItemDelete)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(btnItemUpdate)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnItemClearUpdate))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtItemNameUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbItemCategoryUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtItemQuantityUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txItemPriceUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtItemProfitUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtItemSupplierIDUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtItemIDUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtItemIDUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtItemNameUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cbItemCategoryUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtItemQuantityUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txItemPriceUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtItemProfitUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtItemSupplierIDUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnItemUpdate)
                    .addComponent(btnItemClearUpdate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnItemDelete)
                .addGap(152, 152, 152))
        );

        jTabbedPane1.addTab(bundle.getString("AdminPanel.jPanel4.TabConstraints.tabTitle_1"), jPanel4); // NOI18N

        jPanel7.setBackground(new java.awt.Color(255, 153, 0));

        jtItemView.setBackground(new java.awt.Color(204, 204, 204));
        jtItemView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Item Name", "Category", "Quantity", "Sale Price", "Profit", "Added Date", "Supplier ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtItemView);
        if (jtItemView.getColumnModel().getColumnCount() > 0) {
            jtItemView.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("AdminPanel.jtItemView.columnModel.title0_1")); // NOI18N
            jtItemView.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("AdminPanel.jtItemView.columnModel.title1_1")); // NOI18N
            jtItemView.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("AdminPanel.jtItemView.columnModel.title2_1")); // NOI18N
            jtItemView.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("AdminPanel.jtItemView.columnModel.title3_1")); // NOI18N
            jtItemView.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("AdminPanel.jtItemView.columnModel.title4_1")); // NOI18N
            jtItemView.getColumnModel().getColumn(5).setHeaderValue(bundle.getString("AdminPanel.jtItemView.columnModel.title5_1")); // NOI18N
            jtItemView.getColumnModel().getColumn(6).setHeaderValue(bundle.getString("AdminPanel.jtItemView.columnModel.title6_1")); // NOI18N
            jtItemView.getColumnModel().getColumn(7).setHeaderValue(bundle.getString("AdminPanel.jtItemView.columnModel.title7_1")); // NOI18N
        }

        txtItemIDView.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtItemIDViewKeyReleased(evt);
            }
        });

        cbItemSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item ID", "A-Z", "Z-A", "Category", "Highest Profit", "Lowest Profit", "Supplier ID" }));
        cbItemSort.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbItemSortItemStateChanged(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Trebuchet MS", 1, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText(bundle.getString("AdminPanel.jLabel15.text_1")); // NOI18N

        jLabel45.setFont(new java.awt.Font("Trebuchet MS", 1, 16)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText(bundle.getString("AdminPanel.jLabel45.text_1")); // NOI18N

        btnItemFilterView.setText(bundle.getString("AdminPanel.btnItemFilterView.text_1")); // NOI18N
        btnItemFilterView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemFilterViewActionPerformed(evt);
            }
        });

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AdminPanel.jPanel15.border.title_1"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 1, 12))); // NOI18N

        jLabel47.setFont(new java.awt.Font("Trebuchet MS", 1, 16)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText(bundle.getString("AdminPanel.jLabel47.text_1")); // NOI18N

        btnItemFilterReport.setText(bundle.getString("AdminPanel.btnItemFilterReport.text_1")); // NOI18N
        btnItemFilterReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemFilterReportActionPerformed(evt);
            }
        });

        cbItemSortReport.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item ID", "Item Name", "Category", "Highest Profit", "Lowest Profit", "Supplier ID" }));

        jLabel16.setFont(new java.awt.Font("Trebuchet MS", 1, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText(bundle.getString("AdminPanel.jLabel16.text_1")); // NOI18N

        btnItemSortReport.setText(bundle.getString("AdminPanel.btnItemSortReport.text_1")); // NOI18N
        btnItemSortReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemSortReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jdcAddedDateSortReport1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel47)
                        .addGap(18, 18, 18)
                        .addComponent(jdcAddedDateSortReport2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbItemSortReport, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(89, 89, 89)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnItemFilterReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnItemSortReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(186, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnItemFilterReport)
                    .addComponent(jdcAddedDateSortReport1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47)
                    .addComponent(jdcAddedDateSortReport2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbItemSortReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(btnItemSortReport))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        btnItemResetView.setText(bundle.getString("AdminPanel.btnItemResetView.text_1")); // NOI18N
        btnItemResetView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemResetViewActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Trebuchet MS", 1, 16)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText(bundle.getString("AdminPanel.jLabel35.text_1")); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jdcAddedDateSort1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel45)
                                .addGap(18, 18, 18)
                                .addComponent(jdcAddedDateSort2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(121, 121, 121)
                                .addComponent(btnItemFilterView))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtItemIDView, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbItemSort, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(62, 62, 62)
                        .addComponent(btnItemResetView)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtItemIDView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbItemSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(btnItemResetView)
                    .addComponent(jLabel35))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jdcAddedDateSort1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45)
                    .addComponent(jdcAddedDateSort2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnItemFilterView))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        jTabbedPane1.addTab(bundle.getString("AdminPanel.jPanel7.TabConstraints.tabTitle_1"), jPanel7); // NOI18N

        javax.swing.GroupLayout InventoryPanelLayout = new javax.swing.GroupLayout(InventoryPanel);
        InventoryPanel.setLayout(InventoryPanelLayout);
        InventoryPanelLayout.setHorizontalGroup(
            InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 835, Short.MAX_VALUE)
            .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(InventoryPanelLayout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addComponent(jTabbedPane1)
                    .addGap(3, 3, 3)))
        );
        InventoryPanelLayout.setVerticalGroup(
            InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
            .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE))
        );

        CardPanel.add(InventoryPanel, "card5");

        SupplierPanel.setBackground(new java.awt.Color(255, 153, 0));
        SupplierPanel.setPreferredSize(new java.awt.Dimension(835, 700));

        jTabbedPane2.setBackground(new java.awt.Color(255, 153, 0));

        jPanel5.setBackground(new java.awt.Color(255, 153, 0));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AdminPanel.jPanel6.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(204, 204, 204))); // NOI18N

        jLabel12.setBackground(new java.awt.Color(255, 153, 0));
        jLabel12.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText(bundle.getString("AdminPanel.jLabel12.text_1")); // NOI18N

        jLabel21.setBackground(new java.awt.Color(255, 153, 0));
        jLabel21.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText(bundle.getString("AdminPanel.jLabel21.text_1")); // NOI18N

        jLabel23.setBackground(new java.awt.Color(255, 153, 0));
        jLabel23.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText(bundle.getString("AdminPanel.jLabel23.text_1")); // NOI18N

        jLabel24.setBackground(new java.awt.Color(255, 153, 0));
        jLabel24.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText(bundle.getString("AdminPanel.jLabel24.text_1")); // NOI18N

        txtSupplierIDInsert.setEditable(false);

        btnInsertSupplier.setText(bundle.getString("AdminPanel.btnInsertSupplier.text_1")); // NOI18N
        btnInsertSupplier.setPreferredSize(new java.awt.Dimension(67, 23));
        btnInsertSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertSupplierActionPerformed(evt);
            }
        });

        btnSupplierClearInsert.setText(bundle.getString("AdminPanel.btnSupplierClearInsert.text_1")); // NOI18N
        btnSupplierClearInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupplierClearInsertActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(47, 47, 47)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtSupplierNameInsert)
                    .addComponent(txtSupplierIDInsert)
                    .addComponent(txtSupplierEmailInsert, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addComponent(txtSupplierContactInsert, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSupplierClearInsert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInsertSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(51, 51, 51))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtSupplierIDInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInsertSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtSupplierNameInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSupplierClearInsert))
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtSupplierEmailInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtSupplierContactInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AdminPanel.jPanel9.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(204, 204, 204))); // NOI18N

        txtSupplierIDUpdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSupplierIDUpdateKeyReleased(evt);
            }
        });

        jLabel30.setBackground(new java.awt.Color(255, 153, 0));
        jLabel30.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText(bundle.getString("AdminPanel.jLabel30.text_1")); // NOI18N

        jLabel31.setBackground(new java.awt.Color(255, 153, 0));
        jLabel31.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText(bundle.getString("AdminPanel.jLabel31.text_1")); // NOI18N

        jLabel28.setBackground(new java.awt.Color(255, 153, 0));
        jLabel28.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText(bundle.getString("AdminPanel.jLabel28.text_1")); // NOI18N

        jLabel29.setBackground(new java.awt.Color(255, 153, 0));
        jLabel29.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText(bundle.getString("AdminPanel.jLabel29.text_1")); // NOI18N

        btnSupplierClearUpdate.setText(bundle.getString("AdminPanel.btnSupplierClearUpdate.text_1")); // NOI18N
        btnSupplierClearUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupplierClearUpdateActionPerformed(evt);
            }
        });

        btnSupplierDelete.setText(bundle.getString("AdminPanel.btnSupplierDelete.text_1")); // NOI18N
        btnSupplierDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupplierDeleteActionPerformed(evt);
            }
        });

        btnSupplierUpdate.setText(bundle.getString("AdminPanel.btnSupplierUpdate.text_1")); // NOI18N
        btnSupplierUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupplierUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel30)
                            .addComponent(jLabel29))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSupplierContactUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                            .addComponent(txtSupplierEmailUpdate)
                            .addComponent(txtSupplierNameUpdate)
                            .addComponent(txtSupplierIDUpdate)))
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSupplierDelete, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSupplierUpdate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSupplierClearUpdate, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(42, 42, 42))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtSupplierIDUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSupplierUpdate))
                .addGap(27, 27, 27)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtSupplierNameUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSupplierDelete))
                .addGap(26, 26, 26)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtSupplierEmailUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSupplierClearUpdate))
                .addGap(26, 26, 26)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtSupplierContactUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab(bundle.getString("AdminPanel.jPanel5.TabConstraints.tabTitle_1"), jPanel5); // NOI18N

        jPanel10.setBackground(new java.awt.Color(255, 153, 0));

        jtSupplierView.setBackground(new java.awt.Color(204, 204, 204));
        jtSupplierView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier ID", "Supplier Name", "Email", "Contact Number"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jtSupplierView);
        if (jtSupplierView.getColumnModel().getColumnCount() > 0) {
            jtSupplierView.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("AdminPanel.jtSupplierView.columnModel.title0")); // NOI18N
            jtSupplierView.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("AdminPanel.jtSupplierView.columnModel.title1")); // NOI18N
            jtSupplierView.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("AdminPanel.jtSupplierView.columnModel.title2")); // NOI18N
            jtSupplierView.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("AdminPanel.jtSupplierView.columnModel.title3")); // NOI18N
        }

        txtSupplierIDView.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSupplierIDViewKeyReleased(evt);
            }
        });

        cbSupplierSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Supplier ID", "A-Z", "Z-A" }));
        cbSupplierSort.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbSupplierSortItemStateChanged(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Trebuchet MS", 1, 16)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText(bundle.getString("AdminPanel.jLabel36.text_1")); // NOI18N

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AdminPanel.jPanel14.border.title_1"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 1, 12))); // NOI18N

        jLabel46.setFont(new java.awt.Font("Trebuchet MS", 1, 16)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText(bundle.getString("AdminPanel.jLabel46.text_1")); // NOI18N

        cbSupplierReportSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Supplier ID", "Supplier Name" }));

        btnDisplaySupplierReport.setText(bundle.getString("AdminPanel.btnDisplaySupplierReport.text_1")); // NOI18N
        btnDisplaySupplierReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplaySupplierReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel46)
                .addGap(28, 28, 28)
                .addComponent(cbSupplierReportSort, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDisplaySupplierReport)
                .addGap(54, 54, 54))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(cbSupplierReportSort, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDisplaySupplierReport))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jLabel63.setFont(new java.awt.Font("Trebuchet MS", 1, 16)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText(bundle.getString("AdminPanel.jLabel63.text")); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel63)
                        .addGap(67, 67, 67)
                        .addComponent(txtSupplierIDView, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)
                        .addComponent(jLabel36)
                        .addGap(28, 28, 28)
                        .addComponent(cbSupplierSort, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSupplierIDView, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(cbSupplierSort, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel63))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
        );

        jTabbedPane2.addTab(bundle.getString("AdminPanel.jPanel10.TabConstraints.tabTitle_1"), jPanel10); // NOI18N

        javax.swing.GroupLayout SupplierPanelLayout = new javax.swing.GroupLayout(SupplierPanel);
        SupplierPanel.setLayout(SupplierPanelLayout);
        SupplierPanelLayout.setHorizontalGroup(
            SupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 835, Short.MAX_VALUE)
            .addGroup(SupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(SupplierPanelLayout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addComponent(jTabbedPane2)
                    .addGap(3, 3, 3)))
        );
        SupplierPanelLayout.setVerticalGroup(
            SupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
            .addGroup(SupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING))
        );

        CardPanel.add(SupplierPanel, "card6");

        SalesPanel.setBackground(new java.awt.Color(255, 153, 0));
        SalesPanel.setPreferredSize(new java.awt.Dimension(835, 700));

        jPanel1.setBackground(new java.awt.Color(255, 153, 0));

        btnSalesAddAdmin.setText(bundle.getString("AdminPanel.btnSalesAddAdmin.text_1")); // NOI18N
        btnSalesAddAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalesAddAdminActionPerformed(evt);
            }
        });

        txtSalesSubtotalAdmin.setEditable(false);

        txtSalesPriceAdmin.setEditable(false);

        txtSalesQuantityAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSalesQuantityAdminKeyReleased(evt);
            }
        });

        txtSalesItemNameAdmin.setEditable(false);

        txtSalesItemIDAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSalesItemIDAdminKeyReleased(evt);
            }
        });

        jLabel37.setText(bundle.getString("AdminPanel.jLabel37.text_1")); // NOI18N

        jLabel38.setText(bundle.getString("AdminPanel.jLabel38.text_1")); // NOI18N

        jLabel39.setText(bundle.getString("AdminPanel.jLabel39.text_1")); // NOI18N

        jLabel40.setText(bundle.getString("AdminPanel.jLabel40.text_1")); // NOI18N

        jLabel41.setText(bundle.getString("AdminPanel.jLabel41.text_1")); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38)
                            .addComponent(jLabel39)
                            .addComponent(jLabel37)
                            .addComponent(jLabel40)
                            .addComponent(jLabel41))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSalesItemIDAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                            .addComponent(txtSalesItemNameAdmin)
                            .addComponent(txtSalesQuantityAdmin)
                            .addComponent(txtSalesPriceAdmin)
                            .addComponent(txtSalesSubtotalAdmin)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalesAddAdmin)))
                .addGap(29, 29, 29))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSalesItemIDAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addGap(20, 20, 20)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSalesItemNameAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addGap(23, 23, 23)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSalesQuantityAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSalesPriceAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addGap(20, 20, 20)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSalesSubtotalAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addGap(18, 18, 18)
                .addComponent(btnSalesAddAdmin)
                .addGap(22, 22, 22))
        );

        jtSalesItemViewAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Item Name", "Quantity ", "Price", "Subtotal"
            }
        ));
        jScrollPane5.setViewportView(jtSalesItemViewAdmin);

        jLabel42.setText(bundle.getString("AdminPanel.jLabel42.text_1")); // NOI18N

        jLabel43.setText(bundle.getString("AdminPanel.jLabel43.text_1")); // NOI18N

        jLabel44.setText(bundle.getString("AdminPanel.jLabel44.text_1")); // NOI18N

        btnSalesDisplayAdmin.setText(bundle.getString("AdminPanel.btnSalesDisplayAdmin.text_1")); // NOI18N
        btnSalesDisplayAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalesDisplayAdminActionPerformed(evt);
            }
        });

        txtSalesTotalAdmin.setEditable(false);

        txtSalesAmountAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSalesAmountAdminKeyReleased(evt);
            }
        });

        txtSalesBalanceAdmin.setEditable(false);

        btnSalesPrintAdmin.setText(bundle.getString("AdminPanel.btnSalesPrintAdmin.text_1")); // NOI18N
        btnSalesPrintAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalesPrintAdminActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(btnSalesPrintAdmin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(btnSalesDisplayAdmin))
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel43)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel42)
                                .addComponent(jLabel44))
                            .addGap(35, 35, 35)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtSalesTotalAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                .addComponent(txtSalesAmountAdmin)
                                .addComponent(txtSalesBalanceAdmin)))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSalesTotalAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSalesAmountAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addGap(23, 23, 23)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSalesBalanceAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addGap(26, 26, 26)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalesDisplayAdmin)
                    .addComponent(btnSalesPrintAdmin))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        txtSalesBillAdmin.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jScrollPane3.setViewportView(txtSalesBillAdmin);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(100, 100, 100)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(179, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab(bundle.getString("AdminPanel.jPanel1.TabConstraints.tabTitle_1"), jPanel1); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));

        jtSaleItemView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sale ID", "Total", "Profit", "Sold Date"
            }
        ));
        jScrollPane8.setViewportView(jtSaleItemView);
        if (jtSaleItemView.getColumnModel().getColumnCount() > 0) {
            jtSaleItemView.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("AdminPanel.jtSaleItemView.columnModel.title0_1")); // NOI18N
            jtSaleItemView.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("AdminPanel.jtSaleItemView.columnModel.title1_1")); // NOI18N
            jtSaleItemView.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("AdminPanel.jtSaleItemView.columnModel.title2_1")); // NOI18N
            jtSaleItemView.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("AdminPanel.jtSaleItemView.columnModel.title3_1")); // NOI18N
        }

        btnSaleItemFilterView.setText(bundle.getString("AdminPanel.btnSaleItemFilterView.text_1")); // NOI18N
        btnSaleItemFilterView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaleItemFilterViewActionPerformed(evt);
            }
        });

        cbSaleItemSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sale ID", "Highest Profit", "Lowest Profit" }));
        cbSaleItemSort.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbSaleItemSortItemStateChanged(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Trebuchet MS", 1, 16)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText(bundle.getString("AdminPanel.jLabel55.text_1")); // NOI18N

        jLabel56.setFont(new java.awt.Font("Trebuchet MS", 1, 16)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText(bundle.getString("AdminPanel.jLabel56.text_1")); // NOI18N

        txtSaleItemIDView.setText(bundle.getString("AdminPanel.txtSaleItemIDView.text_1")); // NOI18N
        txtSaleItemIDView.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSaleItemIDViewKeyReleased(evt);
            }
        });

        btnSaleItemResetView.setText(bundle.getString("AdminPanel.btnSaleItemResetView.text_1")); // NOI18N
        btnSaleItemResetView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaleItemResetViewActionPerformed(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Trebuchet MS", 1, 16)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText(bundle.getString("AdminPanel.jLabel57.text_1")); // NOI18N

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AdminPanel.jPanel18.border.title_1"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 1, 12))); // NOI18N

        btnSaleItemFilterReport.setText(bundle.getString("AdminPanel.btnSaleItemFilterReport.text_1")); // NOI18N
        btnSaleItemFilterReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaleItemFilterReportActionPerformed(evt);
            }
        });

        btnSaleItemSortReport.setText(bundle.getString("AdminPanel.btnSaleItemSortReport.text_1")); // NOI18N
        btnSaleItemSortReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaleItemSortReportActionPerformed(evt);
            }
        });

        cbSaleItemSortReport.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sale ID", "Highest Profit", "Lowest Profit" }));

        jLabel53.setFont(new java.awt.Font("Trebuchet MS", 1, 16)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText(bundle.getString("AdminPanel.jLabel53.text_1")); // NOI18N

        jLabel54.setFont(new java.awt.Font("Trebuchet MS", 1, 16)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText(bundle.getString("AdminPanel.jLabel54.text_1")); // NOI18N

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jdcSaleAddedDateSortReport3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel53)
                        .addGap(18, 18, 18)
                        .addComponent(jdcSaleAddedDateSortReport4, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addGap(45, 45, 45)
                        .addComponent(cbSaleItemSortReport, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(79, 79, 79)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSaleItemFilterReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSaleItemSortReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(179, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel53)
                    .addComponent(jdcSaleAddedDateSortReport3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdcSaleAddedDateSortReport4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSaleItemFilterReport))
                .addGap(32, 32, 32)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaleItemSortReport)
                    .addComponent(cbSaleItemSortReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jdcSaleAddedDateSort1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(46, 46, 46)
                                    .addComponent(jLabel57)
                                    .addGap(27, 27, 27)
                                    .addComponent(jdcSaleAddedDateSort2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel56)
                                    .addGap(68, 68, 68)
                                    .addComponent(txtSaleItemIDView, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(64, 64, 64)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel55)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbSaleItemSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(btnSaleItemFilterView)))
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(638, 638, 638)
                            .addComponent(btnSaleItemResetView))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(jLabel56)
                    .addComponent(cbSaleItemSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSaleItemIDView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel57)
                    .addComponent(jdcSaleAddedDateSort1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdcSaleAddedDateSort2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSaleItemFilterView)
                        .addComponent(btnSaleItemResetView)))
                .addGap(37, 37, 37)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab(bundle.getString("AdminPanel.jPanel2.TabConstraints.tabTitle_1"), jPanel2); // NOI18N

        javax.swing.GroupLayout SalesPanelLayout = new javax.swing.GroupLayout(SalesPanel);
        SalesPanel.setLayout(SalesPanelLayout);
        SalesPanelLayout.setHorizontalGroup(
            SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );
        SalesPanelLayout.setVerticalGroup(
            SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );

        CardPanel.add(SalesPanel, "card2");

        ReportPanel.setBackground(new java.awt.Color(255, 153, 0));
        ReportPanel.setPreferredSize(new java.awt.Dimension(835, 700));

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AdminPanel.jPanel16.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 1, 12))); // NOI18N

        jLabel48.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText(bundle.getString("AdminPanel.jLabel48.text")); // NOI18N

        btnReportStockFilter.setText(bundle.getString("AdminPanel.btnReportStockFilter.text")); // NOI18N
        btnReportStockFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportStockFilterActionPerformed(evt);
            }
        });

        btnReportItemSort.setText(bundle.getString("AdminPanel.btnReportItemSort.text")); // NOI18N
        btnReportItemSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportItemSortActionPerformed(evt);
            }
        });

        cbReportItemSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item ID", "Item Name", "Category", "Highest Profit", "Lowest Profit", "Supplier ID" }));

        jLabel49.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText(bundle.getString("AdminPanel.jLabel49.text")); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addGap(49, 49, 49)
                        .addComponent(cbReportItemSort, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jdcReportStockFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel48)
                        .addGap(36, 36, 36)
                        .addComponent(jdcReportStockFilter2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)))
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnReportStockFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReportItemSort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(58, 58, 58))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnReportStockFilter)
                    .addComponent(jdcReportStockFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdcReportStockFilter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReportItemSort)
                    .addComponent(cbReportItemSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addContainerGap())
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AdminPanel.jPanel17.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 1, 12))); // NOI18N

        jLabel59.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText(bundle.getString("AdminPanel.jLabel59.text")); // NOI18N

        cbReportSupplierSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Supplier ID", "Supplier Name" }));

        btnReportSupplierSort.setText(bundle.getString("AdminPanel.btnReportSupplierSort.text")); // NOI18N
        btnReportSupplierSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportSupplierSortActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel59)
                .addGap(51, 51, 51)
                .addComponent(cbReportSupplierSort, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145)
                .addComponent(btnReportSupplierSort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(54, 54, 54))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbReportSupplierSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReportSupplierSort)
                    .addComponent(jLabel59))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AdminPanel.jPanel19.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 1, 12))); // NOI18N

        jLabel52.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText(bundle.getString("AdminPanel.jLabel52.text")); // NOI18N

        jLabel60.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText(bundle.getString("AdminPanel.jLabel60.text")); // NOI18N

        cbReportSalesSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sale ID", "Highest Profit", "Lowest Profit" }));

        btnReportSalesFilter.setText(bundle.getString("AdminPanel.btnReportSalesFilter.text")); // NOI18N
        btnReportSalesFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportSalesFilterActionPerformed(evt);
            }
        });

        btnReportSalesSort.setText(bundle.getString("AdminPanel.btnReportSalesSort.text")); // NOI18N
        btnReportSalesSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportSalesSortActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel60)
                        .addGap(49, 49, 49)
                        .addComponent(cbReportSalesSort, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jdcReportSalesFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jLabel52)
                        .addGap(51, 51, 51)
                        .addComponent(jdcReportSalesFilter2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReportSalesFilter)
                    .addComponent(btnReportSalesSort, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnReportSalesFilter)
                    .addComponent(jLabel52)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jdcReportSalesFilter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jdcReportSalesFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbReportSalesSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60)
                    .addComponent(btnReportSalesSort))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AdminPanel.jPanel20.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 1, 12))); // NOI18N

        jLabel58.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText(bundle.getString("AdminPanel.jLabel58.text")); // NOI18N

        cbReportEmployeeSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employee ID", "Name" }));

        btnReportEmployeeSort.setText(bundle.getString("AdminPanel.btnReportEmployeeSort.text")); // NOI18N
        btnReportEmployeeSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportEmployeeSortActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel58)
                .addGap(51, 51, 51)
                .addComponent(cbReportEmployeeSort, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(146, 146, 146)
                .addComponent(btnReportEmployeeSort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(57, 57, 57))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(cbReportEmployeeSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReportEmployeeSort))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ReportPanelLayout = new javax.swing.GroupLayout(ReportPanel);
        ReportPanel.setLayout(ReportPanelLayout);
        ReportPanelLayout.setHorizontalGroup(
            ReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReportPanelLayout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addGroup(ReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(193, Short.MAX_VALUE))
        );
        ReportPanelLayout.setVerticalGroup(
            ReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReportPanelLayout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
        );

        CardPanel.add(ReportPanel, "card3");

        ProfilePanel.setBackground(new java.awt.Color(255, 153, 0));
        ProfilePanel.setPreferredSize(new java.awt.Dimension(835, 700));

        jLabel22.setBackground(new java.awt.Color(255, 153, 0));
        jLabel22.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText(bundle.getString("AdminPanel.jLabel22.text_1")); // NOI18N

        jLabel25.setBackground(new java.awt.Color(255, 153, 0));
        jLabel25.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText(bundle.getString("AdminPanel.jLabel25.text_1")); // NOI18N

        jLabel26.setBackground(new java.awt.Color(255, 153, 0));
        jLabel26.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText(bundle.getString("AdminPanel.jLabel26.text_1")); // NOI18N

        jLabel27.setBackground(new java.awt.Color(255, 153, 0));
        jLabel27.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText(bundle.getString("AdminPanel.jLabel27.text_1")); // NOI18N

        jLabel32.setBackground(new java.awt.Color(255, 153, 0));
        jLabel32.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText(bundle.getString("AdminPanel.jLabel32.text_1")); // NOI18N

        jLabel33.setBackground(new java.awt.Color(255, 153, 0));
        jLabel33.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText(bundle.getString("AdminPanel.jLabel33.text_1")); // NOI18N

        btnProfileUpdate.setText(bundle.getString("AdminPanel.btnProfileUpdate.text_1")); // NOI18N
        btnProfileUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileUpdateActionPerformed(evt);
            }
        });

        txtProfileAdminIDUpdate.setEditable(false);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnProfileUpdate))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel22)
                            .addComponent(jLabel32)
                            .addComponent(jLabel33)
                            .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel26)
                                .addComponent(jLabel25)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtProfileNameUpdate, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProfileEmailUpdate, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProfileContactUpdate, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProfileAdminIDUpdate)
                            .addComponent(txtProfilePasswordUpdate)
                            .addComponent(txtProfileCPasswordUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(45, 45, 45))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProfileAdminIDUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(26, 26, 26)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProfileNameUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(26, 26, 26)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProfileEmailUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(31, 31, 31)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProfileContactUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(26, 26, 26)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProfilePasswordUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addGap(26, 26, 26)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProfileCPasswordUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(40, 40, 40)
                .addComponent(btnProfileUpdate)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout ProfilePanelLayout = new javax.swing.GroupLayout(ProfilePanel);
        ProfilePanel.setLayout(ProfilePanelLayout);
        ProfilePanelLayout.setHorizontalGroup(
            ProfilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProfilePanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(345, Short.MAX_VALUE))
        );
        ProfilePanelLayout.setVerticalGroup(
            ProfilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ProfilePanelLayout.createSequentialGroup()
                .addContainerGap(123, Short.MAX_VALUE)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117))
        );

        CardPanel.add(ProfilePanel, "card4");

        EmployeePanel.setBackground(new java.awt.Color(255, 153, 0));

        jPanel21.setBackground(new java.awt.Color(255, 153, 0));

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("AdminPanel.jPanel8.border.title"))); // NOI18N

        txtEmployeeIDUpdate.setText(bundle.getString("AdminPanel.txtEmployeeIDUpdate.text")); // NOI18N
        txtEmployeeIDUpdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmployeeIDUpdateKeyReleased(evt);
            }
        });

        txtEmployeeNameUpdate.setText(bundle.getString("AdminPanel.txtEmployeeNameUpdate.text")); // NOI18N

        txtEmployeeEmailUpdate.setText(bundle.getString("AdminPanel.txtEmployeeEmailUpdate.text")); // NOI18N

        txtEmployeeContactUpdate.setText(bundle.getString("AdminPanel.txtEmployeeContactUpdate.text")); // NOI18N

        txtEmployeeGenderUpdate.setEditable(false);
        txtEmployeeGenderUpdate.setText(bundle.getString("AdminPanel.txtEmployeeGenderUpdate.text")); // NOI18N

        txtEmployeePasswordUpdate.setText(bundle.getString("AdminPanel.txtEmployeePasswordUpdate.text")); // NOI18N

        txtEmployeeCPasswordUpdate.setText(bundle.getString("AdminPanel.txtEmployeeCPasswordUpdate.text")); // NOI18N

        jLabel51.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText(bundle.getString("AdminPanel.jLabel51.text")); // NOI18N

        jLabel61.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText(bundle.getString("AdminPanel.jLabel61.text")); // NOI18N

        jLabel62.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText(bundle.getString("AdminPanel.jLabel62.text")); // NOI18N

        jLabel64.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText(bundle.getString("AdminPanel.jLabel64.text")); // NOI18N

        jLabel65.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText(bundle.getString("AdminPanel.jLabel65.text")); // NOI18N

        jLabel66.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setText(bundle.getString("AdminPanel.jLabel66.text")); // NOI18N

        jLabel67.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText(bundle.getString("AdminPanel.jLabel67.text")); // NOI18N

        btnEmployeeClear.setText(bundle.getString("AdminPanel.btnEmployeeClear.text")); // NOI18N
        btnEmployeeClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeeClearActionPerformed(evt);
            }
        });

        btnEmployeeUpdate.setText(bundle.getString("AdminPanel.btnEmployeeUpdate.text")); // NOI18N
        btnEmployeeUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeeUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel62)
                        .addComponent(jLabel61))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel65)
                            .addComponent(jLabel67)
                            .addComponent(jLabel64)
                            .addComponent(jLabel66))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(btnEmployeeUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(btnEmployeeClear))
                    .addComponent(txtEmployeeCPasswordUpdate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmployeePasswordUpdate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmployeeGenderUpdate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmployeeContactUpdate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmployeeNameUpdate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmployeeIDUpdate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmployeeEmailUpdate, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(29, 29, 29))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmployeeIDUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51))
                .addGap(40, 40, 40)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmployeeNameUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61))
                .addGap(40, 40, 40)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmployeeEmailUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62))
                .addGap(40, 40, 40)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(txtEmployeeContactUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(txtEmployeeGenderUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(txtEmployeePasswordUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(txtEmployeeCPasswordUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEmployeeClear)
                    .addComponent(btnEmployeeUpdate))
                .addGap(31, 31, 31))
        );

        jPanel27.setBackground(new java.awt.Color(51, 51, 51));
        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("AdminPanel.jPanel27.border.title"))); // NOI18N

        txtEmployeeIDInsert.setEditable(false);
        txtEmployeeIDInsert.setText(bundle.getString("AdminPanel.txtEmployeeIDInsert.text")); // NOI18N

        txtEmployeeNameInsert.setText(bundle.getString("AdminPanel.txtEmployeeNameInsert.text")); // NOI18N

        txtEmployeeEmailInsert.setText(bundle.getString("AdminPanel.txtEmployeeEmailInsert.text")); // NOI18N

        txtEmployeeContactInsert.setText(bundle.getString("AdminPanel.txtEmployeeContactInsert.text")); // NOI18N

        txtEmployeeGenderInsert.setEditable(false);
        txtEmployeeGenderInsert.setText(bundle.getString("AdminPanel.txtEmployeeGenderInsert.text")); // NOI18N

        txtEmployeePasswordInsert.setText(bundle.getString("AdminPanel.txtEmployeePasswordInsert.text")); // NOI18N

        txtEmployeeCPasswordInsert.setText(bundle.getString("AdminPanel.txtEmployeeCPasswordInsert.text")); // NOI18N

        jLabel72.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText(bundle.getString("AdminPanel.jLabel72.text")); // NOI18N

        jLabel73.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText(bundle.getString("AdminPanel.jLabel73.text")); // NOI18N

        jLabel74.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(255, 255, 255));
        jLabel74.setText(bundle.getString("AdminPanel.jLabel74.text")); // NOI18N

        jLabel75.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setText(bundle.getString("AdminPanel.jLabel75.text")); // NOI18N

        jLabel76.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(255, 255, 255));
        jLabel76.setText(bundle.getString("AdminPanel.jLabel76.text")); // NOI18N

        jLabel77.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setText(bundle.getString("AdminPanel.jLabel77.text")); // NOI18N

        jLabel78.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setText(bundle.getString("AdminPanel.jLabel78.text")); // NOI18N

        btnEmployeeClearInsert.setText(bundle.getString("AdminPanel.btnEmployeeClearInsert.text")); // NOI18N
        btnEmployeeClearInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeeClearInsertActionPerformed(evt);
            }
        });

        btnEmployeeInsert.setText(bundle.getString("AdminPanel.btnEmployeeInsert.text")); // NOI18N
        btnEmployeeInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeeInsertActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel72)
                    .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel74)
                        .addComponent(jLabel73))
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel76)
                            .addComponent(jLabel78)
                            .addComponent(jLabel75)
                            .addComponent(jLabel77))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                        .addComponent(btnEmployeeInsert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(btnEmployeeClearInsert))
                    .addComponent(txtEmployeeCPasswordInsert, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmployeePasswordInsert, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmployeeGenderInsert, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmployeeContactInsert, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmployeeNameInsert, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmployeeIDInsert, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmployeeEmailInsert, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(29, 29, 29))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmployeeIDInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel72))
                .addGap(40, 40, 40)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmployeeNameInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel73))
                .addGap(40, 40, 40)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmployeeEmailInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel74))
                .addGap(40, 40, 40)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(txtEmployeeContactInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel76)
                    .addComponent(txtEmployeeGenderInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel77)
                    .addComponent(txtEmployeePasswordInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78)
                    .addComponent(txtEmployeeCPasswordInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEmployeeClearInsert)
                    .addComponent(btnEmployeeInsert))
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(97, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );

        jTabbedPane4.addTab(bundle.getString("AdminPanel.jPanel21.TabConstraints.tabTitle"), jPanel21); // NOI18N

        jPanel22.setBackground(new java.awt.Color(255, 153, 0));

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AdminPanel.jPanel23.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 1, 12))); // NOI18N

        cbEmployeeReportSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employee ID", "Name" }));

        jLabel50.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText(bundle.getString("AdminPanel.jLabel50.text")); // NOI18N

        btnEmployeeReportSort.setText(bundle.getString("AdminPanel.btnEmployeeReportSort.text")); // NOI18N
        btnEmployeeReportSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeeReportSortActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel50)
                .addGap(50, 50, 50)
                .addComponent(cbEmployeeReportSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117)
                .addComponent(btnEmployeeReportSort)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbEmployeeReportSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50)
                    .addComponent(btnEmployeeReportSort))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        txtEmployeeSearch.setText(bundle.getString("AdminPanel.txtEmployeeSearch.text")); // NOI18N
        txtEmployeeSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmployeeSearchKeyReleased(evt);
            }
        });

        jLabel68.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText(bundle.getString("AdminPanel.jLabel68.text")); // NOI18N

        jtEmployeeView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Name", "Email", "Contact Number", "Gender"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(jtEmployeeView);
        if (jtEmployeeView.getColumnModel().getColumnCount() > 0) {
            jtEmployeeView.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("AdminPanel.jtEmployeeView.columnModel.title0")); // NOI18N
            jtEmployeeView.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("AdminPanel.jtEmployeeView.columnModel.title1")); // NOI18N
            jtEmployeeView.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("AdminPanel.jtEmployeeView.columnModel.title2")); // NOI18N
            jtEmployeeView.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("AdminPanel.jtEmployeeView.columnModel.title3")); // NOI18N
            jtEmployeeView.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("AdminPanel.jtEmployeeView.columnModel.title4")); // NOI18N
        }

        jLabel69.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText(bundle.getString("AdminPanel.jLabel69.text")); // NOI18N

        cbEmployeeSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employee ID", "Name" }));
        cbEmployeeSort.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbEmployeeSortItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jLabel68)
                        .addGap(59, 59, 59)
                        .addComponent(txtEmployeeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                        .addComponent(jLabel69)
                        .addGap(49, 49, 49)
                        .addComponent(cbEmployeeSort, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6))
                .addContainerGap(151, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmployeeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68)
                    .addComponent(jLabel69)
                    .addComponent(cbEmployeeSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        jTabbedPane4.addTab(bundle.getString("AdminPanel.jPanel22.TabConstraints.tabTitle"), jPanel22); // NOI18N

        javax.swing.GroupLayout EmployeePanelLayout = new javax.swing.GroupLayout(EmployeePanel);
        EmployeePanel.setLayout(EmployeePanelLayout);
        EmployeePanelLayout.setHorizontalGroup(
            EmployeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4)
        );
        EmployeePanelLayout.setVerticalGroup(
            EmployeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4)
        );

        CardPanel.add(EmployeePanel, "card7");

        LogAuditPanel.setBackground(new java.awt.Color(255, 153, 0));

        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AdminPanel.jPanel24.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 1, 12))); // NOI18N

        jLabel70.setText(bundle.getString("AdminPanel.jLabel70.text")); // NOI18N

        txtAdminLogSearch.setText(bundle.getString("AdminPanel.txtAdminLogSearch.text")); // NOI18N
        txtAdminLogSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAdminLogSearchKeyReleased(evt);
            }
        });

        btnAdminLogDisplay.setText(bundle.getString("AdminPanel.btnAdminLogDisplay.text")); // NOI18N
        btnAdminLogDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminLogDisplayActionPerformed(evt);
            }
        });

        txtAdminLogView.setColumns(20);
        txtAdminLogView.setRows(5);
        jScrollPane9.setViewportView(txtAdminLogView);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel70)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtAdminLogSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(411, 411, 411)
                        .addComponent(btnAdminLogDisplay)))
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAdminLogSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70)
                    .addComponent(btnAdminLogDisplay))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AdminPanel.jPanel25.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 1, 12))); // NOI18N

        jLabel71.setText(bundle.getString("AdminPanel.jLabel71.text")); // NOI18N

        txtEmployeeLogSearch.setText(bundle.getString("AdminPanel.txtEmployeeLogSearch.text")); // NOI18N
        txtEmployeeLogSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmployeeLogSearchKeyReleased(evt);
            }
        });

        btnEmployeeLogDisplay.setText(bundle.getString("AdminPanel.btnEmployeeLogDisplay.text")); // NOI18N
        btnEmployeeLogDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeeLogDisplayActionPerformed(evt);
            }
        });

        txtEmployeeLogView.setColumns(20);
        txtEmployeeLogView.setRows(5);
        jScrollPane10.setViewportView(txtEmployeeLogView);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(jLabel71)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(txtEmployeeLogSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(414, 414, 414)
                        .addComponent(btnEmployeeLogDisplay)))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmployeeLogSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71)
                    .addComponent(btnEmployeeLogDisplay))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout LogAuditPanelLayout = new javax.swing.GroupLayout(LogAuditPanel);
        LogAuditPanel.setLayout(LogAuditPanelLayout);
        LogAuditPanelLayout.setHorizontalGroup(
            LogAuditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LogAuditPanelLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(LogAuditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        LogAuditPanelLayout.setVerticalGroup(
            LogAuditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LogAuditPanelLayout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );

        CardPanel.add(LogAuditPanel, "card8");

        getContentPane().add(CardPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInventoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInventoryMouseClicked
        setPanel("card5");
        InventorySidePanel.setVisible(true);
        SuppliersSidePanel.setVisible(false);
        EmployeesSidePanel.setVisible(false);
        SalesSidePanel.setVisible(false);
        ReportSidePanel.setVisible(false);
        ProfileSidePanel.setVisible(false);
        AuditLogSidePanel.setVisible(false);
        btnAuditLog.setForeground(Color.WHITE);
        btnInventory.setForeground(Color.orange);
        btnSuppliers.setForeground(Color.WHITE);
        btnEmployees.setForeground(Color.WHITE);
        btnSales.setForeground(Color.WHITE);
        btnReport.setForeground(Color.WHITE);
        btnProfile.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnInventoryMouseClicked

    private void btnSalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalesMouseClicked
        setPanel("card2");
        InventorySidePanel.setVisible(false);
        SuppliersSidePanel.setVisible(false);
        EmployeesSidePanel.setVisible(false);
        SalesSidePanel.setVisible(true);
        ReportSidePanel.setVisible(false);
        ProfileSidePanel.setVisible(false);
        AuditLogSidePanel.setVisible(false);
        btnAuditLog.setForeground(Color.WHITE);
        btnInventory.setForeground(Color.WHITE);
        btnSuppliers.setForeground(Color.WHITE);
        btnEmployees.setForeground(Color.WHITE);
        btnSales.setForeground(Color.orange);
        btnReport.setForeground(Color.WHITE);
        btnProfile.setForeground(Color.WHITE);

    }//GEN-LAST:event_btnSalesMouseClicked

    private void btnReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportMouseClicked
        setPanel("card3");
        InventorySidePanel.setVisible(false);
        SuppliersSidePanel.setVisible(false);
        EmployeesSidePanel.setVisible(false);
        SalesSidePanel.setVisible(false);
        ReportSidePanel.setVisible(true);
        ProfileSidePanel.setVisible(false);
        AuditLogSidePanel.setVisible(false);
        btnAuditLog.setForeground(Color.WHITE);
        btnInventory.setForeground(Color.WHITE);
        btnSuppliers.setForeground(Color.WHITE);
        btnEmployees.setForeground(Color.WHITE);
        btnSales.setForeground(Color.WHITE);
        btnReport.setForeground(Color.orange);
        btnProfile.setForeground(Color.WHITE);

    }//GEN-LAST:event_btnReportMouseClicked

    private void btnProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfileMouseClicked
        setPanel("card4");
        InventorySidePanel.setVisible(false);
        SuppliersSidePanel.setVisible(false);
        EmployeesSidePanel.setVisible(false);
        SalesSidePanel.setVisible(false);
        ReportSidePanel.setVisible(false);
        ProfileSidePanel.setVisible(true);
        AuditLogSidePanel.setVisible(false);
        btnAuditLog.setForeground(Color.WHITE);
        btnInventory.setForeground(Color.WHITE);
        btnSuppliers.setForeground(Color.WHITE);
        btnEmployees.setForeground(Color.WHITE);
        btnSales.setForeground(Color.WHITE);
        btnReport.setForeground(Color.WHITE);
        btnProfile.setForeground(Color.orange);


    }//GEN-LAST:event_btnProfileMouseClicked

    private void btnItemClearInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemClearInsertActionPerformed
        txtItemNameInsert.setText(null);
        txtItemQuantityInsert.setText(null);
        txtItemPriceInsert.setText(null);
        txtItemProfitInsert.setText(null);
        txtItemSupplierIDInsert.setText(null);
        txtItemNameInsert.requestFocus();
    }//GEN-LAST:event_btnItemClearInsertActionPerformed

    private void btnItemUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemUpdateActionPerformed
        if (txtItemIDUpdate.getText().trim().isEmpty() || txtItemQuantityUpdate.getText().trim().isEmpty() || txItemPriceUpdate.getText().trim().isEmpty() || txtItemProfitUpdate.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You cannot leave this field/s empty", "Login error", JOptionPane.ERROR_MESSAGE);
        } else {
            int YesOrNo = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING!", JOptionPane.YES_NO_OPTION);
            if (YesOrNo == 0) {
                int Item_ID = Integer.parseInt(txtItemIDUpdate.getText());
                String Item_Name = txtItemNameUpdate.getText();
                String Category = cbItemCategoryUpdate.getSelectedItem().toString();
                int Quantity = Integer.parseInt(txtItemQuantityUpdate.getText());
                int Price = Integer.parseInt(txItemPriceUpdate.getText());
                int Profit = Integer.parseInt(txtItemProfitUpdate.getText());
                int Supplier_ID = Integer.parseInt(txtItemSupplierIDUpdate.getText());
                try {
                    String sql = "UPDATE `stock_details` SET `Quantity`=?,`Price`=?,`Profit`=? WHERE `Item_ID`=?";

                    pst = DBConnection.getConnection().prepareStatement(sql);
                    pst.setInt(1, Quantity);
                    pst.setInt(2, Price);
                    pst.setInt(3, Profit);
                    pst.setInt(4, Item_ID);
                    if (pst.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "Record updated");
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Item ID", "Update error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    txtItemIDUpdate.setText(null);
                    txtItemNameUpdate.setText(null);
                    txtItemQuantityUpdate.setText(null);
                    txItemPriceUpdate.setText(null);
                    txtItemProfitUpdate.setText(null);
                    txtSupplierIDUpdate.setText(null);
                    txtItemIDUpdate.requestFocus();
                    showItems();
                    showLowStock();
                }
            }
        }
    }//GEN-LAST:event_btnItemUpdateActionPerformed

    private void btnItemClearUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemClearUpdateActionPerformed
        txtItemIDUpdate.setText(null);
        txtItemNameUpdate.setText(null);
        txtItemQuantityUpdate.setText(null);
        txItemPriceUpdate.setText(null);
        txtItemProfitUpdate.setText(null);
        txtItemSupplierIDUpdate.setText(null);
        txtItemIDUpdate.requestFocus();
    }//GEN-LAST:event_btnItemClearUpdateActionPerformed

    private void btnItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemDeleteActionPerformed
        if (txtItemIDUpdate.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You cannot leave this field empty", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int YesOrNo = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING!", JOptionPane.YES_NO_OPTION);
            if (YesOrNo == 0) {
                try {
                    int Item_ID = Integer.parseInt(txtItemIDUpdate.getText());

                    String sql = "DELETE FROM `stock_details` WHERE `Item_ID`=?";
                    pst = DBConnection.getConnection().prepareStatement(sql);

                    pst.setInt(1, Item_ID);
                    if (pst.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "Record deleted");
                        showItems();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Item ID", "Deletion error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    txtItemIDUpdate.setText(null);
                    txtItemIDUpdate.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_btnItemDeleteActionPerformed

    private void btnSuppliersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuppliersMouseClicked
        setPanel("card6");
        InventorySidePanel.setVisible(false);
        SuppliersSidePanel.setVisible(true);
        EmployeesSidePanel.setVisible(false);
        SalesSidePanel.setVisible(false);
        ReportSidePanel.setVisible(false);
        ProfileSidePanel.setVisible(false);
        AuditLogSidePanel.setVisible(false);
        btnAuditLog.setForeground(Color.WHITE);
        btnInventory.setForeground(Color.WHITE);
        btnSuppliers.setForeground(Color.orange);
        btnEmployees.setForeground(Color.WHITE);
        btnSales.setForeground(Color.WHITE);
        btnReport.setForeground(Color.WHITE);
        btnProfile.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnSuppliersMouseClicked

    private void btnInsertSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertSupplierActionPerformed
        if (txtSupplierNameInsert.getText().trim().isEmpty() || txtSupplierEmailInsert.getText().trim().isEmpty() || txtSupplierContactInsert.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You cannot leave this field/s empty", "Insertion error", JOptionPane.ERROR_MESSAGE);
        } else {
            int YesOrNo = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING!", JOptionPane.YES_NO_OPTION);
            if (YesOrNo == 0) {
                String Supplier_Name = txtSupplierNameInsert.getText();
                String Email = txtSupplierEmailInsert.getText();
                int Contact_Number = Integer.parseInt(txtSupplierContactInsert.getText());
                try {
                    String sql = "INSERT INTO `supplier_details`( `Supplier_Name`, `Email`, `Contact_Number`) VALUES (?,?,?)";

                    pst = DBConnection.getConnection().prepareStatement(sql);
                    pst.setString(1, Supplier_Name);
                    pst.setString(2, Email);
                    pst.setInt(3, Contact_Number);
                    if (pst.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "Record inserted");
                        showSuppliers();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    txtSupplierNameInsert.setText(null);
                    txtSupplierEmailInsert.setText(null);
                    txtSupplierContactInsert.setText(null);
                    txtSupplierNameInsert.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_btnInsertSupplierActionPerformed

    private void btnSupplierClearInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupplierClearInsertActionPerformed
        txtSupplierNameInsert.setText(null);
        txtSupplierEmailInsert.setText(null);
        txtSupplierContactInsert.setText(null);
        txtSupplierNameInsert.requestFocus();
    }//GEN-LAST:event_btnSupplierClearInsertActionPerformed

    private void btnSupplierUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupplierUpdateActionPerformed
        if (txtSupplierIDUpdate.getText().trim().isEmpty() || txtSupplierNameUpdate.getText().trim().isEmpty() || txtSupplierEmailUpdate.getText().trim().isEmpty() || txtSupplierContactUpdate.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You cannot leave this field/s empty", "Login error", JOptionPane.ERROR_MESSAGE);
        } else {
            int YesOrNo = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING!", JOptionPane.YES_NO_OPTION);
            if (YesOrNo == 0) {
                int Supplier_ID = Integer.parseInt(txtSupplierIDUpdate.getText());
                String Supplier_Name = txtSupplierNameUpdate.getText();
                String Email = txtSupplierEmailUpdate.getText();
                int Contact_Number = Integer.parseInt(txtSupplierContactUpdate.getText());
                try {
                    String sql = "UPDATE `supplier_details` SET `Supplier_Name`=?, `Email`=?,`Contact_Number`=? WHERE `Supplier_ID`=?";

                    pst = DBConnection.getConnection().prepareStatement(sql);
                    pst.setString(1, Supplier_Name);
                    pst.setString(2, Email);
                    pst.setInt(3, Contact_Number);
                    pst.setInt(4, Supplier_ID);
                    if (pst.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "Record updated");
                        showSuppliers();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Supplier ID", "Update error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    txtSupplierIDUpdate.setText(null);
                    txtSupplierNameUpdate.setText(null);
                    txtSupplierEmailUpdate.setText(null);
                    txtSupplierContactUpdate.setText(null);
                    txtSupplierIDUpdate.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_btnSupplierUpdateActionPerformed

    private void btnSupplierClearUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupplierClearUpdateActionPerformed
        txtSupplierIDUpdate.setText(null);
        txtSupplierNameUpdate.setText(null);
        txtSupplierEmailUpdate.setText(null);
        txtSupplierContactUpdate.setText(null);
        txtSupplierIDUpdate.requestFocus();
    }//GEN-LAST:event_btnSupplierClearUpdateActionPerformed

    private void btnSupplierDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupplierDeleteActionPerformed
        if (txtSupplierIDUpdate.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You cannot leave this field empty", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int YesOrNo = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING!", JOptionPane.YES_NO_OPTION);
            if (YesOrNo == 0) {
                try {
                    int Supplier_ID = Integer.parseInt(txtSupplierIDUpdate.getText());

                    String sql = "DELETE FROM `supplier_details` WHERE `Supplier_ID`=?";
                    pst = DBConnection.getConnection().prepareStatement(sql);

                    pst.setInt(1, Supplier_ID);
                    if (pst.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "Record deleted");
                        showSuppliers();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Supplier ID", "Deletion error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    txtSupplierIDUpdate.setText(null);
                    txtSupplierIDUpdate.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_btnSupplierDeleteActionPerformed

    private void txtItemIDUpdateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemIDUpdateKeyReleased
        try {
            String sql = "SELECT * FROM stock_details WHERE Item_ID =?";
            pst = DBConnection.getConnection().prepareStatement(sql);
            pst.setString(1, txtItemIDUpdate.getText());

            rs = pst.executeQuery();
            if (rs.next()) {
                int IID = rs.getInt("Item_ID");
                txtItemIDUpdate.setText(String.valueOf(IID));
                String IN = rs.getString("Item_Name");
                txtItemNameUpdate.setText(IN);
                String IC = rs.getString("Category");
                cbItemCategoryUpdate.setSelectedItem(IC);
                int IQ = rs.getInt("Quantity");
                txtItemQuantityUpdate.setText(String.valueOf(IQ));
                int IP = rs.getInt("Price");
                txItemPriceUpdate.setText(String.valueOf(IP));
                int Ip = rs.getInt("Profit");
                txtItemProfitUpdate.setText(String.valueOf(Ip));
                int IS = rs.getInt("Supplier_ID");
                txtItemSupplierIDUpdate.setText(String.valueOf(IS));
            } else {
                txtItemIDUpdate.setText(null);
                txtItemNameUpdate.setText(null);
                cbItemCategoryUpdate.setSelectedItem(null);
                txtItemQuantityUpdate.setText(null);
                txItemPriceUpdate.setText(null);
                txtItemProfitUpdate.setText(null);
                txtItemSupplierIDUpdate.setText(null);
                txtItemIDUpdate.requestFocus();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }//GEN-LAST:event_txtItemIDUpdateKeyReleased

    private void btnProfileUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileUpdateActionPerformed
        if (txtProfileNameUpdate.getText().trim().isEmpty() || txtProfileEmailUpdate.getText().trim().isEmpty() || txtProfileContactUpdate.getText().trim().isEmpty() || txtProfilePasswordUpdate.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You cannot leave this field/s empty", "Login error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (txtProfilePasswordUpdate.getText().trim().equals(txtProfileCPasswordUpdate.getText().trim())) {
                int YesOrNo = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING!", JOptionPane.YES_NO_OPTION);
                if (YesOrNo == 0) {
                    int AdminUserID = Integer.parseInt(txtProfileAdminIDUpdate.getText());
                    String Name = txtProfileNameUpdate.getText();
                    String Email = txtProfileEmailUpdate.getText();
                    int Contact_Number = Integer.parseInt(txtProfileContactUpdate.getText());
                    String Password = txtProfilePasswordUpdate.getText();
                    try {
                        String sql = "UPDATE `admin_details` SET `Name`=?,`Email`=?,`Contact_Number`=?,`Password`=? WHERE `Admin_ID`=?";
                        pst = DBConnection.getConnection().prepareStatement(sql);
                        pst.setString(1, Name);
                        pst.setString(2, Email);
                        pst.setInt(3, Contact_Number);
                        pst.setString(4, Password);
                        pst.setInt(5, AdminUserID);
                        if (pst.executeUpdate() > 0) {
                            JOptionPane.showMessageDialog(null, "Profile updated");
                        } else {
                            JOptionPane.showMessageDialog(null, "Update error", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        txtProfilePasswordUpdate.requestFocus();
                        txtProfilePasswordUpdate.setText(null);
                        txtProfileCPasswordUpdate.setText(null);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Password does not match", "Error", JOptionPane.ERROR_MESSAGE);
                txtProfilePasswordUpdate.setText(null);
                txtProfileCPasswordUpdate.setText(null);
                txtProfilePasswordUpdate.requestFocus();
            }
        }
    }//GEN-LAST:event_btnProfileUpdateActionPerformed

    private void btnItemInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemInsertActionPerformed
        if (txtItemNameInsert.getText().trim().isEmpty() || txtItemQuantityInsert.getText().trim().isEmpty() || txtItemPriceInsert.getText().trim().isEmpty() || txtItemProfitInsert.getText().trim().isEmpty() || txtItemSupplierIDInsert.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You cannot leave this field/s empty", "Insertion error", JOptionPane.ERROR_MESSAGE);
        } else {
            int YesOrNo = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING!", JOptionPane.YES_NO_OPTION);
            if (YesOrNo == 0) {
                String Item_Name = txtItemNameInsert.getText();
                String Category = cbItemCategoryInsert.getSelectedItem().toString();
                int Quantity = Integer.parseInt(txtItemQuantityInsert.getText());
                int Price = Integer.parseInt(txtItemPriceInsert.getText());
                int Profit = Integer.parseInt(txtItemProfitInsert.getText());
                DateFormat DF = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                String Added_Date = DF.format(date);
                int Supplier_ID = Integer.parseInt(txtItemSupplierIDInsert.getText());
                try {
                    String sql = "INSERT INTO `stock_details`( `Item_Name`, `Category`, `Quantity`, `Price`, `Profit`,`Added_Date`, `Supplier_ID`) VALUES (?,?,?,?,?,?,?)";

                    pst = DBConnection.getConnection().prepareStatement(sql);
                    pst.setString(1, Item_Name);
                    pst.setString(2, Category);
                    pst.setInt(3, Quantity);
                    pst.setInt(4, Price);
                    pst.setInt(5, Profit);
                    pst.setString(6, Added_Date);
                    pst.setInt(7, Supplier_ID);
                    if (pst.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "Record inserted");
                        showItems();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    txtItemNameInsert.setText(null);
                    txtItemQuantityInsert.setText(null);
                    txtItemPriceInsert.setText(null);
                    txtItemProfitInsert.setText(null);
                    txtItemSupplierIDInsert.setText(null);
                    txtItemNameInsert.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_btnItemInsertActionPerformed

    private void cbSupplierSortItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSupplierSortItemStateChanged
        try {
            Object SI = cbSupplierSort.getSelectedItem();
            if ("Supplier ID".equals(SI)) {
                String sql = "SELECT * FROM supplier_details ORDER BY Supplier_ID ASC";
                pst = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                if (rs.isBeforeFirst()) {
                    int r = 0;
                    while (rs.next()) {
                        jtSupplierView.getModel().setValueAt(rs.getString(1), r, 0);
                        jtSupplierView.getModel().setValueAt(rs.getString(2), r, 1);
                        jtSupplierView.getModel().setValueAt(rs.getString(3), r, 2);
                        jtSupplierView.getModel().setValueAt(rs.getString(4), r, 3);
                        r++;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if ("A-Z".equals(SI)) {
                String sql = "SELECT * FROM supplier_details ORDER BY Supplier_Name ASC";
                pst = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                if (rs.isBeforeFirst()) {
                    int r = 0;
                    while (rs.next()) {
                        jtSupplierView.getModel().setValueAt(rs.getString(1), r, 0);
                        jtSupplierView.getModel().setValueAt(rs.getString(2), r, 1);
                        jtSupplierView.getModel().setValueAt(rs.getString(3), r, 2);
                        jtSupplierView.getModel().setValueAt(rs.getString(4), r, 3);
                        r++;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if ("Z-A".equals(SI)) {
                String sql = "SELECT * FROM supplier_details ORDER BY Supplier_Name DESC";
                pst = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);

                if (rs.isBeforeFirst()) {
                    int r = 0;
                    while (rs.next()) {
                        jtSupplierView.getModel().setValueAt(rs.getString(1), r, 0);
                        jtSupplierView.getModel().setValueAt(rs.getString(2), r, 1);
                        jtSupplierView.getModel().setValueAt(rs.getString(3), r, 2);
                        jtSupplierView.getModel().setValueAt(rs.getString(4), r, 3);
                        r++;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            System.err.println("Error :" + ex.getMessage());
        }
    }//GEN-LAST:event_cbSupplierSortItemStateChanged

    private void cbItemSortItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbItemSortItemStateChanged
        try {
            Object SI = cbItemSort.getSelectedItem();
            if ("Item ID".equals(SI)) {
                String sql = "SELECT * FROM stock_details ORDER BY Item_ID ASC";
                pst = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                if (rs.isBeforeFirst()) {
                    int r = 0;
                    while (rs.next()) {
                        jtItemView.getModel().setValueAt(rs.getString(1), r, 0);
                        jtItemView.getModel().setValueAt(rs.getString(2), r, 1);
                        jtItemView.getModel().setValueAt(rs.getString(3), r, 2);
                        jtItemView.getModel().setValueAt(rs.getString(4), r, 3);
                        jtItemView.getModel().setValueAt(rs.getString(5), r, 4);
                        jtItemView.getModel().setValueAt(rs.getString(6), r, 5);
                        jtItemView.getModel().setValueAt(rs.getString(7), r, 6);
                        jtItemView.getModel().setValueAt(rs.getString(8), r, 7);
                        r++;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if ("A-Z".equals(SI)) {
                String sql = "SELECT * FROM stock_details ORDER BY Item_Name ASC";
                pst = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                if (rs.isBeforeFirst()) {
                    int r = 0;
                    while (rs.next()) {
                        jtItemView.getModel().setValueAt(rs.getString(1), r, 0);
                        jtItemView.getModel().setValueAt(rs.getString(2), r, 1);
                        jtItemView.getModel().setValueAt(rs.getString(3), r, 2);
                        jtItemView.getModel().setValueAt(rs.getString(4), r, 3);
                        jtItemView.getModel().setValueAt(rs.getString(5), r, 4);
                        jtItemView.getModel().setValueAt(rs.getString(6), r, 5);
                        jtItemView.getModel().setValueAt(rs.getString(7), r, 6);
                        jtItemView.getModel().setValueAt(rs.getString(8), r, 7);
                        r++;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if ("Z-A".equals(SI)) {
                String sql = "SELECT * FROM stock_details ORDER BY Item_Name DESC";
                pst = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                if (rs.isBeforeFirst()) {
                    int r = 0;
                    while (rs.next()) {
                        jtItemView.getModel().setValueAt(rs.getString(1), r, 0);
                        jtItemView.getModel().setValueAt(rs.getString(2), r, 1);
                        jtItemView.getModel().setValueAt(rs.getString(3), r, 2);
                        jtItemView.getModel().setValueAt(rs.getString(4), r, 3);
                        jtItemView.getModel().setValueAt(rs.getString(5), r, 4);
                        jtItemView.getModel().setValueAt(rs.getString(6), r, 5);
                        jtItemView.getModel().setValueAt(rs.getString(7), r, 6);
                        jtItemView.getModel().setValueAt(rs.getString(8), r, 7);
                        r++;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if ("Category".equals(SI)) {
                String sql = "SELECT * FROM stock_details ORDER BY Category ASC";
                pst = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                if (rs.isBeforeFirst()) {
                    int r = 0;
                    while (rs.next()) {
                        jtItemView.getModel().setValueAt(rs.getString(1), r, 0);
                        jtItemView.getModel().setValueAt(rs.getString(2), r, 1);
                        jtItemView.getModel().setValueAt(rs.getString(3), r, 2);
                        jtItemView.getModel().setValueAt(rs.getString(4), r, 3);
                        jtItemView.getModel().setValueAt(rs.getString(5), r, 4);
                        jtItemView.getModel().setValueAt(rs.getString(6), r, 5);
                        jtItemView.getModel().setValueAt(rs.getString(7), r, 6);
                        jtItemView.getModel().setValueAt(rs.getString(8), r, 7);
                        r++;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if ("Lowest Profit".equals(SI)) {
                String sql = "SELECT * FROM stock_details ORDER BY Profit ASC";
                pst = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                if (rs.isBeforeFirst()) {
                    int r = 0;
                    while (rs.next()) {
                        jtItemView.getModel().setValueAt(rs.getString(1), r, 0);
                        jtItemView.getModel().setValueAt(rs.getString(2), r, 1);
                        jtItemView.getModel().setValueAt(rs.getString(3), r, 2);
                        jtItemView.getModel().setValueAt(rs.getString(4), r, 3);
                        jtItemView.getModel().setValueAt(rs.getString(5), r, 4);
                        jtItemView.getModel().setValueAt(rs.getString(6), r, 5);
                        jtItemView.getModel().setValueAt(rs.getString(7), r, 6);
                        jtItemView.getModel().setValueAt(rs.getString(8), r, 7);
                        r++;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if ("Highest Profit".equals(SI)) {
                String sql = "SELECT * FROM stock_details ORDER BY Profit DESC";
                pst = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                if (rs.isBeforeFirst()) {
                    int r = 0;
                    while (rs.next()) {
                        jtItemView.getModel().setValueAt(rs.getString(1), r, 0);
                        jtItemView.getModel().setValueAt(rs.getString(2), r, 1);
                        jtItemView.getModel().setValueAt(rs.getString(3), r, 2);
                        jtItemView.getModel().setValueAt(rs.getString(4), r, 3);
                        jtItemView.getModel().setValueAt(rs.getString(5), r, 4);
                        jtItemView.getModel().setValueAt(rs.getString(6), r, 5);
                        jtItemView.getModel().setValueAt(rs.getString(7), r, 6);
                        jtItemView.getModel().setValueAt(rs.getString(8), r, 7);
                        r++;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if ("Supplier ID".equals(SI)) {
                String sql = "SELECT * FROM stock_details ORDER BY Supplier_ID ASC";
                pst = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);

                if (rs.isBeforeFirst()) {
                    int r = 0;
                    while (rs.next()) {
                        jtItemView.getModel().setValueAt(rs.getString(1), r, 0);
                        jtItemView.getModel().setValueAt(rs.getString(2), r, 1);
                        jtItemView.getModel().setValueAt(rs.getString(3), r, 2);
                        jtItemView.getModel().setValueAt(rs.getString(4), r, 3);
                        jtItemView.getModel().setValueAt(rs.getString(5), r, 4);
                        jtItemView.getModel().setValueAt(rs.getString(6), r, 5);
                        jtItemView.getModel().setValueAt(rs.getString(7), r, 6);
                        jtItemView.getModel().setValueAt(rs.getString(8), r, 7);
                        r++;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            System.err.println("Error :" + ex.getMessage());
        }
    }//GEN-LAST:event_cbItemSortItemStateChanged

    private void btnSalesAddAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalesAddAdminActionPerformed
        int QTY = Integer.parseInt(txtSalesQuantityAdmin.getText());
        int Quantity = GetQuantity();

        if (Quantity > 0) {
            try {
                int Item_ID = Integer.parseInt(txtSalesItemIDAdmin.getText());
                String sql = "UPDATE `stock_details` SET `Quantity`=? WHERE `Item_ID`=?";

                pst = DBConnection.getConnection().prepareStatement(sql);
                pst.setInt(1, Quantity);
                pst.setInt(2, Item_ID);
                if (pst.executeUpdate() > 0) {
                    int Profit = GetProfit();
                    int SubProfit = Profit * QTY;
                    LC.profitInsert(SubProfit);
                    DefaultTableModel model = new DefaultTableModel();
                    model = (DefaultTableModel) jtSalesItemViewAdmin.getModel();
                    model.addRow(new Object[]{
                        txtSalesItemIDAdmin.getText(),
                        txtSalesItemNameAdmin.getText(),
                        txtSalesQuantityAdmin.getText(),
                        txtSalesPriceAdmin.getText(),
                        txtSalesSubtotalAdmin.getText(),});
                    int Total = 0;
                    for (int i = 0; i < jtSalesItemViewAdmin.getRowCount(); i++) {
                        Total = Total + Integer.parseInt(jtSalesItemViewAdmin.getValueAt(i, 4).toString());
                        txtSalesTotalAdmin.setText(Integer.toString(Total));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                txtSalesItemIDAdmin.setText(null);
                txtSalesItemNameAdmin.setText(null);
                txtSalesQuantityAdmin.setText(null);
                txtSalesPriceAdmin.setText(null);
                txtSalesSubtotalAdmin.setText(null);
                txtSalesItemIDAdmin.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Entered quantity exceeds the stock", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnSalesAddAdminActionPerformed

    private void txtSalesQuantityAdminKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalesQuantityAdminKeyReleased
        int SQ = Integer.parseInt(txtSalesQuantityAdmin.getText());
        int SIP = Integer.parseInt(txtSalesPriceAdmin.getText());
        int ST = SIP * SQ;
        txtSalesSubtotalAdmin.setText(String.valueOf(ST));
    }//GEN-LAST:event_txtSalesQuantityAdminKeyReleased

    private void txtSalesItemIDAdminKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalesItemIDAdminKeyReleased
        try {
            String sql = "SELECT `Item_ID`,`Item_Name`,`Price` FROM `stock_details` WHERE `Item_ID` =?";
            pst = DBConnection.getConnection().prepareStatement(sql);
            pst.setString(1, txtSalesItemIDAdmin.getText());

            rs = pst.executeQuery();
            if (rs.next()) {
                int SIID = rs.getInt("Item_ID");
                txtSalesItemIDAdmin.setText(String.valueOf(SIID));
                String SIN = rs.getString("Item_Name");
                txtSalesItemNameAdmin.setText(SIN);
                int SIP = rs.getInt("Price");
                txtSalesPriceAdmin.setText(String.valueOf(SIP));

            } else {
                JOptionPane.showMessageDialog(null, "Invalid Item ID", "Error", JOptionPane.ERROR_MESSAGE);
                txtSalesItemIDAdmin.setText(null);
                txtSalesItemNameAdmin.setText(null);
                txtSalesQuantityAdmin.setText(null);
                txtSalesPriceAdmin.setText(null);
                txtSalesSubtotalAdmin.setText(null);
                txtSalesItemIDAdmin.requestFocus();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }//GEN-LAST:event_txtSalesItemIDAdminKeyReleased

    private void btnSalesDisplayAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalesDisplayAdminActionPerformed
        txtSalesBillAdmin.setText(null);

        String Total = txtSalesTotalAdmin.getText();
        String Amount = txtSalesAmountAdmin.getText();
        String Balance = txtSalesBalanceAdmin.getText();

        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) jtSalesItemViewAdmin.getModel();

        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "------------------------------------------------------------------------------------\n");
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + " " + "                   No. JAVA, Highlevel Road, Pannipitiya" + "\t" + "\n");
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "\t" + "0112 333 444 | 071 222 333" + "\t" + "\n");
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "------------------------------------------------------------------------------------\n");
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "Item" + "\t" + "Price" + "\t" + "Qty" + "\t" + "Subtotal" + "\n");
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "------------------------------------------------------------------------------------\n");

        for (int i = 0; i < model.getRowCount(); i++) {
            String ItemName = (String) model.getValueAt(i, 1);
            String ItemPrice = (String) model.getValueAt(i, 2);
            String ItemQty = (String) model.getValueAt(i, 3);
            String ItemST = (String) model.getValueAt(i, 4);
            txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + ItemName + "\t" + ItemPrice + "\t" + ItemQty + "\t" + ItemST + "\n");
        }
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "------------------------------------------------------------------------------------\n");
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "Total" + "\t\t\t" + Total + "\n");
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "Amount" + "\t\t\t" + Amount + "\n");
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "Balance" + "\t\t\t" + Balance + "\n");
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "------------------------------------------------------------------------------------\n");
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "\t" + "Thank You for shopping with us" + "\t" + "\n");
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "\n");
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "\t" + "     *IMPORTANT NOTICE*" + "\t" + "\n");
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "In case of price discrepancy, return the item and bill within" + "\n" + "\t" + "7 days for refund of difference." + "\n");
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "------------------------------------------------------------------------------------\n");
        txtSalesBillAdmin.setText(txtSalesBillAdmin.getText() + "\t" + "               (C)BlackLotus" + "\t" + "\n");
    }//GEN-LAST:event_btnSalesDisplayAdminActionPerformed

    private void txtSalesAmountAdminKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalesAmountAdminKeyReleased
        int Total = Integer.parseInt(txtSalesTotalAdmin.getText());
        int Amount = Integer.parseInt(txtSalesAmountAdmin.getText());
        int Balance = Amount - Total;
        txtSalesBalanceAdmin.setText(String.valueOf(Balance));
    }//GEN-LAST:event_txtSalesAmountAdminKeyReleased

    private void btnItemFilterViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemFilterViewActionPerformed
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String Added_Date1 = sdf.format(jdcAddedDateSort1.getDate());
        String Added_Date2 = sdf.format(jdcAddedDateSort2.getDate());
        try {
            String sql = "SELECT * FROM `stock_details` WHERE Added_Date BETWEEN '" + Added_Date1 + "' AND '" + Added_Date2 + "'";
            pst = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            DefaultTableModel DTM = (DefaultTableModel) jtItemView.getModel();
            DTM.setRowCount(0);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    Object O[] = {rs.getInt("Item_ID"), rs.getString("Item_Name"), rs.getString("Category"), rs.getString("Quantity"), rs.getInt("Price"), rs.getInt("Profit"), rs.getDate("Added_Date"), rs.getInt("Supplier_ID")};
                    DTM.addRow(O);
                }
            } else {
                JOptionPane.showMessageDialog(null, "There are no records between entered two days", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }//GEN-LAST:event_btnItemFilterViewActionPerformed

    private void txtSupplierIDUpdateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSupplierIDUpdateKeyReleased
        try {
            String sql = "SELECT * FROM supplier_details WHERE Supplier_ID =?";
            pst = DBConnection.getConnection().prepareStatement(sql);
            pst.setString(1, txtSupplierIDUpdate.getText());

            rs = pst.executeQuery();
            if (rs.next()) {
                int SID = rs.getInt("Supplier_ID");
                txtSupplierIDUpdate.setText(String.valueOf(SID));
                String SN = rs.getString("Supplier_Name");
                txtSupplierNameUpdate.setText(SN);
                String SE = rs.getString("Email");
                txtSupplierEmailUpdate.setText(SE);
                int SC = rs.getInt("Contact_Number");
                txtSupplierContactUpdate.setText(String.valueOf(SC));
            } else {
                txtSupplierIDUpdate.setText(null);
                txtSupplierNameUpdate.setText(null);
                txtSupplierEmailUpdate.setText(null);
                txtSupplierContactUpdate.setText(null);
                txtSupplierIDUpdate.requestFocus();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }//GEN-LAST:event_txtSupplierIDUpdateKeyReleased

    private void txtItemIDViewKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemIDViewKeyReleased
        DefaultTableModel DTM = (DefaultTableModel) jtItemView.getModel();
        String Search = txtItemIDView.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(DTM);
        jtItemView.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(Search));
    }//GEN-LAST:event_txtItemIDViewKeyReleased

    private void txtSupplierIDViewKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSupplierIDViewKeyReleased
        DefaultTableModel DTM = (DefaultTableModel) jtSupplierView.getModel();
        String Search = txtSupplierIDView.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(DTM);
        jtItemSupplierView.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(Search));
    }//GEN-LAST:event_txtSupplierIDViewKeyReleased

    private void btnSalesPrintAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalesPrintAdminActionPerformed
        addSales();
        try {
            txtSalesBillAdmin.print();
        } catch (PrinterException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            showSales();
        }
    }//GEN-LAST:event_btnSalesPrintAdminActionPerformed

    private void btnSignOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSignOutMouseClicked
        String AdminUserName = txtProfileNameUpdate.getText();

        try {
            String sql = "SELECT Admin_ID FROM admin_details WHERE Name =?";
            pst = DBConnection.getConnection().prepareStatement(sql);
            pst.setString(1, AdminUserName);
            rs = pst.executeQuery();
            if (rs.next()) {
                int AdminUserID = rs.getInt("Admin_ID");
                LocalDateTime localdate = LocalDateTime.now();
                DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MMM-dd-yy h:mm a");
                String AdminLogoutDateTime = DTF.format(localdate);
                LC.AdminInsert(String.valueOf(AdminUserID), AdminUserName, AdminLoginDateTime, AdminLogoutDateTime);
                AdminInsertFile();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }

        this.dispose();
        SignIn SI = new SignIn();
        SI.setVisible(true);
    }//GEN-LAST:event_btnSignOutMouseClicked

    private void btnDisplaySupplierReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplaySupplierReportActionPerformed
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/blacklotus", "root", "");
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Tempo\\Documents\\NetBeansProjects\\JavaFinal\\Reports\\MyReports\\SupplierReport.jrxml");

            Object SI = cbSupplierReportSort.getSelectedItem();
            if ("Supplier ID".equals(SI)) {
                String query = "SELECT * FROM supplier_details ORDER BY Supplier_ID ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Supplier Name".equals(SI)) {
                String query = "SELECT * FROM supplier_details ORDER BY Supplier_Name ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnDisplaySupplierReportActionPerformed

    private void btnItemFilterReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemFilterReportActionPerformed
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String Added_Date1 = sdf.format(jdcAddedDateSortReport1.getDate());
            String Added_Date2 = sdf.format(jdcAddedDateSortReport2.getDate());

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/blacklotus", "root", "");
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Tempo\\Documents\\NetBeansProjects\\JavaFinal\\Reports\\MyReports\\StockReport.jrxml");

            String query = "SELECT * FROM `stock_details` WHERE Added_Date BETWEEN '" + Added_Date1 + "' AND '" + Added_Date2 + "'";
            JRDesignQuery updateQuery = new JRDesignQuery();
            updateQuery.setText(query);

            jdesign.setQuery(updateQuery);

            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
            JasperViewer.viewReport(jprint, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnItemFilterReportActionPerformed

    private void btnItemSortReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemSortReportActionPerformed
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/blacklotus", "root", "");
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Tempo\\Documents\\NetBeansProjects\\JavaFinal\\Reports\\MyReports\\StockReport.jrxml");

            Object SI = cbSupplierReportSort.getSelectedItem();
            if ("Item ID".equals(SI)) {
                String query = "SELECT * FROM stock_details ORDER BY Item_ID ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Item Name".equals(SI)) {
                String query = "SELECT * FROM stock_details ORDER BY Item_Name ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Category".equals(SI)) {
                String query = "SELECT * FROM stock_details ORDER BY Category ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);

            } else if ("Lowest Profit".equals(SI)) {
                String query = "SELECT * FROM stock_details ORDER BY Profit ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Highest Profit".equals(SI)) {
                String query = "SELECT * FROM stock_details ORDER BY Profit DESC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Supplier ID".equals(SI)) {
                String query = "SELECT * FROM stock_details ORDER BY Supplier_ID ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnItemSortReportActionPerformed

    private void btnItemResetViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemResetViewActionPerformed
        showItems();
    }//GEN-LAST:event_btnItemResetViewActionPerformed

    private void btnSaleItemFilterReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaleItemFilterReportActionPerformed
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String Added_Date1 = sdf.format(jdcSaleAddedDateSortReport3.getDate());
            String Added_Date2 = sdf.format(jdcSaleAddedDateSortReport4.getDate());

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/blacklotus", "root", "");
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Tempo\\Documents\\NetBeansProjects\\JavaFinal\\Reports\\MyReports\\SalesReport.jrxml");

            String query = "SELECT * FROM `sales_details` WHERE Sold_Date BETWEEN '" + Added_Date1 + "' AND '" + Added_Date2 + "'";
            JRDesignQuery updateQuery = new JRDesignQuery();
            updateQuery.setText(query);

            jdesign.setQuery(updateQuery);

            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
            JasperViewer.viewReport(jprint, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnSaleItemFilterReportActionPerformed

    private void btnSaleItemSortReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaleItemSortReportActionPerformed
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/blacklotus", "root", "");
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Tempo\\Documents\\NetBeansProjects\\JavaFinal\\Reports\\MyReports\\SalesReport.jrxml");

            Object SI = cbSaleItemSortReport.getSelectedItem();
            if ("Sale ID".equals(SI)) {
                String query = "SELECT * FROM sales_details ORDER BY Sale_ID ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Lowest Profit".equals(SI)) {
                String query = "SELECT * FROM sales_details ORDER BY Profit ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Highest Profit".equals(SI)) {
                String query = "SELECT * FROM sales_details ORDER BY Profit DESC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnSaleItemSortReportActionPerformed

    private void btnSaleItemFilterViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaleItemFilterViewActionPerformed
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String Added_Date1 = sdf.format(jdcSaleAddedDateSort1.getDate());
        String Added_Date2 = sdf.format(jdcSaleAddedDateSort2.getDate());
        try {
            String sql = "SELECT * FROM `sales_details` WHERE Sold_Date BETWEEN '" + Added_Date1 + "' AND '" + Added_Date2 + "'";
            pst = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            DefaultTableModel DTM = (DefaultTableModel) jtSaleItemView.getModel();
            DTM.setRowCount(0);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    Object O[] = {rs.getInt("Sale_ID"), rs.getInt("Total"), rs.getInt("Profit"), rs.getDate("Sold_Date")};
                    DTM.addRow(O);
                }
            } else {
                JOptionPane.showMessageDialog(null, "There are no records between entered two days", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }//GEN-LAST:event_btnSaleItemFilterViewActionPerformed

    private void cbSaleItemSortItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSaleItemSortItemStateChanged
        try {
            Object SI = cbSaleItemSort.getSelectedItem();
            if ("Item ID".equals(SI)) {
                String sql = "SELECT * FROM sales_details ORDER BY Sale_ID ASC";
                pst = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                if (rs.isBeforeFirst()) {
                    int r = 0;
                    while (rs.next()) {
                        jtSaleItemView.getModel().setValueAt(rs.getString(1), r, 0);
                        jtSaleItemView.getModel().setValueAt(rs.getString(2), r, 1);
                        jtSaleItemView.getModel().setValueAt(rs.getString(3), r, 2);
                        jtSaleItemView.getModel().setValueAt(rs.getString(4), r, 3);
                        r++;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if ("Lowest Profit".equals(SI)) {
                String sql = "SELECT * FROM sales_details ORDER BY Profit ASC";
                pst = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                if (rs.isBeforeFirst()) {
                    int r = 0;
                    while (rs.next()) {
                        jtSaleItemView.getModel().setValueAt(rs.getString(1), r, 0);
                        jtSaleItemView.getModel().setValueAt(rs.getString(2), r, 1);
                        jtSaleItemView.getModel().setValueAt(rs.getString(3), r, 2);
                        jtSaleItemView.getModel().setValueAt(rs.getString(4), r, 3);
                        r++;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if ("Highest Profit".equals(SI)) {
                String sql = "SELECT * FROM sales_details ORDER BY Profit DESC";
                pst = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                if (rs.isBeforeFirst()) {
                    int r = 0;
                    while (rs.next()) {
                        jtSaleItemView.getModel().setValueAt(rs.getString(1), r, 0);
                        jtSaleItemView.getModel().setValueAt(rs.getString(2), r, 1);
                        jtSaleItemView.getModel().setValueAt(rs.getString(3), r, 2);
                        jtSaleItemView.getModel().setValueAt(rs.getString(4), r, 3);
                        r++;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            System.err.println("Error :" + ex.getMessage());
        }
    }//GEN-LAST:event_cbSaleItemSortItemStateChanged

    private void btnSaleItemResetViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaleItemResetViewActionPerformed
        showSales();
    }//GEN-LAST:event_btnSaleItemResetViewActionPerformed

    private void txtSaleItemIDViewKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaleItemIDViewKeyReleased
        DefaultTableModel DTM = (DefaultTableModel) jtSaleItemView.getModel();
        String Search = txtSaleItemIDView.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(DTM);
        jtSaleItemView.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(Search));
    }//GEN-LAST:event_txtSaleItemIDViewKeyReleased

    private void btnReportStockFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportStockFilterActionPerformed
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String Added_Date1 = sdf.format(jdcReportStockFilter1.getDate());
            String Added_Date2 = sdf.format(jdcReportStockFilter2.getDate());

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/blacklotus", "root", "");
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Tempo\\Documents\\NetBeansProjects\\JavaFinal\\Reports\\MyReports\\StockReport.jrxml");

            String query = "SELECT * FROM `stock_details` WHERE Added_Date BETWEEN '" + Added_Date1 + "' AND '" + Added_Date2 + "'";
            JRDesignQuery updateQuery = new JRDesignQuery();
            updateQuery.setText(query);

            jdesign.setQuery(updateQuery);

            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
            JasperViewer.viewReport(jprint, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnReportStockFilterActionPerformed

    private void btnReportItemSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportItemSortActionPerformed
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/blacklotus", "root", "");
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Tempo\\Documents\\NetBeansProjects\\JavaFinal\\Reports\\MyReports\\StockReport.jrxml");

            Object SI = cbReportItemSort.getSelectedItem();
            if ("Item ID".equals(SI)) {
                String query = "SELECT * FROM stock_details ORDER BY Item_ID ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Item Name".equals(SI)) {
                String query = "SELECT * FROM stock_details ORDER BY Item_Name ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Category".equals(SI)) {
                String query = "SELECT * FROM stock_details ORDER BY Category ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);

            } else if ("Lowest Profit".equals(SI)) {
                String query = "SELECT * FROM stock_details ORDER BY Profit ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Highest Profit".equals(SI)) {
                String query = "SELECT * FROM stock_details ORDER BY Profit DESC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Supplier ID".equals(SI)) {
                String query = "SELECT * FROM stock_details ORDER BY Supplier_ID ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnReportItemSortActionPerformed

    private void btnReportSalesSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportSalesSortActionPerformed
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/blacklotus", "root", "");
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Tempo\\Documents\\NetBeansProjects\\JavaFinal\\Reports\\MyReports\\SalesReport.jrxml");

            Object SI = cbReportSalesSort.getSelectedItem();
            if ("Sale ID".equals(SI)) {
                String query = "SELECT * FROM sales_details ORDER BY Sale_ID ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Lowest Profit".equals(SI)) {
                String query = "SELECT * FROM sales_details ORDER BY Profit ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Highest Profit".equals(SI)) {
                String query = "SELECT * FROM sales_details ORDER BY Profit DESC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnReportSalesSortActionPerformed

    private void btnReportSalesFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportSalesFilterActionPerformed
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String Added_Date1 = sdf.format(jdcReportSalesFilter1.getDate());
            String Added_Date2 = sdf.format(jdcReportSalesFilter2.getDate());

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/blacklotus", "root", "");
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Tempo\\Documents\\NetBeansProjects\\JavaFinal\\Reports\\MyReports\\SalesReport.jrxml");

            String query = "SELECT * FROM `sales_details` WHERE Sold_Date BETWEEN '" + Added_Date1 + "' AND '" + Added_Date2 + "'";
            JRDesignQuery updateQuery = new JRDesignQuery();
            updateQuery.setText(query);

            jdesign.setQuery(updateQuery);

            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
            JasperViewer.viewReport(jprint, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnReportSalesFilterActionPerformed

    private void btnReportSupplierSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportSupplierSortActionPerformed
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/blacklotus", "root", "");
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Tempo\\Documents\\NetBeansProjects\\JavaFinal\\Reports\\MyReports\\SupplierReport.jrxml");

            Object SI = cbReportSupplierSort.getSelectedItem();
            if ("Supplier ID".equals(SI)) {
                String query = "SELECT * FROM supplier_details ORDER BY Supplier_ID ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Supplier Name".equals(SI)) {
                String query = "SELECT * FROM supplier_details ORDER BY Supplier_Name ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnReportSupplierSortActionPerformed

    private void btnEmployeeClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmployeeClearActionPerformed
        txtEmployeeIDUpdate.setText(null);
        txtEmployeeNameUpdate.setText(null);
        txtEmployeeEmailUpdate.setText(null);
        txtEmployeeContactUpdate.setText(null);
        txtEmployeeGenderUpdate.setText(null);
        txtEmployeePasswordUpdate.setText(null);
        txtEmployeeCPasswordUpdate.setText(null);
        txtEmployeeIDUpdate.requestFocus();
    }//GEN-LAST:event_btnEmployeeClearActionPerformed

    private void btnEmployeeUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmployeeUpdateActionPerformed
        if (txtEmployeeIDUpdate.getText().trim().isEmpty() || txtEmployeeNameUpdate.getText().trim().isEmpty() || txtEmployeeEmailUpdate.getText().trim().isEmpty() || txtEmployeeContactUpdate.getText().trim().isEmpty() || txtEmployeePasswordUpdate.getText().trim().isEmpty() || txtEmployeeCPasswordUpdate.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You cannot leave this field/s empty", "Login error", JOptionPane.ERROR_MESSAGE);
        } else {

            int YesOrNo = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING!", JOptionPane.YES_NO_OPTION);
            if (YesOrNo == 0) {
                if (txtEmployeePasswordUpdate.getText().trim().equals(txtEmployeeCPasswordUpdate.getText().trim())) {
                    int Employee_ID = Integer.parseInt(txtEmployeeIDUpdate.getText());
                    String Name = txtEmployeeNameUpdate.getText();
                    String Email = txtEmployeeEmailUpdate.getText();
                    String Password = txtEmployeePasswordUpdate.getText();
                    int Contact_Number = Integer.parseInt(txtEmployeeContactUpdate.getText());
                    try {
                        String sql = "UPDATE `employee_details` SET `Name`=?, `Email`=?,`Contact_Number`=?,`Password`=? WHERE `Employee_ID`=?";

                        pst = DBConnection.getConnection().prepareStatement(sql);
                        pst.setString(1, Name);
                        pst.setString(2, Email);
                        pst.setInt(3, Contact_Number);
                        pst.setString(4, Password);
                        pst.setInt(5, Employee_ID);
                        if (pst.executeUpdate() > 0) {
                            JOptionPane.showMessageDialog(null, "Record updated");
                            showSuppliers();
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Employee ID", "Update error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        txtEmployeeIDUpdate.setText(null);
                        txtEmployeeNameUpdate.setText(null);
                        txtEmployeeEmailUpdate.setText(null);
                        txtEmployeeContactUpdate.setText(null);
                        txtEmployeeGenderUpdate.setText(null);
                        txtEmployeePasswordUpdate.setText(null);
                        txtEmployeeCPasswordUpdate.setText(null);
                        txtEmployeeIDUpdate.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Password does not match", "Error", JOptionPane.ERROR_MESSAGE);
                    txtEmployeePasswordUpdate.setText(null);
                    txtEmployeeCPasswordUpdate.setText(null);
                    txtEmployeePasswordUpdate.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_btnEmployeeUpdateActionPerformed

    private void btnEmployeeReportSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmployeeReportSortActionPerformed
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/blacklotus", "root", "");
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Tempo\\Documents\\NetBeansProjects\\JavaFinal\\Reports\\MyReports\\EmployeeReport.jrxml");

            Object SI = cbEmployeeReportSort.getSelectedItem();
            if ("Employee ID".equals(SI)) {
                String query = "SELECT * FROM employee_details ORDER BY employee_ID ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Name".equals(SI)) {
                String query = "SELECT * FROM employee_details ORDER BY Name ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnEmployeeReportSortActionPerformed

    private void cbEmployeeSortItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEmployeeSortItemStateChanged
        try {
            Object SI = cbEmployeeSort.getSelectedItem();
            if ("Employee ID".equals(SI)) {
                String sql = "SELECT * FROM employee_details ORDER BY Employee_ID ASC";
                pst = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                if (rs.isBeforeFirst()) {
                    int r = 0;
                    while (rs.next()) {
                        jtEmployeeView.getModel().setValueAt(rs.getString(1), r, 0);
                        jtEmployeeView.getModel().setValueAt(rs.getString(2), r, 1);
                        jtEmployeeView.getModel().setValueAt(rs.getString(3), r, 2);
                        jtEmployeeView.getModel().setValueAt(rs.getString(4), r, 3);
                        jtEmployeeView.getModel().setValueAt(rs.getString(5), r, 4);
                        r++;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if ("Name".equals(SI)) {
                String sql = "SELECT * FROM employee_details ORDER BY Name ASC";
                pst = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                if (rs.isBeforeFirst()) {
                    int r = 0;
                    while (rs.next()) {
                        jtEmployeeView.getModel().setValueAt(rs.getString(1), r, 0);
                        jtEmployeeView.getModel().setValueAt(rs.getString(2), r, 1);
                        jtEmployeeView.getModel().setValueAt(rs.getString(3), r, 2);
                        jtEmployeeView.getModel().setValueAt(rs.getString(4), r, 3);
                        jtEmployeeView.getModel().setValueAt(rs.getString(5), r, 4);
                        r++;

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            System.err.println("Error :" + ex.getMessage());
        }
    }//GEN-LAST:event_cbEmployeeSortItemStateChanged

    private void txtEmployeeSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmployeeSearchKeyReleased
        DefaultTableModel DTM = (DefaultTableModel) jtEmployeeView.getModel();
        String Search = txtEmployeeSearch.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(DTM);
        jtEmployeeView.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(Search));
    }//GEN-LAST:event_txtEmployeeSearchKeyReleased

    private void btnEmployeesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmployeesMouseClicked
        setPanel("card7");
        InventorySidePanel.setVisible(false);
        SuppliersSidePanel.setVisible(false);
        EmployeesSidePanel.setVisible(true);
        SalesSidePanel.setVisible(false);
        ReportSidePanel.setVisible(false);
        ProfileSidePanel.setVisible(false);
        AuditLogSidePanel.setVisible(false);
        btnAuditLog.setForeground(Color.WHITE);
        btnInventory.setForeground(Color.WHITE);
        btnSuppliers.setForeground(Color.WHITE);
        btnEmployees.setForeground(Color.orange);
        btnSales.setForeground(Color.WHITE);
        btnReport.setForeground(Color.WHITE);
        btnProfile.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnEmployeesMouseClicked

    private void txtEmployeeIDUpdateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmployeeIDUpdateKeyReleased
        try {
            String sql = "SELECT * FROM employee_details WHERE Employee_ID =?";
            pst = DBConnection.getConnection().prepareStatement(sql);
            pst.setString(1, txtEmployeeIDUpdate.getText());

            rs = pst.executeQuery();
            if (rs.next()) {
                int EID = rs.getInt("Employee_ID");
                txtEmployeeIDUpdate.setText(String.valueOf(EID));
                String EN = rs.getString("Name");
                txtEmployeeNameUpdate.setText(EN);
                String EE = rs.getString("Email");
                txtEmployeeEmailUpdate.setText(EE);
                int EC = rs.getInt("Contact_Number");
                txtEmployeeContactUpdate.setText(String.valueOf(EC));
                String EG = rs.getString("Gender");
                txtEmployeeGenderUpdate.setText(EG);
            } else {
                txtEmployeeIDUpdate.setText(null);
                txtEmployeeNameUpdate.setText(null);
                txtEmployeeEmailUpdate.setText(null);
                txtEmployeeContactUpdate.setText(null);
                txtEmployeeGenderUpdate.setText(null);
                txtSupplierIDUpdate.requestFocus();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }//GEN-LAST:event_txtEmployeeIDUpdateKeyReleased

    private void btnReportEmployeeSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportEmployeeSortActionPerformed
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/blacklotus", "root", "");
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Tempo\\Documents\\NetBeansProjects\\JavaFinal\\Reports\\MyReports\\EmployeeReport.jrxml");

            Object SI = cbReportEmployeeSort.getSelectedItem();
            if ("Employee ID".equals(SI)) {
                String query = "SELECT * FROM employee_details ORDER BY employee_ID ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            } else if ("Name".equals(SI)) {
                String query = "SELECT * FROM employee_details ORDER BY Name ASC";
                JRDesignQuery updateQuery = new JRDesignQuery();
                updateQuery.setText(query);

                jdesign.setQuery(updateQuery);

                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
                JasperViewer.viewReport(jprint, false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnReportEmployeeSortActionPerformed

    private void btnAuditLogMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAuditLogMouseClicked
        setPanel("card8");
        InventorySidePanel.setVisible(false);
        SuppliersSidePanel.setVisible(false);
        EmployeesSidePanel.setVisible(false);
        SalesSidePanel.setVisible(false);
        ReportSidePanel.setVisible(false);
        ProfileSidePanel.setVisible(false);
        AuditLogSidePanel.setVisible(true);
        btnInventory.setForeground(Color.WHITE);
        btnSuppliers.setForeground(Color.WHITE);
        btnEmployees.setForeground(Color.WHITE);
        btnSales.setForeground(Color.WHITE);
        btnReport.setForeground(Color.WHITE);
        btnProfile.setForeground(Color.WHITE);
        btnAuditLog.setForeground(Color.orange);

    }//GEN-LAST:event_btnAuditLogMouseClicked

    private void btnAdminLogDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminLogDisplayActionPerformed
        txtEmployeeLogView.setText(null);
        txtAdminLogView.setText(LC.AdminDisplay());
    }//GEN-LAST:event_btnAdminLogDisplayActionPerformed

    private void btnEmployeeLogDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmployeeLogDisplayActionPerformed
        txtEmployeeLogView.setText(null);
        txtEmployeeLogView.setText(LC.EmployeeDisplay());
    }//GEN-LAST:event_btnEmployeeLogDisplayActionPerformed

    private void txtAdminLogSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdminLogSearchKeyReleased

        String AdminUserID = txtAdminLogSearch.getText();
        String AdminUserName = txtAdminLogSearch.getText();
        String AdminLoginDateTime = txtAdminLogSearch.getText();
        txtAdminLogView.setText(LC.SearchAdmin(AdminUserID, AdminUserName, AdminLoginDateTime));

    }//GEN-LAST:event_txtAdminLogSearchKeyReleased

    private void txtEmployeeLogSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmployeeLogSearchKeyReleased
        EmployeeLoadData();
        String EmployeeUserID = txtEmployeeLogSearch.getText();
        String EmployeeUserName = txtEmployeeLogSearch.getText();
        String EmployeeLoginDateTime = txtEmployeeLogSearch.getText();
        txtEmployeeLogView.setText(LC.SearchEmployee(EmployeeUserID, EmployeeUserName, EmployeeLoginDateTime));

    }//GEN-LAST:event_txtEmployeeLogSearchKeyReleased

    private void btnEmployeeClearInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmployeeClearInsertActionPerformed
        txtEmployeeIDInsert.setText(null);
        txtEmployeeNameInsert.setText(null);
        txtEmployeeEmailInsert.setText(null);
        txtEmployeeContactInsert.setText(null);
        txtEmployeeGenderInsert.setText(null);
        txtEmployeePasswordInsert.setText(null);
        txtEmployeeCPasswordInsert.setText(null);
        txtEmployeeIDInsert.requestFocus();
    }//GEN-LAST:event_btnEmployeeClearInsertActionPerformed

    private void btnEmployeeInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmployeeInsertActionPerformed
        if (txtEmployeeIDInsert.getText().trim().isEmpty() || txtEmployeeNameInsert.getText().trim().isEmpty() || txtEmployeeEmailInsert.getText().trim().isEmpty() || txtEmployeeContactInsert.getText().trim().isEmpty() || txtEmployeePasswordInsert.getText().trim().isEmpty() || txtEmployeeCPasswordInsert.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You cannot leave this field/s empty", "Login error", JOptionPane.ERROR_MESSAGE);
        } else {

            int YesOrNo = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING!", JOptionPane.YES_NO_OPTION);
            if (YesOrNo == 0) {
                if (txtEmployeePasswordInsert.getText().trim().equals(txtEmployeeCPasswordInsert.getText().trim())) {
                    String Name = txtEmployeeNameInsert.getText();
                    String Email = txtEmployeeEmailInsert.getText();
                    String Password = txtEmployeePasswordInsert.getText();
                    int Contact_Number = Integer.parseInt(txtEmployeeContactInsert.getText());
                    try {
                        String sql = "INSERT INTO `employee_details` ( `Name`, `Email`, `Contact_Number`, `Password`) VALUES (?,?,?,?)";

                        pst = DBConnection.getConnection().prepareStatement(sql);
                        pst.setString(1, Name);
                        pst.setString(2, Email);
                        pst.setInt(3, Contact_Number);
                        pst.setString(4, Password);
                        if (pst.executeUpdate() > 0) {
                            JOptionPane.showMessageDialog(null, "Record Insert");
                            showSuppliers();
                        } else {
                            JOptionPane.showMessageDialog(null, "Existing Employee ID", "Insert error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        txtEmployeeNameInsert.setText(null);
                        txtEmployeeEmailInsert.setText(null);
                        txtEmployeeContactInsert.setText(null);
                        txtEmployeeGenderInsert.setText(null);
                        txtEmployeePasswordInsert.setText(null);
                        txtEmployeeCPasswordInsert.setText(null);
                        txtEmployeeNameInsert.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Password does not match", "Error", JOptionPane.ERROR_MESSAGE);
                    txtEmployeePasswordInsert.setText(null);
                    txtEmployeeCPasswordInsert.setText(null);
                    txtEmployeePasswordInsert.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_btnEmployeeInsertActionPerformed

    public void maxStockID() { //To display the next Item ID
        try {
            String sql = "SELECT MAX(Item_ID) as Item_ID FROM stock_details";
            pst = DBConnection.getConnection().prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                int Iid = rs.getInt("Item_ID");
                int IID = Iid + 1;
                txtItemIDInsert.setText(String.valueOf(IID));

            } else {
                txtItemIDInsert.setText("1");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }
    
    public void maxEmployeeID() { //To display the next Item ID
        try {
            String sql = "SELECT MAX(Employee_ID) as Employee_ID FROM employee_details";
            pst = DBConnection.getConnection().prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                int Eid = rs.getInt("Employee_ID");
                int EID = Eid + 1;
                txtEmployeeIDInsert.setText(String.valueOf(EID));

            } else {
                txtEmployeeIDInsert.setText("1");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }
    
    public void maxSupplierID() {
        try {
            String sql = "SELECT MAX(Supplier_ID) as Supplier_ID FROM supplier_details";
            pst = DBConnection.getConnection().prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                int Sid = rs.getInt("Supplier_ID");
                int SID = Sid + 1;
                txtSupplierIDInsert.setText(String.valueOf(SID));

            } else {
                txtSupplierIDInsert.setText("1");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void showItems() {
        try {
            String sql = "SELECT * FROM `stock_details`";
            pst = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            DefaultTableModel DTM = (DefaultTableModel) jtItemView.getModel();
            DTM.setRowCount(0);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    Object O[] = {rs.getInt("Item_ID"), rs.getString("Item_Name"), rs.getString("Category"), rs.getInt("Quantity"), rs.getInt("Price"), rs.getInt("Profit"), rs.getDate("Added_Date"), rs.getInt("Supplier_ID")};
                    DTM.addRow(O);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            System.err.println("Error :" + ex.getMessage());
        }
    }

    public void showEmployees() {
        try {
            String sql = "SELECT * FROM `employee_details`";
            pst = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            DefaultTableModel DTM = (DefaultTableModel) jtEmployeeView.getModel();
            DTM.setRowCount(0);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    Object O[] = {rs.getInt("Employee_ID"), rs.getString("Name"), rs.getString("Email"), rs.getInt("Contact_Number"), rs.getString("Gender")};
                    DTM.addRow(O);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            System.err.println("Error :" + ex.getMessage());
        }
    }

    public void showSales() {
        try {
            String sql = "SELECT * FROM `sales_details`";
            pst = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            DefaultTableModel DTM = (DefaultTableModel) jtSaleItemView.getModel();
            DTM.setRowCount(0);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    Object O[] = {rs.getInt("Sale_ID"), rs.getInt("Total"), rs.getInt("Profit"), rs.getDate("Sold_Date")};
                    DTM.addRow(O);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            System.err.println("Error :" + ex.getMessage());
        }
    }

    public void addSales() {
        int TotProfit = 0;
        for (int value : LC.arrayProfit) {
            TotProfit = TotProfit + value;
        }
        int Total = Integer.parseInt(txtSalesTotalAdmin.getText());
        int Profit = TotProfit;
        DateFormat DF = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String Sold_Date = DF.format(date);
        try {
            String sql = "INSERT INTO `sales_details`(`Total`, `Profit`, `Sold_Date`) VALUES (?,?,?)";

            pst = DBConnection.getConnection().prepareStatement(sql);
            pst.setInt(1, Total);
            pst.setInt(2, Profit);
            pst.setString(3, Sold_Date);
            if (pst.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Sales details inserted");
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            txtSalesTotalAdmin.setText(null);
            txtSalesAmountAdmin.setText(null);
            txtSalesBalanceAdmin.setText(null);
        }
    }

    public void showLowStock() {
        try {
            String sql = "SELECT `Item_ID`,`Item_Name`,`Quantity` FROM `stock_details` WHERE `Quantity` < 20";
            pst = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            DefaultTableModel DTM = (DefaultTableModel) jtItemStockView.getModel();
            DTM.setRowCount(0);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    Object O[] = {rs.getInt("Item_ID"), rs.getString("Item_Name"), rs.getInt("Quantity")};
                    DTM.addRow(O);
                }
            } else {
                lblLowStock.setVisible(false);
                DTM.setRowCount(0);
            }
        } catch (Exception ex) {
            System.err.println("Error :" + ex.getMessage());
        }
    }

    public void showSuppliers() {
        try {
            String sql = "SELECT * FROM `supplier_details`";
            pst = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            DefaultTableModel DTM = (DefaultTableModel) jtItemSupplierView.getModel();
            DTM.setRowCount(0);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    Object O[] = {rs.getInt("Supplier_ID"), rs.getString("Supplier_Name")};
                    DTM.addRow(O);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Table is empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            System.err.println("Error :" + ex.getMessage());
        }
    }

    public int GetQuantity() {
        int NewQty = Integer.parseInt(txtSalesQuantityAdmin.getText());
        int quantity = 0;
        try {
            String SQL = "SELECT `Quantity` FROM `stock_details` WHERE `Item_ID` =?";
            pst = DBConnection.getConnection().prepareStatement(SQL);
            pst.setString(1, txtSalesItemIDAdmin.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                int OldQty = rs.getInt("Quantity");
                quantity = OldQty - NewQty;
                return quantity;
            }
            return quantity;
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            return quantity;
        } finally {
            return quantity;
        }
    }

    public int GetProfit() {
        int Item_ID = Integer.parseInt(txtSalesItemIDAdmin.getText());
        int profit = 0;
        try {
            String SQL = "SELECT `Profit` FROM `stock_details` WHERE `Item_ID` =?";
            pst = DBConnection.getConnection().prepareStatement(SQL);
            pst.setString(1, txtSalesItemIDAdmin.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                int getprofit = rs.getInt("Profit");
                profit = getprofit;
                return profit;
            }
            return profit;
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            return profit;
        } finally {
            return profit;
        }
    }

    void adminProfile(String PEmail) {
        try {
            String sql = "SELECT Admin_ID,Name,Email,Contact_Number FROM admin_details WHERE Email =?";
            pst = DBConnection.getConnection().prepareStatement(sql);
            pst.setString(1, PEmail);
            rs = pst.executeQuery();
            if (rs.next()) {
                int AID = rs.getInt("Admin_ID");
                txtProfileAdminIDUpdate.setText(String.valueOf(AID));
                String AN = rs.getString("Name");
                txtProfileNameUpdate.setText(AN);
                String AE = rs.getString("Email");
                txtProfileEmailUpdate.setText(AE);
                int ACN = rs.getInt("Contact_Number");
                txtProfileContactUpdate.setText(String.valueOf(ACN));

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void showItemSupplier() {
        try {
            String sql = "SELECT * FROM `supplier_details`";
            pst = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            DefaultTableModel DTM = (DefaultTableModel) jtSupplierView.getModel();
            DTM.setRowCount(0);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    Object O[] = {rs.getInt("Supplier_ID"), rs.getString("Supplier_Name"), rs.getString("Email"), rs.getInt("Contact_Number")};
                    DTM.addRow(O);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Supplier ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            System.err.println("Error :" + ex.getMessage());
        }
    }

    public void AdminInsertFile() {
        try {
            String result = "";
            for (int x = 0; x < LC.elements; x++) {
                result += LC.AdminUserID[x] + ";";
                result += LC.AdminUserName[x] + ";";
                result += LC.AdminLoginDateTime[x] + ";";
                result += LC.AdminLogoutDateTime[x] + "\n";
            }
            File newFile = new File("AdminTemp.txt");
            FileWriter TempFile = new FileWriter(newFile, true);
            BufferedWriter writer = new BufferedWriter(TempFile);
            writer.append(result);
            writer.close();
            TextFile.delete();
            File dump = new File("AdminLogInfo.txt");
            newFile.renameTo(dump);
        } catch (IOException ex) {
            Logger.getLogger(LogClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AuditLogSidePanel;
    private javax.swing.JPanel BorderPanel;
    private javax.swing.JPanel CardPanel;
    private javax.swing.JPanel EmployeePanel;
    private javax.swing.JPanel EmployeesSidePanel;
    private javax.swing.JPanel InventoryPanel;
    private javax.swing.JPanel InventorySidePanel;
    private javax.swing.JPanel LogAuditPanel;
    private javax.swing.JPanel ProfilePanel;
    private javax.swing.JPanel ProfileSidePanel;
    private javax.swing.JPanel ReportPanel;
    private javax.swing.JPanel ReportSidePanel;
    private javax.swing.JPanel SalesPanel;
    private javax.swing.JPanel SalesSidePanel;
    private javax.swing.JPanel SupplierPanel;
    private javax.swing.JPanel SuppliersSidePanel;
    private javax.swing.JButton btnAdminLogDisplay;
    private javax.swing.JLabel btnAuditLog;
    private javax.swing.JButton btnDisplaySupplierReport;
    private javax.swing.JButton btnEmployeeClear;
    private javax.swing.JButton btnEmployeeClearInsert;
    private javax.swing.JButton btnEmployeeInsert;
    private javax.swing.JButton btnEmployeeLogDisplay;
    private javax.swing.JButton btnEmployeeReportSort;
    private javax.swing.JButton btnEmployeeUpdate;
    private javax.swing.JLabel btnEmployees;
    private javax.swing.JButton btnInsertSupplier;
    private javax.swing.JLabel btnInventory;
    private javax.swing.JButton btnItemClearInsert;
    private javax.swing.JButton btnItemClearUpdate;
    private javax.swing.JButton btnItemDelete;
    private javax.swing.JButton btnItemFilterReport;
    private javax.swing.JButton btnItemFilterView;
    private javax.swing.JButton btnItemInsert;
    private javax.swing.JButton btnItemResetView;
    private javax.swing.JButton btnItemSortReport;
    private javax.swing.JButton btnItemUpdate;
    private javax.swing.JLabel btnProfile;
    private javax.swing.JButton btnProfileUpdate;
    private javax.swing.JLabel btnReport;
    private javax.swing.JButton btnReportEmployeeSort;
    private javax.swing.JButton btnReportItemSort;
    private javax.swing.JButton btnReportSalesFilter;
    private javax.swing.JButton btnReportSalesSort;
    private javax.swing.JButton btnReportStockFilter;
    private javax.swing.JButton btnReportSupplierSort;
    private javax.swing.JButton btnSaleItemFilterReport;
    private javax.swing.JButton btnSaleItemFilterView;
    private javax.swing.JButton btnSaleItemResetView;
    private javax.swing.JButton btnSaleItemSortReport;
    private javax.swing.JLabel btnSales;
    private javax.swing.JButton btnSalesAddAdmin;
    private javax.swing.JButton btnSalesDisplayAdmin;
    private javax.swing.JButton btnSalesPrintAdmin;
    private javax.swing.JLabel btnSignOut;
    private javax.swing.JButton btnSupplierClearInsert;
    private javax.swing.JButton btnSupplierClearUpdate;
    private javax.swing.JButton btnSupplierDelete;
    private javax.swing.JButton btnSupplierUpdate;
    private javax.swing.JLabel btnSuppliers;
    private javax.swing.JComboBox<String> cbEmployeeReportSort;
    private javax.swing.JComboBox<String> cbEmployeeSort;
    private javax.swing.JComboBox<String> cbItemCategoryInsert;
    private javax.swing.JComboBox<String> cbItemCategoryUpdate;
    private javax.swing.JComboBox<String> cbItemSort;
    private javax.swing.JComboBox<String> cbItemSortReport;
    private javax.swing.JComboBox<String> cbReportEmployeeSort;
    private javax.swing.JComboBox<String> cbReportItemSort;
    private javax.swing.JComboBox<String> cbReportSalesSort;
    private javax.swing.JComboBox<String> cbReportSupplierSort;
    private javax.swing.JComboBox<String> cbSaleItemSort;
    private javax.swing.JComboBox<String> cbSaleItemSortReport;
    private javax.swing.JComboBox<String> cbSupplierReportSort;
    private javax.swing.JComboBox<String> cbSupplierSort;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private com.toedter.calendar.JDateChooser jdcAddedDateSort1;
    private com.toedter.calendar.JDateChooser jdcAddedDateSort2;
    private com.toedter.calendar.JDateChooser jdcAddedDateSortReport1;
    private com.toedter.calendar.JDateChooser jdcAddedDateSortReport2;
    private com.toedter.calendar.JDateChooser jdcReportSalesFilter1;
    private com.toedter.calendar.JDateChooser jdcReportSalesFilter2;
    private com.toedter.calendar.JDateChooser jdcReportStockFilter1;
    private com.toedter.calendar.JDateChooser jdcReportStockFilter2;
    private com.toedter.calendar.JDateChooser jdcSaleAddedDateSort1;
    private com.toedter.calendar.JDateChooser jdcSaleAddedDateSort2;
    private com.toedter.calendar.JDateChooser jdcSaleAddedDateSortReport3;
    private com.toedter.calendar.JDateChooser jdcSaleAddedDateSortReport4;
    private javax.swing.JTable jtEmployeeView;
    private javax.swing.JTable jtItemStockView;
    private javax.swing.JTable jtItemSupplierView;
    private javax.swing.JTable jtItemView;
    private javax.swing.JTable jtSaleItemView;
    private javax.swing.JTable jtSalesItemViewAdmin;
    private javax.swing.JTable jtSupplierView;
    private javax.swing.JLabel lblAdminPanelDateTime;
    private javax.swing.JLabel lblLowStock;
    private javax.swing.JTextField txItemPriceUpdate;
    private javax.swing.JTextField txtAdminLogSearch;
    private javax.swing.JTextArea txtAdminLogView;
    private javax.swing.JPasswordField txtEmployeeCPasswordInsert;
    private javax.swing.JPasswordField txtEmployeeCPasswordUpdate;
    private javax.swing.JTextField txtEmployeeContactInsert;
    private javax.swing.JTextField txtEmployeeContactUpdate;
    private javax.swing.JTextField txtEmployeeEmailInsert;
    private javax.swing.JTextField txtEmployeeEmailUpdate;
    private javax.swing.JTextField txtEmployeeGenderInsert;
    private javax.swing.JTextField txtEmployeeGenderUpdate;
    private javax.swing.JTextField txtEmployeeIDInsert;
    private javax.swing.JTextField txtEmployeeIDUpdate;
    private javax.swing.JTextField txtEmployeeLogSearch;
    private javax.swing.JTextArea txtEmployeeLogView;
    private javax.swing.JTextField txtEmployeeNameInsert;
    private javax.swing.JTextField txtEmployeeNameUpdate;
    private javax.swing.JPasswordField txtEmployeePasswordInsert;
    private javax.swing.JPasswordField txtEmployeePasswordUpdate;
    private javax.swing.JTextField txtEmployeeSearch;
    private javax.swing.JTextField txtItemIDInsert;
    private javax.swing.JTextField txtItemIDUpdate;
    private javax.swing.JTextField txtItemIDView;
    private javax.swing.JTextField txtItemNameInsert;
    private javax.swing.JTextField txtItemNameUpdate;
    private javax.swing.JTextField txtItemPriceInsert;
    private javax.swing.JTextField txtItemProfitInsert;
    private javax.swing.JTextField txtItemProfitUpdate;
    private javax.swing.JTextField txtItemQuantityInsert;
    private javax.swing.JTextField txtItemQuantityUpdate;
    private javax.swing.JTextField txtItemSupplierIDInsert;
    private javax.swing.JTextField txtItemSupplierIDUpdate;
    private javax.swing.JTextField txtProfileAdminIDUpdate;
    private javax.swing.JPasswordField txtProfileCPasswordUpdate;
    private javax.swing.JTextField txtProfileContactUpdate;
    private javax.swing.JTextField txtProfileEmailUpdate;
    private javax.swing.JTextField txtProfileNameUpdate;
    private javax.swing.JPasswordField txtProfilePasswordUpdate;
    private javax.swing.JTextField txtSaleItemIDView;
    private javax.swing.JTextField txtSalesAmountAdmin;
    private javax.swing.JTextField txtSalesBalanceAdmin;
    private javax.swing.JTextPane txtSalesBillAdmin;
    private javax.swing.JTextField txtSalesItemIDAdmin;
    private javax.swing.JTextField txtSalesItemNameAdmin;
    private javax.swing.JTextField txtSalesPriceAdmin;
    private javax.swing.JTextField txtSalesQuantityAdmin;
    private javax.swing.JTextField txtSalesSubtotalAdmin;
    private javax.swing.JTextField txtSalesTotalAdmin;
    private javax.swing.JTextField txtSupplierContactInsert;
    private javax.swing.JTextField txtSupplierContactUpdate;
    private javax.swing.JTextField txtSupplierEmailInsert;
    private javax.swing.JTextField txtSupplierEmailUpdate;
    private javax.swing.JTextField txtSupplierIDInsert;
    private javax.swing.JTextField txtSupplierIDUpdate;
    private javax.swing.JTextField txtSupplierIDView;
    private javax.swing.JTextField txtSupplierNameInsert;
    private javax.swing.JTextField txtSupplierNameUpdate;
    // End of variables declaration//GEN-END:variables

}
