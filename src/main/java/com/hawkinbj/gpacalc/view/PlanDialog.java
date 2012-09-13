/*    */ package com.hawkinbj.gpacalc.view;
/*    */ 
/*    */ import java.awt.GridLayout;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.swing.BoxLayout;
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JOptionPane;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JTextField;


import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.Course;
import com.hawkinbj.gpacalc.model.GUIPanel;
/*    */ 
/*    */ public class PlanDialog extends GUIPanel
/*    */   implements ActionListener
/*    */ {
/*    */   private static final long serialVersionUID = 8646422403970117818L;
/* 18 */   protected static final String[] CREDITHOURS = { "0", "1", "2", "3", "4" };
/*    */   protected JTextField courseNameField;
/*    */   protected JTextField newGradeTypeField;
/*    */   private JPanel namePanel;
/*    */   private JPanel navigationPanel;
/*    */   private JComboBox<String> creditHrsComboBox;
/*    */ 
/*    */   public PlanDialog(SystemController controller)
/*    */   {
/* 27 */     super(controller);
/* 28 */     addComponentsToPane();
/*    */   }
/*    */ 
/*    */   private void addComponentsToPane() {
/* 32 */     this.courseNameField = new JTextField(10);
/*    */ 
/* 34 */     this.namePanel = new JPanel(new GridLayout(3, 2));
/* 35 */     this.namePanel.add(new JLabel("Course name:"));
/* 36 */     this.namePanel.add(this.courseNameField);
/* 37 */     this.namePanel.add(new JLabel("Credit hours:"));
/*    */ 
/* 39 */     this.creditHrsComboBox = new JComboBox(CREDITHOURS);
/* 40 */     this.creditHrsComboBox.setSelectedItem("3");
/*    */ 
/* 42 */     this.namePanel.add(this.creditHrsComboBox);
/*    */ 
/* 44 */     this.navigationPanel = new JPanel(new GridLayout(2, 1));
/* 45 */     createTitledBorder(this.navigationPanel, "Navigation");
/* 46 */     this.navigationPanel.add(createButton("add", "Add"));
/* 47 */     this.navigationPanel.add(createButton("cancel", "Cancel"));
/*    */ 
/* 50 */     setLayout(new BoxLayout(this, 3));
/* 51 */     add(this.namePanel);
/* 52 */     add(this.navigationPanel);
/*    */   }
/*    */ 
/*    */   public void actionPerformed(ActionEvent e) {
/* 56 */     String action = e.getActionCommand();
/* 57 */     if (action.equals("cancel")) {
/* 58 */       this.controller.getRootFrame().showPanel("planPanel", this);
/* 59 */     } else if (action.equals("add")) {
/* 60 */       String courseName = this.courseNameField.getText();
/*    */ 
/* 62 */       if ((courseName == "") || (courseName == null)) {
/* 63 */         JOptionPane.showMessageDialog(this, 
/* 64 */           "Please enter a course name.", "Error", 
/* 65 */           0);
/*    */       }
/*    */ 
/* 69 */       if (this.controller.getActiveUser().getCoursesToTake().keySet()
/* 69 */         .contains(courseName)) {
/* 70 */         JOptionPane.showMessageDialog(this, "Course already exists.", 
/* 71 */           "Error", 0);
/*    */       } else {
/* 73 */         int creditHours = Integer.parseInt((String)this.creditHrsComboBox
/* 74 */           .getSelectedItem());
/*    */ 
/* 76 */         this.controller.getActiveUser().addCourseToTake(new Course(courseName, 
/* 77 */           creditHours));
/*    */ 
/* 79 */         this.controller.getRootFrame().addPanel(new PlanPanel(this.controller), this);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Brandon\Development\git\gpacalc\bin\
 * Qualified Name:     com.hawkinbj.gpacalc.PlanDialog
 * JD-Core Version:    0.6.1
 */