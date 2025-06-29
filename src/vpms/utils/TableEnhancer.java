package vpms.utils;

import javax.swing.JTable;
import javax.swing.table.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author being
 */
public class TableEnhancer {
    /* ========= table cosmetics ========= */
    public static void beautifyTable(JTable table,int[] widths){
        table.setRowHeight(25);

        /* zebra + selection */
        table.setDefaultRenderer(Object.class,new DefaultTableCellRenderer(){
            @Override
            public java.awt.Component getTableCellRendererComponent(
                  JTable tbl,Object val,boolean sel,boolean foc,int row,int col){
                super.getTableCellRendererComponent(tbl,val,sel,foc,row,col);
                if (sel){
                    setBackground(new java.awt.Color(100,149,237)); // cornflower
                    setForeground(java.awt.Color.WHITE);
                } else {
                    setForeground(java.awt.Color.BLACK);
                    setBackground(row%2==0 ? java.awt.Color.WHITE
                                            : new java.awt.Color(230,240,255));
                }
                return this;
            }
        });

        /* header */
        JTableHeader hdr = table.getTableHeader();
        hdr.setBackground(new java.awt.Color(70,130,180));   // steel blue
        hdr.setForeground(java.awt.Color.WHITE);
        hdr.setFont(hdr.getFont().deriveFont(java.awt.Font.BOLD));

        /* column widths */
        for (int i=0;i<widths.length && i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
    }

}
