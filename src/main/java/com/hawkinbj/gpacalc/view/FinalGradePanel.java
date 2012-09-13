/*    */ package com.hawkinbj.gpacalc.view;
/*    */ 
/*    */ import java.awt.GridLayout;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
/*    */ import java.util.Set;
/*    */ import javax.swing.BoxLayout;
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;

/*    */ 
/*    */ public class FinalGradePanel extends GUIPanel
/*    */ {
/*    */   private static final long serialVersionUID = -438674586864467004L;
/*    */   private JComboBox<?> letterGradeComboBox;
/*    */ 
/*    */   public FinalGradePanel(SystemController controller)
/*    */   {
/* 18 */     super(controller);
/* 19 */     addComponentsToPane();
/*    */   }
/*    */ 
/*    */   private void addComponentsToPane()
/*    */   {
/* 25 */     JPanel setGradePanel = new JPanel(new GridLayout(2, 2));
/* 26 */     createTitledBorder(setGradePanel, 
/* 27 */       this.controller.getActiveCourse().getCourseName());
/* 28 */     setGradePanel.add(new JLabel("Set final letter grade: "));
/*    */ 
/* 31 */     Object[] letterGrades = this.controller.getActiveSchool().getGradingScale()
/* 32 */       .getGradingScaleMap().keySet().toArray();
/* 33 */     Arrays.sort(letterGrades);
/* 34 */     this.letterGradeComboBox = new JComboBox(letterGrades);
/* 35 */     setGradePanel.add(this.letterGradeComboBox);
/* 36 */     this.letterGradeComboBox.setSelectedItem(this.controller.getActiveCourse()
/* 37 */       .getFinalGrade());
/*    */ 
/* 40 */     setGradePanel.add(new JLabel("Clear final grade: "));
/* 41 */     setGradePanel.add(createButton("clearFinalGrade", "Clear"));
/*    */ 
/* 44 */     JPanel navPanel = new JPanel(new GridLayout(2, 1));
/* 45 */     createTitledBorder(navPanel, "Navigation");
/*    */ 
/* 47 */     navPanel.add(createButton("done", "Done"));
/*    */ 
/* 49 */     navPanel.add(createButton("cancel", "Cancel"));
/*    */ 
/* 52 */     setLayout(new BoxLayout(this, 3));
/* 53 */     add(setGradePanel);
/* 54 */     add(navPanel);
/*    */   }
/*    */ 
/*    */   public void actionPerformed(ActionEvent e) {
/* 58 */     String action = e.getActionCommand();
/* 59 */     if (action.equals("clearFinalGrade")) {
/* 60 */       this.letterGradeComboBox.setSelectedIndex(-1);
/* 61 */     } else if (action.equals("done")) {
/* 62 */       this.controller.getActiveCourse().setFinalGrade(
/* 63 */         (String)this.letterGradeComboBox
/* 63 */         .getSelectedItem());
/* 64 */       this.controller.saveUserList();
/* 65 */       this.controller.getRootFrame()
/* 66 */         .addPanel(new CourseInfoPanel(this.controller), this);
/* 67 */     } else if (action.equals("cancel")) {
/* 68 */       this.controller.getRootFrame().showPanel("CourseInfoPanel", this);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Brandon\Development\git\gpacalc\bin\
 * Qualified Name:     com.hawkinbj.gpacalc.FinalGradePanel
 * JD-Core Version:    0.6.1
 */